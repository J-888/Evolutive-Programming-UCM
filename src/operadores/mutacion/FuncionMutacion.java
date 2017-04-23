package operadores.mutacion;

import java.util.ArrayList;
import geneticos.Individuo;
import operadores.fitness.FuncionFitness;

public abstract class FuncionMutacion {
	
	protected double prob;
	protected int contador;
	protected FuncionFitness funFit;
	
	public void setProb(double prob){
		this.contador = 0;
		this.prob = prob;
	}
	
	public double getProb(){
		return this.prob;
	}
	
	public void mutar(ArrayList<Individuo> poblacion) {
		for(int i = 0; i < poblacion.size(); i++){
			Individuo ind = poblacion.get(i);
			mutarInd(ind);
			ind.updateFenotipo();
		}
	}

	public void setFuncionFitness(FuncionFitness fitness) {
		this.funFit = fitness;
	}
	
	public int getCounter(){
		return contador;
	}
	
	public abstract void mutarInd(Individuo ind);
	public abstract String toString();
	
}
