package no;
import o3.OrdnetLenkeliste;

public class Legeliste extends OrdnetLenkeliste<Lege> {

	public Lege finnLege(String navn){
		for (Lege lege : this)
			if (lege.navn.equals(navn))
				return lege;
			// if (lege.navn.compareTo(navn) < 0)
		return null;
	}

	public String[] leger(){
		String[] navn = new String[storrelse()];
		int i = 0;
		for (Lege lege : this)
			navn[i++] = lege.navn;
		return navn;
	}

}
