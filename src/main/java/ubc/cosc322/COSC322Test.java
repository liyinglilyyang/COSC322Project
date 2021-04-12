
package ubc.cosc322;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;
import ygraph.ai.smartfox.games.amazons.HumanPlayer;

/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	
    private String userName = null;
    private String passwd = null;
    

    // implemented Var
    // playerType
	public char playerType = 'B';
	
	// set State
	// private State s = new State();
	private NewState s = new NewState();

	// actionlist 
	private int[][] actionList ={{-1, 0},{-1, -1},{-1, 1},{0, -1},{0, 1},{1, 0},{1, -1},{1, 1}};	
	
	// counter for steps
	private int counter = 1;
	
	// gameBoard
	ArrayList<Integer> currentGameBoard = new ArrayList<Integer>();
	
	// utility
	int utility = 0;
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test("ame", "ame");
    	//team09Player player = new team09Player(args[0], args[1]);
    	//HumanPlayer player = new HumanPlayer();
    	
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
    	}
    	
    	// 
    }
	
    /**
     * Any name and passwd 
     * @param userName
      * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	
    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	this.gamegui = new BaseGameGUI(this);
    }

    @Override
    public void onLogin() {
    	System.out.println("Congratualations!!! Login successfully");
    	this.userName = this.gameClient.getUserName();
    	if(this.gamegui != null) {
    		this.gamegui.setRoomInformation(this.gameClient.getRoomList());
    	}
    	

    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	//This method will be called by the GameClient when it receives a game-related message
    	//from the server.
	
    	//For a detailed description of the message types and format, 
    	//see the method GamePlayer.handleGameMessage() in the game-client-api document. 
    	
    	// get and set the game state after each move
    	
    	//System.out.println((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
    	
    	
    	
    	// GameMessage.GAME_STATE_BOARD
    	if (messageType.equals(AmazonsGameMessage.GAME_STATE_BOARD)) {
    		ArrayList<Integer> gameBoardState = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE);
    		this.gamegui.setGameState(gameBoardState);
			//the above coede is by default
			currentGameBoard = gameBoardState;
			System.out.println("****We are updating game board****");
    	// GameMessage.GAME_ACTION_START
    	}else if (messageType.equals(AmazonsGameMessage.GAME_ACTION_START)) {
    		// set player type 
    		// ++this.counter;
    		if (this.userName.equals((String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK))) {
        		this.playerType = 'B';
        		// this.runTimeTask(this.playerType);
				//System.out.println("Test Eme Move");
				makeMove(playerType);
        		System.out.println("We are black queens");
        	}else if (this.userName.equals((String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE))){
        		this.playerType = 'W';
        		System.out.println("We are white queens");
        	}
    	
    	// GameMessage.GAME_ACTION_MOVE
    	}else if (messageType.equals(AmazonsGameMessage.GAME_ACTION_MOVE)){
    		// this.gamegui.updateGameState(msgDetails);
    		ArrayList <Integer> QueenOri = (ArrayList <Integer>) msgDetails.get((AmazonsGameMessage.QUEEN_POS_CURR));
    		ArrayList <Integer> QueenNew = (ArrayList <Integer>) msgDetails.get((AmazonsGameMessage.Queen_POS_NEXT));
    		ArrayList <Integer> arrowNew = (ArrayList <Integer>) msgDetails.get((AmazonsGameMessage.ARROW_POS));
    		gamegui.updateGameState(QueenOri,QueenNew,arrowNew);
			//newly added 
			// updateBoard(QueenOri, QueenNew, arrowNew);
			System.out.println("Start Run---------------------------------------------");
			
			long startTime=System.currentTimeMillis();
    		Coor Ori_Position = new Coor(QueenOri.get(1), QueenOri.get(0));
    		Coor New_Position = new Coor(QueenNew.get(1), QueenNew.get(0));
    		Coor Arrow_Position = new Coor(arrowNew.get(1), arrowNew.get(0),'A');
    		System.out.println(Ori_Position);
			System.out.println(New_Position);
			System.out.println(Arrow_Position);
    	}
    	
    	return true;   	
    }
    
    
    @Override
    public String userName() {
    	return this.userName;
    }

	@Override
	public GameClient getGameClient() {
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		return  this.gamegui;
	}

	@Override
	public void connect() {
    	gameClient = new GameClient(userName, passwd, this);			
	}

	//----------------------implementation func--------------------------------------
	// run time task
	// public void runTimeTask(char playerType) {
	// 	TimerTask task = new TimerTask() {
	//         public void run() {      	
	//             System.out.println("Time almost running out, perform an emerent move.");
	//             // makeEmerMove(playerType);
	//         }
	//     };
	    
	// 	Timer timer = new Timer("Timer");
	    
	//     long delay = 1000L;// second * 1000
	//     timer.schedule(task, delay);
	//     // showmsg(timer);// minimax goes here with one more para timer
	    
	// }

	// make decision and send move message
	public void makeMove(char playerType) {
		// minimax = alphaBetaSearch();

		// TimerTest tT = new TimerTest(25, s, this.getGameClient(), this.getGameGUI(), playerType);

		// makeAction();
		// makeEmerMove(playerType);

		int availableSpace  = s.getState('N').size();
		if(availableSpace> 70){
			System.out.println("Early");
			alphaBetaPruning(new MiniMax(s,playerType,2));
		}else if(availableSpace>45){
			System.out.println("Stage 45");
			alphaBetaPruning(new MiniMax(s,playerType,3));
		}else if(availableSpace>30){
			System.out.println("Stage 30");
			alphaBetaPruning(new MiniMax(s,playerType,4));
		}else if(availableSpace>25){
			System.out.println("Stage 20");
			alphaBetaPruning(new MiniMax(s,playerType,10));
		}else{
			System.out.println("End Game");
			alphaBetaPruning(new MiniMax(s,playerType,12));
		}

		// tT.timer.cancel();
	}

	public void alphaBetaPruning(MiniMax helper){
        Action bestAction = helper.alphaBetaSearch();
        System.out.println("Action is: " + bestAction + "Now we find arrow");
        Coor arrowPosition = helper.findArrowWithUtility(bestAction,helper.getMaxArrow(bestAction,playerType)).getDe();
        updateAction(bestAction.getOr(), bestAction.getDe(), arrowPosition);
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
			gameClient.sendMoveMessage(moveQueenOri, moveQueenNew, movearrowNew);//send message to game client
			getGameGUI().updateGameState(moveQueenOri, moveQueenNew, movearrowNew);//update gui
		}else{
			System.out.println("Error! Handle From Here");
		}

	}

}//end of class
