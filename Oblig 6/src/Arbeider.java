
public class Arbeider {

	private boolean done = false;
	
	protected void iAmDone() {
		done = true;
		Hovedprogram.iAmDone(this);
	}
	
	public boolean isDone() {
		return done;
	}
	
}
