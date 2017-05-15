package no;

public class Fastlege extends Lege implements Kommuneavtale {

	private int avtalenummer;
	
	public Fastlege(String navn, int avtalenummer) {
		super(navn);
		this.avtalenummer = avtalenummer;
	}
	
	public Fastlege(Lege lege, int avtalenummer) {
		super(lege.navn);
		this.resepter = lege.resepter;
		this.avtalenummer = avtalenummer;
	}

	public int hentAvtalenummer() {
		return avtalenummer;
	}
	
}

interface Kommuneavtale {
    public int hentAvtalenummer();
}
