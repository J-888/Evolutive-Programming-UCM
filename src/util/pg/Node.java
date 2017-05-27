package util.pg;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Node {
	
	protected Node parent;
	protected int altura;
	protected int numNodos;
	protected TipoNodo tipo;
	protected int numHijo;
	protected boolean esRaiz;
	
	public int getNumHijo() {
		return numHijo;
	}

	public void setNumHijo(int numHijo) {
		this.numHijo = numHijo;
	}

	Node(){
		parent = null;
	}

	public void setParent(Node p){
		this.parent = p;
	}
	
	public Node getParent(){
		return this.parent;
	}

	public TipoNodo getTipo(){
		return this.tipo;
	}

	public void setTipo(TipoNodo tipo){
		this.tipo = tipo;
	}

	public int getAltura() {
		return altura;
	}

	public int getNumNodos() {
		return numNodos;
	}
	
	public void setAltura(int altura){
		this.altura = altura;
	}
	
	public void setNumNodos(int numNodos){
		this.numNodos = numNodos;
	}
	
	public boolean getEsRaiz() {
		return esRaiz;
	}

	public void setEsRaiz(boolean esRaiz) {
		this.esRaiz = esRaiz;
	}

	public void actualizarArbol(){
		if(!isLeaf()){
			numNodos = 0;
			altura = 0;
			ArrayList<Node> child = ((NonLeafNode)this).getChildren();
			for (int i = 0; i < child.size(); i++) {
				child.get(i).actualizarArbol();
				numNodos += child.get(i).getNumNodos();
				if (child.get(i).getAltura() >= altura)
					altura = child.get(i).getAltura();
			}
			//aportacion propia
			altura++;
			numNodos++;
		}
		else{
			numNodos = 1;
			altura = 1;
		}
	}
	
	public abstract String toString();
	public abstract Node clone();
	public abstract boolean resolve();
	public abstract boolean isLeaf();
	/*
	 * Devuelve todos los nodos del arbol con n puertas. 
	 * Si puertas es -1, devuelve todos los del arbol. 
	 * Si puertas es -2, devuelve todos los no terminales del arbol.
	 * Detalle: Las entradas(terminales) se interpretan como 0 puertas, para no confundir con el not que tiene una.
	 */
	public abstract void nodificar(HashSet<Node> nodosh1, int puertas);
	public abstract int getNumEntradas();
	
}