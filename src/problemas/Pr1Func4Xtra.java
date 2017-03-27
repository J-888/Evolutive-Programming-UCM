package problemas;

import geneticos.CromosomaReal;
import geneticos.Individuo;
import geneticos.TipoCromosoma;

import java.util.ArrayList;

import operadores.cruce.FuncionCruce;
import operadores.fitness.FitnessPr1Func4;
import operadores.mutacion.FuncionMutacion;
import operadores.seleccion.FuncionSeleccion;
import util.Par;
import view.GraficaPanel;

public class Pr1Func4Xtra extends ProblemaFuncion{
	
	private int N;
	
	public Pr1Func4Xtra(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, GraficaPanel chartPanel, int n){
		this.N = n;
		this.funcSelec = funcSelec;
		this.funcMuta = funcMuta;
		this.funcCruz = funcCruz;
		this.rangoVar = new ArrayList<Par<Double>>(N);
		for(int i = 0; i < N; i++)
			this.rangoVar.add(new Par<Double>(new Double(0.0),new Double(Math.PI)));
		this.tolerancia = 0.001;
		this.funcFit = new FitnessPr1Func4();
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
			CromosomaReal newCromo = new CromosomaReal(rangoVar, TipoCromosoma.REAL);
			newCromo.randomizeCromosome(tolerancia);
			Individuo newInd = new Individuo(newCromo);
			poblacion.add(newInd);		
		}
	}
}