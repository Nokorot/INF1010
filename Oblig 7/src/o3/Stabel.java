package o3;

import java.util.Iterator;

public class Stabel<T> implements Liste<T> {
	
	protected Par<T> topp;
	protected int size;
	
	public Stabel() {}
	
	public Stabel(Stabel<T> other){
		this.topp = other.topp;
		this.size = other.size;
	}
	
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private Par<T> next = topp;
			
			public boolean hasNext() {
				return next != null;
			}

			public T next() {
				T value = next.value;
				next = next.next;
				return value;
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
		 topp = new Par<T>(element, topp);
		 size++;
	}
	
	public T pop() {
		if (size() < 1)
			throw new TomListeUnntak();
		T old = topp.value;
		topp = topp.next;
		size--;
		return old;
	}
	
	public void clear() {
		topp = null;
	}
	
	class Par<T> {
		Par<T> next;
		T value;
		
		public Par(T value, Par<T> next) {
			this.value = value;
			this.next = next;
		}
	}

	public boolean contains(T element) {
		for (T e : this)
			if (e == element)
				return true;
		return false;
	}
	
}
