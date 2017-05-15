import krypto.Kryptografi;

public class Kryptograf extends Arbeider {

	private static int KryptografCounter = 0;
	
	private int kryptografNr = KryptografCounter++;
	
	private Monitor inn,  out;
	
	public Kryptograf(Monitor inn, Monitor out) {
		this.inn = inn;
		this.out = out;
	}

	private void dekrypter(){
		Melding melding;
		while ((melding = inn.fjern()) != null){
			
			String kryptertData = Kryptografi.dekrypter(melding.getData()); 
			melding.setData(kryptertData);
			
//			System.out.println(kryptertData);
			
			out.settInn(melding);
		}
		iAmDone();
	}
	
	public void begynne() {
		new Thread(() -> dekrypter(), "Kryptograf nr.: " + kryptografNr)
		.start();
	}
	
}
