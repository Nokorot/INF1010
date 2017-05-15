package no;
import o3.Koe;

public class Lege implements Comparable<Lege> {

	protected final String navn;
	protected Koe<Resept> resepter;

	public Lege(String navn) {
		this.navn = navn;

		resepter = new Koe<Resept>();
	}

	public String hentNavn() {
		return navn; }

	public void leggTilResept(Resept resept) {
		resepter.settInn(resept);
	}

	public Koe<Resept> hentReseptliste() {
		return new Koe<>(resepter);
	}

	public int compareTo(Lege annen) {
		return navn.compareTo(annen.navn);
	}

}
