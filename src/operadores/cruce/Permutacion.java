package operadores.cruce;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import geneticos.Gen;
import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaPG;
import geneticos.cromosomas.CromosomaStd;
import util.Par;
import util.Utiles;
import util.pg.Node;

public class Permutacion extends FuncionCruce {

	protected Par<Cromosoma> cruceCromosomas(Cromosoma pp1, Cromosoma pp2) {
		CromosomaPG p1 = (CromosomaPG) pp1;
		CromosomaPG p2 = (CromosomaPG) pp2;
		
		CromosomaPG h1 = p1.clone();
		CromosomaPG h2 = p2.clone();
		
		//Almacenar todos los nodos de h1
		HashSet<Node> nodosh1 = new HashSet<Node>();
		h1.getArbol().nodificar(nodosh1, 0);
		//Seleccionar uno al azar(menos el raiz)
		int select = Utiles.randomIntNO() % (h1.getArbol().getNumNodos() - 1);
		select++;
		nodosh1.iterator //ITERAR PARA COGER EL BUSCADO
		
		//BUSCAR OTRO EN H2 DE LAS MISMAS CARACTERISTICAS
		
		//CRUZAR
		
		return new Par<Cromosoma>(h1,h2);
	}

	@Override
	public String toString() {
		return "Permutación(NotForced)";
	}

}
