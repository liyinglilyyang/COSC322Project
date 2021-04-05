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
        int maxUtility = -99;
        StateHelper sh = new StateHelper(suggestedMap, playerType);
        for(Action a: sh.getAllActions(playerType)){
            // //we iterate through all possible actions given current state
            // NewState hMap = createHypotheticalMap(suggestedMap, a);
            // //initialize a new map with the hypothentical action
            // StateHelper hypotheticalStateHelper = new StateHelper(hMap, playerType);
            // if(hypotheticalStateHelper.getUtility() > maxUtility){
            //     maxUtility = hypotheticalStateHelper.getUtility();
            // }
            int hUtility = new StateHelper(createHypotheticalMap(suggestedMap, a), playerType).getUtility();
            if (hUtility > maxUtility)
                maxUtility = hUtility;
        }
        return maxUtility;
    }

    public NewState createHypotheticalMap(NewState Ori, Action a){
        NewState hMap = StateHelper.CopyMap(Ori);
        hMap.getCoor(a.getOr()).setCoorType('N');
        hMap.getCoor(a.getDe()).setCoorType(a.getOr().getType());
        
        //test code bellow!~
        System.out.println("------------");
        System.out.println(Ori);
        System.out.println("------------");
        System.out.println(hMap);
        System.out.println("------------");
        System.out.println(a);
        //test code bellow!~
        return hMap;
    }
}
