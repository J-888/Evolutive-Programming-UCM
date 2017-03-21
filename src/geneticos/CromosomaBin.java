package geneticos;

import java.util.ArrayList;
import util.Par;
import util.Utiles;


public class CromosomaBin extends Cromosoma {
	
	public CromosomaBin(ArrayList<Par<Double>> rango, TipoCromosoma tipo){
		this.tipo = tipo;
		this.rango = rango;
		genes = new ArrayList<Gen>(rango.size());
	}

	public ArrayList<Double> toFenotipo() {
		ArrayList<Double> fen = new ArrayList<Double>();
		for (int i = 0; i < genes.size(); i++) {
			double max_range = rango.get(i).getN1();
			double min_range = rango.get(i).getN2();
			int lgen = ((GenBin)genes.get(i)).getLongitud();
			double value = min_range + (max_range - min_range) * Utiles.bin_dec(genes.get(i).toString()) / (Math.pow(2, lgen) - 1);
			fen.add(value);
		}
		return fen;
	}
	
	public void randomizeCromosome(double tolerancia){
		for (int i = 0; i < rango.size(); i++) {
			Par<Double> rangVar = (Par<Double>) rango.get(i);
			int longGen = getGenLenBin(rangVar.getN1(), rangVar.getN2(), tolerancia);
			GenBin newGen = new GenBin(longGen);
			newGen.initializeRandomGen();
			genes.add(newGen);
		}
		
	}
	
	public Cromosoma clone(){
		Cromosoma nuevo = new CromosomaBin(rango, tipo);
		ArrayList<Gen> nuevosGenes = new ArrayList<Gen>();
		for(int i = 0; i < genes.size(); i++){
			nuevosGenes.add(genes.get(i).clone());
		}
		nuevo.setGenes(nuevosGenes);
		
		return nuevo;
	}
	
}
