package o3;

public class Koe<T> extends Stabel<T> {
	
	private Par<T> bottom;

	public Koe(){}

	public Koe(Koe<T> other){
		super(other);
		this.bottom = other.bottom;
	}
	
	public Koe(Stabel<T> stabel){
		for(T element : stabel)
			super.settInn(element);
	}
	
	public void settInn(T element) {
		if (size() == 0)
			bottom = topp = new Par<T>(element, null);
		else
			bottom = bottom.next = new Par<>(element, null);
		size++;
	}
	
	public String toString() {
		StringBuilder bulder = new StringBuilder();

		for (T t : this)
			bulder.append("    " + t.toString() + "\n");
		
		return bulder.toString();
	}
	
}
