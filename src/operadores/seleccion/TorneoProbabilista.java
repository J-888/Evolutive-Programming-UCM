package operadores.seleccion;

import geneticos.Individuo;
import geneticos.IndividuoStd;

import java.util.ArrayList;
import java.util.Collections;

import util.Utiles;

public class TorneoProbabilista extends FuncionSeleccion {
	private final double pParam = 0.60;

	@Override
	public Individuo select(ArrayList<Individuo> orderedPob, ArrayList<Double> punts_acum) {
		ArrayList<Individuo> inds = new ArrayList<Individuo>(2);
		for(int i = 0; i < 2; i++)
			inds.add(orderedPob.get(Utiles.randomIntNO()%orderedPob.size()));
		
		Collections.sort(inds);
		
		if(Utiles.randomDouble01() > pParam)
			return inds.get(0);
		else
			return inds.get(1);
	}
	
	public String toString() {
		return "Torneo PROB (2)";
	}
}
