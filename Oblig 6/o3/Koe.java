public class Koe<T> extends Stabel<T> {
	
	private Par<T> bottom;

	public void settInn(T element) {
		if (storrelse() == 0)
			bottom = topp = new Par<T>(element, null);
		else
			bottom = bottom.next = new Par<>(element, null);
		size++;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (T object : this)
			builder.append(object.toString() + ", ");
		
		return builder.toString();
	}
	
}
