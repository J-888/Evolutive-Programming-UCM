package operadores.seleccion;

import geneticos.Individuo;
import geneticos.IndividuoStd;

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
		Individuo best = new IndividuoStd();
		best.setFitnessAdaptado(Double.NEGATIVE_INFINITY);
		for(int i = 0; i < nElems; i++){
			if(!this.escalado) {
				if(inds.get(i).getFitnessAdaptado() >= best.getFitnessAdaptado())
					best = inds.get(i);
			}
			else {
				if(inds.get(i).getFitnessEscalado() >= best.getFitnessEscalado())
					best = inds.get(i);
			}
		}
		
		return best;
	}
}
