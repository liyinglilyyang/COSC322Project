package ubc.cosc322;

public class Coor {
	private int x,y;
	
	// Constructor 
	public Coor(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// Getter 
	// return an array storing x, y 
	public int[] getCoor() {
		int[] coor = {this.x, this.y};
		return coor;
	}
	
	// Setter
	public void setCoor(Coor coor) {
		this.x = coor.getCoor()[0];
		this.y = coor.getCoor()[1];
	}
	
	public void setCoor(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
