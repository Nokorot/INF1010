package o3;

public class OrdnetLenkeliste<T extends Comparable<T>> extends Stabel<T> {
	
	public void settInn(T element) {
		Par<T> next = topp;

		if (topp == null || element.compareTo((T) next.value) <= 0) {
			topp = new Par<T>(element, next);
			size++;
			return;
		}
		
		while (next.next != null){
			if (element.compareTo((T) next.next.value) <= 0){
				next.next = new Par<T>(element, next.next);
				size++;
				return;
			}
			next = next.next;
		}
		next.next = new Par<T>(element, null);
		size++;
	}
	
}
