
import krypto.Kanal;
import krypto.Operasjonssentral;

public class Hovedprogram {

	private static Telegrafist[] telegrafister = new Telegrafist[3];
	private static Kryptograf[] kryptografer = new Kryptograf[50];
	
	private static Monitor monitor1;
	private static Monitor monitor2;
	
	public static void main(String[] args) throws InterruptedException {
		monitor1 = new Monitor();
		monitor2 = new Monitor();
		
		Operasjonssentral ops = new Operasjonssentral(telegrafister.length);
		Kanal[] kanaler = ops.hentKanalArray();
		
		for(int i=0; i < telegrafister.length; i++){
			telegrafister[i] = new Telegrafist(monitor1, kanaler[i]);
			telegrafister[i].begynne();
		}

		for(int i=0; i < kryptografer.length; i++){
			kryptografer[i] = new Kryptograf(monitor1, monitor2);
			kryptografer[i].begynne();
		}
		
		new Operasjonsleder(monitor2);
	}
	
	public static void iAmDone(Arbeider arbeider) {
		if (arbeider instanceof Telegrafist){
			for(Telegrafist telegrafist : telegrafister)
				if (!telegrafist.isDone())
					return;
			monitor1.makeDone();
		} else if (arbeider instanceof Kryptograf){
			for(Telegrafist telegrafist : telegrafister)
				if (!telegrafist.isDone())
					return;
			monitor2.makeDone();
		} 
		// TODO: all are done	
	}
	
}
