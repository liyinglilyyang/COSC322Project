package ubc.cosc322;

public class Coor {
	private int x,y;
	private char type;
	// private int index = Integer.MAX_VALUE;//inorder to indentify a minimum step to a node
	private int index = 9;
	// Constructor 
	
	public Coor(int x, int y, char type, int index){
		this(x,y,type);
		this.index = index;
	}

	public Coor(int x, int y, char type){
		this.x = x;
		this.y = y;
		this.type = type;//A for arrow, B for black, W for White, N for None
	}
	
	public Coor(int x, int y) {
		this(x,y,'N');
	}

	public Coor() {
		this(0,0,'N');
	}
	
	// Getter 
	// return an array storing x, y 
	
	// Setter
	public void setCoor(Coor coor) {
		this.x = coor.getX();
		this.y = coor.getY();
		this.type = coor.getType();
	}
	
	public void setCoor(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public char getType(){
		return type;
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setType(char type){
		this.type = type;
	}
	public int getIndex(){
		return index;
	}

	public void setIndex(int index){
		this.index = index;
	}
	public String toString(){
		return "The Coordinates are X: " + x +" Y: " + y + " Type: " + type + " (Index Equals: " + index + ")";
	}
}
