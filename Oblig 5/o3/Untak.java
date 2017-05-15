package o3;

class TomListeUnntak extends RuntimeException {
	TomListeUnntak() {
		super("Listen er tom");
	}
}

class UgyldigPlassUnntak extends RuntimeException {
	UgyldigPlassUnntak(int plass, int storrelse) {
		super(String.format("Plass: %d, storrelse: %d", plass, storrelse));
	}
}

class FullTabellUnntak extends RuntimeException {
	FullTabellUnntak(int storrelse) {
		super(String.format("Storrelse: %d", storrelse));
	}
}
