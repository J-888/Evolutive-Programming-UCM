package operadores.cruce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import geneticos.Cromosoma;
import geneticos.Gen;
import util.Par;
import util.Utiles;

/*
 * Crossover utilizado en "A Genetic Approach to the Quadratic Assignment Problem", por David M. Tate y Alice E. Smith.
 * 
 * Es un crossover bastante poco agresivo, pero muy sólido, que permite altos % de cross y da muy buenos resultados.
 */

public class SX extends FuncionCruce {

	@Override
	protected Par<Cromosoma> cruceCromosomas(Cromosoma p1, Cromosoma p2) {
		Cromosoma h1 = p1.clone();
		Cromosoma h2 = p2.clone();
		
		ArrayList<Gen> genesH1 = h1.getGenes();
		ArrayList<Gen> genesH2 = h2.getGenes();
		
		ArrayList<Gen> genesP1 = p1.getGenes();
		ArrayList<Gen> genesP2 = p2.getGenes();
		
		int tamCrom = genesP1.size();
		
		TreeSet<Integer> genPoolH1 = new TreeSet<Integer>();
		TreeSet<Integer> genPoolH2 = new TreeSet<Integer>();

		//Store de los elementos por insertar en ambos hijos, marcar sus genes a -1
		for(int i = 0; i < tamCrom; i++){
			genesH1.get(i).getBases().set(0, -1);
			genesH2.get(i).getBases().set(0, -1);
			genPoolH1.add(i);
			genPoolH2.add(i);
		}
		
		for(int i = 0; i < tamCrom; i++){
			Object genP1 = genesP1.get(i).getBases().get(0);
			Object genP2 = genesP2.get(i).getBases().get(0);
			if(genP1.equals(genP2)){//Se mantienen los que coincidian en ambos padres
				genesH1.get(i).getBases().set(0, genP1);
				genesH2.get(i).getBases().set(0, genP2);
				genPoolH1.remove(genP1);
				genPoolH2.remove(genP2);
			}
			else{//Si no coinciden, se escoge para ellos cualquiera de los padres (Si no estan ya asociados)
				if(Utiles.randomDouble01() > 0.5){//P1 a H1, P2 a H2
					if(genPoolH1.contains(genP1)){
						genesH1.get(i).getBases().set(0, genP1);
						genPoolH1.remove(genP1);
					}
					if(genPoolH2.contains(genP2)){
						genesH2.get(i).getBases().set(0, genP2);
						genPoolH2.remove(genP2);
					}
				}
				else{//P1 a H2, P2 a H1
					if(genPoolH1.contains(genP2)){
						genesH1.get(i).getBases().set(0, genP2);
						genPoolH1.remove(genP2);
					}
					if(genPoolH2.contains(genP1)){
						genesH2.get(i).getBases().set(0, genP1);
						genPoolH2.remove(genP1);
					}
				}
			}
		}
		
		for(int i = 0; i < tamCrom; i++){//Segunda pasada para rellenar los huecos restantes con los genes no utilizados de los pools
			Iterator<Integer> genPoolH1it = genPoolH1.iterator();
			Iterator<Integer> genPoolH2it = genPoolH2.iterator();
			
			if(genesH1.get(i).getBases().get(0).equals(-1)){
				genesH1.get(i).getBases().set(0, genPoolH1it.next());
				genPoolH1it.remove();
			}
			
			if(genesH2.get(i).getBases().get(0).equals(-1)){
				genesH2.get(i).getBases().set(0, genPoolH2it.next());
				genPoolH2it.remove();
			}
		}
		
		return new Par<Cromosoma>(h1,h2);
	}

	@Override
	public String toString() {
		return "Solid conservative";
	}
	
}
