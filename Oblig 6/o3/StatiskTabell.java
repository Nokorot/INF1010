import java.util.Iterator;

public class StatiskTabell<T> implements Tabell<T> {

	public T[] tabell;
	protected int size;
	
	@SuppressWarnings("unchecked")
	public StatiskTabell(int length){
		tabell = (T[]) new Object[length];
	}
	
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int count = 0;
			
			public boolean hasNext() {
				return count < size;
			}

			public T next() {
				return tabell[count++];
			}
		};
	}

	public int storrelse() {
		return size;
	}

	public boolean erTom() {
		return size == 0;
	}

	public void settInn(T element) {
		if (size >= tabell.length )
			throw new FullTabellUnntak(size);
		tabell[size++] = element;
	}

	public T hentFraPlass(int plass) {
		if (plass < 0 || plass >= size)
			throw new UgyldigPlassUnntak(plass, size);
		return tabell[plass];
	}

}
