package finalproject;

import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;

public class Book {
    //instance variables
    private String bookTitle;
    private double bookPrice;
    private CheckBox check;

    //constructor
    public Book(String bookTitle, double bookPrice){
        this.bookTitle=bookTitle;
        this.bookPrice = bookPrice;
        check=new CheckBox();
    }
    //getters and setters
    public String getBookTitle(){
        return bookTitle;
    }
    public void setBookTitle(String bookTitle){
        this.bookTitle = bookTitle;
    }
    public double getBookPrice(){
        return bookPrice;
    }
    public void setBookPrice(double bookPrice){
        this.bookPrice=bookPrice;
    }
    public CheckBox getCheck(){
        return check;
    }
    public void setCheck(CheckBox check){
        this.check=check;
    }
    // create a list with selected books for purchase
    public static ObservableList<Book> selectedBooks(ObservableList<Book> booksList) {
        ObservableList<Book> booksSelected = FXCollections.observableArrayList();
        for (int i = 0; i < booksList.size(); i++) {
            if (booksList.get(i).getCheck().isSelected()) {
                booksSelected.add(booksList.get(i));
            }
        }
        return booksSelected;
    }
    //read books from file
    public static ObservableList<Book> loadBooksFromFile(){
        try{
            File inputFile = new File("books.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String currentLine = reader.readLine();
            while (currentLine!=null){
                String data[] = currentLine.split("/");
                String bookTitle = data[0];
                double bookPrice = Double.parseDouble(data[1]);

                CentralCore.booksList.add(new Book(bookTitle,bookPrice));
                currentLine = reader.readLine();
            }
            reader.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return CentralCore.booksList;
    }
    //add a book to books.txt
    public static void writeToBookFile(String bookTitle, double bookPrice){
        CentralCore.booksList.add(new Book(bookTitle,bookPrice));
        File bookFile = new File("books.txt");
        try{
            BufferedReader rw = new BufferedReader(new FileReader(bookFile));
            BufferedWriter fw = new BufferedWriter( new FileWriter(bookFile, true));
            fw.write(bookTitle+"/"+bookPrice+"\n");
            fw.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    //delete a book to books.txt
    public static void deleteFromBookFile(String bookTitle, double bookPrice){
        List<String> bookContents = new ArrayList<String>();
        String fileLine;
        //remove a book from booksList
        for (int i=0; i<CentralCore.booksList.size();i++){
            if(CentralCore.booksList.get(i).getBookTitle().equals(bookTitle)&&CentralCore.booksList.get(i).getBookPrice()==(bookPrice)){
                CentralCore.booksList.remove(i);
            }
        }

        //remove book data from file
        File bookFile = new File("books.txt");
        try{
            BufferedReader rw = new BufferedReader(new FileReader(bookFile));
            BufferedWriter fw = new BufferedWriter( new FileWriter(bookFile, true));
            PrintWriter pw;
            while((fileLine = rw.readLine()) != null){
                String data[] = fileLine.split("/");
                String booktitle = data[0];
                Double bookprice = Double.parseDouble(data[1]);

                if(!(booktitle.equals(bookTitle)&&bookprice==bookPrice)){
                    bookContents.add(booktitle+"/"+bookprice+"\n");
                }
            }
            pw = new PrintWriter(bookFile);

            for(int i = 0; i < bookContents.size(); i++){

                // System.out.println(userContents.get(i));
                pw.append(bookContents.get(i));
            }
            rw.close();
            fw.close();
            pw.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

}
