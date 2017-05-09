package operadores.cruce;

import java.util.ArrayList;
import java.util.TreeSet;

import geneticos.Gen;
import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaStd;
import util.Par;
import util.Utiles;

public class OX extends FuncionCruce {

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
		
		TreeSet<Integer> gensH1 = new TreeSet<Integer>();
		TreeSet<Integer> gensH2 = new TreeSet<Integer>();

		//Swapping entre los puntos de cruce y registro de los anyadidos a cada uno
		for(int i = pCorte1; i < pCorte2; i++){
			Object t = genesH1.get(i).getBases().get(0);
			
			genesH1.get(i).getBases().set(0, genesH2.get(i).getBases().get(0));
			gensH1.add((int)genesH2.get(i).getBases().get(0));
			
			genesH2.get(i).getBases().set(0, t);
			gensH2.add((int)t);
		}
		
		int insert = pCorte2%tamCrom;
		int iter = pCorte2%tamCrom;
		boolean insertado;
		
		//Hijo 1
		while(insert != pCorte1){
			insertado = false;

			while(!insertado){
				if(!gensH1.contains(genesP1.get(iter).getBases().get(0))){
					genesH1.get(insert).getBases().set(0, genesP1.get(iter).getBases().get(0));
					insertado = true;
				}
				iter++;
				if(iter == tamCrom)//Dar la vuelta
					iter = 0;
			}	
			
			insert++;
			if(insert == tamCrom)//Dar la vuelta
				insert = 0;
		}

		insert = pCorte2%tamCrom;
		iter = pCorte2%tamCrom;
		//Hijo 2
		while(insert != pCorte1){
			insertado = false;

			while(!insertado){
				if(!gensH2.contains(genesP2.get(iter).getBases().get(0))){
					genesH2.get(insert).getBases().set(0, genesP2.get(iter).getBases().get(0));
					insertado = true;
				}
				iter++;
				if(iter == tamCrom)//Dar la vuelta
					iter = 0;
			}	
			
			insert++;
			if(insert == tamCrom)//Dar la vuelta
				insert = 0;
		}
		
		
		return new Par<Cromosoma>(h1,h2);
	}

	@Override
	public String toString() {
		return "OX";
	}


}
