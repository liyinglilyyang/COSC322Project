package ubc.cosc322;

public class StateHelperTest {
    public static void main(String args[]){
        //this is intended to be used to test stateHelper

        NewState testBoard = new NewState();
        
        for(int yi = 6; yi >=5; yi--){
            for(int xi = 1; xi <=10; xi++){
                testBoard.gameBoard[xi][yi].setType('A');
            }
        }
        for(int yi = 10; yi >=1; yi--){
            for(int xi = 5; xi <=6; xi++){
                testBoard.gameBoard[xi][yi].setType('A');
            }
        }

        System.out.println(testBoard);
    }
}
