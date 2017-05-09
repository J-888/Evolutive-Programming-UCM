package util.pg;

public class LeafNode extends Node {
	
	private boolean valor;
	
	public LeafNode(boolean valor){
		super();
		this.valor = valor;
	}

	public boolean resolve() {
		return valor;
	}
	
}
