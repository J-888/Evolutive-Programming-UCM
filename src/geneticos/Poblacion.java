package geneticos;

import java.util.ArrayList;

public class Poblacion {
	private ArrayList<Individuo> poblacion;
	private Individuo mejorIndividuo;
	private Individuo mejorAbsoluto;
	private Individuo peorIndividuo;
	private double pobAvgFitness;

	public Poblacion(ArrayList<Individuo> poblacion, Individuo mejorIndividuo, Individuo mejorAbsoluto,
			Individuo peorIndividuo, double pobAvgFitness) {
		this.poblacion = poblacion;
		this.mejorIndividuo = mejorIndividuo;
		this.mejorAbsoluto = mejorAbsoluto;
		this.peorIndividuo = peorIndividuo;
		this.pobAvgFitness = pobAvgFitness;
	}

	public ArrayList<Individuo> getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(ArrayList<Individuo> poblacion) {
		this.poblacion = poblacion;
	}

	public Individuo getMejorIndividuo() {
		return mejorIndividuo;
	}

	public void setMejorIndividuo(Individuo mejorIndividuo) {
		this.mejorIndividuo = mejorIndividuo;
	}

	public Individuo getMejorAbsoluto() {
		return mejorAbsoluto;
	}

	public void setMejorAbsoluto(Individuo mejorAbsoluto) {
		this.mejorAbsoluto = mejorAbsoluto;
	}

	public Individuo getPeorIndividuo() {
		return peorIndividuo;
	}

	public void setPeorIndividuo(Individuo peorIndividuo) {
		this.peorIndividuo = peorIndividuo;
	}

	public double getPobAvgFitness() {
		return pobAvgFitness;
	}

	public void setPobAvgFitness(double pobAvgFitness) {
		this.pobAvgFitness = pobAvgFitness;
	}

}
