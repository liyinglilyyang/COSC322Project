
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
    	COSC322Test player = new COSC322Test("IRIDIX", "TestSP");
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
    	if (messageType.equals(GameMessage.GAME_STATE_BOARD)) {
    		ArrayList<Integer> gameBoardState = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE);
    		this.gamegui.setGameState(gameBoardState);
			currentGameBoard = gameBoardState;
			System.out.println("****We are updating game board****");
    	// GameMessage.GAME_ACTION_START
    	}else if (messageType.equals(GameMessage.GAME_ACTION_START)) {
    		// set player type 
    		// ++this.counter;
    		if (this.userName.equals((String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK))) {
        		this.playerType = 'B';
				System.out.println("Test Eme Move");
        		this.makeEmerMove(this.playerType);
        		System.out.println("We are black queens");
        		
        		//calMinDis(this.s.getState(playerType), this.playerType, this.counter);
        		System.out.println(showMinDisBoard(this.s.getState(), this.playerType));//print out mindis board
        		// this.counter++;
        	}else if (this.userName.equals((String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE))){
        		this.playerType = 'W';
        		//this.makeEmerMove(this.playerType);
        		System.out.println("We are white queens");
   
        	}
    	
    	// GameMessage.GAME_ACTION_MOVE
    	}else if (messageType.equals(GameMessage.GAME_ACTION_MOVE)){
    		// this.gamegui.updateGameState(msgDetails);
    		ArrayList <Integer> QueenOri = (ArrayList <Integer>) msgDetails.get((AmazonsGameMessage.QUEEN_POS_CURR));
    		ArrayList <Integer> QueenNew = (ArrayList <Integer>) msgDetails.get((AmazonsGameMessage.Queen_POS_NEXT));
    		ArrayList <Integer> arrowNew = (ArrayList <Integer>) msgDetails.get((AmazonsGameMessage.ARROW_POS));
    		gamegui.updateGameState(QueenOri,QueenNew,arrowNew);

			System.out.println("Start Run---------------------------------------------");
    		Coor Ori_Position = new Coor(QueenOri.get(1), QueenOri.get(0));
    		Coor New_Position = new Coor(QueenNew.get(1), QueenNew.get(0));
    		Coor Arrow_Position = new Coor(arrowNew.get(1), arrowNew.get(0),'A');
    		System.out.println(Ori_Position);
			System.out.println(New_Position);
			System.out.println(Arrow_Position);

    		// update state and arrowboard
    		this.s.updateState(Ori_Position, New_Position);
    		this.s.setCoor(Arrow_Position);
    		
    		calMinDis(this.s.getState(playerType), this.playerType, this.counter);
    		System.out.println(showMinDisBoard(this.s.getState(), this.playerType));//print out mindis board
    		//System.out.println("oripost is " + Ori_Position.getCoor()[0]);
    		// ++this.counter;
    		makeEmerMove(playerType);
    		
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
	public void runTimeTask(char playerTypeTest) {
		TimerTask task = new TimerTask() {
	        public void run() {
	        	
	        	// alogrithm runs here
	    	    // alphaBeta
	        	
	            //System.out.println("Time almost running out, perform a quick move.");
	            makeEmerMove(playerTypeTest);
	        }
	    };
	    
	    
	    
	    Timer timer = new Timer("Timer");
	    
	    long delay = 2800L;
	    timer.schedule(task, delay);
	}
	
	
	// make decision and send move message
	public void makeMove(char playerType) {
		// minimax = alphaBetaSearch();
		
		// makeAction();
	}
	
	
	// make emergent move
	public void makeEmerMove(char playerType) {
		
		ArrayList<Coor> queens = s.getState(playerType);
		for(Coor currentQueen : queens){//the first queen
			for(int testDirection = 0; testDirection<7; testDirection++){//test all directions
				int testStep = 1;//we always try step one
				//notice that if 1 is impossible, then we do not need to proceed either
				if(makeAction(currentQueen,testStep,testDirection)){
					System.out.println("****Player Type: " + playerType);
					System.out.println("****CurrentObject: " + currentQueen.getX() + "," + currentQueen.getY() );
					int[] currentD = actionList[testDirection];
					System.out.println("****Test Direction"+ currentD.toString() +"****Test Step"+testStep);
					return;
				}
					
			}
		}
	}
	

	
	// calculate min-distance  function
	public void calMinDis(ArrayList<Coor> queens, char playerType, int step) {
		if(step == 1) {//start with four queens we have on board
			queens = s.getState(playerType);
		}
			
		for(Coor currentQueen : queens) {
			for(int testDirection = 0; testDirection<7; testDirection++){//for each direction at each step
				ArrayList<Coor> tmpQueue = new ArrayList<Coor>();// crate a tmp array for storing step ith movable tile at one direction
				int count = 1;
				while (hasValidAction(currentQueen, count, testDirection)){//same direction, go as far as possible by increasing count
					int[] currentAction = actionList[testDirection];//store each blank tile with steps value
					int targetX = currentQueen.getX()+currentAction[0];
					int targetY = currentQueen.getY()+currentAction[1];
					Coor targetQueen = new Coor(targetX, targetY);
					tmpQueue.add(targetQueen);//used for recursive call go further step
						
					if (playerType == 'B' && step<targetQueen.getBlackDis()) {//only update for a shorter distance
						targetQueen.setBlackDis(step);
					}else if(playerType == 'W' && step<targetQueen.getWhiteDis()) {
						targetQueen.setWhiteDis(step);
					}
					count++;	
				}
				calMinDis(tmpQueue, playerType, step++);
					
			}
		}
			
	}
//	// calculate min-distance
//	public void calMinDis(char playerType) {
//		ArrayList<Coor> queens = s.getState(playerType);
//		for(Coor currentQueen : queens){//iterate through each queen
//			
//			helper_calMinDis(currentQueen, playerType);//call the recursive helper function	
//			this.counter = 0;//reset the global counter(step number) to 0
//		}
//	}
	
	// calculate min-distance helper function
//	public void helper_calMinDis(ArrayList<Coor> queens, char playerType, int step) {
//		for(Coor currentQueen : queens) {
//			while(!isVisitedAll(currentQueen)) {//while current queen still has neighbors 
//				for(int testDirection = 0; testDirection<7; testDirection++){//for each direction at each step
//					ArrayList<Coor> tmpQueue = new ArrayList<Coor>();// crate a tmp array for storing step ith movable tile at one direciton
//					
//					if (hasValidAction(currentQueen, this.counter, testDirection)){
//						int[] currentAction = actionList[testDirection];//store each blank tile with steps value
//						int targetX = currentQueen.getX()+currentAction[0];
//						int targetY = currentQueen.getY()+currentAction[1];
//						Coor targetQueen = new Coor(targetX, targetY);
//						
//						if (playerType == 'B') {//update step value 
//							targetQueen.setBlackDis(counter);
//						}else if(playerType == 'W') {
//							targetQueen.setWhiteDis(counter);
//						}
//						
//						tmpQueue.add(targetQueen);
//						//helper_calMinDis(targetQueen, playerType);//recursive call
//					}else {
////						queens.remove(currentQueen);//remove the queen from the queue
////						helper_helper_calMinDis
//					}
//					
//				}
//			}
//		}
//		
//	}
	
	//showing MinDis board (debug usage)
	 public String showMinDisBoard(Coor[][] gameBoard, char playerType){
	        String r = "";
	        for(int yi = 10; yi >=1; yi--){
	            for(int xi = 1; xi <=10; xi++){
	                if(playerType == 'B') {
	                	r += gameBoard[xi][yi].getBlackDis() + ", ";  
	                }else if (playerType == 'W') {
	                	r += gameBoard[xi][yi].getWhiteDis() + ", "; 
	                }
	            }
	            r+="\n";
	        }
	        return r;
	                
	 }
	
	// calculate heuristic value
	public void calHeuristic(NewState s, char playerType) {
		ArrayList<Coor> availableCoor = s.getAvailableCoordinates();//get all available tiles
		
		for (Coor co: availableCoor) {
			if (co.getBlackDis() < co.getWhiteDis()) {//if blackDis < whiteDis and we are black, utility +1
				utility = playerType == 'B' ? utility+1 : utility-1;
			}else if (co.getBlackDis() > co.getWhiteDis()) {//else, otherwise
				utility = playerType == 'B' ? utility-1 : utility+1;
			}
		}
	}
		
	
	
	// test if we have visited all neibor at currentLocation
	public boolean isVisitedAll(Coor currentLocation) {
		boolean isVisited = false;
		for(int testDirection = 0; testDirection<7; testDirection++){//test all directions
			if(hasValidAction(currentLocation,1,testDirection)){
				//System.out.println("****This tile still has neighbors");//(debug usage)
				isVisited = false;
			}else {
				isVisited = true;
			}
				
		}
		return isVisited;
	}
	
	// move function, will only move one tile at valid direction (legacy) 
	public boolean makeAction(Coor ori, int step, int direction){
		//notice that we always shoot to where we started
		if(hasValidAction(ori,step,direction)){
			int[] currentAction = actionList[direction];
			Coor op = ori;
			Coor np = s.getCoor(ori.getX()+currentAction[0],ori.getY()+currentAction[1]);
			Coor ap = new Coor(op.getX(),op.getY(),'A');// always shoot to ori, for test purpose
			updateAction(op,np,ap);
			return true;
		}else
			return false;

		// return s.getType(ori.getY()+currentAction[0], ori.getX()+currentAction[1]) == 'N';
	}

	//int[][] actionList = {{-1, 0}, {-1, -1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};	
	// test whether given queen has valid move
	public boolean hasValidAction(Coor ori, int step, int direction){
		int[] currentAction = actionList[direction];
		int targetX = ori.getX()+currentAction[0]*step;
		int targetY = ori.getY()+currentAction[1]*step;
		return isCoorValid(new Coor(targetX,targetY)) && s.getType(targetX,targetY) == 'N';
		// return s.getType(ori.getY()+currentAction[0], ori.getX()+currentAction[1]) == 'N';
	}
	
	// test whether the move is valid (Legacy)
	public boolean isCoorValid(Coor np){
		return (np.getX()<=10)&&(np.getX()>=1)&&(np.getY()<=10)&&(np.getY()>=1);
	}

	// send move msg to server
	public void updateAction(Coor op, Coor np, Coor ap){
		System.out.println("We are now updating actions");
		//we update state object information
		s.updateState(op, np);//move Amazons to new position
		s.setCoor(ap);//update Arrow on board
		//now we prepare information to send to the server
		System.out.println(s);//print state
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
	}
 
}//end of class