package problemas;

import javax.swing.JFrame;

import geneticos.CromosomaBin;
import geneticos.Individuo;
import geneticos.TipoCromosoma;

import operadores.cruce.FuncionCruce;
import operadores.fitness.FitnessPr1Func2;
import operadores.mutacion.FuncionMutacion;
import operadores.seleccion.FuncionSeleccion;
import util.Par;
import view.GraficaPanel;

public class Pr1Func2 extends Problema{
		
	public Pr1Func2(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, boolean escalado, JFrame gui){
		super(funcCruz, funcMuta, funcSelec, elite0to1, numGenerations, tamPob, 2, escalado, gui);
		
		this.rangoVar.add(new Par<Double>(new Double(-512),new Double(512))); //reminder to update rangoSize on super constructor 
		this.rangoVar.add(new Par<Double>(new Double(-512),new Double(512)));
		this.tolerancia = 0.001;
		this.minimizacion = true;
		this.funcFit = new FitnessPr1Func2(this.minimizacion);
		this.funcMuta.setFuncionFitness(this.funcFit);
	}
	
	public void generaPobIni() {
		for (int i = 0; i < tamPob; i++) {
			CromosomaBin newCromo = new CromosomaBin(rangoVar, TipoCromosoma.BIN);
			newCromo.randomizeCromosome(tolerancia);
			Individuo newInd = new Individuo(newCromo);
			poblacion.add(newInd);		
		}
	}
	
	public Double getOptimo(){
		return -959.6407;
	}
}
