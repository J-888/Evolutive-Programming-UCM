package operadores.cruce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import geneticos.Individuo;
import geneticos.IndividuoStd;
import geneticos.TipoCromosoma;
import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaStd;
import util.Par;

/*
 * Crossover utilizado en "A Genetic Approach to the Quadratic Assignment Problem", por David M. Tate y Alice E. Smith.
 * 
 * Es un crossover bastante poco agresivo, pero muy sólido, que permite altos % de cross y da muy buenos resultados.
 */

public class OPX extends FuncionCruce {
	
	private FuncionCruce[] crossoverOptionsBin = {new Monopunto(), new Multipunto(2), new Multipunto(3), new Uniforme()}; 
	private FuncionCruce[] crossoverOptionsReal = {new Monopunto(), new Multipunto(2), new Multipunto(3), new Uniforme(), new Aritmetico()};
	private FuncionCruce[] crossoverOptionsPermInt = {new PMX(), new OX(), new OXPosPrio(), new OXOrdenPrio(), new CX(), /*new ERX(),*/ new Monopunto(), new Multipunto(2), new Multipunto(3), new Uniforme(), new SX()};
	private FuncionCruce[] current;
	
	@Override
	protected Par<Cromosoma> cruceCromosomas(Cromosoma pp1, Cromosoma pp2) {
		CromosomaStd p1 = (CromosomaStd) pp1;
		CromosomaStd p2 = (CromosomaStd) pp2;
		
		if(p1.getTipo() == TipoCromosoma.BIN || p2.getTipo() == TipoCromosoma.BIN)	//redundante
			current = crossoverOptionsBin;
		if(p1.getTipo() == TipoCromosoma.REAL || p2.getTipo() == TipoCromosoma.REAL)	//redundante
			current = crossoverOptionsReal;
		if(p1.getTipo() == TipoCromosoma.PERMINT || p2.getTipo() == TipoCromosoma.PERMINT)	//redundante
				current = crossoverOptionsPermInt;
		else {
			System.err.println("Tipo de cromosoma desconocido");
			return null;
		}
		
		ArrayList<Individuo> children = new ArrayList<>(current.length*2);
		
		for (int i = 0; i < current.length; i++) {
			Par<Cromosoma> cromInd = current[i].cruceCromosomas(p1, p2);
			IndividuoStd child1 = new IndividuoStd((CromosomaStd)cromInd.getN1());
			IndividuoStd child2 = new IndividuoStd((CromosomaStd)cromInd.getN2());
			children.add(child1);
			children.add(child2);
		}

		
		IndividuoStd h1 = (IndividuoStd) this.funFit.evaluate(children).get(0);	 //el mejor individuo obtenido
		IndividuoStd h2;	//segundo mejor individuo
				
		Collections.sort(children);
		
		if(h1.getFitness() == children.get(0).getFitness())	//ordenado de mejor a peor
			h2 = (IndividuoStd) children.get(1);
		else
			h2 = (IndividuoStd) children.get(children.size()-1);
		
		return new Par<Cromosoma>(h1.getCromosoma().clone(), h2.getCromosoma().clone());
	}

	@Override
	public String toString() {
		return "OPX";
	}
	
}
