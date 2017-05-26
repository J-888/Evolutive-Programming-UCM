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
	public static List<Integer> valoresSelectsYEnts;
	
	public FitnessPr3MuxN (boolean isMinimizacion, int nSoloEnts, int nSelects){
		super(isMinimizacion);
		this.nSelect = nSelects;
		this.nPosib = (int) Math.pow(2,(nSoloEnts + nSelect));
		
		valoresSelectsYEnts = new ArrayList<Integer>(Arrays.asList(new Integer[nSoloEnts + nSelect]));
		Collections.fill(valoresSelectsYEnts, 0);
	}
	
	public void evaluate(Individuo indiv) {
		CromosomaPG crom = (CromosomaPG) indiv.getCromosoma();
		Node arbol = crom.getArbol();
		int entSeleccionada;
		boolean valorEntSelec;
		int numAciertos = 0;
		
		for(int i = 0; i < nPosib; i++) {
			List<Integer> selectSL = valoresSelectsYEnts.subList(0, nSelect);
			entSeleccionada = Utiles.array2int(selectSL);
			valorEntSelec = valoresSelectsYEnts.get(entSeleccionada+nSelect) == 1;
			if (valorEntSelec == arbol.resolve())
				numAciertos++;
			
			if(i != nPosib -1)
				increment();
			else
				Collections.fill(valoresSelectsYEnts, 0);
		}
		
		indiv.setFitness(numAciertos);
		
	}
	
	private void increment() {
	    boolean carry = true;
	    for (int i = (valoresSelectsYEnts.size() - 1); i >= 0; i--) {
	        if (carry) {
	            if (valoresSelectsYEnts.get(i) == 0) {
	            	valoresSelectsYEnts.set(i, 1);
	                carry = false;
	            }
	            else {
	            	valoresSelectsYEnts.set(i, 0);
	                carry = true;
	            }
	        }
	    }
	}
	
}
