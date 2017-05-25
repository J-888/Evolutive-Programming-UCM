package util.pg;

import java.util.ArrayList;
import java.util.HashSet;

public class NonLeafNode extends Node {

	protected ArrayList<Node> children;
	
	public NonLeafNode(){
		super();
		parent = null;
		children = new ArrayList<>();
	}
	
	public void setChildren(ArrayList<Node> nuevosHijos){
		children = nuevosHijos;
	}
	
	public void addChildren(Node c){
		this.children.add(c);
		c.setParent(this);
	}

	public ArrayList<Node> getChildren(){
		return this.children;
	}
	
	public boolean resolve() {
		return tipo.resolve(children);
	}

	public Node clone() {
		NonLeafNode ret = new NonLeafNode();
		
		ArrayList<Node> hijosclonados = new ArrayList<Node>(children.size());
		for(int i = 0; i < children.size(); i++){
			Node hijo = children.get(i).clone();
			hijo.setParent(ret);
			hijosclonados.add(hijo);
		}
		
		ret.setAltura(altura);
		ret.setNumNodos(numNodos);
		ret.setTipo(tipo);
		ret.setChildren(hijosclonados);
		
		return ret;
	}

	public boolean isLeaf() {
		return false;
	}

	public String toString() {
		String cad = "";
		cad += this.tipo.toString();
		cad += "(";
		for(int i = 0; i < children.size(); i++){
			cad += children.get(i).toString();
			if(i != children.size() - 1)
				cad += ",";
		}
		cad += ")";
		
		return cad;
	}

	public void nodificar(HashSet<Node> nodosh1, int puertas) {
		if(puertas == -1 || puertas == children.size()) // add si todos o si se buscan con las puertas que tiene este nodo
			nodosh1.add(this);
		
		for(int i = 0; i < children.size(); i++)
			children.get(i).nodificar(nodosh1, puertas);
	}

	public int getNumEntradas() {
		return children.size();
	}
}
