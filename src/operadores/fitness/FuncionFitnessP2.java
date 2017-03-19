package operadores.fitness;

import geneticos.Individuo;

public class FuncionFitnessP2 extends FuncionFitness {

	public void evaluate(Individuo ind) {
		double x1 = ind.getFenotipo().get(0);
		double x2 = ind.getFenotipo().get(1);

		double parentesis1 = (x2 + 47);
		double parentesis2 = Math.sqrt(Math.abs(x2 + (x1/2.0) + 47));
		double parentesis3 = Math.sqrt(Math.abs(x1 - (x2 + 47)));
		
		double result = ((-1)*parentesis1*Math.sin(parentesis2)) - (x1*Math.sin(parentesis3)); 
		
		ind.setFitness(result);
	}
	
}