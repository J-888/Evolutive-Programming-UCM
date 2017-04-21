package geneticos;

import java.util.ArrayList;

public class Individuo implements Comparable<Individuo> {

	private Cromosoma cromosoma;
	private double fitness;
	private double fitnessAdaptado;
	private double fitnessEscalado;
	private ArrayList<?> fenotipo;
	
	public Individuo(Cromosoma c){
		cromosoma = c;
		fenotipo = cromosoma.toFenotipo();
	}
	
	public Individuo(){

	}
	
	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public ArrayList<?> getFenotipo() {
		return fenotipo;
	}

	public int compareTo(Individuo o) {
		if(fitnessAdaptado == 0 && o.fitnessAdaptado == 0)	//fitness adaptado sin rellenar
			return Double.compare(fitness, o.fitness);
		else
			return Double.compare(fitnessAdaptado, o.fitnessAdaptado);
	}
	
	public Cromosoma getCromosoma() {
		return cromosoma;
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
	
	public Individuo clone(){
		Individuo nuevo = new Individuo(cromosoma.clone());
		nuevo.setFitness(fitness);
		nuevo.setFitnessAdaptado(fitnessAdaptado);
		nuevo.setFitnessEscalado(fitnessEscalado);
		return nuevo;
	}
	
	public void updateFenotipo(){
		fenotipo = cromosoma.toFenotipo();
	}
	
}
