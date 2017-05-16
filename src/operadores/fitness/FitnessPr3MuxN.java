package operadores.fitness;

import geneticos.Individuo;
import geneticos.IndividuoPG;
import geneticos.cromosomas.CromosomaPG;
import util.Utiles;
import util.pg.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FitnessPr3MuxN extends FuncionFitness {
	
	private int nSelect;
	private int nPosib;
	public static List<Integer> valorEnts;
	
	public FitnessPr3MuxN (boolean isMinimizacion, int nEnt){
		super(isMinimizacion);
		nSelect = (int) Math.sqrt(nEnt);
		nPosib = 2^(nEnt + nSelect);
	}
	
	public void evaluate(Individuo indiv) {
		CromosomaPG crom = (CromosomaPG) indiv.getCromosoma();
		Node arbol = crom.getArbol();
		int nEnt = crom.getnEntradas();
		int entSeleccionada;
		boolean valorEntSelec;
		int numAciertos = 0;
		
		valorEnts = new ArrayList<Integer>(Arrays.asList(new Integer[nEnt]));
		Collections.fill(valorEnts, 0);
		
		for(int i = 0; i < nPosib; i++) {
			List<Integer> selectSL = valorEnts.subList(0, nSelect);
			entSeleccionada = Utiles.array2int(selectSL);
			valorEntSelec = valorEnts.get(entSeleccionada) == 1;
			if (valorEntSelec == arbol.resolve())
				numAciertos++;
			
			if(i != nPosib -1)
				increment();
			else
				Collections.fill(valorEnts, 0);
		}
		
		indiv.setFitness(numAciertos);
		
	}
	
	private void increment() {
	    boolean carry = true;
	    for (int i = (valorEnts.size() - 1); i >= 0; i--) {
	        if (carry) {
	            if (valorEnts.get(i) == 0) {
	            	valorEnts.set(i, 1);
	                carry = false;
	            }
	            else {
	            	valorEnts.set(i, 0);
	                carry = true;
	            }
	        }
	    }
	}
	
}
