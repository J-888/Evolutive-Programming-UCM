package util.pg;

import java.util.ArrayList;

import operadores.fitness.FitnessPr3MuxN;

public class LeafNode extends Node {
	
	private int entrada;
	
	public LeafNode(int entrada){
		super();
		this.numNodos = 1;
		this.altura = 1;
		this.entrada = entrada;
	}

	public boolean resolve() {
		return FitnessPr3MuxN.valoresSelectsYEnts.get(entrada) == 1;
	}

	public Node clone() {
		LeafNode ret = new LeafNode(entrada);
		
		ret.setAltura(altura);
		ret.setNumNodos(numNodos);
		ret.setTipo(tipo);
		
		return ret;
	}

	public boolean isLeaf() {
		return true;
	}

	public String toString() {
		return String.valueOf(entrada);
	}
	
}
