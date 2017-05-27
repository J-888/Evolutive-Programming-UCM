package operadores.fitness;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import geneticos.Individuo;
import geneticos.cromosomas.CromosomaPG;
import problemas.Pr3MuxN;
import util.Utiles;

public class BloatControl {
	public enum AntibloatingMethod { 
		NONE {
			public void inicialization(ArrayList<Individuo> poblacion) { /*Do nothing*/ }
			public void adjustFitness(Individuo individuo) { /*Do nothing*/ }
		
		}, NAIVE {
			private final double k = 0.1; //worsened fitness for each node

			public void inicialization(ArrayList<Individuo> poblacion) { /*Do nothing*/ }
			
			public void adjustFitness(Individuo individuo) {
				Double adjustedFitness;

				adjustedFitness = individuo.getFitnessAdaptado() - this.k * ((CromosomaPG) individuo.getCromosoma()).getArbol().getNumNodos();

				individuo.setFitnessAdaptado(adjustedFitness);
			}
			
		}, TARPEIAN {
			private final double chambers = 3; //russian roulette chambers
			private double avgNodes;

			public void inicialization(ArrayList<Individuo> poblacion) {
				avgNodes = 0;
				for (Iterator iterator = poblacion.iterator(); iterator.hasNext();) {
					Individuo individuo = (Individuo) iterator.next();
					avgNodes += ((CromosomaPG) individuo.getCromosoma()).getArbol().getNumNodos();
				}
				avgNodes = avgNodes / poblacion.size();
			}
			
			public void adjustFitness(Individuo individuo) {
				int numNodes = ((CromosomaPG) individuo.getCromosoma()).getArbol().getNumNodos();
				if(numNodes > avgNodes && (Utiles.randomIntNO()%chambers == 0)) {
					individuo.setFitnessAdaptado(0);
				}
			}
			
		}, CPP { 				//Covariant Parsimony Pressure
			private double k;

			public void inicialization(ArrayList<Individuo> poblacion) {

				List<Double> fitnessList = poblacion.stream().map(ind -> ind.getFitnessAdaptado()).collect(Collectors.toList());
				List<Double> nodeNumberList = poblacion.stream().map(ind -> ((double)((CromosomaPG)ind.getCromosoma()).getArbol().getNumNodos())).collect(Collectors.toList());
				
				double nodeAvg = Utiles.mean(nodeNumberList);
				double cov_Leng_Fit = Utiles.covariance(nodeNumberList, nodeAvg, fitnessList);
				double var_Leng = Utiles.variance(nodeNumberList, nodeAvg);
				k = cov_Leng_Fit/var_Leng;
			}
			
			public void adjustFitness(Individuo individuo) {
				double adjustedFitness;
				adjustedFitness = individuo.getFitnessAdaptado() - this.k * ((CromosomaPG) individuo.getCromosoma()).getArbol().getNumNodos();

				individuo.setFitnessAdaptado(adjustedFitness);
			}
			
		},	ADVANCED {
			private double maxNodes = -1;
			private final double pow = 2;	//strength

			public void inicialization(ArrayList<Individuo> poblacion) { 
				maxNodes = Math.pow(Pr3MuxN.maxEntsPorNodo, Pr3MuxN.profundidadMax) - 1;
			}
			
			public void adjustFitness(Individuo individuo) {
				int numNodos = ((CromosomaPG) individuo.getCromosoma()).getArbol().getNumNodos();
				if(numNodos > maxNodes) {
					double ratio = numNodos/maxNodes;
					ratio = Math.pow(ratio, pow);
					individuo.setFitnessAdaptado(individuo.getFitnessAdaptado()/ratio);
				}			
			}
			
		};
		
		public String toString() {
			String name = this.name();
			if(name.length() > 3)
				name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
			return name;
		}
		public abstract void inicialization(ArrayList<Individuo> poblacion);
		public abstract void adjustFitness(Individuo individuo);
	};
	
	private AntibloatingMethod currentMethod;
	
	public BloatControl(AntibloatingMethod currentMethod) {
		this.currentMethod = currentMethod;
	}

	public BloatControl(String currentMethodStr) {
		this.currentMethod = AntibloatingMethod.valueOf(currentMethodStr);
	}
	
	public boolean isActive() {
		return currentMethod != AntibloatingMethod.NONE;
	}

	public void adjustFitness(ArrayList<Individuo> poblacion) {
		
		currentMethod.inicialization(poblacion);
		
		double worseFitness = Double.MAX_VALUE;
		for (Iterator iterator = poblacion.iterator(); iterator.hasNext();) {
			Individuo individuo = (Individuo) iterator.next();
			currentMethod.adjustFitness(individuo);
			if((currentMethod == AntibloatingMethod.NAIVE || currentMethod == AntibloatingMethod.CPP) && individuo.getFitnessAdaptado() < worseFitness)
				worseFitness = individuo.getFitnessAdaptado();				
		}
		
		if((currentMethod == AntibloatingMethod.NAIVE || currentMethod == AntibloatingMethod.CPP) && worseFitness < 0) {
			for (Iterator iterator = poblacion.iterator(); iterator.hasNext();) {
				Individuo individuo = (Individuo) iterator.next();
				individuo.setFitnessAdaptado(individuo.getFitnessAdaptado() - worseFitness);			
			}
		}
	}
	
	
	
}
