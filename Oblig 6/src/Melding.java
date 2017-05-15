import krypto.Kanal;

public class Melding implements Comparable<Melding> {
	
	private static int MeldingCounter = 0;
	
	private String data;
	private final Kanal kanal;
	private final int ID = MeldingCounter++;
	
	public Melding(String data, Kanal kanal) {
		this.data = data;
		this.kanal = kanal;
	}

	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}

	public Kanal getKanal() {
		return kanal;
	}
	
	public int getID() {
		return ID;
	}

	public int compareTo(Melding melding) {
		return ID - melding.ID;
	}
	
}
