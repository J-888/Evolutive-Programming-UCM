package operadores.mutacion;

import java.util.ArrayList;
import geneticos.Individuo;

public abstract class FuncionMutacion {
	
	protected double prob;
	protected FuncionMutacion fitness;
	
	public void setProb(double prob){
		this.prob = prob;
	}
	
	public void mutar(ArrayList<Individuo> poblacion) {
		for(int i = 0; i < poblacion.size(); i++){
			Individuo ind = poblacion.get(i);
			mutarInd(ind);
			ind.updateFenotipo();
		}
	}

	public void setFuncionFitness(FuncionMutacion fitness) {
		this.fitness = fitness;
	}
	
	public abstract void mutarInd(Individuo ind);
	public abstract String toString();
	
}
