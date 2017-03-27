package operadores.fitness;

import geneticos.Individuo;

public class FitnessPr1Func5 extends FuncionFitness {

	public void evaluate(Individuo ind) {
		double x1 = (double) ind.getFenotipo().get(0);
		double x2 = (double) ind.getFenotipo().get(1);
		
		double sum1 = 0;
		double sum2 = 0;
		for(int i = 1; i <= 5; i++){
			sum1 += i*Math.cos((i+1)*x1 + i);
			sum2 += i*Math.cos((i+1)*x2 + i);
		}
		
		double result = sum1*sum2;
		
		ind.setFitness(result);
	}

}