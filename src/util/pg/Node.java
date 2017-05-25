package util.pg;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Node {
	
	protected Node parent;
	protected int altura;
	protected int numNodos;
	protected TipoNodo tipo;
	protected int numHijo;
	
	public int getNumHijo() {
		return numHijo;
	}

	public void setNumHijo(int numHijo) {
		this.numHijo = numHijo;
	}

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
	
	public void actualizarArbol(){
		if(!isLeaf()){
			ArrayList<Node> child = ((NonLeafNode)this).getChildren();
			for (int i = 0; i < child.size(); i++) {
				child.get(i).actualizarArbol();
				numNodos += child.get(i).getNumNodos() + 1;
				if (child.get(i).getAltura() > altura + 1)
					altura = child.get(i).getAltura() + 1;
			}
		}
	}
	
	public abstract String toString();
	public abstract Node clone();
	public abstract boolean resolve();
	public abstract boolean isLeaf();
	//Devuelve todos los nodos del arbol con n puertas. Si puertas es -1, devuelve todos los del arbol. Detalle: Las entradas(terminales) se interpretan como 0 puertas,
	//Para no confundir con el not que tiene una.
	public abstract void nodificar(HashSet<Node> nodosh1, int puertas);
	
}