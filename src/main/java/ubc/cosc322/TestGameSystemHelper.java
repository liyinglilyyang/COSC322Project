package ubc.cosc322;

import java.util.ArrayList;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;

public class TestGameSystemHelper {
    NewState s;
    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
    public TestGameSystemHelper(NewState s, GameClient gameClient,BaseGameGUI gamegui){
        this.s = s;
        this.gameClient = gameClient;
        this.gamegui = gamegui;
    }
    
    public void updateAction(Coor op, Coor np, Coor ap){
		System.out.println("We are now updating actions");//we update state object information
		ErrorChecker ec = new ErrorChecker(op, np, ap);
		if(ec.updateValid()){
			s.updateState(op, np);//move Amazons to new position
			s.setCoor(ap,'A');//update Arrow on board
			System.out.println(s);//print state
			//now we prepare information to send to the server
			ArrayList <Integer> moveQueenOri = new ArrayList <Integer>();//{{op.getX();op.getY();}}
			ArrayList <Integer> moveQueenNew = new ArrayList <Integer>();//{{np.getX();np.getY();}}
			ArrayList <Integer> movearrowNew = new ArrayList <Integer>();//{{ap.getX();ap.getY();}}
			//update y
			moveQueenOri.add(op.getY());
			moveQueenNew.add(np.getY());
			movearrowNew.add(ap.getY());
			//update x
			moveQueenOri.add(op.getX());
			moveQueenNew.add(np.getX());
			movearrowNew.add(ap.getX());
			gCl.sendMoveMessage(moveQueenOri, moveQueenNew, movearrowNew);//send message to game client
			GUI.updateGameState(moveQueenOri, moveQueenNew, movearrowNew);//update gui
		}else{
			System.out.println("Error! Handle From Here");
		}

	}
}
