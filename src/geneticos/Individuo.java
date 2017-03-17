package geneticos;

import java.util.ArrayList;

public class Individuo implements Comparable<Individuo> {

	private Cromosoma cromosoma;
	private double fitness;
	private double fitnessAdaptado;
	private ArrayList<Double> fenotipo;
	
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

	public ArrayList<Double> getFenotipo() {
		return fenotipo;
	}

	public int compareTo(Individuo o) {
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
	
	public Individuo clone(){
		Individuo nuevo = new Individuo(cromosoma.clone());
		nuevo.setFitness(fitness);
		nuevo.setFitnessAdaptado(fitnessAdaptado);
		return nuevo;
	}
	
	public void updateFenotipo(){
		fenotipo = cromosoma.toFenotipo();
	}
	
}
