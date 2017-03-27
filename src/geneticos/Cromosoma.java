package geneticos;

import java.util.ArrayList;
import util.Par;

public abstract class Cromosoma {

	protected ArrayList<Gen> genes;
	protected ArrayList<Par<Double>> rango;
	protected TipoCromosoma tipo;
	
	
	public TipoCromosoma getTipo() {
		return tipo;
	}

	public abstract ArrayList<?> toFenotipo();
	
	public int getGenLenBin(double xmin, double xmax, double tol){
		double parentesis = ((xmax-xmin)/tol)+1;
		
		return (int) (Math.ceil(Math.log(parentesis)/Math.log(2)));
	}
	
	public abstract void randomizeCromosome(double tolerancia);

	public ArrayList<Gen> getGenes() {
		return genes;
	}

	public void setGenes(ArrayList<Gen> genes) {
		this.genes = genes;
	}
	
	public abstract Cromosoma clone();

	public ArrayList<Par<Double>> getRango() {
		return rango;
	}
	
}
