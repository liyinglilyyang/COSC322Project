package ubc.cosc322;

import java.util.ArrayList;

import ygraph.ai.smartfox.games.Amazon.GameBoard;


/**
 * This class encapsulates some methods for analyzing the State
 * 
 * This includes:
 	* Utility Calculation for a current Map
	* get Action List for a current Map
 */

public class StateHelper {
    public NewState suggestedMap;
    private char playerType;
    private char opType;

    public StateHelper(NewState suggestedMap, char playerType){
        this.suggestedMap =CopyMap(suggestedMap);
        this.playerType = playerType;
        this.opType = playerType == 'B' ? 'W':'B';//we assign the opType to be different form player type
    }

    public int getUtility(){
		//this returns the current Utility
        //this should invoke getMinDistanceMap() for comparison
		int[][] pm = getMinDistanceMap(playerType);
		int[][] om = getMinDistanceMap(opType);

		int utility = 0;
		for(int yi = 10; yi >=1; yi--){
            for(int xi = 1; xi <=10; xi++){
                if(pm[xi][yi] == om[xi][yi]){
					//do nothing
				}else if(pm[xi][yi] == -1 || pm[xi][yi] > om[xi][yi]){
					utility--;
				}else if(om[xi][yi] == -1 || pm[xi][yi] > om[xi][yi]){
					utility++;
				}else{
					System.out.println("Error in utility calculation: " + pm[xi][yi] + ", " + om[xi][yi]);
				}
            }
        }
        return utility;
    }

    private int[][] getMinDistanceMap(char targetType){
        //this returns a map consistenting MinDistance Information for the current board
		int[][] minDistanceMap = new int[11][11];//a matrix for min distance
		ArrayList<Coor> queue = new ArrayList<Coor>();
		for(Action a: getAllActions(targetType)){
			queue.add(a.getDe());//insert all possible destination position to the queue
			
		}

		for(int yi = 10; yi >=1; yi--){
            for(int xi = 1; xi <=10; xi++){

            }
        }
		
        return minDistanceMap;
    }

    private ArrayList<Action> getAllActions(char targetType){
		ArrayList<Coor> queens = suggestedMap.getState(targetType);
		ArrayList<Action> allActions = new ArrayList<Action>();
		for(Coor queen: queens)
			allActions.addAll(getActions(queen));
		return allActions;
	}

    public ArrayList<Action> getActions(Coor queen){
		ArrayList<Action> actions = new ArrayList<Action>();
		//for all directions
		//while a step is available, go into it
		// System.out.println("We are now printing one instance of a one queen map");
		// System.out.println(oneQueenMap);

		for(int di = 0; di < 8; di++){
			int step = 1;
			while(hasValidAction(queen,step,di)){
				actions.add(new Action(queen,step,di));
				step++;
			}
		}
		return actions;
	}

    public NewState CopyMap(NewState Ori){
        //we want to create an *identical* copy of a map
        //but we do not want this new copy to share the same memory space (so they are different *objects*)
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
		// System.out.println(hm);
		System.out.println(hm);
		return hm;
	}

    private boolean isCoorValid(Coor np){
		return (np.getX()<=10)&&(np.getX()>=1)&&(np.getY()<=10)&&(np.getY()>=1);
	}

    private boolean hasValidAction(Coor ori, int step, int direction){
        int[][] actionList ={{-1, 0},{-1, -1},{-1, 1},{0, -1},{0, 1},{1, 0},{1, -1},{1, 1}};	
		int[] currentAction = actionList[direction];
		int targetX = ori.getX()+currentAction[0]*step;
		int targetY = ori.getY()+currentAction[1]*step;
		return isCoorValid(new Coor(targetX,targetY)) && suggestedMap.getType(targetX,targetY) == 'N';
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
}