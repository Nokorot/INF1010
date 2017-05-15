package o3;

import java.lang.reflect.Array;
import java.util.Iterator;

public class StatiskTabell<T> implements Tabell<T> {

	public T[] tabell;
	protected int size;
	
	public boolean ordnet = true;
	
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

	public int size() {
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
	
	public void fjern(int i) {
		if (ordnet)
			for (int j = i; j < size; j++)
				tabell[j] = tabell[j+1];
		else
			tabell[i] = tabell[size-1];
		size--;
	}
	
	public void fjern(T element) {
		for (int i = 0; i < this.size; i++)
			if (tabell[i] == element)
				this.fjern(i);
	}

	public T get(int plass) {
		if (plass < 0 || plass >= size)
			throw new UgyldigPlassUnntak(plass, size);
		return tabell[plass];
	}
	
	@SuppressWarnings("unchecked")
	public T[] asArray() {
		T[] copy = (T[]) (new Object[size]);
		for (int i = 0; i < size; i++)
			copy[i] = tabell[i];
		return copy;
	}

}
