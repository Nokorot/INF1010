import java.util.Iterator;

public class Stabel<T> implements Liste<T> {
	
	protected int size;
	protected Par<T> topp;

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

	public int storrelse() {
		return size;
	}

	public boolean erTom() {
		return size == 0;
	}

	public void settInn(T element) {
		 topp = new Par<T>(element, topp);
		 size++;
	}
	
	public T fjern() {
		if (storrelse() < 1)
			throw new TomListeUnntak();
		T old = topp.value;
		topp = topp.next;
		size--;
		return old;
	}
	
	public void clear() {
		
	}
	
	class Par<T> {
		Par<T> next;
		T value;
		
		public Par(T value, Par<T> next) {
			this.value = value;
			this.next = next;
		}
	}
	
}
