package operadores.cruce;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

import geneticos.Gen;
import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaStd;
import util.Par;
import util.Utiles;

public class OXPosPrio extends FuncionCruce {

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
		
		int tamCrom = genesP1.size();
		double probIntercambio = Utiles.randomDouble01();
		if(probIntercambio < 0.1)
			probIntercambio = 0.1;
		else if(probIntercambio > 0.9)
			probIntercambio = 0.9;
		
		//probIntercambio = Utiles.randomDoubleGaussSOff01();
		//probIntercambio = Utiles.randomDoubleGaussLOff01();
		
		TreeSet<Integer> gensH1Interc = new TreeSet<Integer>();
		TreeSet<Integer> gensH2Interc = new TreeSet<Integer>();
		
		TreeSet<Integer> posiciones = new TreeSet<Integer>();
		
		LinkedList<Integer> poolH1 = new LinkedList<Integer>();
		LinkedList<Integer> poolH2 = new LinkedList<Integer>();
		
		//Swapping entre puntos de intercambio seleccionados al azar, registro de sus posiciones, registro de los anyadidos a cada hijo, registro del pool de genes donados de cada uno
		for(int i = 0; i < tamCrom; i++){
			if(Utiles.randomDouble01() > probIntercambio && ((int)genesH1.get(i).getBases().get(0) != (int)genesH2.get(i).getBases().get(0))){
				posiciones.add(i);
				
				Object t = genesH1.get(i).getBases().get(0);
				
				poolH1.add((int)t);
				
				genesH1.get(i).getBases().set(0, genesH2.get(i).getBases().get(0));
				gensH1Interc.add((int)genesH2.get(i).getBases().get(0));
				
				poolH2.add((int)genesH2.get(i).getBases().get(0));
				genesH2.get(i).getBases().set(0, t);
				gensH2Interc.add((int)t);
			}
		}
		
		int posInsert = 0;
		boolean stop = false;
		
		//Hijo 1
		while((posInsert != tamCrom) && !stop){
			
			while(!stop && (posiciones.contains(posInsert) || !gensH1Interc.contains((int)genesH1.get(posInsert).getBases().get(0)))){//Siguiente posicion en la que insertar (conflictiva)
				posInsert++;

				if(posInsert == tamCrom)
					stop = true;
			}	
			
			if(!stop){//Se encontró una inserción necesaria
				int aInsertar = -1;
				boolean enc = false;
				while(!enc){
					aInsertar = poolH1.get(0);
					poolH1.remove(0);
			
					if(!gensH1Interc.contains(aInsertar))
						enc = true;
				}

				genesH1.get(posInsert).getBases().set(0, aInsertar);
				posInsert++;
			}
		}

		posInsert = 0;
		stop = false;
		
		//Hijo 2
		while((posInsert != tamCrom) && !stop){

			while(!stop && (posiciones.contains(posInsert) || !gensH2Interc.contains((int)genesH2.get(posInsert).getBases().get(0)))){//Siguiente posicion en la que insertar (conflictiva)
				posInsert++;
				if(posInsert == tamCrom)
					stop = true;
			}	
			
			if(!stop){//Se encontró una inserción necesaria
				int aInsertar = -1;
				boolean enc = false;
				while(!enc){
					aInsertar = poolH2.get(0);
					poolH2.remove(0);
					if(!gensH2Interc.contains(aInsertar))
						enc = true;
				}
				genesH2.get(posInsert).getBases().set(0, aInsertar);
				posInsert++;
			}
		}
		
		return new Par<Cromosoma>(h1,h2);
	}

	@Override
	public String toString() {
		return "OX Pos. prioritarias";
	}


}