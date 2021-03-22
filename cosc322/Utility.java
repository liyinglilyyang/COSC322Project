package ubc.cosc322;

public class Utility {
	private int utility; 
	public Utility() {
	}
	
	// Getter
	public int getUtility() {
		return this.utility;
	}
	
	// Setter
	public void setUtility(int utility) {
		this.utility = utility;
	}
	
	// increment
	public void increUtility() {
		this.utility++;
	}
	
	// decrease
	public void decreUtility() {
		this.utility--;
	}
}
