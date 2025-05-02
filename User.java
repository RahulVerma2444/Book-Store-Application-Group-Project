package finalproject;

import javafx.collections.ObservableList;

public class User {
    //instance variables
    protected String username;
    protected String password;
    protected int points;
    //constructor
    public User(String username,String password, int points){
        this.username = username;
        this.password = password;
        this.points = points;
    }

    // buy
    public double buy(ObservableList<Book>booksSelected){
        double transactionCost=0;
        for (int i=0; i<booksSelected.size();i++){
            transactionCost = transactionCost + booksSelected.get(i).getBookPrice();
        }
        this.points = this.points + (int)(transactionCost * 10);

        return transactionCost ;
    }

    //redeem and buy
    public double redeemBuy(ObservableList<Book>booksSelected){
        double transactionCost=0;
        for (int i=0; i<booksSelected.size();i++){
            transactionCost = transactionCost + booksSelected.get(i).getBookPrice();
        }
        if(this.points > (int)(transactionCost*100) ){
            this.points = this.points - (int)(transactionCost*100);
            transactionCost = 0;
        }
        else {
            transactionCost = transactionCost - (this.points / 100);
            this.points = this.points % 100;
            this.points = this.points + (int)(transactionCost * 10);
        }
        return transactionCost;
    }
    //getters and setters
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public int getPoints(){
        return points;
    }

    public void setPoints(int p){
        this.points=p;
    }




}
