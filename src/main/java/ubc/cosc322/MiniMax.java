package ubc.cosc322;

import java.util.*;

/**
 * This class involves same instances as the StateHelper class doess
 * except that it needs the number of max level we look at (so that it does not consume excess amount of type)
 * 
*/
public class MiniMax {
    public NewState suggestedMap;
    private char playerType;
    private char opType;
    private int level;

    public Action alphaBetaSearch(){
        int v = maxValue(suggestedMap,Integer.MIN_VALUE,Integer.MAX_VALUE,level);
        return  findActionGivenUtility(suggestedMap,v);
    }

    public int maxValue(NewState state, int al, int be, int level){
        // System.out.println("The current level is: " + level);
        if(level == 1)
            return maxUtility(state, playerType);
        else{
            StateHelper sh = new StateHelper(state,playerType);
            if (sh.terminalState())
                return sh.getUtility();
            else{
                int v = Integer.MIN_VALUE;
                int newal = al;
                for(Action a: sh.getAllActions(playerType)){
                    NewState nextGraph =  createHypotheticalMap(state,a);
                    v = Math.max(v, minValue(nextGraph,newal,be,level-1));
                    if(v>=be)
                        return v;//prunning occurs
                    newal = Math.max(v, newal);
                }
                return v;
            }
        } 
    }

    public int minValue(NewState state, int al, int be, int level){
        // System.out.println("The current level is: " + level);
        if(level == 1)
            return maxUtility(state, opType)*-1;//not sure if we want to change it
        else{
            StateHelper sh = new StateHelper(state,playerType);
            if (sh.terminalState())
                return sh.getUtility();
            else{
                int v = Integer.MAX_VALUE;
                int newbe = be;
                for(Action a: sh.getAllActions(opType)){
                    NewState nextGraph =  createHypotheticalMap(state,a);
                    v = Math.min(v, maxValue(nextGraph,al,newbe,level-1));
                    if(v<=al)
                        return v;//prunning occurs
                    newbe = Math.min(v, newbe);
                }
                return v;
            }
        } 
    }

    public MiniMax(NewState suggestedMap, char playerType, int level){
        this.suggestedMap = StateHelper.CopyMap(suggestedMap);
        this.playerType = playerType;
        this.opType = playerType == 'B' ? 'W':'B';//we assign the opType to be different form player type
        this.level = level;
    }

    public int getMax(){
        return maxUtility(suggestedMap, playerType);
    }

    public int getMaxArrow(Action bestA){
        //first, we create the map given an Aciton, which is intended to be the "best" Action
        return maxUtility(createHypotheticalMap(suggestedMap, bestA),playerType);
    }

    public int maxUtility(NewState currentSuggestedMap, char currentPlayerType){
        //this is a simplified version of MiniMax
        //specifically, we only consider level = 1
        //and we don't find arrow position
        //you need to find one action which results max utility
        int maxUtility = -99;
        StateHelper sh = new StateHelper(currentSuggestedMap, currentPlayerType);
        for(Action a: sh.getAllActions(currentPlayerType)){
            int hUtility = new StateHelper(createHypotheticalMap(currentSuggestedMap, a), currentPlayerType).getUtility();
            if (hUtility > maxUtility)
                maxUtility = hUtility;
        }
        return maxUtility;
    }

    

    public NewState createHypotheticalMap(NewState Ori, Action a){
        NewState hMap = StateHelper.CopyMap(Ori);
        hMap.getCoor(a.getOr()).setCoorType('N');
        hMap.getCoor(a.getDe()).setCoorType(a.getOr().getType());
        return hMap;
    }

    public NewState createHypotheticalMap(NewState Ori, Coor arrow){
        NewState hMap = StateHelper.CopyMap(Ori);
        if(hMap.getCoor(arrow).getType()!='N')
            System.out.println("Invalid setting at " + arrow);
        hMap.getCoor(arrow).setCoorType('A');
        return hMap;
    }

    public Action findActionGivenUtility(NewState currentSuggestedMap, int suggestedUtility){
        StateHelper sh = new StateHelper(currentSuggestedMap, playerType);
        ArrayList<Action> actionList = new ArrayList<Action>();
        for(Action a: sh.getAllActions(playerType)){
            int hUtility = new StateHelper(createHypotheticalMap(currentSuggestedMap, a), playerType).getUtility();
            if (hUtility == suggestedUtility)
                actionList.add(a);
        }
        if(actionList.isEmpty()){
            System.out.println("------------Warning---------------");
            System.out.println(currentSuggestedMap);
            System.out.println("------------Warning---------------");
            System.out.println("Unexpected Case: Aciton with suggested utility of "+suggestedUtility+" unfound！");
            System.out.println("The max utility possible is: " + maxUtility(currentSuggestedMap,playerType));
            return null;
        }else{
            System.out.println("The Size of the available action list is : "+ actionList.size());
            return actionList.get((int)(Math.random()*actionList.size()));
        }
    }

    public Action findArrowWithUtility(Action bestA, int arrowUtility){
        return findActionGivenUtility(createHypotheticalMap(suggestedMap, bestA), arrowUtility);
    }

    public Action findAcitonWithUtility(int suggestedUtility){
        return findActionGivenUtility(suggestedMap, suggestedUtility);
    }



}
