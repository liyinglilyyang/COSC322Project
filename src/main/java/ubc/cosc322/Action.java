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
        int[][] actionList ={{-1, 0},{-1, -1},{-1, 1},{0, -1},{0, 1},{1, 0},{1, -1},{1, 1}};	
        this.or = or;
        int xd = or.getX()+actionList[direciton][0]*step;
        int yd = or.getY()+actionList[direciton][1]*step;
        this.de = new Coor(xd, yd);
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
