
public class Fossilbil extends Bil {

	private float CO2utslipp;
	
	public Fossilbil(String kjennetegn, float CO2utslipp) {
		super(kjennetegn);
		this.CO2utslipp = CO2utslipp;
	}

	/**
	 * @return CO2-utslipp målt i g/km
	 */
	public float getCO2utslipp() {
		return CO2utslipp;
	}

	/**
	 * @param CO2-utslipp målt i g/km
	 */
	public void setCO2utslipp(float cO2utslipp) {
		CO2utslipp = cO2utslipp;
	}

	public String toString() {
		return super.toString() + 
				"\nCO2-utslipp: " + CO2utslipp;
	}
	
	
}
