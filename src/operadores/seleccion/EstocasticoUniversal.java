package operadores.seleccion;

import geneticos.Individuo;
import geneticos.IndividuoStd;

import java.util.ArrayList;
import java.util.Collections;

import util.Utiles;

public class EstocasticoUniversal extends FuncionSeleccion {

	private boolean firstExec = true;
	private double currentMarca = Utiles.randomDouble01();
	private double despMarca;
	
	@Override
	public Individuo select(ArrayList<Individuo> orderedPob, ArrayList<Double> punts_acum) {
		if(firstExec){
			despMarca = 1/(double)orderedPob.size();
			firstExec = false;
		}
		else{
			currentMarca += despMarca;
			if(currentMarca > 1)
				currentMarca = currentMarca-1;
		}
		
		int idx = Collections.binarySearch(punts_acum, currentMarca);
		
		if(idx < 0)
			idx = Math.abs(idx+1);
		
		return orderedPob.get(idx);
	}

	@Override
	public String toString() {
		return "Estocástico Universal";
	}

}
