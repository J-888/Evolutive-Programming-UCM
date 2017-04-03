package operadores.cruce;

import java.util.ArrayList;

import geneticos.Cromosoma;
import geneticos.Gen;
import util.Par;
import util.Utiles;

public class PMX extends FuncionCruce {

	@Override
	protected Par<Cromosoma> cruceCromosomas(Cromosoma p1, Cromosoma p2) {
		Cromosoma h1 = p1.clone();
		Cromosoma h2 = p2.clone();
		
		ArrayList<Gen> genesH1 = h1.getGenes();
		ArrayList<Gen> genesH2 = h2.getGenes();
		
		ArrayList<Gen> genesP1 = p1.getGenes();
		ArrayList<Gen> genesP2 = p2.getGenes();
		
		int pCorte1, pCorte2 = -1;
		int tamCrom = p1.getGenes().size();
		pCorte1 = Utiles.randomIntNO()%tamCrom;
		while(pCorte2 == -1 || pCorte2 == pCorte1)
			pCorte2 = Utiles.randomIntNO()%tamCrom;
		
		if(pCorte1 > pCorte2){
			int t = pCorte2;
			pCorte2 = pCorte1;
			pCorte1 = t;
		}
		
		//Swapping entre los puntos de cruce
		for(int i = pCorte1; i < pCorte2; i++){
			Gen t = h1.getGenes().get(i);
			h1.getGenes().set(i, h2.getGenes().get(i));
			h2.getGenes().set(i, t);
		}
		
		//Pre punto de cruce 1
		for(int i = 0; i < pCorte1; i++){
			//Hijo1
			int posFind = searchInArrayList(genesH1, pCorte1, pCorte2, genesH1.get(i));
			if(posFind != -1)
				genesH1.set(i, genesP1.get(posFind));
			
			//Hijo2
			posFind = searchInArrayList(genesH2, pCorte1, pCorte2, genesH2.get(i));
			if(posFind != -1)
				genesH2.set(i, genesP2.get(posFind));
		}
		
		//Tras punto de cruce 2
		for(int i = pCorte2; i < tamCrom; i++){
			//Hijo1
			int posFind = searchInArrayList(genesH1, pCorte1, pCorte2, genesH1.get(i));
			if(posFind != -1)
				genesH1.set(i, genesP1.get(posFind));
			
			//Hijo2
			posFind = searchInArrayList(genesH2, pCorte1, pCorte2, genesH2.get(i));
			if(posFind != -1)
				genesH2.set(i, genesP2.get(posFind));
		}
		
		return new Par<Cromosoma>(h1,h2);
		
	}
	
	<E> int searchInArrayList(ArrayList<E> array, int start, int end, E target){
		for(int i = start; i < end; i++)
			if(array.get(i) == target)
				return i;
		return -1;
	}

	@Override
	public String toString() {
		return "PMX";
	}

}
