
public class Elbil extends Bil {

	private float batteri;
	
	public Elbil(String kjennetegn, float batteri) {
		super(kjennetegn);
		this.batteri = batteri;
	}
	
	/**
	 * 
	 * @return Batterikaasitet målt i kWh
	 */
	public float getBatteri() { return batteri; }

	/**
	 * 
	 * @param Batterikaasitet målt i kWh
	 */
	public void setBatteri(float batteri) { this.batteri = batteri; }

	public String toString() {
		return super.toString() + 
				"\nBatterikapasitet (kWh): " + batteri;
	}
}
