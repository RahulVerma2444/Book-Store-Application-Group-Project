package finalproject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

public class Owner extends User {
    //constructor
    public Owner(String username, String password, int points){
        super(username, password, points);
    }
    //read users from file
    public static ObservableList<User> loadUsersFromFile(){
        try{
            File inputFile = new File("users.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String currentLine = reader.readLine();
            while (currentLine!=null){
                String data[] = currentLine.split("/");
                String username = data[0];
                String password = data[1];
                int points = Integer.parseInt(data[2]);

                CentralCore.usersList.add(new User(username,password,points));
                currentLine = reader.readLine();
            }
            reader.close();
        } catch (Exception e){
            System.out.println(e.getMessage()+" here");
        }
        return CentralCore.usersList;
    }
    //add a user to user.txt
    public static void writeToUserFile(String username, String password, int points){
        CentralCore.usersList.add(new User(username,password,points));
        File userFile = new File("users.txt");
        try{
            BufferedReader rw = new BufferedReader(new FileReader(userFile));
            BufferedWriter fw = new BufferedWriter( new FileWriter(userFile, true));
            fw.write(username+"/"+password+"/"+points+"\n");
            fw.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    //delete a user form user.txt
    public static void deleteFromUserFile(String username, String password, int points){
        List<String> userContents = new ArrayList<String>();
        String fileLine="";
        //remove user from usersList
        for (int i=0; i<CentralCore.usersList.size();i++){
            if(CentralCore.usersList.get(i).getUsername().equals(username)&&CentralCore.usersList.get(i).getPassword().equals(password)&&CentralCore.usersList.get(i).getPoints()==points){
                CentralCore.usersList.remove(i);
            }
        }

        //remove user data from file
        File userFile = new File("users.txt");
        try{
            BufferedReader rw = new BufferedReader(new FileReader(userFile));
            BufferedWriter fw = new BufferedWriter( new FileWriter(userFile, true));
            PrintWriter pw;
            while((fileLine = rw.readLine()) != null){
                String data[] = fileLine.split("/");
                String userName = data[0];
                String pasSword = data[1];
                int pointss = Integer.parseInt(data[2]);
                if(!(userName.equals(username)&&pasSword.equals(password)&&(pointss==points))){
                    userContents.add(userName+"/"+pasSword+"/"+pointss+"\n");
                }
            }
            pw = new PrintWriter(userFile);

            for(int i = 0; i < userContents.size(); i++){

                // System.out.println(userContents.get(i));
                pw.append(userContents.get(i));

            }
            rw.close();
            fw.close();
            pw.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage()+"ketu");
        }
    }
    //modify points of a user in user.txt
    public static void updatePoints(String username, String password,int oldPoints, int newPoints){
        List<String> userContents = new ArrayList<String>();
        String fileLine;

        File userFile = new File("users.txt");
        try{
            BufferedReader rw = new BufferedReader(new FileReader(userFile));
            BufferedWriter fw = new BufferedWriter( new FileWriter(userFile, true));
            PrintWriter pw;
            while((fileLine = rw.readLine()) != null){
                String data[] = fileLine.split("/");
                String userName = data[0];
                String pasSword = data[1];
                int pointss = Integer.parseInt(data[2]);
                if((userName.equals(username)&&pasSword.equals(password)&&(pointss==oldPoints))){
                    userContents.add(userName+"/"+pasSword+"/"+newPoints+"\n");
                    for(int i=0;i<CentralCore.usersList.size();i++){
                        if(CentralCore.usersList.get(i).getUsername().equals(username)&&CentralCore.usersList.get(i).getPassword().equals(password)){
                            CentralCore.usersList.get(i).setPoints(newPoints);
                        }
                    }

                }else{
                    userContents.add(fileLine+"\n");
                }
            }
            pw = new PrintWriter(userFile);

            for(int i = 0; i < userContents.size(); i++){
                 pw.append(userContents.get(i));
                 System.out.println("\n");
            }
            rw.close();
            fw.close();
            pw.close();
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

    }



}
