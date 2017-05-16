package geneticos.cromosomas;

import java.util.ArrayList;

import util.pg.Node;

public class CromosomaPG extends Cromosoma {

	private Node arbol;
	private int nEntradas;
	private int profMax;
	
	public CromosomaPG(Node arbol, int nE, int pm){
		this.arbol = arbol;
		this.nEntradas = nE;
		this.profMax = pm;
	}
	
	public CromosomaPG clone() {
		return new CromosomaPG(arbol.clone(), nEntradas, profMax);
	}

	public ArrayList<?> toFenotipo() {
		ArrayList<Node> ret = new ArrayList<Node>(1);
		ret.add(this.arbol);
		return ret;
	}

	public Node getArbol() {
		return arbol;
	}

	public void setArbol(Node arbol) {
		this.arbol = arbol;
	}

	public int getnEntradas() {
		return nEntradas;
	}

	public void setnEntradas(int nEntradas) {
		this.nEntradas = nEntradas;
	}

	public int getProfMax() {
		return profMax;
	}

	public void setProfMax(int profMax) {
		this.profMax = profMax;
	}
	
}
