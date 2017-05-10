package geneticos;

import geneticos.cromosomas.CromosomaStd;

import java.util.ArrayList;

public class IndividuoStd extends Individuo {

	private CromosomaStd cromosoma;
	
	public IndividuoStd(CromosomaStd c){
		cromosoma = c;
		fenotipo = cromosoma.toFenotipo();
	}
	
	public IndividuoStd(){}
	
	public IndividuoStd clone(){
		IndividuoStd nuevo = new IndividuoStd(cromosoma.clone());
		nuevo.setFitness(fitness);
		nuevo.setFitnessAdaptado(fitnessAdaptado);
		nuevo.setFitnessEscalado(fitnessEscalado);
		return nuevo;
	}
	
	public void updateFenotipo(){
		fenotipo = cromosoma.toFenotipo();
	}
	
	public String toString() {
		String res = "";
		for(int i = 0; i < getFenotipo().size(); i++){
			res += ("Gen#" + i + ":" + getFenotipo().get(i));
			if(i != getFenotipo().size()-1)
				res += ", ";
		}
		return res;
	}
	
	public String toStringShort() {
		String res = "";
		for(int i = 0; i < getFenotipo().size(); i++){
			res += (getFenotipo().get(i));
			if(i != getFenotipo().size()-1)
				res += " ";
		}
		return res;
	}
	
	public String toStringTabla() {
		String res = "";
		for(int i = 0; i < getFenotipo().size(); i++){
			res += (getFenotipo().get(i));
			if(i != getFenotipo().size()-1)
				res += " ";
		}
		return res;
	}
	
	public CromosomaStd getCromosoma() {
		return cromosoma;
	}
}
