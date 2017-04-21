package operadores.cruce;

import geneticos.Cromosoma;
import geneticos.Individuo;
import operadores.fitness.FuncionFitness;
import util.Par;
import util.Utiles;

public abstract class FuncionCruce {

	protected double prob;
	protected FuncionFitness funFit;
	
	public void setProb(double prob){
		this.prob = prob;
	}
		
	public Par<Individuo> cruzar(Par<Individuo> padres) {
		if (prob > Utiles.randomDouble01()){
			Individuo padre1 = padres.getN1();
			Individuo padre2 = padres.getN2();
			
			Par<Cromosoma> newCroms = cruceCromosomas(padre1.getCromosoma(),padre2.getCromosoma());
			Individuo hijo1 = new Individuo(newCroms.getN1());
			Individuo hijo2 = new Individuo(newCroms.getN2());
			//Al crear hijo1 y hijo2 se actualiza su fenotipo
			
			return new Par<Individuo>(hijo1, hijo2);
		}
		else 
			return new Par<Individuo>(padres.getN1().clone(), padres.getN2().clone());
	}	
	
	public void setFuncionFitness(FuncionFitness fitness) {
		this.funFit = fitness;
	}
	
	protected abstract Par<Cromosoma> cruceCromosomas(Cromosoma p1, Cromosoma p2);
	public abstract String toString();
	
}
