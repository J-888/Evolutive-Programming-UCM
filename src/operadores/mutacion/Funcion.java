package operadores.mutacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import geneticos.Individuo;
import geneticos.cromosomas.CromosomaPG;
import util.Utiles;
import util.pg.Node;
import util.pg.TipoNodo;

public class Funcion extends FuncionMutacion{

	public void mutarInd(Individuo ind) {

		CromosomaPG crom = (CromosomaPG) ind.getCromosoma();

		HashSet<Node> nodos = new HashSet<Node>();
		crom.getArbol().nodificar(nodos, -2);
		nodos.add(crom.getArbol());
		
		if(!nodos.isEmpty()){
			int rand = Utiles.randomIntNO() % (nodos.size());
			Iterator it = nodos.iterator();
			Node selectednode = null;
			for(int i = 0; i <= rand; i++)
				selectednode = (Node) it.next();

			TipoNodo tipoActual = selectednode.getTipo();
			List<TipoNodo> tiposPosibles = new ArrayList<>(Arrays.asList(TipoNodo.values()));
			tiposPosibles.remove(TipoNodo.ENTRADA);
			tiposPosibles.remove(tipoActual);
			
			TipoNodo nuevoTipo;
			int selectedIndex;
			int entradasActual = selectednode.getNumEntradas();
			do {
				selectedIndex = Utiles.randomIntNO()%tiposPosibles.size();
				nuevoTipo = tiposPosibles.get(selectedIndex);
				tiposPosibles.remove(selectedIndex);
			} while (!tiposPosibles.isEmpty() && (nuevoTipo.getMinEnts() > entradasActual || entradasActual > nuevoTipo.getMaxEnts()));
			
			if(tiposPosibles.isEmpty() && (nuevoTipo.getMinEnts() > entradasActual || entradasActual > nuevoTipo.getMaxEnts()))
				nuevoTipo = tipoActual;
			
			selectednode.setTipo(nuevoTipo);
			
		}
	}

	public String toString() {
		return "Función";
	}

}
