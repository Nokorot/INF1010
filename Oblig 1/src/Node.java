public class Node {

	final int minne;
	final Prosessor[] prosessorer;
	
	public Node(int minne, Prosessor... prosesorer){
		this.minne = minne;
		this.prosessorer = prosesorer;
	}
	
	public Node(int minne, float klokkehastigheten, int... prosesorKjerner) {
		this.minne = minne;
		this.prosessorer = new Prosessor[prosesorKjerner.length];
		int i = 0;
		for (int kjerner : prosesorKjerner){
			this.prosessorer[i++] = new Prosessor(klokkehastigheten,kjerner);
		}
	}

	float flops(){
		float flops = 0;
		for (Prosessor p : prosessorer)
			flops += p.flops();
		return flops;
	}

	public boolean nokMinne(int paakrevdMinne) {
		return paakrevdMinne <= minne;
	}
	
}
