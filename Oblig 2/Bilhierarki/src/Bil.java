
public abstract class Bil {

	private String kjennetegn;

	public Bil(String kjennetegn) {
		this.kjennetegn = kjennetegn;
	}
	
	/**
	 * 
	 * @return kjennetegn: Bilens kjennetegn, eller registreringsnummer.
	 */
	public String Kjennetegn() { return kjennetegn; }

	/**
	 * 
	 * @param kjennetegn: Bilens kjennetegn, eller registreringsnummer.
	 */
	public void setKjennetegn(String kjennetegn) { this.kjennetegn = kjennetegn; }
	
	public String toString() {
		return "Type motorvogn:\t  " + this.getClass().getName() 
				+ "\nReg.nr: " + kjennetegn;
	}
	
}
