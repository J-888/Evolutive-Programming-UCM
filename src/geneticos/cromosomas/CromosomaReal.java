package geneticos.cromosomas;

import geneticos.Gen;
import geneticos.GenReal;
import geneticos.TipoCromosoma;

import java.util.ArrayList;

import util.Par;

public class CromosomaReal extends CromosomaStd {
	
	public CromosomaReal(ArrayList<Par<Double>> rango, TipoCromosoma tipo){
		this.tipo = tipo;
		this.rango = rango;
		genes = new ArrayList<Gen>(rango.size());
	}

	public ArrayList<Double> toFenotipo() {
		ArrayList<Double> fen = new ArrayList<Double>(genes.size());
		for (int i = 0; i < genes.size(); i++) 
			fen.add((double) genes.get(i).getBases().get(0));
		
		return fen;
	}
	
	public void randomizeCromosome(double tolerancia){
		for (int i = 0; i < rango.size(); i++) {
			Par<Double> rangVar = (Par<Double>) rango.get(i);
			GenReal newGen = new GenReal(rangVar.getN1(), rangVar.getN2(), tolerancia);
			newGen.initializeRandomGen();
			genes.add(newGen);
		}
		
	}

	public CromosomaStd clone(){
		CromosomaStd nuevo = new CromosomaReal(rango, tipo);
		ArrayList<Gen> nuevosGenes = new ArrayList<Gen>(this.genes.size());
		for(int i = 0; i < genes.size(); i++){
			nuevosGenes.add(genes.get(i).clone());
		}
		nuevo.setGenes(nuevosGenes);
		
		return nuevo;
	}
	
}