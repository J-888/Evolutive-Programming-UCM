package problemas;

import java.util.ArrayList;

import datos.DatosMatrices;
import operadores.cruce.FuncionCruce;
import operadores.fitness.FitnessPr2;
import operadores.mutacion.FuncionMutacion;
import operadores.seleccion.FuncionSeleccion;
import util.Par;
import view.GraficaPanel;
import geneticos.CromosomaPermInt;
import geneticos.Individuo;
import geneticos.TipoCromosoma;

public class Pr2Ajuste extends ProblemaFuncion {
	
	public Pr2Ajuste(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, GraficaPanel chartPanel){
		this.funcSelec = funcSelec;
		this.funcMuta = funcMuta;
		this.funcCruz = funcCruz;
		this.rangoVar = new ArrayList<Par<Double>>();
		this.rangoVar.add(new Par<Double>(DatosMatrices.getMatrixDim(1) , DatosMatrices.getMatrixDim(1)));
		this.funcFit = new FitnessPr2();
		this.tamPob = tamPob;
		this.numGenerations = numGenerations;
		this.tamElite = (int)Math.floor(elite0to1 * tamPob);	
		this.poblacion = new ArrayList<Individuo>(tamPob);
		this.puntuaciones = new ArrayList<Double>(tamPob);
		this.punts_acum = new ArrayList<Double>(tamPob);
		this.grafica = chartPanel;
		this.minimizacion = true;
	}
	
	
	public void generaPobIni() {
		for (int i = 0; i < tamPob; i++) {
			CromosomaPermInt newCromo = new CromosomaPermInt(rangoVar, TipoCromosoma.BIN);
			newCromo.randomizeCromosome(tolerancia);
			Individuo newInd = new Individuo(newCromo);
			poblacion.add(newInd);		
		}
	}

}