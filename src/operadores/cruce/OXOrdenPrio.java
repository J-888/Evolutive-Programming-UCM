package operadores.cruce;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

import geneticos.Cromosoma;
import geneticos.Gen;
import util.Par;
import util.Utiles;

public class OXOrdenPrio extends FuncionCruce {

	@Override
	protected Par<Cromosoma> cruceCromosomas(Cromosoma p1, Cromosoma p2) {
		
		Cromosoma h1 = p2.clone();
		Cromosoma h2 = p1.clone();

		ArrayList<Gen> genesH1 = h1.getGenes();
		ArrayList<Gen> genesH2 = h2.getGenes();
		
		ArrayList<Gen> genesP1 = p1.getGenes();
		ArrayList<Gen> genesP2 = p2.getGenes();
		
		int tamCrom = genesP1.size();
		double probIntercambio = Utiles.randomDouble01();
		if(probIntercambio < 0.1)
			probIntercambio = 0.1;
		else if(probIntercambio > 0.9)
			probIntercambio = 0.9;
		
		TreeSet<Integer> gensH1Interc = new TreeSet<Integer>();
		TreeSet<Integer> gensH2Interc = new TreeSet<Integer>();
		
		TreeSet<Integer> posiciones = new TreeSet<Integer>();
		
		LinkedList<Integer> poolP1 = new LinkedList<Integer>();
		LinkedList<Integer> poolP2 = new LinkedList<Integer>();
		
		LinkedList<Integer> posicionesH1 = new LinkedList<Integer>();
		LinkedList<Integer> posicionesH2 = new LinkedList<Integer>();
		
		//Swapping entre puntos de intercambio seleccionados al azar, registro de sus posiciones, registro de los anyadidos a cada hijo, registro del pool de genes donados de cada uno
		for(int i = 0; i < tamCrom; i++){
			if(Utiles.randomDouble01() > probIntercambio && ((int)genesH1.get(i).getBases().get(0) != (int)genesH2.get(i).getBases().get(0))){	//TODO: probar con y sin
				posiciones.add(i);
				
				Object t1 = genesP1.get(i).getBases().get(0);
				poolP1.add((int)t1);
				posicionesH1.add(findAndSetInvalid((int)t1, genesH1, tamCrom));
				
				Object t2 = genesP2.get(i).getBases().get(0);
				poolP2.add((int)t2);
				posicionesH2.add(findAndSetInvalid((int)t2, genesH2, tamCrom));
			}
		}
		
		while(!posicionesH1.isEmpty()) {
			genesH1.get(posicionesH1.getFirst()).getBases().set(0, poolP1.getFirst());
			posicionesH1.removeFirst();
			poolP1.removeFirst();

			genesH2.get(posicionesH2.getFirst()).getBases().set(0, poolP2.getFirst());
			posicionesH2.removeFirst();
			poolP2.removeFirst();
		}
			
		return new Par<Cromosoma>(h1, h2);
	}

	private int findAndSetInvalid(int target, ArrayList<Gen> genes, int tamCrom) {
		for(int i = 0; i < tamCrom; i++) {
			if (target == (int)genes.get(i).getBases().get(0)) {
				genes.get(i).getBases().set(0, -1);	//TODO comment
				return i;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		return "OX Orden prioritario";
	}


}