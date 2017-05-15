package no;
import o3.OrdnetLenkeliste;

public class IDPool {

	private OrdnetLenkeliste<Integer> pool;
	private int size = 0; 
	public int utvid = 100;
		
	public IDPool(int size) {
		pool = new OrdnetLenkeliste<Integer>();
		increseSize(size);
	}
	
	public IDPool() {
		this(100);
	}
	
	private void increseSize(int size){
		for (int i = size; i >= 0; i--)
			pool.settInn(this.size + i);
		this.size += size;
	}
	
	public int getID() {
		if (pool.erTom())
			increseSize(100);
		return pool.fjern();
	}
	
	public void addID(int id) {
		pool.settInn(id);
	}
	
}
