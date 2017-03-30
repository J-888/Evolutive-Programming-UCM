package view;

import geneticos.Individuo;
import operadores.cruce.Aritmetico;
import operadores.cruce.FuncionCruce;
import operadores.cruce.Monopunto;
import operadores.mutacion.FuncionMutacion;
import operadores.mutacion.MutaBaseABase;
import operadores.seleccion.EstocasticoUniversal;
import operadores.seleccion.FuncionSeleccion;
import operadores.seleccion.Ruleta;
import operadores.seleccion.TorneoDeterminista;
import problemas.Pr1Func1;
import problemas.Pr1Func2;
import problemas.Pr1Func3;
import problemas.Pr1Func4;
import problemas.Pr1Func4Xtra;
import problemas.Pr1Func5;
import problemas.Pr2Ajuste;
import problemas.ProblemaFuncion;

public class Tester {
	
	private String problem;
	private int npass;
	private FuncionSeleccion[] selectionOptions;
	private FuncionCruce[] crossoverOptions;
	private double[] crossoverPercentages;
	private FuncionMutacion[] mutationOptions;
	private double[] mutationPercentages;
	private double elite;
	private int genNum;
	private int popSize;
	private int executions;
	private boolean isMinimization;
	
	private String bestCombination;
	private double bestFitness;

	public static void main(String[] args) {	
		generalBattery();	
		//intensiveBattery();
	}
	
	public static void generalBattery() {	
		
		FuncionSeleccion[] selections = {new Ruleta(), new TorneoDeterminista(2), new TorneoDeterminista(3), new EstocasticoUniversal()};
		FuncionCruce[] crossoversBin = {new Monopunto()};
		FuncionCruce[] crossoversReal = {new Monopunto(), new Aritmetico()};
		double[] crossoverPercentages = {0, 0.10, 0.15, 0.20, 0.30, 0.40, 0.50, 0.60};
		FuncionMutacion[] mutationsBin = {new MutaBaseABase()};
		FuncionMutacion[] mutationsReal = {new MutaBaseABase()};
		double[] mutationPercentages = {0.02, 0.05, 0.10, 0.15, 0.20, 0.30};
		double elite = 0;
		int genNum = 15;
		int popSize = 50;
		int executions = 50;
		
		Tester t = new Tester("Pr1.1", selections, crossoversBin, crossoverPercentages, mutationsBin, mutationPercentages, elite, genNum, popSize, executions, true);
		//Tester t = new Tester("Pr1.2", selections, crossoversBin, crossoverPercentages, mutationsBin, mutationPercentages, elite, genNum, popSize, executions, true);
		//Tester t = new Tester("Pr1.3", selections, crossoversBin, crossoverPercentages, mutationsBin, mutationPercentages, elite, genNum, popSize, executions, false);
		//Tester t = new Tester("Pr1.4", 3, selections, crossoversBin, crossoverPercentages, mutationsBin, mutationPercentages, elite, genNum, popSize, executions, true);
		//Tester t = new Tester("Pr1.4Xtra", 3, selections, crossoversReal, crossoverPercentages, mutationsReal, mutationPercentages, elite, genNum, popSize, executions, true);
		//Tester t = new Tester("Pr1.5", selections, crossoversBin, crossoverPercentages, mutationsBin, mutationPercentages, elite, genNum, popSize, executions, true);
		
		t.run();
	}
	
	public static void intensiveBattery() {	
		
		FuncionSeleccion[] selections = {new Ruleta(), new TorneoDeterminista(2), new TorneoDeterminista(3), new EstocasticoUniversal()};
		FuncionCruce[] crossoversBin = {new Monopunto()};
		FuncionCruce[] crossoversReal = {new Monopunto(), new Aritmetico()};
		double[] crossoverPercentages = {0, 0.5, 0.10, 0.15, 0.20, 0.25, 0.30, 0.30, 0.40, 0.45, 0.50, 0.55, 0.60};
		FuncionMutacion[] mutationsBin = {new MutaBaseABase()};
		FuncionMutacion[] mutationsReal = {new MutaBaseABase()};
		double[] mutationPercentages = {0.02, 0.05, 0.10, 0.15, 0.20, 0.30, 0.35, 0.40};
		double elite = 0;
		int genNum = 15;
		int popSize = 50;
		int executions = 50;
		
		Tester t = new Tester("Pr1.1", selections, crossoversBin, crossoverPercentages, mutationsBin, mutationPercentages, elite, genNum, popSize, executions, true);
		//Tester t = new Tester("Pr1.2", selections, crossoversBin, crossoverPercentages, mutationsBin, mutationPercentages, elite, genNum, popSize, executions, true);
		//Tester t = new Tester("Pr1.3", selections, crossoversBin, crossoverPercentages, mutationsBin, mutationPercentages, elite, genNum, popSize, executions, false);
		//Tester t = new Tester("Pr1.4", 3, selections, crossoversBin, crossoverPercentages, mutationsBin, mutationPercentages, elite, genNum, popSize, executions, true);
		//Tester t = new Tester("Pr1.4Xtra", 3, selections, crossoversReal, crossoverPercentages, mutationsReal, mutationPercentages, elite, genNum, popSize, executions, true);
		//Tester t = new Tester("Pr1.5", selections, crossoversBin, crossoverPercentages, mutationsBin, mutationPercentages, elite, genNum, popSize, executions, true);
		
		t.run();
	}

