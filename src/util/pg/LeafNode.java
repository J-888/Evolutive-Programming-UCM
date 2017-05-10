package util.pg;

import java.util.ArrayList;

public class LeafNode extends Node {
	
	private boolean valor;
	
	public LeafNode(boolean valor){
		super();
		this.numNodos = 0;
		this.valor = valor;
	}

	public boolean resolve() {
		return valor;
	}

	public Node clone() {
		LeafNode ret = new LeafNode(valor);
		
		ret.setAltura(altura);
		ret.setNumNodos(numNodos);
		ret.setTipo(tipo);
		
		return ret;
	}
	
}
