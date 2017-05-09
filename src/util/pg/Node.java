package util.pg;

public abstract class Node {
	
	protected Node parent;
	protected TipoNodo tipo;
	
	Node(){
		parent = null;
	}

	protected void setParent(Node p){
		this.parent = p;
	}
	
	protected Node getParent(){
		return this.parent;
	}

	public abstract boolean resolve();
	
}