package geneticos;

import java.util.ArrayList;
import java.util.Collections;

import util.Par;

public class CromosomaPermInt extends Cromosoma implements Comparable{

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
			genes.add(nuevoGen);
		}
		
	}

	public Cromosoma clone() {
		Cromosoma nuevo = new CromosomaPermInt(rango, tipo);
		ArrayList<Gen> nuevosGenes = new ArrayList<Gen>(this.genes.size());
		for(int i = 0; i < genes.size(); i++){
			nuevosGenes.add(genes.get(i).clone());
		}
		nuevo.setGenes(nuevosGenes);
		
		return nuevo;
	}
	
	public boolean esValido(){
		boolean ret = true;
		for(int i = 0; i < genes.size(); i++){
			for(int j = 0; j < genes.size(); j++){
				if (i != j){
					if(genes.get(i).getBases().get(0).equals(genes.get(j).getBases().get(0))){
						System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						ret = false;
					}
				}
			}
		}
		
		
		return ret;
	}

	@Override
	public int compareTo(Object o) {
		CromosomaPermInt otro = (CromosomaPermInt)o;
		
		int i = 0;
		int ret = 0;
		boolean finished = false;
		while(i < this.rango.get(0).getN1() && !finished){
			if((int)this.getGenes().get(i).getBases().get(0) < (int)otro.getGenes().get(i).getBases().get(0)){
				finished = true;
				ret = -1;
			}
			else if((int)this.getGenes().get(i).getBases().get(0) > (int)otro.getGenes().get(i).getBases().get(0)){
				finished = true;
				ret = 1;
			}
			
			i++;
		}
		
		return ret;
	}

}
