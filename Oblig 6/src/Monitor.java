public class Monitor {

	private volatile Koe<Melding> melinger = new Koe<Melding>();

	private boolean done = false;

	public synchronized void settInn(Melding element) {
		melinger.settInn(element);

		notify();
	}

	public synchronized Melding fjern() {
		try {
			while (melinger.erTom()) {
				if (done)
					return null;
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return melinger.fjern();
	}

	public boolean isDone() {
		return done && melinger.erTom();
	}

	public synchronized void makeDone() {
		done = true;
		notifyAll();
	}

}
