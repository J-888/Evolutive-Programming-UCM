package operadores.fitness;

import java.util.ArrayList;
import java.util.List;

import util.Par;
import datos.DatosMatrices;
import geneticos.CromosomaPermInt;
import geneticos.Individuo;
import geneticos.TipoCromosoma;

public class FitnessPr2 extends FuncionFitness {

	private String fileName;
	private List<List<Integer>> matDists;
	private List<List<Integer>> matFluxs;
	private int n;
		
	public FitnessPr2 (String fn, boolean isMinimizacion){
		super(isMinimizacion);
		this.fileName = fn;
		matDists = DatosMatrices.getMatrizDist(fileName);
		matFluxs = DatosMatrices.getMatrizFlux(fileName);
		n = DatosMatrices.getMatrixDim(fileName);
	}
	
	public void evaluate(Individuo ind) {
		double trafico = 0;
		
		for(int i = 0; i < n-1; i++){
			int gen = (int) ind.getFenotipo().get(i);	
			for(int j = i+1; j < n; j++){
				int genSig = (int) ind.getFenotipo().get(j);	
				trafico += matDists.get(i).get(j)*matFluxs.get(gen).get(genSig);
			}
		}
		
		ind.setFitness(trafico*2); //*2 por ser los flujos en ambos sentidos
	}
	
}
