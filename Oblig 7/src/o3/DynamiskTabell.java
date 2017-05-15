package o3;

public class DynamiskTabell<T> extends StatiskTabell<T> {
	
	private int utvidelse = 100;
	
	public DynamiskTabell() {
		super(100);
	}
	
	public DynamiskTabell(int length){
		super(length);
	}
	
	@SuppressWarnings("unchecked")
	public void settInn(T element) {
		if (size >= tabell.length ){
			T[] nyTabell = (T[]) new Object[tabell.length + utvidelse];
			for (int i = 0; i < size; i++)
				nyTabell[i] = tabell[i];
			tabell = nyTabell;
		}
		super.settInn(element);
	}
		
	public void setUtvidelse(int utvidelse) {
		this.utvidelse = utvidelse;
	}
}
