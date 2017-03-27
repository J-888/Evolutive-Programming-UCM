package operadores.fitness;

import geneticos.Individuo;

public class FitnessPr1Func1 extends FuncionFitness {

	public void evaluate(Individuo ind) {
		double x = (double) ind.getFenotipo().get(0);
		double parentesis = Math.sqrt(Math.abs(x));
		double result = 0 - Math.abs(x*Math.sin(parentesis));
		
		ind.setFitness(result);
	}
	
}
