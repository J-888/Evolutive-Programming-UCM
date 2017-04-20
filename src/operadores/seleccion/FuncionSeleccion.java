package operadores.seleccion;

import geneticos.Individuo;
import java.util.ArrayList;

public abstract class FuncionSeleccion {

	public abstract Individuo select(ArrayList<Individuo> orderedPob, ArrayList<Double> punts_acum);
	
	public void clasificarPob(ArrayList<Individuo> poblacion, ArrayList<Double> puntuaciones, ArrayList<Double> punts_acum){
		double fittotaladap = fitnessAdaptadoPob(poblacion);
		
		for(int j = 0; j < poblacion.size(); j++){
			Individuo indd = poblacion.get(j);
			puntuaciones.add(indd.getFitnessAdaptado() / fittotaladap);
			if(j==0)
				punts_acum.add(puntuaciones.get(j));
			else
				punts_acum.add(punts_acum.get(j-1) + puntuaciones.get(j));
		}
	}
	
	public double fitnessAdaptadoPob(ArrayList<Individuo> poblacion){
		int fittotaladap = 0;
		for(int i = 0; i < poblacion.size(); i++){
			fittotaladap += poblacion.get(i).getFitnessAdaptado();
		}
		return fittotaladap;
	}
	
	public double fitnessPob(ArrayList<Individuo> poblacion){
		int fittotal = 0;
		for(int i = 0; i < poblacion.size(); i++){
			fittotal += poblacion.get(i).getFitness();
		}
		return fittotal;
	}
	
	public void adapt(ArrayList<Individuo> poblacion, double fitPeor, boolean minimizacion){
		for(int i = 0; i < poblacion.size(); i++){
			adaptInd(poblacion.get(i), fitPeor, minimizacion);
		}
	}
	
	public void adaptInd(Individuo ind, double fitPeor, boolean minimizacion){
		if(minimizacion)
			ind.setFitnessAdaptado(fitPeor - ind.getFitness());
		else
			ind.setFitnessAdaptado(ind.getFitness() - fitPeor);
	}
	
	public abstract String toString();
	
}
