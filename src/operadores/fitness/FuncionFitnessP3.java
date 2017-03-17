package operadores.fitness;

import java.util.ArrayList;

import util.Par;
import geneticos.Individuo;

public class FuncionFitnessP3 extends FuncionFitness {

	public void evaluate(Individuo ind) {
		double x = ind.getFenotipo().get(0);
		double y = ind.getFenotipo().get(1);

		double parentesis1 = 4*(Math.PI)*x;
		double parentesis2 = 20*(Math.PI)*y;
		
		
		
		double result = 21.5 + (x*Math.sin(parentesis1)) + (y*Math.sin(parentesis2));
		
		ind.setFitness(result);
	}

}