package ubc.cosc322;

public class UtilityCalculator {
    public NewState suggestedMap;
    private char playerType;
    private char opType;

    public UtilityCalculator(NewState suggestedMap, char playerType){
        this.suggestedMap =CopyMap(suggestedMap);
        this.playerType = playerType;
        this.opType = playerType == 'B' ? 'W':'B';//we assign the opType to be different form player type
    }

    public int getUtility(){
        //this returns the current Utility
        //this should invoke getMinDistanceMap() for comparison
        return 0;
    }

    private NewState getMinDistanceMap(char targetType){
        //this returns a map consistenting MinDistance Information for the current board
        return new NewState();
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
		// System.out.println(hm);\
		System.out.println(hm);
		return hm;
	}

    private boolean isCoorValid(Coor np){
		return (np.getX()<=10)&&(np.getX()>=1)&&(np.getY()<=10)&&(np.getY()>=1);
	}

    private boolean hasValidAction(Coor ori, int step, int direction, NewState suggestedGameMap){
		int[] currentAction = actionList[direction];
		int targetX = ori.getX()+currentAction[0]*step;
		int targetY = ori.getY()+currentAction[1]*step;
		return isCoorValid(new Coor(targetX,targetY)) && suggestedGameMap.getType(targetX,targetY) == 'N';
	}
}