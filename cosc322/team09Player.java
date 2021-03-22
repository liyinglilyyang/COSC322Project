package ubc.cosc322;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;
import ygraph.ai.smartfox.games.amazons.HumanPlayer;

public class team09Player extends GamePlayer{
	
	private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	
    private String userName = null;
    private String passwd = null;
    
    
    // implementation var
    //private int gameboardSize = 10;
    private String playerColor = null;
    private int[][] actionList = {{-1, 0}, {-1, -1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
    
    Dict <Integer> gameBoard = 
    Pos = msgDetails.get(AmazonsGameMessage.GAME_STATE);
    Arrow = msgDetails.get(AmazonsGameMessage.GAME_STATE.ARROW_POS);
    
    public ArrayList <Integer> gameboardState;
    public ArrayList <Integer> arrowState;
    
    public int posInf = 999; // set Positive infinite
    public int negInf = -999; // set negative infinite
    
//    public int posInf = Integer.MAX_VALUE; // set Positive infinite
//    public int negInf = Integer.MIN_VALUE; // set negative infinite
    
    
    
    
    // main function
	public static void main(String[] args) {
		team09Player player = new team09Player(args[0], args[1]);
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
	}
	
	
	
	// player constructor
	public team09Player(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	
    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	this.gamegui = new BaseGameGUI(this);
    	
    }
	
	//----------------------bulit-in func--------------------------------------
	// handleGameMessage
	@SuppressWarnings("unchecked")
	@Override
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
		//GameMessage.GAME_STATE_BOARD
    	if (messageType == GameMessage.GAME_STATE_BOARD) {
    		this.gamegui.setGameState((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
    		this.gameboardState = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE);
    		
    		System.out.println("this is the gameboardstate message" + gameboardState);
    	}else if (messageType == GameMessage.GAME_ACTION_MOVE){
    	//GameMessage.GAME_ACTION_MOVE
    		this.gamegui.updateGameState(msgDetails);
    	}
    	
		return true;
	}

	@Override
	public void onLogin() {
		System.out.println("Team09 Login successful.");
		
		// initialize gameboard
		this.userName = this.gameClient.getUserName();
    	if(this.gamegui != null) {
    		this.gamegui.setRoomInformation(this.gameClient.getRoomList());
    	}
		
		
	}

	@Override
	public String userName() {
		return this.userName;
	}
	

	@Override
	public void connect() {
		gameClient = new GameClient(userName, passwd, this);	
	}

	@Override
	public GameClient getGameClient() {
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		return this.gamegui;
	}

	//----------------------implementation func--------------------------------------
	
	//private void Reset() {}
		// the total row is 10, identify each position in row
		//for (int i in row): 
			// the total column is 10, identify each position in column
			//for (j in column):
			// set the value to null and then reset the board
			//The position of i and j = null; 
	
	
	
	// Move() method will return AmazonsAction for sendMoveMessage
	public AmazonsAction Move(State state){ 
		// the minimax function yields the result of an amazon movement only
		Action minimax = alphaBetaSearch(state);
		
		// get both positions from the movement	
		int[] original_Position = minimax.getOriginal_Position();
		int[] new_Position = minimax.getNew_Position();
		
		//we decide the arrow position with another* min distance function evaluation
		state.updateState(original_Position, new_Position);
		int[] arrowPosition = this.ShootingArrow(state, new_Position);
		
		//the amazonsAction is intended to be used for the sendMoveMessage() in api
		return new AmazonsAction(original_Position, new_Position, arrowPosition);
			
	}
	
	
	
	// return the utility of carrying out a move action at state s
	@SuppressWarnings("rawtypes")
	public int calUtility(State s, Action a){
		Dictionary actionboard_B = new Hashtable();//[“each Coor”: Integer.MAX_VALUE]
		Dictionary actionboard_W = new Hashtable();//[“each Coor”: Integer.MAX_VALUE]

		//both returned by Cal_Min_D()
			//State sCurrent = s.action(a)
			//MinDistance(actionboard_B, sCurrent );
			//MinDistance(actionboard_W, sCurrent );
			//these two functions are intended to do the same thing as the function below

		this.Cal_Min_D(Dictionary actionboard_B, Dictionary actionboard_W, State s);
		//Notice that we still need to take in the current state and action into consideration

		Dictionary actionboard_Eva =(team09Player player == B) ? actionboard_B - actionboard_W : actionboard_W - actionboard_B; 
		int utility = 0;
		for each grid in actionboard_Eva:
			//we want the difference in # of steps to be as low as possible
			//hence, a negative # in grid suggests we have a higher chance of getting it
			//we add one to the utility in such case
			//******notice that gird should be the value, NOT the key in the dictionary*******
			if(grid > 0) utility++;    //utility++
			else if (grid < 0) utility--;//utility--
			//else, we do not change utility (it’s either a tie or an burnt block)
		return utility;
		//player == W suggests that we are moving white ones
	}
	
	
	
	// set player color
	public String setPlayerColor(String color) { // "b" for Black & "w" for White
		return this.playerColor = color;
	}
	
	
	
	// return the new_position as the dest for an arrow
	public int[] shootingArrow(State stateAfterQueenMove, int[] queen_Position){
		//Shooting Arrow returns position of arrow with max utility given queen position
		
		//the set of all possible actions originating at queen_Position
		Action[] actions = this.getAllPossibleActions(stateAfterQueenMove, queen_Position);
		int maxUtility = 0;
		Action maxUtilityAction = new Action();
		
		for (Action a : actions){//to get a action of arrow which yields max utility
			int utility = calUtility(stateAfterQueenMove, a);
			if(utility > maxUtility){
				maxUtility = utility;
				maxUtilityAction = a;
			}
		}
		return maxUtilityAction.getNew_Position();
	}
		
	//
	public Action[] getAllPossibleActions(State s, int[] pos){
		Action[] actions = 
		return actions
	}
	
	
	

	// calculate the min-D
	public void Cal_Min_D(Dictionary actionboard_B, Dictionary actionboard_W, State s) {
				this.cal_hB(Dictionary actionboard_B, s.getBlackState());
				this.cal_hW(Dictionary actionboard_W, s.getWhiteState());
	}
	
	// helper func for Cal_Min_D()
	// calculate the hB for gameBoard B 
	public void cal_hB1(Dictionary actionboard_B, Coor[] s) {
		int step = 1;
		// iterate each black queen
		for (Coor b : s) {                      			 
			int[] current_Position = b.getCoor(); //original position
			While we have not visited all the neighbours of the node in step-th iteration
				for a in actionList:{
					boolean isMoveValid = isMoveValid(current_Position, a);
					while (isMoveValid):
						// do matrix multi to get step one action 
						current_Position = current_Position * actionList[a];
						intoActionBoard(actionboard_B, current_Position, step); 
						isMoveValid(current_Position, a);
				}
				step++;
	}
	
	// calculate the hW for gameBoard W 
	public void cal_hW2(Dictionary actionboard_W, State s) {
		int step = 1;
		// iterate each white queen 
		for each w in W:                      			
			current_Position = original_Position  
			While we have not visited all the neighbours of the node in step-th iteration
				for a in actionList:{ 
					boolean isMoveValid = isMoveValid(current_Position, a);
					while (isMoveValid):
						// do matrix multi to get step one action
						current_Position = current_Position * actionList[a];
						intoActionBoard(actionboard_B, current_Position, step); 
						isMoveValid(current_Position, a);
				}
				step++;
	}	

	
	// determine whether we have visited all nodes 
	
	// determine whether the movement is valid in each direction 
	public boolean isMoveValid(int[] pos, int[] a) {
		return true;
	}
	
	
	
	// Update the steps required to reach to each grid
	public void intoActionBoard(Dictionary<String, Integer> acitonboard, String actionBoardKey, int step) {  
		int tmp = acitonboard.get(actionBoardKey);
		if (step < tmp){
			acitonboard.put(actionBoardKey, tmp);
		}
	}
		
	
	
	// Minimax function	
	public Action alphaBetaSearch(State state){ 
		v:= max_value(state, negInf, posInf);
		
		// return the action
		return  the action in Actions(state) with value v;
	}
	
	// return max value 
	public int max_value(State state, al,be){
		If terminal_test(state) then return utility(state);
		V =  -infinity;
		for each a in actions(state) do 
			V := max(v, min-value(Utility(s,a),al,be));
		 //prunning occurs
			If v>= be then return v
			al := Max(al, v);
		Return v;
	} 
	
	// return min value
	public int min_value(State state, al, be){
		If terminal_test(state) then return utility(state)
		V =  +infinity
		For each a in actions(state) do 
			V := min(v, max-value(Utility(s,a),al,be))
			If v<= al then return v //prunning occurs
			be := Min(al, v);
		Return v;
	} 



}
