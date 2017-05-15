
public class Personbil extends Fossilbil {
	
	private int seteplasser;

	public Personbil(String kjennetegn, float CO2utslipp, int seteplasser) {
		super(kjennetegn, CO2utslipp);
		this.seteplasser = seteplasser;
	}

	/**
	 * @return Anntall seteplasser bilen er registrert for
	 */
	public int Seteplasser() {
		return seteplasser;
	}

	/**
	 * @param Anntall seteplasser bilen er registrert for
	 */
	public void setSeteplasser(int seteplasser) {
		this.seteplasser = seteplasser;
	}

	public String toString() {
		return super.toString() + 
				"\nSeteplasser: " + seteplasser;
	}
	
}
