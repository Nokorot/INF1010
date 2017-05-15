package o5; 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import o3.*;

public class Labyrint {

	public Rute[][] labyrint;
	public int width, height;
	
	private Labyrint() {
	}
	
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
	
	public DynamiskTabell<Koe<Rute>> solve(int x, int y){
		DynamiskTabell<Koe<Rute>> veier = new DynamiskTabell<>(5);
		veier.setUtvidelse(5);
		
		Stabel<Rute> vei = new Stabel<>();
		
		return labyrint[x][y].solve(veier, vei);//.asArray();
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

	public void showShortestWayOut(int x, int y) {
		DynamiskTabell<Koe<Rute>> utveier = this.solve(x, y);
		
		System.out.println(utveier.size());
		
		Koe<Rute> kortest = utveier.get(0);
		for (Koe<Rute> s : this.solve(x, y))
			if (kortest.size() > s.size())
				kortest = s;
		this.showSolotion(kortest);
	}
	
}
