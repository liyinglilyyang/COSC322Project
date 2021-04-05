package ubc.cosc322;

public class MiniMaxTest {
    public static void main(String args[]){
        NewState testBoard = initializeState();

        MiniMax miniMax = new MiniMax(testBoard,'B',1);
        int maxUtility = miniMax.getMax();
        System.out.println("The max utility is :" + maxUtility);
        System.out.println("The action with Max Utility is: \n" + miniMax.findAcitonWithUtility(maxUtility));
        // System.out.print();

    }
    public static NewState initializeState(){
        NewState testBoard = new NewState();
        char[][] gameBoard = {
            {'N','N','N','N','N','N','N','N','N','N','N'},
            {'N','N','N','N','N','N','A','N','N','N','N'},
            {'N','N','N','N','A','N','A','N','N','N','N'},
            {'N','N','N','N','N','A','A','N','N','B','N'},
            {'N','W','N','A','W','A','A','A','N','A','N'},
            {'N','N','N','A','B','A','A','N','A','A','N'},
            {'N','N','A','A','A','A','N','A','N','N','N'},
            {'N','N','N','N','N','A','N','N','A','N','A'},
            {'N','A','A','A','A','A','A','N','N','N','N'},
            {'N','A','A','A','W','A','A','B','A','A','N'},
            {'N','A','N','B','A','N','A','A','N','N','W'}
        };

        for(int yi = 10; yi >=1; yi--){
            for(int xi = 1; xi <=10; xi++){
                testBoard.gameBoard[yi][xi].setType(gameBoard[11-xi][yi]);
            }
        }
        System.out.println(testBoard);
        return testBoard;
    }
}
