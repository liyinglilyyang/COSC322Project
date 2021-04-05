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
        // int v = maxValue(suggestedMap,Integer.MIN_VALUE,Integer.MAX_VALUE,level);
        // return  findActionGivenUtility(suggestedMap,v);
        if(level == 1){
            return findActionGivenUtility(suggestedMap, maxUtility(suggestedMap, playerType));
        }else{
            ArrayList<Integer> values = new ArrayList<Integer>();
            Action returnAction = null;
            StateHelper sh = new StateHelper(suggestedMap,playerType);
            int v = Integer.MIN_VALUE;
            int newal = Integer.MIN_VALUE;
            for(Action a: sh.getAllActions(playerType)){
                // NewState nextGraph =  createHypotheticalMap(suggestedMap,a);
                NewState nextGraph = predictedNextGraph(suggestedMap,a,playerType);
                int min = minValue(nextGraph,newal,Integer.MAX_VALUE,level-1);
                values.add(min);
                if(v<min){
                    v = min;
                    returnAction = a; 
                }
                newal = Math.max(v, newal);
            }
            System.out.println("The list of min values is: " + values);
            System.out.println("We have choosen: " + v);
            return returnAction;
        
        } 
    }

    public int maxValue(NewState state, int al, int be, int level){
        // System.out.println("The current level is: " + level);
        if(level == 1){
            return new StateHelper(state,playerType).getUtility();
            // return maxUtility(state, playerType);
        }else{
            StateHelper sh = new StateHelper(state,playerType);
            if (sh.terminalState())
                return sh.getUtility();
            else{
                int v = Integer.MIN_VALUE;
                int newal = al;
                for(Action a: sh.getAllActions(playerType)){
                    // NewState nextGraph =  createHypotheticalMap(state,a);
                    NewState nextGraph = predictedNextGraph(state,a,playerType);
                    v = Math.max(v, minValue(nextGraph,newal,be,level-1));
                    if(v>=be){
                        // System.out.println("pruning occurs");
                        return v;// occurs
                    }
                    newal = Math.max(v, newal);
                }
                return v;
            }
        } 
    }

    public int minValue(NewState state, int al, int be, int level){
        // System.out.println("The current level is: " + level);
        if(level == 1){
            // int opMU = maxUtility(state, opType);
            // System.out.println("Opponent max utility is: " + opMU);
            // System.out.println("Player max utility is: " + maxUtility(state, opType););
            // return opMU*-1;//not sure if we want to change it
            return new StateHelper(state,playerType).getUtility();
        }
        else{
            StateHelper sh = new StateHelper(state,playerType);
            if (sh.terminalState())
                return sh.getUtility();
            else{
                int v = Integer.MAX_VALUE;
                int newbe = be;
                for(Action a: sh.getAllActions(opType)){
                    // NewState nextGraph =  createHypotheticalMap(state,a);
                    NewState nextGraph = predictedNextGraph(state,a,opType);
                    v = Math.min(v, maxValue(nextGraph,al,newbe,level-1));
                    if(v<=al){
                        // System.out.println("pruning occurs");
                        return v;// occurs
                    }
                    newbe = Math.min(v, newbe);
                }
                return v;
            }
        } 
    }

    public NewState predictedNextGraph(NewState Ori, Action a, char cP){
        NewState nextGraph = createHypotheticalMap(Ori,a);
        NewState preNG = null;
        NewState postNG = null;
        int utility = Integer.MIN_VALUE;
        StateHelper sh = new StateHelper(nextGraph,cP);
        for(Action itemAction : sh.getActions(a.getDe())){
            preNG = createHypotheticalMap(nextGraph,itemAction.getDe());
            int arrowU = new StateHelper(preNG, cP).getUtility();
            if(arrowU>utility){
                utility = arrowU;
                postNG = StateHelper.CopyMap(preNG);
            }
        }
        // return postNG;
        return nextGraph;
        //when we return merely the "nextGraph" we are not considering arrow positions
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

    public Action predictArrow(NewState cM, Action cA, char cP, int cU){
        StateHelper sh = new StateHelper(cM, cP);
        ArrayList<Action> actionList = new ArrayList<Action>();
        for(Action a: sh.getActions(cA.getDe())){
            int hUtility = new StateHelper(createHypotheticalMap(cM, a), cP).getUtility();
            if (hUtility == cU)
                actionList.add(a);
        }
        if(actionList.isEmpty()){
            System.out.println("Error Predicting Arrow");
            System.out.println(cM);
            System.out.println(cA);
            System.out.println("Current Player is: " + cP);
            System.out.println("Current Utility is: " + cU);
            return null;
        }else{
            // System.out.println("The Size of the available predicted Arrow list is : "+ actionList.size());
            return actionList.get((int)(Math.random()*actionList.size()));
        }
    }

    public Action findArrowWithUtility(Action bestA, int arrowUtility){
        NewState hMap = createHypotheticalMap(suggestedMap, bestA);
        StateHelper sh = new StateHelper(hMap, playerType);
        ArrayList<Action> actionList = new ArrayList<Action>();
        for(Action a: sh.getActions(bestA.getDe())){
            int hUtility = new StateHelper(createHypotheticalMap(hMap, a), playerType).getUtility();
            if (hUtility == arrowUtility)
                actionList.add(a);
        }
        if(actionList.isEmpty()){
            System.out.println("------------Warning---------------");
            System.out.println(hMap);
            System.out.println("------------Warning---------------");
            System.out.println("Unexpected Case: Arrow with suggested utility of "+arrowUtility+" unfound！");
            System.out.println("The max utility for Arrow possible is: " + maxUtility(hMap,playerType));
            return null;
        }else{
            System.out.println("The Size of the available action list is : "+ actionList.size());
            return actionList.get((int)(Math.random()*actionList.size()));
        }

    }

    public int getMaxArrow(Action bestA, char currentPlayer){
        //first, we create the map given an Action, which is intended to be the "best" Action
        NewState hMap = createHypotheticalMap(suggestedMap, bestA);
        int maxUtility = -99;
        StateHelper sh = new StateHelper(hMap, currentPlayer);
        for(Action a: sh.getActions(bestA.getDe())){
            int hUtility = new StateHelper(createHypotheticalMap(hMap, a), currentPlayer).getUtility();
            if (hUtility > maxUtility)
                maxUtility = hUtility;
        }
        return maxUtility;
    }



    public Action findAcitonWithUtility(int suggestedUtility){
        return findActionGivenUtility(suggestedMap, suggestedUtility);
    }



}
