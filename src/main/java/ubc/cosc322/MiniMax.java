package ubc.cosc322;

import java.util.*;

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

    public int getMaxArrow(NewState map){
        int maxArrow = -99;
        StateHelper arrowsh = new StateHelper(map, playerType);
        for(Action a: arrowsh.getAllActions(playerType)){
            int arrowUtility = new StateHelper(createHypotheticalMap(map, a), playerType).getUtility();
            if(arrowUtility > maxArrow){
                maxArrow = arrowUtility;
            }
        }
        return 0;
    }

    public Action findArrowWithUtility(NewState map, int arrowUtility){
        StateHelper sh = new StateHelper(map, playerType);
        ArrayList<Action> arrowActions = new ArrayList<Action>();
        for(Action a: sh.getAllActions(playerType)){
            int hUtility = new StateHelper(createHypotheticalMap(map, a), playerType).getUtility();
            if (hUtility == arrowUtility)
                arrowActions.add(a);
        }
        if(arrowActions.isEmpty()){
            System.out.println("Unexpected Case: arrow action with suggested arrow utility unfound！");
            return null;
        }else{
            System.out.println("The size of the available arrow action list is : "+ arrowActions.size());
            return arrowActions.get((int)(Math.random()*arrowActions.size()));
        }
    }

    public NewState createHypotheticalMap(NewState Ori, Action a){
        NewState hMap = StateHelper.CopyMap(Ori);
        hMap.getCoor(a.getOr()).setCoorType('N');
        hMap.getCoor(a.getDe()).setCoorType(a.getOr().getType());
        
        //test code bellow!~
        // System.out.println("------------");
        // System.out.println(Ori);
        // System.out.println("------------");
        // System.out.println(hMap);
        // System.out.println("------------");
        // System.out.println(a);
        //test code above!~
        return hMap;
    }

    public NewState createHypotheticalMap(NewState Ori, Coor arrow){
        NewState hMap = StateHelper.CopyMap(Ori);
        hMap.getCoor(arrow).setCoorType('A');
        return hMap;
    }

    public Action findAcitonWithUtility(int suggestedUtility){
        StateHelper sh = new StateHelper(suggestedMap, playerType);
        ArrayList<Action> actionList = new ArrayList<Action>();
        for(Action a: sh.getAllActions(playerType)){
            int hUtility = new StateHelper(createHypotheticalMap(suggestedMap, a), playerType).getUtility();
            if (hUtility == suggestedUtility)
                actionList.add(a);
        }
        if(actionList.isEmpty()){
            System.out.println("Unexpected Case: Aciton with suggested utility unfound！");
            return null;
        }else{
            System.out.println("The Size of the available action list is : "+ actionList.size());
            return actionList.get((int)(Math.random()*actionList.size()));
        }
    }

}
