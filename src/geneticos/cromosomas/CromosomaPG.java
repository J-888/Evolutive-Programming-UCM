package geneticos.cromosomas;

import java.util.ArrayList;

import util.pg.Node;

public class CromosomaPG extends Cromosoma {

	private Node arbol;
	
	public CromosomaPG(Node arbol){
		this.arbol = arbol;
	}
	
	public CromosomaPG clone() {
		return new CromosomaPG(arbol.clone());
	}

	public ArrayList<?> toFenotipo() {
		ArrayList<Node> ret = new ArrayList<Node>(1);
		ret.add(this.arbol);
		return ret;
	}

}
