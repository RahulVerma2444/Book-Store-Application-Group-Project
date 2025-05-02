package finalproject;

import javafx.collections.ObservableList;

public class Silver extends State {

    @Override
    public String checkStatus(Client c) {
        if (c.getPoints()>=1000){
            c.setState(new Gold());
            c.setStatus("Gold");
        }
        else if (c.getPoints() >=0 && c.getPoints()<1000){
            c.setState(new Silver());
            c.setStatus("SILVER");
        }
        return c.getStatus();
    }
}
