package ubc.cosc322;

public class Coor {
	private int x,y;
	
	// Constructor 
	public Coor(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// Getter
	public int[] getCoor() {
		int[] coor = {this.x, this.y};
		return coor;
	}
	
	// Setter
	public void setCoor(int[] coor) {
		this.x = coor[0];
		this.y = coor[1];
	}
	
	public void setCoor(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
