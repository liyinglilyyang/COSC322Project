package ubc.cosc322;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;

public class TimerTest {
    // Timer timer;
    // NewState s;
    // GameClient gc;
    // BaseGameGUI bgg;
    // char playerType;

    // public TimerTest(int seconds, NewState s, GameClient gc ,BaseGameGUI bgg,char playerType) {
    //     this.s = s;
    //     this.gc = gc;
    //     this.bgg = bgg;
    //     this.playerType = playerType;
    //     timer = new Timer();
    //     timer.schedule(new RemindTask(), seconds*1000);
	// }

    // class RemindTask extends TimerTask {
    //     public void run() {
    //         System.out.println("Time's up!");
    //         makeEmerMove();
    //         timer.cancel(); //Terminate the timer thread
    //     }
    // }
    // public void makeEmerMove() {
    //     // System.out.println("Emergency Move!");
    //     alphaBetaPruning(new MiniMax(s,playerType,2));
	// 	// int availableSpace  = s.getState('N').size();
	// 	// if(availableSpace> 70){
	// 	// 	System.out.println("Early");
	// 	// 	alphaBetaPruning(new MiniMax(s,playerType,2));
	// 	// }else if(availableSpace>50){
	// 	// 	System.out.println("Stage 50");
	// 	// 	alphaBetaPruning(new MiniMax(s,playerType,3));
	// 	// }else if(availableSpace>30){
	// 	// 	System.out.println("Stage 30");
	// 	// 	alphaBetaPruning(new MiniMax(s,playerType,5));
	// 	// }else if(availableSpace>25){
	// 	// 	System.out.println("Stage 20");
	// 	// 	alphaBetaPruning(new MiniMax(s,playerType,12));
	// 	// }else{
	// 	// 	System.out.println("End Game");
	// 	// 	//------------------------------
	// 	// 	alphaBetaPruning(new MiniMax(s,playerType,50));
	// 	// 	//------------------------------
	// 	// }

	// 	// updateAction(decide.getOr(), decide.getDe(), ArrowP.get(j).getDe());
	// }
    // public void alphaBetaPruning(MiniMax helper){
    //     Action bestAction = helper.alphaBetaSearch();
    //     System.out.println("Action is: " + bestAction + "Now we find arrow");
    //     Coor arrowPosition = helper.findArrowWithUtility(bestAction,helper.getMaxArrow(bestAction,playerType)).getDe();
    //     updateAction(bestAction.getOr(), bestAction.getDe(), arrowPosition);
    // }

    // public void updateAction(Coor op, Coor np, Coor ap){
	// 	System.out.println("We are now updating actions");//we update state object information
	// 	ErrorChecker ec = new ErrorChecker(op, np, ap);
	// 	if(ec.updateValid()){
	// 		s.updateState(op, np);//move Amazons to new position
	// 		s.setCoor(ap,'A');//update Arrow on board
	// 		System.out.println(s);//print state
	// 		//now we prepare information to send to the server
	// 		ArrayList <Integer> moveQueenOri = new ArrayList <Integer>();//{{op.getX();op.getY();}}
	// 		ArrayList <Integer> moveQueenNew = new ArrayList <Integer>();//{{np.getX();np.getY();}}
	// 		ArrayList <Integer> movearrowNew = new ArrayList <Integer>();//{{ap.getX();ap.getY();}}
	// 		//update y
	// 		moveQueenOri.add(op.getY());
	// 		moveQueenNew.add(np.getY());
	// 		movearrowNew.add(ap.getY());
	// 		//update x
	// 		moveQueenOri.add(op.getX());
	// 		moveQueenNew.add(np.getX());
	// 		movearrowNew.add(ap.getX());
	// 		gc.sendMoveMessage(moveQueenOri, moveQueenNew, movearrowNew);//send message to game client
	// 		bgg.updateGameState(moveQueenOri, moveQueenNew, movearrowNew);//update gui
	// 	}else{
	// 		System.out.println("Error! Handle From Here");
	// 	}

	// }
}
