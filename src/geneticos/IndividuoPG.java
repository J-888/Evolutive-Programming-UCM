package geneticos;

import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaPG;
import geneticos.cromosomas.CromosomaStd;

public class IndividuoPG extends Individuo {

	private CromosomaPG cromosoma;
	
	public IndividuoPG(CromosomaPG c) {
		cromosoma = c;
		fenotipo = cromosoma.toFenotipo();
	}

	public Cromosoma getCromosoma() {
		return cromosoma;
	}

	public Individuo clone() {
		IndividuoPG nuevo = new IndividuoPG(cromosoma.clone());
		nuevo.setFitness(fitness);
		nuevo.setFitnessAdaptado(fitnessAdaptado);
		nuevo.setFitnessEscalado(fitnessEscalado);
		return nuevo;
	}

	public void updateFenotipo() {
		//No hace nada porque el fenotipo es igual al genotipo
	}


}
