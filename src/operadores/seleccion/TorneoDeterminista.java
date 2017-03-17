package operadores.seleccion;

import geneticos.Individuo;

import java.util.ArrayList;

import util.Utiles;

public class TorneoDeterminista extends FuncionSeleccion {
	private int nElems;
	
	public TorneoDeterminista(int ne){
		nElems = ne;
	}
	
	public String toString() {
		return "Torneo DET (" + nElems + ")";
	}

	@Override
	public Individuo select(ArrayList<Individuo> orderedPob, ArrayList<Double> punts_acum) {
		ArrayList<Individuo> inds = new ArrayList<Individuo>(nElems);
		for(int i = 0; i < nElems; i++)
			inds.add(orderedPob.get(Utiles.randomIntNO()%orderedPob.size()));
		Individuo best = new Individuo();
		best.setFitnessAdaptado(-1);
		for(int i = 0; i < nElems; i++){
			if(inds.get(i).getFitnessAdaptado() > best.getFitnessAdaptado())
				best = inds.get(i);
		}
		
		return best;
	}
}
