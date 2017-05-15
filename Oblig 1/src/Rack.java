import java.util.ArrayList;
import java.util.List;

public class Rack {

	final int size;
	
	private List<Node> nodes;
	
	public Rack(int size) {
		this.size = size;
		nodes = new ArrayList<Node>(size);
	}
	
	public boolean addNode(Node node){
		if (nodes.size() < size) {
			nodes.add(node);
			return true;
		}
		return false;
		
	}
	
	float flops(){
		int result = 0;
		for (Node n : nodes)
			result += n.flops();
		return result;
	}

	public int noderMedNokMinne(int paakrevdMinne) {
		int noder = 0;
		for (Node n : nodes)
			if (n.nokMinne(paakrevdMinne))
				noder += 1;
		return noder;
	}
	
}

