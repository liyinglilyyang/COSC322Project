package ubc.cosc322;

import java.util.ArrayList;

/**
 * This class involves same instances as the StateHelper class doess
 * except that it needs the number of max level we look at (so that it does not consume excess amount of type)
 * 
 * 
 * 
*/
public class MiniMax {
    public NewState suggestedMap;
    private char playerType;
    private char opType;
    private int level;

    public MiniMax(NewState suggestedMap, char playerType, int level){
        this.suggestedMap = StateHelper.CopyMap(suggestedMap);
        this.playerType = playerType;
        this.opType = playerType == 'B' ? 'W':'B';//we assign the opType to be different form player type
        this.level = level;
    }

    public int getMax(){
        //this is a simplified version of MiniMax
        //specifically, we only consider level = 1
        //and we don't find arrow position

        //you need to find one action which results max utility
        int max = 0;
        StateHelper sh = new StateHelper(suggestedMap, playerType);
        for(Action a: sh.getAllActions(playerType)){
            NewState hMap = createHypotheticalMap(suggestedMap, a);
            StateHelper SH = new StateHelper(hMap, playerType);
            if(SH.getUtility() > max){
                max = SH.getUtility();
            }
        }
        return max;
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
		System.out.println(hm);
		return hm;
	}

    public NewState createHypotheticalMap(NewState Ori, Action a){
        NewState hMap = CopyMap(Ori);
        hMap.getCoor(a.getOr()).setCoorType('N');
        hMap.getCoor(a.getDe()).setCoorType(a.getOr().getType());
        return hMap;
    }
}
