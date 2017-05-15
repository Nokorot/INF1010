public class Koe<T> extends Stabel<T> {
	
	private Par<T> bottom;

	public Koe(){}
	
	public Koe(Koe<T> other){
		super(other);
		this.bottom = other.bottom;
	}
	
	public void settInn(T element) {
		if (storrelse() == 0)
			bottom = topp = new Par<T>(element, null);
		else
			bottom = bottom.next = new Par<>(element, null);
		size++;
	}
	
}
