
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
    		ArrayList <Integer> QueenNew = (ArrayList <Integer>) msgDetails.get((AmazonsGameMessage.QUEEN_POS_NEXT));
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

    		// update state and arrowboard
    		this.s.updateState(Ori_Position, New_Position);
    		this.s.setCoor(Arrow_Position);
    		
    		// calMinDis(this.s.getState(playerType), this.playerType, this.counter);
    		// System.out.println(showMinDisBoard(this.s.getState(), this.playerType));//print out mindis board
    		//System.out.println("oripost is " + Ori_Position.getCoor()[0]);
    		// ++this.counter;
    		// this.runTimeTask(this.playerType);
    		// makeEmerMove(playerType);
			makeMove(playerType);
    		long endTime=System.currentTimeMillis();
        	System.out.println("Run time: "+(endTime-startTime)+"ms-----------------------------------------------");
		
			
			
			
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
	public void runTimeTask(char playerType) {
		TimerTask task = new TimerTask() {
	        public void run() {
	        	
	        	// alogrithm runs here
	    	    // alphaBeta
	        	
	            System.out.println("Time almost running out, perform an emerent move.");
	            makeEmerMove(playerType);
	        }
	    };
	    
	    
	    
		Timer timer = new Timer("Timer");
	    
	    long delay = 1000L;// second * 1000
	    timer.schedule(task, delay);
	    // showmsg(timer);// minimax goes here with one more para timer
	    
	}
	
	// // test function to cancel the timer 
	// public void showmsg(Timer timer) {
	// 	System.out.println("timer is terminated.");
	// 	timer.cancel();
	// }
	
	
	
	// make decision and send move message
	public void makeMove(char playerType) {
		// minimax = alphaBetaSearch();
		
		// makeAction();
		makeEmerMove(playerType);
	}
	
	
	// make emergent move
	public void makeEmerMove(char playerType) {
		
		ArrayList<Coor> queens = s.getState(playerType);
		// Coor queen = queens.get((int)Math.random()*4);

		ArrayList<Action> allActions = new ArrayList<Action>();
		for(Coor queen: queens)
			allActions.addAll(getActions(s,queen));
		//the above could be replaced using getAllActions()

		System.out.println("***********The number of random actions we have is: "+ allActions.size() +"***************");
		int i = (int)(allActions.size()*Math.random());
		//randomlly select an action
		Action randomAction = allActions.get(i);
		Action mostUtilityAction;
		if(s.getState('N').size()> 30){
			//------------------------------
			System.out.println("Early");
			mostUtilityAction = allActions.get(0);
			int mostUtility = getAllActions(creatHypotheticalMap(s,mostUtilityAction),playerType).size();
			for(Action a: allActions){
				// int currentU = findUtility(creatHypotheticalMap(s,a));
				int currentU = getAllActions(creatHypotheticalMap(s,a),playerType).size();
				if(currentU>mostUtility){
					//we should update
					mostUtilityAction = a;
					mostUtility = currentU;
				}
			}
			//------------------------------
		}else{
			System.out.println("End Game");
			//------------------------------
			mostUtilityAction = allActions.get(0);
			// int mostUtility = findUtility(creatHypotheticalMap(s,mostUtilityAction));
			int mostUtility = findUtility(creatHypotheticalMap(s,mostUtilityAction));
			for(Action a: allActions){
				int currentU = findUtility(creatHypotheticalMap(s,a));
				if(currentU>mostUtility){
					//we should update
					mostUtilityAction = a;
					mostUtility = currentU;
				}
			}
			//------------------------------
		}
		Action decide = mostUtilityAction;

		// System.out.println("utility is " + findUtility(s));
		//******************************************* */
		// Coor ap = new Coor(randomAction.getOr().getX(),randomAction.getOr().getY(),'A');
		// // always shoot to ori, for test purpose
		
		
		//we have already decided the action, namely the ori and de coordinates
		//now we are looking at merely the new arrow position
		ArrayList<Action> ArrowP = getActions(creatHypotheticalMap(s, decide), decide.getDe());
		if(ArrowP.size()==0){
			System.out.println("********************_____________________***************************");
			System.out.println(decide.getOr());
			System.out.println(decide.getDe());
		}
		//ArrowP contains all the possible actions
		// Action bestArrow = ArrowP.get(0);
		// int bestActions = getAllActions(creatHypotheticalMap(s, randomAction,bestArrow.getDe()), playerType).size();

		// for(Action a: ArrowP){
		// 	int currentSize = getAllActions(creatHypotheticalMap(s, randomAction, a.getDe()), playerType).size();

		// 	System.out.println("the current random action number available is: " + currentSize);
		// 	if(currentSize<bestActions){
		// 		//we've found a better arrow position, so we update
		// 		bestArrow = a;
		// 		bestActions = currentSize;
		// 	}
		// }
		// this generates a random action at index namely j
		int j = (int)(ArrowP.size()*Math.random());
		updateAction(decide.getOr(), decide.getDe(), ArrowP.get(j).getDe());

		// updateAction(randomAction.getOr(), randomAction.getDe(), bestArrow.getDe());

		// if(randomAction.getDe().equals(bestArrow.getOr()))
		// 	System.out.println("This is a valid arrow shooting action!");

		// for(Coor currentQueen : queens){//the first queen
		// 	for(int testDirection = 0; testDirection<7; testDirection++){//test all directions
		// 		int testStep = 1;//we always try step one
		// 		//notice that if 1 is impossible, then we do not need to proceed either
		// 		if(makeAction(currentQueen,testStep,testDirection)){
		// 			System.out.println("****Player Type: " + playerType);
		// 			System.out.println("****CurrentObject: " + currentQueen.getX() + "," + currentQueen.getY() );
		// 			int[] currentD = actionList[testDirection];
		// 			System.out.println("****Test Direction"+ currentD.toString() +"****Test Step"+testStep);
		// 			return;
		// 		}
					
		// 	}
		// }
	}
	
	public int findUtility(NewState suggestedGameBoard){
		int blackUtility = 0;
		System.out.println("Now we find utility");
		NewState black = assignMinDistance(suggestedGameBoard,'B');
		System.out.println("Now we find utility for white queens");
		NewState white = assignMinDistance(suggestedGameBoard,'W');

		for(int yi = 10; yi >=1; yi--)
            for(int xi = 1; xi <=10; xi++){
				//firstly, we want to make sure it is not -1
				if(black.getState()[xi][yi].getIndex()==white.getState()[xi][yi].getIndex()){
					//do nothing
				}else if(white.getState()[xi][yi].getIndex()==-1 && black.getState()[xi][yi].getIndex()>0){
					blackUtility++;
				}else if(black.getState()[xi][yi].getIndex()==-1 && white.getState()[xi][yi].getIndex()>0){
					blackUtility--;
				}else if(black.getState()[xi][yi].getIndex()<white.getState()[xi][yi].getIndex()){
					blackUtility++;
				}else{
					blackUtility--;
				}
			}
 

		return playerType == 'B' ? blackUtility : -blackUtility;
	}

	public NewState[] getMdMap(NewState suggestedGameBoard){
		NewState[] mdMap = new NewState[8];
		for(NewState si : mdMap){
			si = new NewState('A');
			for(Coor c: suggestedGameBoard.getState('N'))
				si.setCoor(new Coor(c.getX(),c.getY(),-1));
		}//Now mdMap contains 8 identical maps, and all available positions are N
		int i = 0;
		//black first, white second
		for(Coor c: suggestedGameBoard.getState('B')){
			// assignMinDistance(mdMap[i],)
			///////////////////////////////////////////////////////////////////
		}
		
		return mdMap;
	}

	public void assignMinDistance(NewState oneQueenMap, Coor queen){
		ArrayList<Coor> queue = new ArrayList<Coor>();
		//this queue stores all the list of available neighbours
		System.out.println(oneQueenMap);
		for(Action a: getActions(oneQueenMap,queen)){
			queue.add(a.getDe());
			oneQueenMap.setCoorIndex(a.getDe(),1);
		}
		
		// while(!queue.isEmpty()){
		// 	Coor currentNode = queue.remove(0);
		// 	for(Action a: getActions(oneQueenMap,currentNode)){//we weren't sure if we want to call creatHypotheticalMap or not
		// 		if(a.getDe().getIndex()==-1){//if we have not visit it before
		// 			queue.add(a.getDe());
		// 			oneQueenMap.setCoorIndex(a.getDe(),a.getDe().getIndex()+1);
		// 		}
		// 	}
		// }
	}
	
	public NewState assignMinDistance(NewState suggestedmap, char playerT){
		ArrayList<Coor> queue = new ArrayList<Coor>();

		NewState indexMap = CopyMap(suggestedmap);//we will return it

		for(Action a: getAllActions(indexMap,playerT)){
			queue.add(a.getDe());
			indexMap.setCoorIndex(a.getDe(),1);
		}
		System.out.println("index map for player: " + playerT);
		System.out.println(indexMap.toStringIndex());
		
		while(!queue.isEmpty()){
			Coor currentNode = queue.remove(0);
			for(Action a: getActions(suggestedmap,currentNode)){//we weren't sure if we want to call creatHypotheticalMap or not
				if(a.getDe().getIndex()==-1){//if we have not visit it before
					queue.add(a.getDe());
					indexMap.setCoorIndex(a.getDe(),a.getDe().getIndex()+1);
				}
			}
		}
		System.out.println(indexMap);
		return indexMap;
	}

	public ArrayList<Action> getActions(NewState oneQueenMap, Coor queen){
		ArrayList<Action> actions = new ArrayList<Action>();
		//for all directions
		//while a step is available, go into it
		// System.out.println("We are now printing one instance of a one queen map");
		// System.out.println(oneQueenMap);

		for(int di = 0; di < 8; di++){
			int step = 1;
			while(hasValidAction(queen,step,di,oneQueenMap)){
				actions.add(new Action(queen,step,di));
				step++;
			}
		}
		return actions;
	}

	public ArrayList<Action> getAllActions(NewState suggestedMap, char playerType){
		ArrayList<Coor> queens = suggestedMap.getState(playerType);
		ArrayList<Action> allActions = new ArrayList<Action>();
		for(Coor queen: queens)
			allActions.addAll(getActions(s,queen));
		return allActions;
	}
	
	public NewState creatHypotheticalMap(NewState oriMap, Action action){
		NewState hm = CopyMap(oriMap);
		hm.getCoor(action.getDe()).setType(action.getOr().getType());//the destination ought to be covered
		hm.getCoor(action.getOr()).setType('N');//the ori ought to be space
		return hm;
	}

	public NewState creatHypotheticalMap(NewState oriMap, Action action, Coor Ap){//for arrow position
		NewState hm = creatHypotheticalMap(oriMap,action);
		hm.setCoor(Ap);
		return hm;
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

	public boolean hasValidAction(Coor ori, int step, int direction, NewState suggestedGameMap){
		int[] currentAction = actionList[direction];
		int targetX = ori.getX()+currentAction[0]*step;
		int targetY = ori.getY()+currentAction[1]*step;
		return isCoorValid(new Coor(targetX,targetY)) && suggestedGameMap.getType(targetX,targetY) == 'N';
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
		s.setCoor(ap,'A');//update Arrow on board

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

	public NewState CopyMap(NewState Ori){
		NewState hm = new NewState('N');
		Coor[][] ori = Ori.getState();
		for(int yi = 10; yi >=1; yi--){
            for(int xi = 1; xi <=10; xi++){
				hm.setCoor(ori[xi][yi]);
            }
        }
		// System.out.println("The first -------------------");
		// System.out.println(Ori);
		
		// System.out.println("The Second -------------------");
		// System.out.println(hm);\
		System.out.println(hm);
		return hm;
	}
}//end of class
