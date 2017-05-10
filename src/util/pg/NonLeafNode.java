package util.pg;

import java.util.ArrayList;

public class NonLeafNode extends Node {

	protected ArrayList<Node> children;
	
	public NonLeafNode(){
		super();
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
}
