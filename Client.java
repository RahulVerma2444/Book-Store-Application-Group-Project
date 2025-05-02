package finalproject;

import javafx.collections.ObservableList;

public class Client extends User {
    //instance variables
    protected State myState;
    protected String currentStatus = null;

    //constructor
    public Client(String username,String password,int points, State myState){
        super(username, password,points);
        this.myState = myState;
    }
//    public static ObservableList<Book> selectedBooks(ObservableList<Book> booksList) {
//        for (int i = 0; i < booksList.size(); i++) {
//            if (booksList.get(i).getCheck().isSelected()) {
//                CentralCore.booksSelected.add(booksList.get(i));
//            }
//        }
//        return CentralCore.booksSelected;
//    }
    public State getState(){
        return myState;
    }
    public void setState(State s){
        myState = s;
    }
    public void setStatus (String status){
        currentStatus = status;
    }
    public  String getStatus (){
        return currentStatus;
    }

    public String checkStatus(){
        myState.checkStatus(this);
        return this.getStatus();
    }

}
