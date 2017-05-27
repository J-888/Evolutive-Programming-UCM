package operadores.fitness;

import java.util.ArrayList;
import java.util.Iterator;

import geneticos.Individuo;
import geneticos.cromosomas.CromosomaPG;
import util.Utiles;

public class BloatControl {
	public enum AntibloatingMethod { 
		NONE {
			public void inicialization(ArrayList<Individuo> poblacion) { /*Do nothing*/	}
			public void adjustFitness(Individuo individuo, boolean isMinimizacion) { /*Do nothing*/	}
		
		}, NAIVE {
			private final double k = 0.1; //worsened fitness for each node

			public void inicialization(ArrayList<Individuo> poblacion) { /*Do nothing*/	}
			
			public void adjustFitness(Individuo individuo, boolean isMinimizacion) {
				double adjustedFitness;
				if(isMinimizacion)
					adjustedFitness = individuo.getFitness() + this.k * ((CromosomaPG) individuo.getCromosoma()).getArbol().getNumNodos();
				else
					adjustedFitness = individuo.getFitness() - this.k * ((CromosomaPG) individuo.getCromosoma()).getArbol().getNumNodos();

				individuo.setFitness(adjustedFitness);
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
			
			public void adjustFitness(Individuo individuo, boolean isMinimizacion) {
				int numNodes = ((CromosomaPG) individuo.getCromosoma()).getArbol().getNumNodos();
				if(numNodes > avgNodes && (Utiles.randomIntNO()%chambers == 0)) {
					if(isMinimizacion)
						individuo.setFitness(Double.POSITIVE_INFINITY);
					else
						individuo.setFitness(Double.NEGATIVE_INFINITY);
				}
			}
			
		},	ADVANCED {
			private final double maxNodes = 35;
			private final double pow = 2;	//strength

			public void inicialization(ArrayList<Individuo> poblacion) { /*Do nothing*/	}
			
			public void adjustFitness(Individuo individuo, boolean isMinimizacion) {
				int numNodos = ((CromosomaPG) individuo.getCromosoma()).getArbol().getNumNodos();
				if(numNodos > maxNodes) {
					double ratio = numNodos/maxNodes;
					ratio = Math.pow(ratio, pow);
					if(isMinimizacion)
						individuo.setFitness(ratio/individuo.getFitness());
					else
						individuo.setFitness(individuo.getFitness()/ratio);
				}			
			}
			
		};

		public abstract void inicialization(ArrayList<Individuo> poblacion);
		public abstract void adjustFitness(Individuo individuo, boolean isMinimizacion);
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

	public void adjustFitness(ArrayList<Individuo> poblacion, boolean isMinimizacion) {
		
		currentMethod.inicialization(poblacion);
		
		for (Iterator iterator = poblacion.iterator(); iterator.hasNext();) {
			Individuo individuo = (Individuo) iterator.next();
			currentMethod.adjustFitness(individuo, isMinimizacion);
		}
	}
	
	
	
}
