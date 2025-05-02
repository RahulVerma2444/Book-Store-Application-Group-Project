/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.io.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.PasswordField;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author acobo
 */
public class CentralCore extends Application {
    
    public static User activeUser=null;
    public static Client activeClient1 = null;
    public static Client activeClient2 = null;
    protected static ObservableList<User> usersList = FXCollections.observableArrayList();
    protected static ObservableList<User> selectedUser = FXCollections.observableArrayList();
    protected static ObservableList<Book> booksList = FXCollections.observableArrayList();
   
    protected static ObservableList<Book> SelectBook = FXCollections.observableArrayList();
    protected static ObservableList<Book> books = null;
    
   
    @Override
    public void start(Stage primaryStage) {
        
        // Create a pane and set its properties
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(30, 10, 30, 30));
        pane.setHgap(10);
        pane.setVgap(5);
        
        Label welcomeTitle = new Label("Welcome to the BookStore App ");
        Label username = new Label("Username: ");
        TextField username_text_field = new TextField();
        Label password = new Label("Password: ");
        PasswordField password_text_field = new PasswordField();
        Button btn = new Button();
        btn.setText("Login");
        
        pane.add(welcomeTitle, 0, 0, 1, 1);
        pane.add(username, 0, 1, 1, 1);
        pane.add(username_text_field, 1, 1, 1, 1);
        pane.add(password, 0, 2, 1, 1);
        pane.add(password_text_field, 1, 2, 1, 1);
        pane.add(btn, 1, 3, 1, 1);
       
        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 500, 250);
        primaryStage.setTitle("Bookstore App"); // Title of the primary stage
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
                String username = username_text_field.getText();
                String password = password_text_field.getText();
                if(username.equals("admin")&&password.equals("admin")){
                    System.out.println("Owner logged in!");
                    ownerStartScreen(primaryStage); 
                }else if (login(username,password)){
                    System.out.println("Client logged in!");
                    clientStartScreen(primaryStage); 
                }else{
                    System.out.println("The username and password enterd is not correct! Try again!");
                    
                }
            }
        });
        
           
    }
    
    //Verify if login of a client is successful
    public static Boolean login(String username, String password){
        boolean verified = false;
        ObservableList<User> users = Owner.loadUsersFromFile();
        for (int i=0; i<users.size();i++){
            if(users.get(i).getUsername().equals(username)&& users.get(i).getPassword().equals(password)){
                activeUser = CentralCore.usersList.get(i);
                activeClient1 = new Client(activeUser.getUsername(),activeUser.getPassword(),activeUser.getPoints(), new Silver());
                System.out.println("Username: " + activeUser.getUsername());
                System.out.println("Password: " + activeUser.getPassword());
                verified = true;
                users.clear();
            }else{
                continue;
            }
            
        }
        users.clear();
        return verified;
    }
    
    
    //Owner Start Screen
    public void ownerStartScreen(Stage primaryStage){
        
        // Create a pane and set its properties
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(30, 10, 30, 30));
        pane.setHgap(10);
        pane.setVgap(5);
        // Create Books, Clients and Logout buttons
        Button Books = new Button("Books");
        Books.setMinSize(150,50);
        Button Clients = new Button("Clients");
        Clients.setMinSize(150,50);
        Button Logout = new Button("Logout");
        Logout.setMinSize(150,50);
        
        pane.add(Books, 0, 1, 1, 1);
        pane.add(Clients, 0, 2, 1, 1);
        pane.add(Logout, 0, 3, 1, 1);
        
        //handle the ActionEvent when button Books is clicked
        Books.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ownerBooks(primaryStage);
            }
        });
        
        //handle the ActionEvent when button Clients is clicked
        Clients.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ownerClients(primaryStage);
            }
        });
        
        //handle the ActionEvent when button Logout is clicked
        Logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                start(primaryStage);
            }
        });
        
        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 500, 250);
        primaryStage.setTitle("Bookstore App"); // Title of the primary stage
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
    
    //Owner Books Screen
    public void ownerBooks(Stage primaryStage){
        
        ObservableList<Book> books = Book.loadBooksFromFile();
        //Top Part of Owner Books Screen
        //Title column 
        TableColumn<Book, String> titleColumn = new TableColumn<>("Book Title");
        titleColumn.setMinWidth(360);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        //Price column
        TableColumn<Book, String> priceColumn = new TableColumn<>("Book Price");
        priceColumn.setMinWidth(90);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        // Create and populate table
        TableView<Book> booksTable = new TableView<>();
        booksTable.setItems(books);
        booksTable.getColumns().addAll(titleColumn,priceColumn);
        //Middle part of Owner Books Screen
        //Add title input
        TextField addTitle = new TextField();
        addTitle.setPromptText("Book Title");
        addTitle.setMinWidth(50);
        //Add price input
        TextField addPrice = new TextField();
        addPrice.setPromptText("Book Price");
        addPrice.setMinWidth(50);
        //Create button Add
        Button addBook = new Button("Add");
        addBook.setMinWidth(50);
        
        //Bottom part of Owner Books Screen
        //Create delete and back buttons
        //handle the ActionEvent when buttons Add,Delete or Back are clicked
        Button deleteBook = new Button("Delete");
        Button back = new Button("Back");
        //add book event handler
        addBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //do not accept books with negative price or 0
                if(Double.parseDouble(addPrice.getText())>0){
                    Book.writeToBookFile((String)addTitle.getText(),Double.parseDouble(addPrice.getText()));
                    System.out.println("The book with title: "+ (String)addTitle.getText()+
                    " and price: "+ Double.parseDouble(addPrice.getText())+" is added on the database");
                    addTitle.clear();
                    addPrice.clear();
                }else{
                    System.out.println("Try adding a positive or greater than 0 book price.");
                    addPrice.clear();
                }
                
            }
        });
        //delete book event handler
        deleteBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SelectBook = booksTable.getSelectionModel().getSelectedItems();
                System.out.println("The book with title: "+ SelectBook.get(0).getBookTitle()+
                " and price: "+SelectBook.get(0).getBookPrice()+" is deleted from the database");
                Book.deleteFromBookFile(SelectBook.get(0).getBookTitle(), SelectBook.get(0).getBookPrice());
                
            }
        });
        //back button event handler
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                booksTable.getItems().clear();
                ownerStartScreen(primaryStage);
            }
        });
        //create a horizontal layout component and add addTitle, addPrice, and addBook as its child nodes
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(addTitle,addPrice,addBook);
        hbox1.setSpacing(10);
        //create a horizontal layout component and add delete button, and back button as its child nodes
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(deleteBook, back);
        hbox2.setSpacing(300);
        //create a vertical layout component and add book table, hbox1, and hbox2 as its child nodes
        VBox vbox = new VBox();
        vbox.getChildren().addAll(booksTable,hbox1,hbox2);
        vbox.setPadding(new Insets(23,23,23,23));
        vbox.setSpacing(10);
        // Create a scene and place it in the stage
        Scene scene = new Scene(vbox,500,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //Owner Clients Screen
    public void ownerClients(Stage primaryStage){
        ObservableList<User> users = Owner.loadUsersFromFile();
        //Top Part of Owner Customers Screen
        //Username column
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(360);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        //Password column
        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(90);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        //Points column
        TableColumn<User, String> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setMinWidth(90);
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        // Create and populate table
        TableView<User> clientsTable = new TableView<>();
        clientsTable.setItems(users);
        clientsTable.getColumns().addAll(usernameColumn,passwordColumn,pointsColumn);
        //Middle part of Owner Customers Screen
        //Add username input
        TextField addUsername = new TextField();
        addUsername.setPromptText("Username");
        addUsername.setMinWidth(100);
        //Add password input
        TextField addPassword = new TextField();
        addPassword.setPromptText("Password");
        addPassword.setMinWidth(50);
        //Create button Add
        Button addClient = new Button("Add");
        addClient.setMinWidth(50);
        
        //Bottom part of Owner Books Screen
        //Create delete and back buttons
        //handle the ActionEvent when buttons Add,Delete or Back are clicked
        Button deleteClient = new Button("Delete");
        Button back = new Button("Back");
        
        //add client event handler
        addClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Owner.writeToUserFile((String)addUsername.getText(),(String)addPassword.getText(),0);
                System.out.println("The User with Username: "+ (String)addUsername.getText()+
                "password: "+(String)addPassword.getText()+" and 0 points is added on the database");
                addUsername.clear();
                addPassword.clear();
            }
        });
        //delete book event handler
        deleteClient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectedUser = clientsTable.getSelectionModel().getSelectedItems();
                System.out.println("The client with Username: "+ selectedUser.get(0).getUsername()+
                "  ,password: "+selectedUser.get(0).getPassword()+" and points: "+selectedUser.get(0).getPoints()+" is deleted from the database");
                Owner.deleteFromUserFile(selectedUser.get(0).getUsername(), selectedUser.get(0).getPassword(),selectedUser.get(0).getPoints());
                
            }
        });
        //back button event handler
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                users.clear();
                clientsTable.getItems().clear();
                ownerStartScreen(primaryStage);
            }
        });
        //create a horizontal layout component and add addTitle, addPrice, and addBook as its child nodes
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(addUsername,addPassword,addClient);
        hbox1.setSpacing(60);
        //create a horizontal layout component and add delete button, and back button as its child nodes
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(deleteClient, back);
        hbox2.setSpacing(400);
        //create a vertical layout component and add book table, hbox1, and hbox2 as its child nodes
        VBox vbox = new VBox();
        vbox.getChildren().addAll(clientsTable,hbox1,hbox2);
        vbox.setPadding(new Insets(23,23,23,23));
        vbox.setSpacing(10);
        // Create a scene and place it in the stage
        Scene scene = new Scene(vbox,590,500);
        primaryStage.setScene(scene);
        primaryStage.show();
           
    }
     
    //Client Start Screen
    public void clientStartScreen(Stage primaryStage){
        books = Book.loadBooksFromFile();
        //top part of the client start screen
        //Client activeClient = new Client(activeUser.getUsername(),activeUser.getPassword(),activeUser.getPoints(), new Silver());
        Text title = new Text("Welcome "+ activeUser.getUsername()+". You have "+
                activeUser.getPoints()+" points. Your status is "+activeClient1.checkStatus()+".");
        
        //middle part of the client start screen
        //Title column 
        TableColumn<Book, String> titleColumn = new TableColumn<>("Book Title");
        titleColumn.setMinWidth(360);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        //Price column
        TableColumn<Book, String> priceColumn = new TableColumn<>("Book Price");
        priceColumn.setMinWidth(90);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        //Check Box column
        TableColumn<Book, String> checkBoxColumn = new TableColumn<>("Select");
        checkBoxColumn.setMinWidth(90);
        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("check"));
        // Create and populate table
        TableView<Book> booksTable = new TableView<>();
        booksTable.setItems(books);
        booksTable.getColumns().addAll(titleColumn,priceColumn,checkBoxColumn);
        
        //Bottom part of the client start screen
        //Create Buy, Redeem points and buy, and Logout buttons
        //handle the ActionEvent when buttons Buy, Redeem points and buy or Logout are clicked
        Button buy = new Button("Buy");
        buy.setMinWidth(50);
        Button redeemPointsBuy = new Button("Redeem points and buy");
        redeemPointsBuy.setMinWidth(70);
        Button logout = new Button("Logout");
        logout.setMinWidth(50);
        
        //Buy books event handler
        buy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            int oldPoints = activeUser.getPoints(); 
            int newPoints = 0;
            booksList = booksTable.getItems(); 
            SelectBook = Book.selectedBooks(booksList);
            
            double transactionCost = activeUser.buy(SelectBook);
            newPoints = activeUser.getPoints();
            Client activeClient2 = new Client(activeUser.getUsername(),activeUser.getPassword(),newPoints, new Silver());
            
            
            clientCostScreen(primaryStage,transactionCost,activeUser.getPoints(),activeClient2.checkStatus(),books);
            Owner.updatePoints(activeUser.getUsername(), activeUser.getPassword(), oldPoints, newPoints);
            //delete purchased books from data base
            for(int i=0;i<SelectBook.size();i++){
                Book.deleteFromBookFile(SelectBook.get(i).getBookTitle(),SelectBook.get(i).getBookPrice());
            }
            }
        });
        
        // Redeem points and buy event handler
        redeemPointsBuy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            int oldPoints = activeUser.getPoints();
            int newPoints = 0;
            booksList = booksTable.getItems(); 
            SelectBook = Book.selectedBooks(booksList);
            
            double transactionCost = activeUser.redeemBuy(SelectBook);
            newPoints = activeUser.getPoints();
            Client activeClient2 = new Client(activeUser.getUsername(),activeUser.getPassword(),newPoints, new Silver());
            
            clientCostScreen(primaryStage,transactionCost,activeUser.getPoints(),activeClient2.checkStatus(),books);
            Owner.updatePoints(activeUser.getUsername(), activeUser.getPassword(), oldPoints, activeUser.getPoints());
            //delete purchased books from data base
            for(int i=0;i<SelectBook.size();i++){
                Book.deleteFromBookFile(SelectBook.get(i).getBookTitle(),SelectBook.get(i).getBookPrice());
            }
            }
        });
        
        //handle the ActionEvent when button Logout is clicked
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                booksTable.getItems().clear();
                start(primaryStage);
            }
        });
        
       //create a horizontal layout component and add Buy, redeemPointsBuy, and logout as its child nodes
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(buy,redeemPointsBuy,logout);
        hbox1.setSpacing(125);
        //create a vertical layout component and add title, books table and hbox1 as its child nodes
        VBox vbox = new VBox();
        vbox.getChildren().addAll(title,booksTable,hbox1);
        vbox.setPadding(new Insets(23,23,23,23));
        vbox.setSpacing(10);
        // Create a scene and place it in the stage
        Scene scene = new Scene(vbox,590,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //Client Cost Screen
    public void clientCostScreen(Stage primaryStage,double totalCost, int points, String status,ObservableList<Book>books){
        
        String tc = String.format("%.2f",totalCost);
        Text transactionCost = new Text("Total Cost: "+tc+"$");
        Text pointsStatus = new Text("Points: "+points+", Status: "+status);
        //create logout button and handle event when logout button is clicked
        Button logout = new Button("Logout");
        
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                books.clear();
                start(primaryStage);
            }
        });
        
        
        //create a vertical layout component and add transactioCost, pointsStatus and logout button as its child nodes
        VBox vbox = new VBox();
        vbox.getChildren().addAll(transactionCost,pointsStatus,logout);
        vbox.setPadding(new Insets(23,23,23,23));
        vbox.setSpacing(30);
        // Create a scene and place it in the stage
        Scene scene = new Scene(vbox,590,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }
    
}
