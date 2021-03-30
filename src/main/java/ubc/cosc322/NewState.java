package ubc.cosc322;

import java.util.ArrayList;

import ygraph.ai.smartfox.games.Amazon.GameBoard;

public class NewState {
	// game state
	Coor[][] gameBoard = new Coor[11][11];
	// by the nature of this game, we manually created the state for the game 
	public NewState() {
		for(int i = 1; i<11; i++)//initialize the gameborad to be all null
            for(int j = 1 ; j<11; j++){
                gameBoard[i][j] = new Coor(i,j);
            }
        //now we insert the amazons
        gameBoard[1][7].setType('B');
        gameBoard[10][7].setType('B');
        gameBoard[4][10].setType('B');
        gameBoard[7][10].setType('B');
        gameBoard[1][4].setType('W');
        gameBoard[4][1].setType('W');
        gameBoard[7][1].setType('W');
        gameBoard[10][4].setType('W');
	}
	
	// setter
    public void setCoor(Coor coor){//intended to be used to set new positions of arrows *NOT for setting Amazons
        if(coor.getType()!='A'){//print error message
            System.out.println("Warning! Unexpected setting method occured at position " + coor.getX()+","+coor.getY());
        }
        
        gameBoard[coor.getX()][coor.getY()].setCoor(coor);
        
    }

	public void updateState(Coor op, Coor np) {//op for original pos and so on
        char currentTargetType = gameBoard[np.getX()][np.getY()].getType();//get the type at the current target Position
        if(currentTargetType!='N'){
            System.out.println("Error occurs at updating state at " +np.getX()+","+np.getY() );
        }
        char targetType = gameBoard[op.getX()][op.getY()].getType();//get the type at original position ( to set it to target later)
		gameBoard[op.getX()][op.getY()].setType('N');//remove type at original position
        gameBoard[np.getX()][np.getY()].setType(targetType);//update type at target position
	}
	
	public ArrayList<Coor> getState(char Type){
        ArrayList<Coor> targets = new ArrayList<Coor>();
        for(int x = 1; x <=10; x++)
            for(int y = 1; y <=10; y++)
                if(gameBoard[x][y].getType() == Type)
                    targets.add(gameBoard[x][y]);

        if(targets.size()==0)
            System.out.println("*Error getting state " + Type);
        
        return targets;
    }

    public Coor[][] getState(){
        return gameBoard;
    }

    public Coor getCoor( int x, int y){
        if(y<=10 && y>0 &&x<=10 && x>0 )
            return gameBoard[x][y];
        else
            return null;
    }

    public char getType( int x,int y){
        return gameBoard[x][y].getType();
    }

    public ArrayList<Coor> getAvailableCoordinates(){//returns all available positions
        return getState('N');
    }

    public boolean CoorValid(int xc, int yc){//np is intended to be the ori
		return (xc<=10)&&(xc>=1)&&(yc<=10)&&(yc>=1);
    }

    public void MinDisMap(Coor ori){//not perfect
        int[][] aL ={{-1, 0},{-1, -1},{-1, 1},{0, -1},{0, 1},{1, 0},{1, -1},{1, 1}};	
        ArrayList<Coor> queue = new ArrayList<Coor>();
        queue.add(ori);
        // int step = level;
        int level = 1;
        while(!queue.isEmpty()){//to go until we see an obstacles
            Coor currentCoor = queue.remove(0);
            for(int dc = 0; dc<8; dc++){//eight directions
                int[] currentAction = aL[dc];
                int xc = currentCoor.getX()+currentAction[0];
                int yc = currentCoor.getY()+currentAction[1];//get new position
                while(CoorValid(xc,yc) && gameBoard[xc][yc].getType()=='N'){
                    if(gameBoard[xc][yc].getIndex()>level){//then we want to update it
                        gameBoard[xc][yc].setIndex(level);
                        queue.add(gameBoard[xc][yc]);
                    }
                    xc += currentAction[0];
                    yc += currentAction[1];
                }
            } 
            level++; //we have looked after all the directions o at ori, move on to next level
        }
            
    }
        // return gameBoard;
    
    public void MDALL(char type){
        for(Coor ori : getState(type)){
            MinDisMap(ori);
        }
    }

    public void resetMD(){
        for(int yi = 10; yi >=1; yi--)
            for(int xi = 1; xi <=10; xi++)
               gameBoard[xi][yi].setIndex(9);//Integer.MAX_VALUE
    }

    public String toMD(){
        String r = "";
        for(int yi = 10; yi >=1; yi--){
            for(int xi = 1; xi <=10; xi++){
                r+=gameBoard[xi][yi].getIndex()+", ";
            }
            r+="\n";
        }
        return r;       
    }

    public int[][] toMDArry(){
        int[][] r = new int[11][11];
        for(int yi = 10; yi >=1; yi--){
            for(int xi = 1; xi <=10; xi++){
                r[xi][yi] = gameBoard[xi][yi].getIndex();
            }
        }
        return r; 
    }
  
    public String toString(){
        String r = "";
        for(int yi = 10; yi >=1; yi--){
            for(int xi = 1; xi <=10; xi++){
                if(gameBoard[xi][yi].getType()=='N')
                    r+="-"+", ";
                else
                    r+=gameBoard[xi][yi].getType()+", ";
            }
            r+="\n";
        }
        return r;
                
    }
    // public Coor[][] MinDisMap(char type){
    //     return gameBoard;
    // }
}
