
package ubc.cosc322;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
    
    
    // test var
    
    // playerType
	public char playerType = 'B';
	
	// set State
	// private State s = new State();
	private NewState s = new NewState();
	
	// arrowBoard
	// private ArrayList<Coor> arrowBoard = new ArrayList<Coor>(); 
	
	// actionlist 
	// private int[][] actionList = {{-1, 0}, {-1, -1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, 0}};
	private int[][] actionList ={{-1, 0},{-1, -1},{-1, 1},{0, -1},{0, 1},{1, 0},{1, -1},{1, 1}};	
	// counter for steps
	private int counter = 0;
	ArrayList<Integer> currentGameBoard = new ArrayList<Integer>();
	
	
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
    		ArrayList <Integer> QueenNew = (ArrayList <Integer>) msgDetails.get((AmazonsGameMessage.QUEEN_POS_NEXT));
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
	public void makeMove(String playerType) {

		//this.gameClient.sendMoveMessage(moveQueenOri, moveQueenNew, movearrowNew);
	}
	
	
	// make emergent move
	public void makeEmerMove(char playerType) {
		
		ArrayList<Coor> queens = s.getState(playerType);
		Random rand=new Random(47);
		Collections.shuffle(queens,rand);
		for(Coor currentQueen : queens){//the first queen
			//we have found a queen
			//we would like to indentify the node with max utility
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

	public boolean makeAction(Coor ori, int step, int direction){
		//notice that we always shoot to where we started
		if(hasValidAction(ori,step,direction)){
			int[] currentAction = actionList[direction];
			Coor op = ori;
			Coor np = s.getCoor(ori.getX()+currentAction[0],ori.getY()+currentAction[1]);
			
			Coor ap = new Coor(op.getX(),op.getY(),'A');
			// Coor ap = shootBestArrow(op, np);
			updateAction(op,np,ap);
			return true;
		}else
			return false;

		// return s.getType(ori.getY()+currentAction[0], ori.getX()+currentAction[1]) == 'N';
	}

	public Coor shootBestArrow(Coor ori, Coor fin){
		int[][] aL ={{-1, 0},{-1, -1},{-1, 1},{0, -1},{0, 1},{1, 0},{1, -1},{1, 1}};	
        ArrayList<Coor> queue = new ArrayList<Coor>();
        queue.add(fin);
        // int step = level;
        int level = 1;

		Coor currentCoor = queue.remove(0);
		for(int dc = 0; dc<8; dc++){//eight directions
			int[] currentAction = aL[dc];
			int xc = currentCoor.getX()+currentAction[0];
			int yc = currentCoor.getY()+currentAction[1];//get new position
			Coor current = s.getState()[xc][yc];
			while(CoorValid(xc,yc) && current.getType()=='N'){
				if(current.getIndex()>level){//then we want to update it
					current.setIndex(level);
					queue.add(current);
				}
				xc += currentAction[0];
				yc += currentAction[1];
			}
		} 

		Coor best = new Coor(queue.get(0).getX(),queue.get(0).getY(),'A');
		int bestU = Integer.MIN_VALUE;
		
		Coor current = best;
		for(Coor i : queue){
			current = new Coor(i.getX(),i.getY(),'A');
			s.setCoor(current);
			if(bestU <MDE()){
				bestU = MDE();
				best = new Coor(current.getX(),current.getY(),'A');
			}
				

			current.setType('N');
			s.setCoor(current);
		}

		return current;
	}

	public boolean hasValidAction(Coor ori, int step, int direction){
		int[] currentAction = actionList[direction];
		int targetX = ori.getX()+currentAction[0];
		int targetY = ori.getY()+currentAction[1];
		return isCoorValid(new Coor(targetX,targetY)) && s.getType(targetX,targetY) == 'N';
		// return s.getType(ori.getY()+currentAction[0], ori.getX()+currentAction[1]) == 'N';
	}
	// test whether the move is valid
	public boolean isCoorValid(Coor np){
		return (np.getX()<=10)&&(np.getX()>=1)&&(np.getY()<=10)&&(np.getY()>=1);
	}
	public boolean CoorValid(int xc, int yc){//np is intended to be the ori
		return (xc<=10)&&(xc>=1)&&(yc<=10)&&(yc>=1);
    }
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

		// s.MinDisMap(np);//calculate min dis map originating at arrow position
		s.MDALL('B');
		System.out.println(s.toMD());//print mid dis map
		s.resetMD();
		s.MDALL('W');
		System.out.println(s.toMD());//print mid dis map
		s.resetMD();
		System.out.println(MDE());
		moveQueenOri.add(op.getY());
		moveQueenNew.add(np.getY());
		movearrowNew.add(ap.getY());
		moveQueenOri.add(op.getX());
		moveQueenNew.add(np.getX());
		movearrowNew.add(ap.getX());
		gameClient.sendMoveMessage(moveQueenOri, moveQueenNew, movearrowNew);//send message to game client
		getGameGUI().updateGameState(moveQueenOri, moveQueenNew, movearrowNew);//update gui
	}
	public int MDE(){
		s.MDALL('W');
		int[][] rW = s.toMDArry();
		s.resetMD();
		s.MDALL('B');
		int[][] rB = s.toMDArry();
		s.resetMD();
		int utility = 0;
		for(int yi = 10; yi >=1; yi--){
            for(int xi = 1; xi <=10; xi++){
                if(rW[xi][yi]>rB[xi][yi])
					utility = playerType == 'B' ? utility+1 : utility -1;
				else if(rW[xi][yi]<rB[xi][yi])
					utility = playerType == 'B' ? utility-1 : utility +1;
            }
        }
		return utility;
	}
 
}//end of class
