import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Regneklynge {

	
	private int rackSize;
	
	private List<Rack> racks = new ArrayList<Rack>();
	
	public Regneklynge(int rackSize) {
		this.rackSize = rackSize;
	}
	
	public void settInnNode(Node node) {
		for (Rack rack : racks)
			if (rack.addNode(node))
				return; 
		Rack r = new Rack(rackSize);
		r.addNode(node);
		racks.add(r);
	}
	
	public float flops(){
		float result = 0;
		for (Rack r : racks)
			result += r.flops();
		return result;
	}
	
	public int noderMedNokMinne(int paakrevdMinne){
		int noder = 0;
		for (Rack r : racks)
			noder += r.noderMedNokMinne(paakrevdMinne);
		return noder;
	}

	public int anntalRack() {
		return racks.size();
	}
	
	public static Regneklynge fraFil(String file) throws NumberFormatException, FileNotFoundException {
		Scanner scan = new Scanner(new File(file));
		Regneklynge klynge = null;
		
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			if (line.startsWith("#"))
				continue;
			
			if (klynge == null){
				int rackSize = Integer.parseInt(  line  );
				klynge = new Regneklynge(rackSize);
				continue;
			}
			
			String[] inp = line.split(" ");
			int anntall = Integer.parseInt( inp[0] );
			int minne = Integer.parseInt( inp[1] );
			
			Prosessor[] prosesors = new Prosessor[(inp.length - 2) / 2]; 
			for (int j = 0; j < prosesors.length; j++){
				int kjerner = Integer.parseInt( inp[j*2+2] );
				float klokkehastighet = Float.parseFloat( inp[j*2+3] );
				prosesors[j] = new Prosessor(klokkehastighet, kjerner);
			}
			
			for (int i = 0; i < anntall; i++)
				klynge.settInnNode(new Node(minne, prosesors));
		}
		
		return klynge;
	}
}
