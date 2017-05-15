
public class Prosessor {

	final static int FlOpPerSlynge = 8;
	
	final int kjerner;
	final float klokkehastighet;

	public Prosessor(float klokkehastighet, int kjerner) {
		this.klokkehastighet = klokkehastighet;
		this.kjerner = kjerner;
	}

	public float flops() {
		return FlOpPerSlynge * kjerner * klokkehastighet;
	}

}
