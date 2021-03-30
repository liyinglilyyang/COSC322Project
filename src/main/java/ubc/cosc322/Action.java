package ubc.cosc322;

import java.util.ArrayList;

public class Action {
    //this representation of action 
    Coor or,de;
    int step, direction;
    public Action(Coor or, Coor de){
        this.or = or;
        this.de = de;
    }

    public Action(Coor or, int step, int direciton){
        this.or = or;
        int xd,yd;
    }

    public Coor getDe(){
        return de;
    }

    public Coor getOr(){
        return or;
    }

    // public ArrayList<Action> getActionList(){
    
    // }
}
