package ubc.cosc322;

public class State {
	// game state
	private Coor b1 = new Coor(1,2); Coor b2 = new Coor(1,3); Coor b3 = new Coor(1,4); Coor b4 = new Coor(1,5);
	private Coor w1 = new Coor(2,2); Coor w2 = new Coor(2,3); Coor w3 = new Coor(2,4); Coor w4 = new Coor(2,5);
	private Coor[] stateCoor = {b1, b2, b3, b4, w1, w2, w3, w4};
	
	// by the nature of this game, we manually created the state for the game 
	public State() {
		
	}
	
	// setter
	public void updateState(Coor original_Position, Coor new_Position) {
		for (Coor co : this.stateCoor) {
			if (co.getCoor()[0] == original_Position.getCoor()[0] && co.getCoor()[1] == original_Position.getCoor()[1]) {
				co.setCoor(new_Position);
			}
		} 	
	}
	
	public void updateState(int[] original_Position, int[] new_Position) {
		for (Coor co : this.stateCoor) {
			if (co.getCoor()[0] == original_Position[0] && co.getCoor()[1] == original_Position[1]) {
				co.setCoor(new_Position[0], new_Position[1]);
			}
		} 	
	}
	
	// getter
	// get black queen
	public Coor[] getBlackState() {
		Coor[] blackState = {this.stateCoor[0], this.stateCoor[1], this.stateCoor[2], this.stateCoor[3]};
		return blackState;
	}
 	
	
	// get white queen
	public Coor[] getWhiteState() {
		Coor[] whiteState = {this.stateCoor[4], this.stateCoor[5], this.stateCoor[6], this.stateCoor[7]};
		return whiteState;
	}
	
}
