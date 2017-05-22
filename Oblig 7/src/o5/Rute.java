package o5; 
import o3.*;
import o5.Labyrint.UtVei;

public class Rute {

	public static enum TileType {
		Black('#', true),
		White(' ', false);
		
		private char symbol;
		private boolean solid;
		
		TileType(char symbol, boolean solid) {
			this.symbol = symbol;
			this.solid = solid;
		}
	}
	
	private Labyrint labyrint;
	private int x, y;
	public TileType tileType;
	
	boolean isSurrounded = true;
	
	public Rute(Labyrint labyrint, TileType tileType, int x, int y) {
		this.labyrint = labyrint;
		this.tileType = tileType;
		this.x = x;
		this.y = y;
	}
	
	public char symbol() {
		return tileType.symbol;
	}
	
	public boolean isSolid(){
		return tileType.solid;
	}

	public int X() {
		return x;
	}

	public int Y() {
		return y;
	}
	
	public Rute nord(){
		if (y > 0)
			return labyrint.labyrint[this.x][this.y-1];
		return null;
	}
	
	public Rute sør(){
		if (y < labyrint.height-1)
			return labyrint.labyrint[this.x][this.y+1];
		return null;
	}
	
	public Rute vest(){
		if (x > 0)
			return labyrint.labyrint[this.x-1][this.y];
		return null;
	}
	
	public Rute øst(){
		if (x < labyrint.width-1)
			return labyrint.labyrint[this.x+1][this.y];
		return null;
	}

	public Stabel<Rute> naboer(){
		Stabel<Rute> naboer = new Stabel<>();
		if (nord() != null) naboer.settInn(nord());
		if (sør() != null) naboer.settInn(sør());
		if (vest() != null) naboer.settInn(vest());
		if (øst() != null) naboer.settInn(øst());
		return naboer;
	}
	
	OrdnetLenkeliste<UtVei> solve(OrdnetLenkeliste<UtVei> utveier, Stabel<Rute> vei) {
		vei.settInn(this);
		
		
		if (this instanceof Aapning){
			utveier.settInn(new UtVei(vei));
		}
		for (Rute nabo : naboer())
			if (!nabo.isSolid() && !vei.contains(nabo))
				utveier = nabo.solve(utveier, vei);
		
		vei.pop();
		return utveier;
	}
	
	public String toString() {
		return "("+x+","+y+")";
	}
}
