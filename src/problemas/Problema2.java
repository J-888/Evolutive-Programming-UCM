package problemas;

import geneticos.CromosomaBin;
import geneticos.Individuo;

import java.util.ArrayList;

import operadores.cruce.FuncionCruce;
import operadores.fitness.FuncionFitnessP1;
import operadores.fitness.FuncionFitnessP2;
import operadores.mutacion.FuncionMutacion;
import operadores.seleccion.FuncionSeleccion;
import util.Par;
import view.GraficaPanel;

public class Problema2 extends ProblemaFuncion{
		
	public Problema2(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, GraficaPanel chartPanel){
		this.funcSelec = funcSelec;
		this.funcMuta = funcMuta;
		this.funcCruz = funcCruz;
		this.rangoVar = new ArrayList<Par<Double>>();
		this.rangoVar.add(new Par<Double>(new Double(-512),new Double(512)));
		this.rangoVar.add(new Par<Double>(new Double(-512),new Double(512)));
		this.tolerancia = 0.001;
		this.funcFit = new FuncionFitnessP2();
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
			CromosomaBin newCromo = new CromosomaBin(rangoVar);
			newCromo.randomizeCromosome(tolerancia);
			Individuo newInd = new Individuo(newCromo);
			poblacion.add(newInd);		
		}
	}
}
