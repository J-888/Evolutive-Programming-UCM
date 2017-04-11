package operadores.fitness;

import geneticos.Individuo;

public class FitnessPr1Func3 extends FuncionFitness {

	public FitnessPr1Func3(boolean isMinimizacion) {
		super(isMinimizacion);
	}

	public void evaluate(Individuo ind) {
		double x = (double) ind.getFenotipo().get(0);
		double y = (double) ind.getFenotipo().get(1);

		double parentesis1 = 4*(Math.PI)*x;
		double parentesis2 = 20*(Math.PI)*y;
		
		
		
		double result = 21.5 + (x*Math.sin(parentesis1)) + (y*Math.sin(parentesis2));
		
		ind.setFitness(result);
	}

}