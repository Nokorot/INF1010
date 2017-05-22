package o5; 

public class HvitRute extends Rute {

	public HvitRute(Labyrint labyrint, int x, int y) {
		super(labyrint, x, y);
	}
	
	public char symbol() {
		return ' ';
	}

	public boolean isOpen() {
		return true;
	}
	
}
