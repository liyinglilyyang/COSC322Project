package ubc.cosc322;

public class Action {
	Coor Original_Position;
	Coor New_Position;
	//positions are represented by arraylists;
	
	// Constructor
	public Action(Coor Original_Position, Coor New_Position){
		this.Original_Position = Original_Position;
		this.New_Position = New_Position;
	}
	
	public Action(int ox, int oy, int nx, int ny){
		this.Original_Position = new Coor(ox,oy);
		this.New_Position= new Coor(nx,ny);
		}
	
	// Getter
	// return a 2d array for original_pos and new_pos
	public int[][] getAction(){
		int[][] action = {this.Original_Position.getCoor(), this.New_Position.getCoor()};
		return action;
	}
	
	public int[] getOriginal_Position() {
		return this.Original_Position.getCoor();
	}
	
	public int[] getNew_Position_Position() {
		return this.New_Position.getCoor();
	}
	
	// Setter
	public void setAction(Coor Original_Position, Coor New_Position) {
		this.Original_Position = Original_Position;
		this.New_Position = New_Position;
	}
	
	public void setAction(int ox, int oy, int nx, int ny) {
		this.Original_Position = new Coor(ox,oy);
		this.New_Position= new Coor(nx,ny);
	}
	
	

}
