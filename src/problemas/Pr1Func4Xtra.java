package problemas;

import geneticos.CromosomaReal;
import geneticos.Individuo;
import geneticos.TipoCromosoma;

import java.util.ArrayList;

import javax.swing.JFrame;

import operadores.cruce.FuncionCruce;
import operadores.fitness.FitnessPr1Func4;
import operadores.mutacion.FuncionMutacion;
import operadores.seleccion.FuncionSeleccion;
import util.Par;
import view.GraficaPanel;

public class Pr1Func4Xtra extends Problema{
	
	private int N;
	
	public Pr1Func4Xtra(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, JFrame gui, int n){
		super(funcCruz, funcMuta, funcSelec, elite0to1, numGenerations, tamPob, n, gui);
		
		this.N = n;
		this.rangoVar = new ArrayList<Par<Double>>(N);
		for(int i = 0; i < N; i++)
			this.rangoVar.add(new Par<Double>(new Double(0.0),new Double(Math.PI))); //reminder to update rangoSize on super constructor 
		this.tolerancia = 0.001;
		this.minimizacion = true;
		this.funcFit = new FitnessPr1Func4(this.minimizacion);
		this.funcMuta.setFuncionFitness(this.funcFit);
	}
	
	public void generaPobIni() {
		for (int i = 0; i < tamPob; i++) {
			CromosomaReal newCromo = new CromosomaReal(rangoVar, TipoCromosoma.REAL);
			newCromo.randomizeCromosome(tolerancia);
			Individuo newInd = new Individuo(newCromo);
			poblacion.add(newInd);		
		}
	}
	
	public Double getOptimo(){
		return null;
	}
}