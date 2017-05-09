package geneticos;

import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaStd;

import java.util.ArrayList;

public abstract class Individuo implements Comparable<Individuo> {
	protected double fitness;
	protected double fitnessAdaptado;
	protected double fitnessEscalado;
	protected ArrayList<?> fenotipo;
	
	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public ArrayList<?> getFenotipo() {
		return fenotipo;
	}
	
	public double getFitnessAdaptado() {
		return fitnessAdaptado;
	}

	public void setFitnessAdaptado(double fitnessAdaptado) {
		this.fitnessAdaptado = fitnessAdaptado;
	}

	public double getFitnessEscalado() {
		return fitnessEscalado;
	}

	public void setFitnessEscalado(double fitnessEscalado) {
		this.fitnessEscalado = fitnessEscalado;
	}
	
	public int compareTo(Individuo o) {
		if(fitnessAdaptado == 0 && o.fitnessAdaptado == 0)	//fitness adaptado sin rellenar
			return Double.compare(fitness, o.fitness);
		else
			return Double.compare(fitnessAdaptado, o.fitnessAdaptado);
	}
	
	public abstract Cromosoma getCromosoma();
	public abstract Individuo clone();
	public abstract void updateFenotipo();
}
