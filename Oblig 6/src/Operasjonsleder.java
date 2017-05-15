import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import krypto.Kanal;

public class Operasjonsleder extends Arbeider {
	
	private OrdnetLenkeliste<KanalData> data = new OrdnetLenkeliste<>();
	
	public Operasjonsleder(Monitor monitor) {
		Melding melding;
		while ((melding = monitor.fjern()) != null){
			Kanal kanal = melding.getKanal();
			boolean ny = true;
			for (KanalData kd : data){
				if(kd.kanal.equals(kanal)){
					kd.settInn(melding);
					ny = false;
				}
			}
			if (ny){
				KanalData kd = new KanalData(kanal);
				kd.settInn(melding);
				data.settInn(kd);
			}
			
			System.out.println(melding.getData());
			
		}
		
		System.out.println("Done!!");
		
		try {
			PrintWriter writer = new PrintWriter("filename.txt", "utf-8");
			for (KanalData kd : data){
				writer.print(kd.hentMelinger() + "\n\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public class KanalData extends OrdnetLenkeliste<Melding> implements Comparable<KanalData> {
 
		private Kanal kanal;
		
		public KanalData(Kanal kanal) {
			this.kanal = kanal;
		}

		public String hentMelinger() {
			StringBuilder builder = new StringBuilder();
			for (Melding melding : this)
				builder.append(melding.getData() + "\n");
			return builder.toString();
		}
		
		public int compareTo(KanalData data) {
			return kanal.hentId() - data.kanal.hentId();
		}
		
	}

}

