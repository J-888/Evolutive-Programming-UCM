package operadores.cruce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import geneticos.Cromosoma;
import geneticos.Gen;
import util.Par;
import util.Utiles;

public class ERX extends FuncionCruce {
	
	private List<List<Integer>> routes;
	private Comparator<Integer> comp = createComparator();

	@Override
	protected Par<Cromosoma> cruceCromosomas(Cromosoma p1, Cromosoma p2) {
				
		Cromosoma h1 = p1.clone();
		Cromosoma h2 = p2.clone();

		ArrayList<Gen> genesH1 = h1.getGenes();
		ArrayList<Gen> genesH2 = h2.getGenes();
		
		ArrayList<Gen> genesP1 = p1.getGenes();
		ArrayList<Gen> genesP2 = p2.getGenes();
		
		int tamCrom = genesP1.size();
		routes = new ArrayList<List<Integer>>(tamCrom);
		
		for(int i = 0; i < tamCrom; i++) {
			routes.add(new ArrayList<Integer>(4));
		}
		
		for(int i = 0; i < tamCrom; i++) {	//fill routes
			int value1 = (int) genesP1.get(i).getBases().get(0);
			int value2 = (int) genesP2.get(i).getBases().get(0);
			
			int nextValue1 = (int) genesP1.get((i+1)%tamCrom).getBases().get(0);
			int nextValue2 = (int) genesP2.get((i+1)%tamCrom).getBases().get(0);
			
			int previousValue1 = (int) genesP1.get((i-1+tamCrom)%tamCrom).getBases().get(0);
			int previousValue2 = (int) genesP2.get((i-1+tamCrom)%tamCrom).getBases().get(0);
			
			if(!routes.get(value1).contains(nextValue1))	//TODO: try with hashset
				routes.get(value1).add(nextValue1);
			if(!routes.get(value2).contains(nextValue2))
				routes.get(value2).add(nextValue2);
			if(!routes.get(value1).contains(previousValue1))
				routes.get(value1).add(previousValue1);
			if(!routes.get(value2).contains(previousValue2))
				routes.get(value2).add(previousValue2);
		}
		
		sortRoutes(routes);
		
		int initial1 = (int) genesP1.get(0).getBases().get(0);
		int initial2 = (int) genesP2.get(0).getBases().get(0);
		
		LinkedHashSet<Integer> childBases1 = new LinkedHashSet<Integer>(tamCrom);
		LinkedHashSet<Integer> childBases2 = new LinkedHashSet<Integer>(tamCrom);
		generateChild(childBases1, initial1, tamCrom);
		generateChild(childBases2, initial2, tamCrom);
		
		Iterator<Integer> it1 = childBases1.iterator();
		Iterator<Integer> it2 = childBases1.iterator();
		for (int i = 0; i < tamCrom; i++) {
			h1.getGenes().get(i).getBases().set(0, it1.next());
			h2.getGenes().get(i).getBases().set(0, it2.next());
		}
		
		return new Par<Cromosoma>(h1, h2);
	}
	
	private Comparator<Integer> createComparator(){
		return new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				// -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
				int o1Neighbours = routes.get(o1).size();
				int o2Neighbours = routes.get(o2).size();
				
				if(o1Neighbours < o2Neighbours)
					return -1;
				else if(o1Neighbours > o2Neighbours)
					return +1;
				else
					return 2*(Utiles.randomIntNO()%2) - 1;
			}
		};
	}
	
	private void sortRoutes(List<List<Integer>> routes) {
		for (int i = 0; i < routes.size(); i++) {
			Collections.sort(routes.get(i), comp);
		}
	}
	
	private boolean generateChild(LinkedHashSet<Integer> childBases, int current, int basesLeft) {
		if(basesLeft != 0){			
			for (int i = 0; i < routes.get(current).size(); i++) {
				int neightbour = routes.get(current).get(i);
				if(!childBases.contains(neightbour)){
					childBases.add(neightbour);
					if(generateChild(childBases, neightbour, basesLeft-1))
						return true;	//execution complete
				}
			}
		}
		return false;	//backtrack
	}
	
	@Override
	public String toString() {
		return "ERX";
	}

}
