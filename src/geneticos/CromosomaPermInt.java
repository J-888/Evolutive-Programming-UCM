package geneticos;

import java.util.ArrayList;
import java.util.Collections;

import util.Par;

public class CromosomaPermInt extends Cromosoma {

	public CromosomaPermInt (ArrayList<Par<Double>> rango, TipoCromosoma tipo){
		this.tipo = tipo;
		this.rango = rango;
		genes = new ArrayList<Gen>(rango.size());
	}
	
	public ArrayList<Integer> toFenotipo() {
		ArrayList<Integer> ret = new ArrayList<Integer>(genes.size());
		
		for(int i = 0; i < genes.size(); i++){
			ret.add((Integer) genes.get(i).getBases().get(0));
		}
		
		return ret;
	}

	public void randomizeCromosome(double tolerancia) {
		/* la tolerancia no se usa en este cromosoma, el rango(0) contiene el numero de centros */
		ArrayList<Integer> genePool = new ArrayList<Integer>(genes.size());
		
		for(int i = 0; i < rango.get(0).getN1(); i++){
			genePool.add(i);
		}
		
		Collections.shuffle(genePool);
		
		for(int i = 0; i < rango.get(0).getN1(); i++){
			GenInt nuevoGen = new GenInt(rango.get(0).getN1(), rango.get(0).getN2(),tolerancia);
			nuevoGen.instanceBases();
			nuevoGen.getBases().add(genePool.get(i));
		}
		
	}

	public Cromosoma clone() {
		Cromosoma nuevo = new CromosomaPermInt(rango, tipo);
		ArrayList<Gen> nuevosGenes = new ArrayList<Gen>();
		for(int i = 0; i < genes.size(); i++){
			nuevosGenes.add(genes.get(i).clone());
		}
		nuevo.setGenes(nuevosGenes);
		
		return nuevo;
	}
	
}
