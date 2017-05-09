package problemas;

import geneticos.IndividuoStd;
import geneticos.TipoCromosoma;
import geneticos.cromosomas.CromosomaPermInt;

import java.util.TreeSet;

import javax.swing.JFrame;

import operadores.cruce.FuncionCruce;
import operadores.fitness.FitnessPr2;
import operadores.mutacion.FuncionMutacion;
import operadores.seleccion.FuncionSeleccion;

public class Pr3Mux4 extends Problema {
	public Pr3Mux4(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, JFrame gui){
		super(funcCruz, funcMuta, funcSelec, elite0to1, numGenerations, tamPob, 1, gui);
		
		this.minimizacion = false;
		//this.funcFit = new FitnessPr3Mux4(this.minimizacion);

		this.funcCruz.setFuncionFitness(this.funcFit);
		this.funcMuta.setFuncionFitness(this.funcFit);
	}
	
	
	public void generaPobIni() {
		String pobIniGenMethod = null;//gui.getPobIniGenMethod();
		if(pobIniGenMethod.equals("Creciente")){
			
		}
		else if(pobIniGenMethod.equals("Completo")){
			
		}
		else if(pobIniGenMethod.equals("RampedAndHalf")){
			
		}
		else{
			System.err.println("Error en la selección de generación de pob ini");
		}
	}

	public Double getOptimo(){
		return 64.0;
	}
}
