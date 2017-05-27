package operadores.cruce;

import java.util.HashSet;
import java.util.Iterator;
import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaPG;
import util.Par;
import util.Utiles;
import util.pg.Node;
import util.pg.NonLeafNode;

public class Permutacion extends FuncionCruce {

	protected Par<Cromosoma> cruceCromosomas(Cromosoma pp1, Cromosoma pp2) {
		CromosomaPG p1 = (CromosomaPG) pp1;
		CromosomaPG p2 = (CromosomaPG) pp2;
		
		CromosomaPG h1 = p1.clone();
		CromosomaPG h2 = p2.clone();
		
		if (p1.getArbol().getNumNodos() > 1 && p2.getArbol().getNumNodos() > 1){ //Solo tiene sentido cruzar un arbol con mas de 1 nodo
			//Almacenar todos los nodos de h1
			HashSet<Node> nodosh1 = new HashSet<Node>();
			h1.getArbol().nodificar(nodosh1, -1);
			//Seleccionar uno al azar
			int select1 = Utiles.randomIntNO() % (nodosh1.size());
			Iterator it = nodosh1.iterator();
			Node selected1 = null;
			for(int i = 0; i <= select1; i++)
				selected1 = (Node) it.next();
			int numPuertasNode1 = selected1.getNumEntradas();
			
			//Se busca otro en h2 con el mismo numero de puertas (puede no haber)
			HashSet<Node> nodosh2 = new HashSet<Node>();
			h2.getArbol().nodificar(nodosh2, numPuertasNode1);
			//Comprobación de que el otro tiene alguna puerta de ese tam
			if(!nodosh2.isEmpty()){
				int select2 = Utiles.randomIntNO() % (nodosh2.size());
				Iterator it2 = nodosh2.iterator();
				Node selected2 = null;
				for(int i = 0; i <= select2; i++)
					selected2 = (Node) it2.next();
				
				//Cruzar		
				NonLeafNode padreh1 = (NonLeafNode) selected1.getParent();
				int numHijoh1 = selected1.getNumHijo();
				NonLeafNode padreh2 = (NonLeafNode) selected2.getParent();
				int numHijoh2 = selected2.getNumHijo();
				
				padreh1.getChildren().set(numHijoh1, selected2);
				padreh2.getChildren().set(numHijoh2, selected1);
				selected2.setParent(padreh1);
				selected1.setParent(padreh2);
				
				h1.getArbol().actualizarArbol();
				h2.getArbol().actualizarArbol();
				
				return new Par<Cromosoma>(h1,h2);
				
			}
			else{
				return new Par<Cromosoma>(h1,h2);
			}
		}
		else{
			return new Par<Cromosoma>(h1,h2);
		}
	}

	@Override
	public String toString() {
		return "Permutación(NotForced)";
	}

}
