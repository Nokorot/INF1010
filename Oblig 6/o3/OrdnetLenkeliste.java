public class OrdnetLenkeliste<T extends Comparable<T>> extends Stabel<T> {
	
	public void settInn(T element) {
		Par<T> current = topp;

		if (topp == null || element.compareTo((T) current.value) <= 0) {
			topp = new Par<T>(element, current);
			size++;
			return;
		}
		
		while (current.next != null){
			if (element.compareTo((T) current.next.value) <= 0){
				current.next = new Par<T>(element, current.next);
				size++;
				return;
			}
			current = current.next;
		}
		current.next = new Par<T>(element, null);
		size++;
	}
	
}
