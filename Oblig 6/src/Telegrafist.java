import krypto.Kanal;

public class Telegrafist extends Arbeider {

	private static int TelegrafistCounter = 0;

	private Monitor monitor;
	private Kanal kanal;
	private int telegrafistnr = TelegrafistCounter++;

	public Telegrafist(Monitor monitor, Kanal kanal) {
		this.monitor = monitor;
		this.kanal = kanal;
	}

	private void lytt() {
		String data;
		while ((data = kanal.lytt()) != null) {
			Melding kryptertMeling = new Melding(data, kanal);
			monitor.settInn(kryptertMeling);
		}
		iAmDone();
	}

	public void begynne() {
		new Thread(() -> lytt(), "Telegrafist nr.: " + telegrafistnr).start();
	}

}
