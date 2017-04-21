package operadores.seleccion;

import geneticos.Individuo;
import util.EscaladoLineal;

import java.util.ArrayList;

public abstract class FuncionSeleccion {
	
	protected boolean escalado;
	
	public void clasificarPob(ArrayList<Individuo> poblacion, ArrayList<Double> puntuaciones, ArrayList<Double> punts_acum){
		double fitTotalAdaptOrSca;
		if (!this.escalado)
			fitTotalAdaptOrSca = fitnessAdaptadoPob(poblacion);
		else
			fitTotalAdaptOrSca = fitnessEscaladoPob(poblacion);
		
		for(int j = 0; j < poblacion.size(); j++){
			Individuo indd = poblacion.get(j);
			if(!this.escalado)
				puntuaciones.add(indd.getFitnessAdaptado() / fitTotalAdaptOrSca);
			else
				puntuaciones.add(indd.getFitnessEscalado() / fitTotalAdaptOrSca);
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
	
	public double fitnessEscaladoPob(ArrayList<Individuo> poblacion){
		int fittotaladap = 0;
		for(int i = 0; i < poblacion.size(); i++){
			fittotaladap += poblacion.get(i).getFitnessEscalado();
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
	
	public void setEscalado(boolean useEscalado) {
		this.escalado = useEscalado;
	}
	
	public void scale(EscaladoLineal escalado, ArrayList<Individuo> poblacion){
		for(int i = 0; i < poblacion.size(); i++){
			scaleInd(escalado, poblacion.get(i));
		}
	}
	
	public void scaleInd(EscaladoLineal escalado, Individuo ind){
		ind.setFitnessEscalado(escalado.scaleFitness(ind.getFitnessAdaptado()));
	}
	
	public abstract String toString();

	public abstract Individuo select(ArrayList<Individuo> orderedPob, ArrayList<Double> punts_acum);
	
}
