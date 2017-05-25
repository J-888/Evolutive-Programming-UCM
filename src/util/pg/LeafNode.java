package util.pg;

import java.util.ArrayList;
import java.util.HashSet;

import operadores.fitness.FitnessPr3MuxN;

public class LeafNode extends Node {
	
	private int entrada;
	
	public LeafNode(int entrada){
		super();
		parent = null;
		this.numNodos = 1;
		this.altura = 1;
		this.entrada = entrada;
		this.tipo = TipoNodo.ENTRADA;
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

	public void nodificar(HashSet<Node> nodosh1, int puertas) {
		if(puertas == -1 || puertas == 0) //add si todos o solo terminales
			nodosh1.add(this);
	}

	public int getNumEntradas() {
		return 0;
	}
	
	
}
