package util;

public class EscaladoLineal {
	
	private final double P = 1.5;
	private double aParam;
	private double bParam;

	public EscaladoLineal(int tamPob, double bestFitness, double avgFitness) {
		this.aParam = ((P - 1) * avgFitness) / (bestFitness - avgFitness);
		this.bParam = (1 - aParam) * avgFitness;
	}
	
	public double scaleFitness(double fitness){
		return aParam * fitness + bParam;
	}
}
