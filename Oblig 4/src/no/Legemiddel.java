package no;

public abstract class Legemiddel {

	private static IDPool idPool = new IDPool();

	private final int ID = idPool.getID();
	private final String navn;
	private double pris;
	private double virkestoff;

	private Legemiddel(String navn, double pris, double virkestoff) {
		this.navn = navn;
		this.pris = pris;
		this.virkestoff = virkestoff;
	}

	public int hentId() {
		return ID;
	}

	public String hentNavn() {
		return navn;
	}

	public double hentPris() {
		return pris;
	}

	public double hentVirkestoff() {
		return virkestoff;
	}

	@Override
	public String toString() {
		return "["+ID+"] " + navn;
	}

	public static class LegemiddelA extends Legemiddel {
		int styrke;

		public LegemiddelA(String navn, double pris, double virkestoff, int styrke) {
			super(navn, pris, virkestoff);
			this.styrke = styrke;
		}

		public int hentNarkotiskStyrke() { return styrke; }

		public String toString() {
			return super.toString() + " ( TypeA )";
		}
	}

	public static class LegemiddelB extends Legemiddel {
		int styrke;

		public LegemiddelB(String navn, double pris, double virkestoff, int styrke) {
			super(navn, pris, virkestoff);
			this.styrke = styrke;
		}

		public int hentVanedannendeStyrke() { return styrke; }

		public String toString() {
			return super.toString() + " ( TypeB )";
		}
	}

	public static class LegemiddelC extends Legemiddel {

		public LegemiddelC(String navn, double pris, double virkestoff) {
			super(navn, pris, virkestoff);
		}

		public String toString() {
			return super.toString() + " ( TypeC )";
		}
	}

}
