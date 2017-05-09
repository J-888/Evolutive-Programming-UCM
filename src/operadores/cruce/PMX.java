package operadores.cruce;

import java.util.ArrayList;

import geneticos.Gen;
import geneticos.GenInt;
import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaStd;
import util.Par;
import util.Utiles;

public class PMX extends FuncionCruce {

	@Override
	protected Par<Cromosoma> cruceCromosomas(Cromosoma pp1, Cromosoma pp2) {
		CromosomaStd p1 = (CromosomaStd) pp1;
		CromosomaStd p2 = (CromosomaStd) pp2;
		
		CromosomaStd h1 = p1.clone();
		CromosomaStd h2 = p2.clone();
		
		ArrayList<Gen> genesH1 = h1.getGenes();
		ArrayList<Gen> genesH2 = h2.getGenes();
		
		ArrayList<Gen> genesP1 = p1.getGenes();
		ArrayList<Gen> genesP2 = p2.getGenes();
		
		int pCorte1, pCorte2 = -1;
		int tamCrom = genesP1.size();
		pCorte1 = Utiles.randomIntNO()%(tamCrom+1);
		while(pCorte2 == -1 || pCorte2 == pCorte1)
			pCorte2 = Utiles.randomIntNO()%(tamCrom+1);
		
		if(pCorte1 > pCorte2){
			int t = pCorte2;
			pCorte2 = pCorte1;
			pCorte1 = t;
		}

		//Swapping entre los puntos de cruce
		for(int i = pCorte1; i < pCorte2; i++){
			Object t = genesH1.get(i).getBases().get(0);
			genesH1.get(i).getBases().set(0, genesH2.get(i).getBases().get(0));
			genesH2.get(i).getBases().set(0, t);
		}
		
		boolean hijoOK;
		int posFind;
		//Pre punto de cruce 1
		for(int i = 0; i < pCorte1; i++){
			//Hijo1
			hijoOK = false;
			
			while(!hijoOK){	
				posFind = searchInArrayList(genesH1, pCorte1, pCorte2, genesH1.get(i));
				if(posFind != -1)
					genesH1.get(i).getBases().set(0, genesP1.get(posFind).getBases().get(0));
				else 
					hijoOK = true;
			}
			
			//Hijo2
			hijoOK = false;

			while(!hijoOK){	
				posFind = searchInArrayList(genesH2, pCorte1, pCorte2, genesH2.get(i));
				if(posFind != -1)
					genesH2.get(i).getBases().set(0, genesP2.get(posFind).getBases().get(0));
				else 
					hijoOK = true;
			}
		}
		
		//Tras punto de cruce 2
		for(int i = pCorte2; i < tamCrom; i++){
			//Hijo1
			hijoOK = false;
			
			while(!hijoOK){	
				posFind = searchInArrayList(genesH1, pCorte1, pCorte2, genesH1.get(i));
				if(posFind != -1)
					genesH1.get(i).getBases().set(0, genesP1.get(posFind).getBases().get(0));
				else 
					hijoOK = true;
			}
			
			//Hijo2
			hijoOK = false;

			while(!hijoOK){	
				posFind = searchInArrayList(genesH2, pCorte1, pCorte2, genesH2.get(i));
				if(posFind != -1)
					genesH2.get(i).getBases().set(0, genesP2.get(posFind).getBases().get(0));
				else 
					hijoOK = true;
			}
		}
		
		return new Par<Cromosoma>(h1,h2);
		
	}
	
	private int searchInArrayList(ArrayList<Gen> array, int start, int end, Gen target){
		for(int i = start; i < end; i++)
			if(array.get(i).getBases().get(0).equals(target.getBases().get(0)))
				return i;
		return -1;
	}

	@Override
	public String toString() {
		return "PMX";
	}

}
