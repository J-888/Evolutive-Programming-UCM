package operadores.mutacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import geneticos.Cromosoma;
import geneticos.Individuo;
import geneticos.TipoCromosoma;
import util.Utiles;

public class Heuristica extends FuncionMutacion {

	private int nBases;
	private int nPermutations;
	
	public Heuristica(int nb){
		nBases = nb;
		nPermutations = 6;	//TODO factorial
	}

	@Override
	public void mutarInd(Individuo ind) {
		Cromosoma c = ind.getCromosoma();
		
		if(c.getTipo() == TipoCromosoma.PERMINT){
			
			int tamCrom = c.getGenes().size();
			ArrayList<Integer> mutationLocations = new ArrayList<Integer>(nBases);
			while(mutationLocations.size() != nBases) {
				int randLocation = Utiles.randomIntNO()%(tamCrom);
				if (! mutationLocations.contains(randLocation))
					mutationLocations.add(randLocation);
			}
			
			ArrayList<Integer> posibleBases = new ArrayList<Integer>(nBases);
			for (int i = 0; i < mutationLocations.size(); i++) {
				int loc = mutationLocations.get(i);
				posibleBases.add((Integer) c.getGenes().get(loc).getBases().get(0));
			}
			
			ArrayList<ArrayList<Integer>> permutations = generatePermutations(posibleBases);
			ArrayList<Individuo> individuos = generateIndividuo(mutationLocations, permutations, ind);

			ind = this.funFit.evaluate(individuos).get(0);				//usa el mejor individuo
			
		}
		else{
			if(prob > 0)
				System.err.println("CROMOSOMA DE TIPO DESCONOCIDO O NO VALIDO EN MUTACION POR INTERCAMBIO");
		}
	}

	private ArrayList<ArrayList<Integer>> generatePermutations(ArrayList<Integer> options) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<>(nPermutations);
		generatePermutationsRecursive(result, new ArrayList<>(options.size()), options);
		
		return result;
	}
	
	private void generatePermutationsRecursive(ArrayList<ArrayList<Integer>> result, ArrayList<Integer> current, ArrayList<Integer> available) {
		if(available.size() == 0)
			result.add(current);
		else {
			for(int i = 0; i < available.size(); i++) {
				ArrayList<Integer> nextCurrent = new ArrayList<Integer>(current);
				nextCurrent.add(available.get(i));
				ArrayList<Integer> nextAvailable = new ArrayList<Integer>(available);
				nextAvailable.remove(i);
				generatePermutationsRecursive(result, nextCurrent, nextAvailable);
			}
		}
	}
	
	private ArrayList<Individuo> generateIndividuo(ArrayList<Integer> mutationLocations, ArrayList<ArrayList<Integer>> permutations, Individuo orig) {
		ArrayList<Individuo> result = new ArrayList<>(nPermutations);
		
		for (int i = 0; i < permutations.size(); i++) {	//crea nPermutations Individuos
			Individuo ind = orig.clone();
			for (int j = 0; j < permutations.get(i).size(); j++) {	//modifica el individuo a cada permutacion
				int location = mutationLocations.get(j);
				int value = permutations.get(i).get(j);
				ind.getCromosoma().getGenes().get(location).getBases().set(0, value);
			}
			result.add(ind);
		}
		
		return result;
	}

	@Override
	public String toString() {
		return "Heurística";
	}

}
