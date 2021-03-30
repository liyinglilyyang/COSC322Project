package ubc.cosc322;

import java.util.ArrayList;

import ygraph.ai.smartfox.games.Amazon.GameBoard;

public class NewState implements Cloneable{
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
    
    // update state
	public void updateState(Coor op, Coor np) {//op for original pos and so on
        char currentTargetType = gameBoard[np.getX()][np.getY()].getType();//get the type at the current target Position
        if(currentTargetType!='N'){
            System.out.println("Error occurs at updating state at " +np.getX()+","+np.getY() );
        }
        char targetType = gameBoard[op.getX()][op.getY()].getType();//get the type at original position ( to set it to target later)
		gameBoard[op.getX()][op.getY()].setType('N');//remove type at original position
        gameBoard[np.getX()][np.getY()].setType(targetType);//update type at target position
	}
	
	// getter
	public ArrayList<Coor> getState(char Type){
        ArrayList<Coor> targets = new ArrayList<Coor>();
        // for(Coor[] itemList: gameBoard)
        //     for(Coor item : itemList)
        //         if (item!=null && item.getType()==Type)
        //             targets.add(item);
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
    
//    public int getDis(char type) {
//    	if (type == 'B') {
//    		return gameBoard
//    	}else if (type == 'W') {
//    		
//    	}
//    }

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
}
