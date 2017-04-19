package operadores.cruce;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.LongConsumer;

import geneticos.Cromosoma;
import geneticos.Gen;
import util.Par;

public class ERX extends FuncionCruce {

	@Override
	protected Par<Cromosoma> cruceCromosomas(Cromosoma p1, Cromosoma p2) {
		Cromosoma h1 = p1.clone();
		Cromosoma h2 = p2.clone();

		ArrayList<Gen> genesH1 = h1.getGenes();
		ArrayList<Gen> genesH2 = h2.getGenes();
		
		ArrayList<Gen> genesP1 = p1.getGenes();
		ArrayList<Gen> genesP2 = p2.getGenes();
		
		int tamCrom = genesP1.size();
		List<TreeSet<Integer>> routes = new LinkedList<TreeSet<Integer>>();
		//List<TreeSet<Integer>> routes = new ArrayList<TreeSet<Integer>>(tamCrom);
		
		for(int i = 0; i < tamCrom; i++) {
			routes.add(new TreeSet<Integer>(4,1));
		}
		
		for(int i = 0; i < tamCrom; i++) {	//fill routes
			int value1 = (int) genesP1.get(i).getBases().get(0);
			int value2 = (int) genesP2.get(i).getBases().get(0);
			
			int nextValue1 = (int) genesP1.get((i+1)%tamCrom).getBases().get(0);
			int nextValue2 = (int) genesP2.get((i+1)%tamCrom).getBases().get(0);
			
			int previousValue1 = (int) genesP1.get((i-1+tamCrom)%tamCrom).getBases().get(0);
			int previousValue2 = (int) genesP2.get((i-1+tamCrom)%tamCrom).getBases().get(0);
			
			routes.get(value1).add(nextValue1);
			routes.get(value2).add(nextValue2);
			routes.get(value1).add(previousValue1);
			routes.get(value2).add(previousValue2);
		}
		
		int initial1 = (int) genesP1.get(0).getBases().get(0);
		int initial2 = (int) genesP2.get(0).getBases().get(0);
		
		ArrayList<Integer> childBases1 = new ArrayList<Integer>(tamCrom);
		ArrayList<Integer> childBases2 = new ArrayList<Integer>(tamCrom);
		generateChild(childBases1, new LinkedList<TreeSet<Integer>>(routes), initial1, tamCrom);
		generateChild(childBases2, new LinkedList<TreeSet<Integer>>(routes), initial2, tamCrom);
		
		return null;
	}
	
	
	
	private boolean generateChild(ArrayList<Integer> childBases, List<TreeSet<Integer>> routes, int current, int basesLeft) {
		if(basesLeft != 0){
			//routes.get(current).iterator();
		}
		return false;			
	}



	@Override
	public String toString() {
		return "ERX";
	}

}