	public Tester(String problem, FuncionSeleccion[] selections, FuncionCruce[] crossovers, double[] crossoverPercentages, FuncionMutacion[] mutations, double[] mutationPercentages, double elite, int genNum, int popSize, int executions, boolean isMin) {
		super();
		this.problem = problem;
		this.selectionOptions = selections;
		this.crossoverOptions = crossovers;
		this.crossoverPercentages = crossoverPercentages;
		this.mutationOptions = mutations;
		this.mutationPercentages = mutationPercentages;
		this.elite = elite;
		this.genNum = genNum;
		this.popSize = popSize;
		this.executions = executions;
		this.isMinimization = isMin;
		this.bestCombination = "No combination found";	//default value
		
		if(isMinimization)
			bestFitness = Double.MAX_VALUE;
		else
			bestFitness = Double.MIN_VALUE;
	}
	
	public Tester(String problem, int npass, FuncionSeleccion[] selections, FuncionCruce[] crossovers, double[] crossoverPercentages, FuncionMutacion[] mutations, double[] mutationPercentages, double elite, int genNum, int popSize, int executions, boolean isMin) {
		this(problem, selections, crossovers, crossoverPercentages, mutations, mutationPercentages, elite, genNum, popSize, executions, isMin);
		this.npass = npass;
	}
	
	public void run() {
		
		System.err.println(problem + npass + ": Testing " + selectionOptions.length*crossoverOptions.length*crossoverPercentages.length*mutationOptions.length*mutationPercentages.length + " combinations...");
		
		for (int i = 0; i < selectionOptions.length; i++)
			for (int j = 0; j < crossoverOptions.length; j++)
				for (int k = 0; k < crossoverPercentages.length; k++)
					for (int l = 0; l < mutationOptions.length; l++)
						for (int m = 0; m < mutationPercentages.length; m++)
								run(selectionOptions[i], crossoverOptions[j], crossoverPercentages[k], mutationOptions[l], mutationPercentages[m]);	
		

		System.err.println("Best combination found:");
		System.err.println(bestCombination);
		System.err.println(bestFitness);
	}

	private void run(FuncionSeleccion fselec, FuncionCruce fcross, double pcross, FuncionMutacion fmut, double pmut) {
		
		ProblemaFuncion pf = null;
		
		switch (problem) {
		case "Pr1.1":
			pf = new Pr1Func1(fcross, fmut, fselec, elite, genNum, popSize, null);
			break;
		case "Pr1.2":
			pf = new Pr1Func2(fcross, fmut, fselec, elite, genNum, popSize, null);				
			break;
		case "Pr1.3":
			pf = new Pr1Func3(fcross, fmut, fselec, elite, genNum, popSize, null);
			break;
		case "Pr1.4":
			pf = new Pr1Func4(fcross, fmut, fselec, elite, genNum, popSize, null, npass);
			break;
		case "Pr1.4Xtra":
			pf = new Pr1Func4Xtra(fcross, fmut, fselec, elite, genNum, popSize, null, npass);
			break;
		case "Pr1.5":
			pf = new Pr1Func5(fcross, fmut, fselec, elite, genNum, popSize, null);
			break;
		case "Pr2.Ajuste":
			pf = new Pr2Ajuste(fcross, fmut, fselec, elite, genNum, popSize, null);
			break;
		/*case "Pr2.Datos12":
			pf = new Pr2Datos12(fcross, fmut, fselec, elite, genNum, popSize, null);
			break;
		case "Pr2.Datos15":
			pf = new Pr2Datos15(fcross, fmut, fselec, elite, genNum, popSize, null);
			break;
		case "Pr2.Datos30":
			pf = new Pr2Datos30(fcross, fmut, fselec, elite, genNum, popSize, null);
			break;*/
		default:
			break;
		}

		double avgFitness = 0;
		for (int i = 0; i < executions; i++) {
			Individuo bestFound = pf.execute();
			avgFitness += bestFound.getFitness();
		}
		avgFitness = avgFitness/executions;
		
		String currentCombination = fselec + " & " + fcross + " (" + pcross*100 + "%) & " + fmut + " (" + pmut*100 + "%)";
		if((isMinimization && avgFitness < bestFitness) || (!isMinimization && avgFitness > bestFitness)) {
			bestFitness = avgFitness;
			bestCombination = currentCombination;
		}
		
		System.out.println(currentCombination + System.lineSeparator() + avgFitness);
	}
}
