package o5; 
import o3.DynamiskTabell;
import o3.*;

public abstract class Rute {
	
	private Labyrint labyrint;
	private int x, y;
	
	public Rute(Labyrint labyrint, int x, int y) {
		this.labyrint = labyrint;
		this.x = x;
		this.y = y;
	}
	
	public abstract char symbol();
	
	public boolean isOpen(){
		return true;
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
	
	DynamiskTabell<Koe<Rute>> solve(DynamiskTabell<Koe<Rute>> utveier, Stabel<Rute> vei) {
		//Koe<Rute> nyVei = new Koe<Rute>(vei);
		vei.settInn(this);
		
		
		if (this instanceof Aapning){
			utveier.settInn(new Koe<Rute>(vei));
			return utveier;
		}
		for (Rute nabo : naboer())
			if (nabo.isOpen() && !vei.contains(nabo))
				utveier = nabo.solve(utveier, vei);
		
		vei.pop();
		return utveier;
	}
	
	public String toString() {
		return "("+x+","+y+")";
	}
}
