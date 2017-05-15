import java.util.Iterator;

public class OrdnetTre<T extends Comparable<T>> implements Liste<T> {

	private int size = 0;

	private Node<T> topp;

	public OrdnetTre() {
	}
	
	public Koe<T> Leafs(){
		Koe<T> koe = new Koe<>();
		
		if (topp != null)
			topp.leafs(koe);
		
		return koe;
	}

	public Iterator<T> iterator() {
		return Leafs().iterator();
	}

	public int storrelse() {
		return size;
	}

	public boolean erTom() {
		return topp == null;
	}

	public void settInn(T element) {
		if (erTom())
			topp = new Node<T>(element);
		else
			topp.setInn(element);
	}

	public T fjern() {
		if (erTom())
			return null;
		Node current = topp;
		while (current.left != null)
			current = current.left;
		return (T) current.object;
	}

	public class Node<T extends Comparable<T>> {
		Node<T> left, right;

		T object;

		private Node(T object) {
			this.object = object;
		}

		private Node(T object, Node<T> left, Node<T> right) {
			this.object = object;
			this.left = left;
			this.right = right;
		}
		
		private void leafs(Koe<T> koe){
			if (left != null)
				left.leafs(koe);
			koe.settInn(object);
			if (right != null)
				right.leafs(koe);
		}

		private void setInn(T object) {
			if (object.compareTo(this.object) <= 0) {
				if (left == null)
					left = new Node<T>(object);
				else
					left.setInn(object);
			} else {
				if (right == null)
					right = new Node<T>(object);
				else
					right.setInn(object);
			}

		}

	}
	
	public static void main(String[] args) {
		OrdnetTre<Integer> nums = new OrdnetTre<>();

		nums.settInn(1);
		nums.settInn(6);
		nums.settInn(2);
		nums.settInn(9);
		
		for (int i : nums){
			System.out.println(i);
		}
		
		System.out.println(nums);
		
	}

}
