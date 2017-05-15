
public class Lastebil extends Fossilbil {

	private float nyttelast;
	
	public Lastebil(String kjennetegn, float CO2utslipp, float nyttelast) {
		super(kjennetegn, CO2utslipp);
		this.nyttelast = nyttelast;
	}

	/**
	 * @return Lovlig nyttelast målt i kg
	 */
	public float getNyttelast() {
		return nyttelast;
	}

	/**
	 * @param Lovlig nyttelast målt i kg
	 */
	public void setNyttelast(float nyttelast) {
		this.nyttelast = nyttelast;
	}

	public String toString() {
		return super.toString() + 
				"\nNyttelast: " + nyttelast;
	}
	
}
