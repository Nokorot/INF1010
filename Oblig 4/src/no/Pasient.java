package no;
import o3.Stabel;

public class Pasient {

	private static final IDPool idPool = new IDPool();

	private final int ID;
	private final long fodselsnumer;
	private String navn;
	private String gateadresse;
	private int postnummer;

	private final Stabel<Resept> resepter;

	public Pasient(String navn, long fødselsnummer, String gateadresse, int postnummer) {
		this.ID = idPool.getID();
		this.navn = navn;
		this.fodselsnumer = fødselsnummer;
		this.gateadresse = gateadresse;
		this.postnummer = postnummer;

		resepter = new Stabel<Resept>();
	}

	public int hentId() {
		return ID;
	}

	public String hentNavn() {
		return navn;
	}

	public long hentFodselsnummer() {
		return fodselsnumer;
	}

	public String hentGateadresse() {
		return gateadresse;
	}

	public int hentPostnummer() {
		return postnummer;
	}

	public void leggTilResept(Resept resept) {
		resepter.settInn(resept);
	}

	public Stabel<Resept> hentReseptliste() {
		Stabel<Resept> copy = new Stabel<Resept>(resepter);
		resepter.clear();
		return copy;
	}

	@Override
	public String toString() {
		return "["+ID+"] " + navn + " (" + fodselsnumer + ")";
	}

}
