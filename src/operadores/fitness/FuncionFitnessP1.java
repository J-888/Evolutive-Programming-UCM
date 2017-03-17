package operadores.fitness;

import java.util.ArrayList;

import util.Par;
import geneticos.Individuo;

public class FuncionFitnessP1 extends FuncionFitness {

	public void evaluate(Individuo ind) {
		double x = ind.getFenotipo().get(0);
		double parentesis = Math.sqrt(Math.abs(x));
		double result = 0 - Math.abs(x*Math.sin(parentesis));
		
		ind.setFitness(result);
	}
	
}
