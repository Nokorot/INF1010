package no;

public abstract class Resept {

	private static IDPool idPool = new IDPool();

	private int ID = idPool.getID();
	private Legemiddel legemiddel;
	private Lege lege;
	private int pasient;
	private int reit;

	public Resept(Legemiddel legemiddel, Lege lege, int pasient, int reit){
		this.legemiddel = legemiddel;
		this.lege = lege;
		this.pasient = pasient;
		this.reit = reit;
	}

	public int hentId() {
		return ID;
	}

	public Legemiddel hentLegemiddel() {
		return legemiddel;
	}

	public Lege hentLege() {
		return lege;
	}

	public int hentPasientId() {
		return pasient;
	}

	public int hentReit() {
		return reit;
	}

	/**
	 * Bruker resepten én gang. Returner false om resepten er oppbrukt, ellers
	 * returnerer den true.
	 *
	 * @return om resepten kunne brukes
	 */
	public boolean bruk() {
		if (reit > 0) {
			reit--;
			return true;
		}
		return false;
	}

	/**
	 * Returnerer reseptens farge. Enten "blaa" eller "hvit".
	 *
	 * @return reseptens farge
	 */
	abstract public String farge();

	/**
	 * Returnerer prisen pasienten maa betale.
	 *
	 * @return prisen pasienten maa betale
	 */
	abstract public double prisAaBetale();

	@Override
	public String toString() {
		return "["+ID+"] " + legemiddel.hentNavn() + "( " + farge() + " )";
	}

	public static class BlaaResept extends Resept {

		private static final double subsidusjon = 0.75;

		public BlaaResept(Legemiddel legemiddel, Lege lege, int pasient, int reit) {
			super(legemiddel, lege, pasient, reit);
		}

		public String farge() {
			return "Blå";
		}

		public double prisAaBetale() {
			return this.hentLegemiddel().hentPris() * (1 - subsidusjon);
		}

	}

	public static class HvitResept extends Resept {

		private static final double subsidusjon = 0.25;

		public HvitResept(Legemiddel legemiddel, Lege lege, int pasient, int reit) {
			super(legemiddel, lege, pasient, reit);
		}

		public String farge() {
			return "Hvit";
		}

		public double prisAaBetale() {
			return this.hentLegemiddel().hentPris() * (1 - subsidusjon);
		}

	}

}
