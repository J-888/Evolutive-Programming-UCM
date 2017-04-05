package problemas;

import javax.swing.JFrame;

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

public class Pr2Datos15 extends ProblemaFuncion {
	
	public Pr2Datos15(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, JFrame gui){
		super(funcCruz, funcMuta, funcSelec, elite0to1, numGenerations, tamPob, 1, gui);
		
		this.rangoVar.add(new Par<Double>((double)DatosMatrices.getMatrixDim("datos15") , (double)DatosMatrices.getMatrixDim("datos15"))); //reminder to update rangoSize on super constructor 
		this.funcFit = new FitnessPr2("datos15");
		this.minimizacion = true;
	}
	
	
	public void generaPobIni() {
		for (int i = 0; i < tamPob; i++) {
			CromosomaPermInt newCromo = new CromosomaPermInt(rangoVar, TipoCromosoma.PERMINT);
			newCromo.randomizeCromosome(tolerancia);
			Individuo newInd = new Individuo(newCromo);
			poblacion.add(newInd);		
		}
	}

}