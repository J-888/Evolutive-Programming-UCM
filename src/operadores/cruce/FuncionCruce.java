package operadores.cruce;

import geneticos.Individuo;
import geneticos.IndividuoPG;
import geneticos.IndividuoStd;
import geneticos.TipoCromosoma;
import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaPG;
import geneticos.cromosomas.CromosomaStd;
import operadores.fitness.FuncionFitness;
import util.Par;
import util.Utiles;

public abstract class FuncionCruce {

	protected double prob;
	protected int contador;
	protected FuncionFitness funFit;
	
	public void setProb(double prob){
		this.contador = 0;
		this.prob = prob;
	}
		
	public Par<Individuo> cruzar(Par<Individuo> padres) {
		if (prob > Utiles.randomDouble01()){
			
			this.contador++;

			Individuo padre1 = padres.getN1();
			Individuo padre2 = padres.getN2();
			
			Individuo hijo1;
			Individuo hijo2;
			
			Par<Cromosoma> newCroms = cruceCromosomas(padre1.getCromosoma(),padre2.getCromosoma());
			if(!padre1.getCromosoma().getTipo().equals(TipoCromosoma.CROMPG)){
				hijo1 = new IndividuoStd((CromosomaStd)newCroms.getN1());
				hijo2 = new IndividuoStd((CromosomaStd)newCroms.getN2());
			}
			else{
				hijo1 = new IndividuoPG((CromosomaPG)newCroms.getN1());
				hijo2 = new IndividuoPG((CromosomaPG)newCroms.getN2());
			}
			//Al crear hijo1 y hijo2 se actualiza su fenotipo
			
			return new Par<Individuo>(hijo1, hijo2);
		}
		else 
			return new Par<Individuo>(padres.getN1().clone(), padres.getN2().clone());
	}	
	
	public void setFuncionFitness(FuncionFitness fitness) {
		this.funFit = fitness;
	}
	
	public int getCounter(){
		return contador;
	}
	
	protected abstract Par<Cromosoma> cruceCromosomas(Cromosoma p1, Cromosoma p2);
	public abstract String toString();
	
}
