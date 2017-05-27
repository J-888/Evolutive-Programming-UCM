package operadores.mutacion;

import java.util.HashSet;
import java.util.Iterator;

import geneticos.Individuo;
import geneticos.cromosomas.CromosomaPG;
import operadores.fitness.FitnessPr3MuxN;
import util.Utiles;
import util.pg.LeafNode;
import util.pg.Node;

public class Terminal extends FuncionMutacion{

	public void mutarInd(Individuo ind) {

		CromosomaPG crom = (CromosomaPG) ind.getCromosoma();

		HashSet<Node> nodos = new HashSet<Node>();
		crom.getArbol().nodificar(nodos, 0);
		
		if(!nodos.isEmpty()){
			int rand = Utiles.randomIntNO() % (nodos.size());
			Iterator it = nodos.iterator();
			Node selectednode = null;
			for(int i = 0; i <= rand; i++)
				selectednode = (Node) it.next();
			
			int entradaActual = ((LeafNode) selectednode).getEntrada();
			int nuevaEntrada = entradaActual;
			while (nuevaEntrada == entradaActual)
				nuevaEntrada = Utiles.randomIntNO()%FitnessPr3MuxN.valoresSelectsYEnts.size();
			
			((LeafNode) selectednode).setEntrada(nuevaEntrada);
			
		}
		
		crom.getArbol().actualizarArbol();
	}

	public String toString() {
		return "Terminal";
	}

}
