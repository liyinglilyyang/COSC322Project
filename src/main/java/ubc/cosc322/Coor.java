package ubc.cosc322;

public class Coor {
	private int x,y;
	private char type, owned;
	private int blackDis,whiteDis;
	private int index;
	
	// Constructor 
	public Coor(int x, int y, char type){
		this.x = x;
		this.y = y;
		this.type = type;//A for arrow, B for black, W for White, N for None
		this.owned = 'N';//B for black, W for White, N for None
		this.blackDis = 999;//set initial value to positive infinite (or Integer.MAX_VALUE)
		this.whiteDis = 999;
		
	}
	
	public Coor(int x, int y) {
		this(x,y,'N');
	}

	public Coor() {
		this(0,0,'N');
	}
	
	
	
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
	
	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void setType(char type){
		this.type = type;
	}
	
	public void setBlackDis(int dis){
		this.blackDis = dis;
	}
	
	public void setWhiteDis(int dis){
		this.whiteDis = dis;
	}
	
	public void setOwned(char type){
		this.owned = type;
	}
	
	public void setIndex(int index){
		this.index = index;
	}

	
	// Getter

	public int getIndex(){
		return index;
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
	
	public int getBlackDis() {
		return blackDis;
	}
	
	public int getWhiteDis() {
		return whiteDis;
	}

	public int getOwned() {
		return owned;
	}


	
	// print func
	public String toString(){
		return "The Coordinates are X: " + x +" Y: " + y + " Type: " + type;
	}
	
	// transToString
	public String transCoorToString() {
		return x + "" + y;
	}
}
