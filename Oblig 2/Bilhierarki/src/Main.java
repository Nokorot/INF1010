import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static List<Bil> biler = new ArrayList<Bil>();

	public static int asInt(String s){
		return Integer.parseInt(s);
	}
	
	public static float asFloat(String s){
		return Float.parseFloat(s);
	}
	public static void main(String[] args) throws FileNotFoundException, FileFormatExeption {
		
		if (args.length == 0)
			return;
		
		readFromFile(args[0]);
		
		Bil[] aktuelleBiler = bilFilter(args.length > 1 ? args[1] : ""); ;
		
		for (Bil b : aktuelleBiler)
			System.out.println(b.toString() + "\n");
		
		
	}
	
	public static Bil[] bilFilter(String key){
		List<Bil> result = new ArrayList<Bil>();
		
		switch (key) {
		case "":
			result.addAll(biler); 
			break;
		case "EL":
			for (Bil b : biler)
				if (b instanceof Elbil)
					result.add(b);
			break;
		case "FOSSIL":
			for (Bil b : biler)
				if (b instanceof Fossilbil)
					result.add(b);
			break;
		}
		
		return result.toArray(new Bil[result.size()]);
	}
	
	
	@SuppressWarnings("resource")
	private static void readFromFile(String file) throws FileNotFoundException, FileFormatExeption {
		int linecount = 0;
		Scanner scan = new Scanner(new File(file));
		while (scan.hasNextLine()){
			linecount++;
			String line = scan.nextLine();
			String[] words = line.split(" ");
			
			try {
				Bil bil = null;

				switch (words[0]) {
				case "EL":
					bil = new Elbil(words[1], asFloat(words[2]));
					break;
				case "PERSONBIL":
					bil = new Personbil(words[1], asFloat(words[2]), asInt(words[3]));
					break;
				case "LASTEBIL":
					bil = new Lastebil(words[1], asFloat(words[2]), asFloat(words[3]));
					break;
				default:
					throw new FileFormatExeption(file, "\nOn line " + linecount);
				}
				if (bil != null)
					biler.add(bil);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new FileFormatExeption(file, "\nOn line " + linecount);
			}
		}
	}
	
	static class FileFormatExeption extends Exception {
		private static final long serialVersionUID = 7581451598411030947L;

		public FileFormatExeption(String file) {
			this(file, "");
		}

		public FileFormatExeption(String file, String massage) {
			super("In the file \"" + file + "\"." + massage);
		}

	}
	
}
