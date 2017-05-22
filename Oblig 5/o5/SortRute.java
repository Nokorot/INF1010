package o5; 

public class SortRute extends Rute{

	public SortRute(Labyrint labyrint, int x, int y) {
		super(labyrint, x, y);
	}
	
	public char symbol() {
		return '#';
	}

	public boolean isOpen() {
		return false;
	}

}
