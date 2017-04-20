package operadores.cruce;

import java.util.ArrayList;

import geneticos.Cromosoma;
import util.Par;
import util.Utiles;

public class CX extends FuncionCruce {

	@Override
	protected Par<Cromosoma> cruceCromosomas(Cromosoma p1, Cromosoma p2) {
		
		ArrayList<Boolean> exchangeBases = new ArrayList<>(p1.getGenes().size());
		ArrayList<Integer> remainingLocations = new ArrayList<>(p1.getGenes().size());
		int exchangeNum = 0;
		int startIndex = 0;
		boolean currentCycleChildren = true;
		
		for (int i = 0; i < p1.getGenes().size(); i++) {
			exchangeBases.add(null);
			remainingLocations.add(i);
		}
		
		while(remainingLocations.size() != 0) {	//find cycles
			startIndex = findCycle(startIndex, exchangeBases, remainingLocations, currentCycleChildren, p1, p2);
			currentCycleChildren = !currentCycleChildren;
		}
		
		return Utiles.exchangeBases(exchangeBases, exchangeNum, p1, p2);
	}
	
	private int findCycle(int startIndex, ArrayList<Boolean> exchangeBases, ArrayList<Integer> remainingLocations, boolean currentCycleChildren, Cromosoma p1, Cromosoma p2) {
		
		int currentIndex = startIndex;
		
		while (currentIndex != -1) {
			exchangeBases.set(currentIndex, currentCycleChildren);
			remainingLocations.remove((Integer)currentIndex);
			Object p2base = p2.getGenes().get(currentIndex).getBases().get(0);
			currentIndex = searchInRemaningLocations(p2base, p1, remainingLocations);
		}
		
		if (remainingLocations.size() > 0)
			return remainingLocations.get(0);
		else
			return -1;
	}
		
	private int searchInRemaningLocations(Object p2base, Cromosoma p1, ArrayList<Integer> remainingLocations) {
		for(int i = 0; i < remainingLocations.size(); i++) {
			int location = remainingLocations.get(i);
			if(p1.getGenes().get(location).getBases().get(0).equals(p2base))
				return location;
		}
		return -1;
	}
	
	@Override
	public String toString() {
		return "CX";
	}

}
