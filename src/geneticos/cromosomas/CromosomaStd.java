package geneticos.cromosomas;

import geneticos.Gen;
import geneticos.TipoCromosoma;

import java.util.ArrayList;

import util.Par;

public abstract class CromosomaStd extends Cromosoma {

	protected ArrayList<Gen> genes;
	protected ArrayList<Par<Double>> rango;
	
	public int getGenLenBin(double xmin, double xmax, double tol){
		double parentesis = ((xmax-xmin)/tol)+1;
		
		return (int) (Math.ceil(Math.log(parentesis)/Math.log(2)));
	}
	
	public ArrayList<Gen> getGenes() {
		return genes;
	}

	public void setGenes(ArrayList<Gen> genes) {
		this.genes = genes;
	}

	public ArrayList<Par<Double>> getRango() {
		return rango;
	}
	
	public abstract CromosomaStd clone();
	public abstract void randomizeCromosome(double tolerancia);

}
