package operadores.mutacion;

import java.util.ArrayList;

import util.Utiles;
import geneticos.Cromosoma;
import geneticos.Gen;
import geneticos.Individuo;

public class MutaBitABit extends FuncionMutacion {

	public void mutarInd(Individuo ind){
		
		Cromosoma c = ind.getCromosoma();
		
		for(int i = 0; i < c.getGenes().size(); i++){
			Gen g = c.getGenes().get(i);
			ArrayList<Object> bases = g.getBases();
			
			for(int k = 0; k < bases.size(); k++){
				double rand = Utiles.randomDouble01();
				if (rand < prob)
					bases.set(k, c.mutaBase(bases.get(k)));
			}
		}
	
	}

	public String toString() {
		return "Bit a bit";
	}
	
}
