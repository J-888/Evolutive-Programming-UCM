package operadores.mutacion;

import java.util.HashSet;
import java.util.Iterator;

import geneticos.Individuo;
import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaPG;
import operadores.fitness.FitnessPr3MuxN;
import util.Par;
import util.Utiles;
import util.pg.LeafNode;
import util.pg.Node;
import util.pg.NonLeafNode;

public class Subarbol extends FuncionMutacion{

	public static boolean found;
	
	public void mutarInd(Individuo ind) {

		CromosomaPG crom = (CromosomaPG) ind.getCromosoma();

		HashSet<Node> nodosSA1 = new HashSet<Node>();
		HashSet<Node> nodosSA2 = new HashSet<Node>();

		
		Node arbolito = crom.getArbol();
		
		//Buscamos un nodo con dos o mas ramas independientes para poder cruzar subarboles
		found = false;
		lookForTrees(arbolito, nodosSA1, nodosSA2);

		if(found){
			int rand = Utiles.randomIntNO() % (nodosSA1.size());
			Iterator<Node> it = nodosSA1.iterator();
			Node nodo1 = null;
			for(int i = 0; i <= rand; i++)
				nodo1 = (Node) it.next();
			
			int rand2 = Utiles.randomIntNO() % (nodosSA2.size());
			Iterator<Node> it2 = nodosSA2.iterator();
			Node nodo2 = null;
			for(int i = 0; i <= rand2; i++)
				nodo2 = (Node) it2.next();

			NonLeafNode padreNodo1 = (NonLeafNode) nodo1.getParent();
			int numHijoh1 = nodo1.getNumHijo();
			NonLeafNode padrehNodo2 = (NonLeafNode) nodo2.getParent();
			int numHijoh2 = nodo2.getNumHijo();
			

			padreNodo1.getChildren().set(numHijoh1, nodo2);
			padrehNodo2.getChildren().set(numHijoh2, nodo1);
			nodo2.setParent(padreNodo1);
			nodo1.setParent(padrehNodo2);
			
			crom.getArbol().actualizarArbol();
		}
	}

	private void lookForTrees(Node arbolito, HashSet<Node> nodosSA1, HashSet<Node> nodosSA2) {
		if(!arbolito.isLeaf() && !found){
			NonLeafNode arb = (NonLeafNode)arbolito;
			if(arb.getChildren().size() > 1){ //Si el arbol tiene al menos dos ramas..
				//Se cogen dos ramas
				int rama1 = Utiles.randomIntNO() % (arb.getChildren().size());
				int rama2 = Utiles.randomIntNO() % (arb.getChildren().size());
				while(rama1 == rama2)
					rama2 = Utiles.randomIntNO() % (arb.getChildren().size());
				
				Node subarbol1 = arb.getChildren().get(rama1);
				Node subarbol2 = arb.getChildren().get(rama2);
				
				subarbol1.nodificar(nodosSA1, -1);
				subarbol2.nodificar(nodosSA2, -1);
				
				found = true;
			}
			else if (!found){ //Si no tiene dos ramas, se busca en sus hijos (Solo sera 1)
				for(int i = 0; i < arb.getChildren().size() && !found; i++){
					lookForTrees(arb.getChildren().get(i), nodosSA1, nodosSA2);
				}
			}
		}
	}

	public String toString() {
		return "SubArbol";
	}

}
