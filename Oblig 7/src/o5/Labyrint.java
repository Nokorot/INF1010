package o5; 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.concurrent.WorkerStateEvent;
import o3.Koe;
import o3.OrdnetLenkeliste;
import o3.Stabel;
import o5.Rute.TileType;

public class Labyrint {

	public Rute[][] labyrint;
	public int width, height;
	
	private OrdnetLenkeliste<UtVei> utveier;
	
	private Labyrint() {
	}
	
	public Labyrint(int width, int height){
		this.labyrint = new Rute[width][height];
		this.width = width;
		this.height = height;
				
		for (int j = 0; j < height; j++){
			for (int i = 0; i < width; i++){
				this.labyrint[i][j] = new Rute(this, TileType.Black, i, j);
				if (i == 0 || i == width-1 || j == 0 || j == height-1)
					this.labyrint[i][j].isSurrounded = false;
			}
		}
	}
	
//	public Labyrint(Rute[][] labyrint) {
//		this.labyrint = labyrint;
//		this.width = labyrint[0].length;
//		this.height = labyrint.length;
//	}
	
	@SuppressWarnings("resource")
	public static Labyrint readFromFile(String fil) throws FileNotFoundException {
		Labyrint l = new Labyrint();
		
		Scanner scaner = new Scanner(new File(fil));
		
		String[] ssize = scaner.nextLine().split(" ");

		l.height = Integer.parseInt(ssize[0]);
		l.width = Integer.parseInt(ssize[1]);
		
		l.labyrint = new Rute[l.width][l.height];
		
		for (int y = 0; y < l.height; y++){
			char[] line = scaner.nextLine().toCharArray();
			for (int x = 0; x < l.width; x++){
				if (line.length > x && line[x] == '#')
					l.labyrint[x][y] = new SortRute(l, x, y);
				else if (x == 0 || y == 0 || x == l.width - 1 || y == l.height-1)
					l.labyrint[x][y] = new Aapning(l, x, y);
				else
					l.labyrint[x][y] = new HvitRute(l, x, y);
			}
		}
		
		return l;
			
	}
	
	public OrdnetLenkeliste<UtVei> solve(int x, int y){
		utveier = new OrdnetLenkeliste<UtVei>();
		Stabel<Rute> vei = new Stabel<>();
		
		return labyrint[x][y].solve(utveier, vei);
	}
	
	public void showSolotion(Koe<Rute> solution) {
		char[] backgrodnd = new char[(width + 1) * height];
		
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				backgrodnd[x + y * (this.width+1)] = labyrint[x][y].symbol();
			}
			backgrodnd[(y + 1) * (this.width+1) - 1] = '\n';
		}
		
		for (Rute r : solution){
			backgrodnd[r.X() + r.Y() * (this.width+1)] = '-';
		}
		
		System.out.println(new String(backgrodnd));
	}

	public void showShortestWayOut() {
		showSolotion(shortestWayOut());
	}
	
	public void showShortestWayOut(int x, int y) {
		this.solve(x, y);
		this.shortestWayOut();
	}

	public UtVei shortestWayOut() {
		return utveier.get(0);
	}
	
	public static class UtVei extends Koe<Rute> implements Comparable<UtVei> {

		public UtVei(Stabel<Rute> vei) {
			super(vei);
		}

		public int compareTo(UtVei other) {
			return this.size() - other.size();
		}
		
		public String toString() {
			StringBuilder builder = new StringBuilder();
			int count = this.size;
			for (Rute r : this){
				builder.append("(" + r.X() + ", " + r.Y() + ")");
				if (--count > 0)
					builder.append(" --> ");
			}
			
			return builder.toString();
		}
		
	}
	
	public String toString() {
		StringBuilder bulder = new StringBuilder();

		for (int y = 0; y < height; y++){
			bulder.append(String.format("%2d", y));
			for (int x = 0; x < width; x++){
				bulder.append(labyrint[x][y].symbol());
			}
			bulder.append("\n");
		}
		
		return bulder.toString();
	}
	
}
