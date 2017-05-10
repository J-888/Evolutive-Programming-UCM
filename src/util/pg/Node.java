package util.pg;

public abstract class Node {
	
	protected Node parent;
	protected int altura;
	protected int numNodos;
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

	public void setTipo(TipoNodo tipo){
		this.tipo = tipo;
	}
	
	public void setAltura(int altura){
		this.altura = altura;
	}
	
	public void setNumNodos(int numNodos){
		this.numNodos = numNodos;
	}
	
	public abstract Node clone();
	public abstract boolean resolve();
	
}