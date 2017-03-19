package operadores.seleccion;

import geneticos.Individuo;
import java.util.ArrayList;
import java.util.Collections;
import util.Utiles;

public class Ruleta extends FuncionSeleccion {

	public Individuo select(ArrayList<Individuo> orderedPob, ArrayList<Double> punts_acum) {
		double rand = Utiles.randomDouble01();
		
		int idx = Collections.binarySearch(punts_acum, rand);
	
		if(idx < 0)
			idx = Math.abs(idx+1);
		
		return orderedPob.get(idx);
	}

	public String toString() {
		return "Ruleta";
	}

}
