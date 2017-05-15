package o5; 
import java.io.FileNotFoundException;
import java.util.Scanner;

import o3.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		String menu = "[quit] or [q]: to quit the program. \n"
				+ "[read *.in]: read in the file \"*.in\" \n"
				+ "[solve x y]: solves the maze from position (x, y) \n";
		
		System.out.println(menu);
		
		Scanner scan = new Scanner(System.in);
		
		Labyrint labyrint = null;
		
		main:
		while(true){
			System.out.print(">> ");
			String in = scan.nextLine();
			System.out.println();
			String[] inn = in.split(" ");
			switch (inn[0]) {
			case "quit":
			case "q":
				break main;
			case "read":
				try{
					labyrint = Labyrint.readFromFile(inn[1]);
				}catch (FileNotFoundException e) {
					System.err.println("The file " + inn[1] + " was not found");
					break;
				}
				System.out.println(labyrint);
				break;
			case "solve":
				if (labyrint == null){
					System.err.println("You need to read in a maze.");
					break;
				}
				try{
					int x = Integer.parseInt(inn[1]);
					int y = Integer.parseInt(inn[2]);
					if (x < 0 || x >= labyrint.width || y < 0 || y >= labyrint.height)
						System.err.println("You have given a position outside the maze.");
					labyrint.solve(x, y);
					labyrint.showShortestWayOut();
					System.out.println(labyrint.shortestWayOut());
				}catch (NumberFormatException e) {
					System.err.println("You have not given a number.");
				}
				break;
			default:
				System.err.println("Comand " + inn[0] + "is not known.");
				System.out.println(menu);
			}
			
		}
		
		
	}
	
	
}
