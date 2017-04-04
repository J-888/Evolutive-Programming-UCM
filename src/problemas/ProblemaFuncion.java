package problemas;

import geneticos.Individuo;
import java.util.ArrayList;
import java.util.Collections;
import operadores.cruce.FuncionCruce;
import operadores.fitness.FuncionFitness;
import operadores.mutacion.FuncionMutacion;
import operadores.seleccion.FuncionSeleccion;
import util.Par;
import view.GraficaPanel;

public abstract class ProblemaFuncion {

	protected ArrayList<Individuo> poblacion;
	protected ArrayList<Individuo> poblacionNueva;
	protected double tolerancia;
	protected int tamPob;
	protected int numGenerations;
	protected ArrayList<Par<Double>> rangoVar;
	protected FuncionFitness funcFit;
	protected FuncionCruce funcCruz;
	protected FuncionMutacion funcMuta;
	protected FuncionSeleccion funcSelec;
	protected int tamElite;
	protected ArrayList<Double> puntuaciones;
	protected ArrayList<Double> punts_acum;
	protected Individuo mejorIndividuo;
	protected Individuo mejorAbsoluto;
	protected Individuo peorIndividuo;
	protected double pobAvgFitness;
	protected GraficaPanel grafica;
	protected boolean minimizacion;
	
	public ProblemaFuncion(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, int rangoSize, GraficaPanel chartPanel) {
		this.funcSelec = funcSelec;
		this.funcMuta = funcMuta;
		this.funcCruz = funcCruz;

		this.tamPob = tamPob;
		this.numGenerations = numGenerations;
		this.tamElite = (int)Math.floor(elite0to1 * tamPob);

		this.rangoVar = new ArrayList<Par<Double>>(rangoSize);
		this.poblacion = new ArrayList<Individuo>(tamPob);
		this.puntuaciones = new ArrayList<Double>(tamPob);
		this.punts_acum = new ArrayList<Double>(tamPob);

		this.grafica = chartPanel;
	}

	public abstract void generaPobIni();
	
	public Individuo execute(){
		generaPobIni();
		initGraphInds();
		
		for(int i = 0; i < numGenerations; i++){
			evalPoblacion();
			if (i != numGenerations-1){ //No es necesario en la ultima generacion
				adaptPoblacion();
				Collections.sort(poblacion);
				poblacionNueva = new ArrayList<Individuo>(tamPob);			
				puntuaciones = new ArrayList<Double>(tamPob);
				punts_acum = new ArrayList<Double>(tamPob);
				funcSelec.clasificarPob(poblacion, puntuaciones, punts_acum);
				rellenarPobCruce();
				funcMuta.mutar(poblacionNueva);
				insertElite();
				poblacion = poblacionNueva;
			}
			if(grafica != null)
				grafica.update(i, mejorIndividuo.getFitness(), peorIndividuo.getFitness(), pobAvgFitness, mejorAbsoluto.getFitness());
		}
	
		return mejorAbsoluto;
	}

	private void rellenarPobCruce(){ //Rellena la nueva poblacion cruzando si procede (y guardando espacio para la elite)
		while(poblacionNueva.size() < tamPob - tamElite){
			Individuo ind1 = funcSelec.select(poblacion, punts_acum).clone();
			Individuo ind2 = funcSelec.select(poblacion, punts_acum).clone();
			
			Par<Individuo> trasCruce = funcCruz.cruzar(new Par<Individuo>(ind1,ind2));
			
			Individuo ind1t = trasCruce.getN1();
			Individuo ind2t = trasCruce.getN2();
			
			poblacionNueva.add(ind1t);
			if(poblacionNueva.size() < tamPob - tamElite) //Puede ser un numero impar de nuevos individuos
				poblacionNueva.add(ind2t);
		}
	}
	
	private void evalPoblacion(){
		ArrayList<Individuo> mejorPeorAvg = funcFit.evaluate(poblacion, minimizacion);
		actualizarGraphInds(mejorPeorAvg);
	}
	
	private void actualizarGraphInds(ArrayList<Individuo> mejorPeorAvg) {
		//Guardar fitness medio de la pob
		pobAvgFitness = mejorPeorAvg.get(2).getFitness();
		
		//Guardar mejor individuo de la generacion (posible mejor absoluto)
		mejorIndividuo = mejorPeorAvg.get(0).clone();
		
		if((mejorAbsoluto.getFitness() > mejorIndividuo.getFitness()) && minimizacion)
			mejorAbsoluto = mejorIndividuo.clone();
		else if((mejorAbsoluto.getFitness() < mejorIndividuo.getFitness()) && !minimizacion)
			mejorAbsoluto = mejorIndividuo.clone();
		
		//Guardar peor individuo de la generacion
		peorIndividuo = mejorPeorAvg.get(1).clone();
	}

	private void initGraphInds() {
		mejorAbsoluto  = new Individuo();
		if(minimizacion)
			mejorAbsoluto.setFitness(Double.MAX_VALUE);
		else
			mejorAbsoluto.setFitness(Double.MIN_VALUE);
	}
	
	private void insertElite(){
		for(int i = 0; i < tamElite; i++)
			poblacionNueva.add(poblacion.get(tamPob - i - 1).clone());
	}
	
	private void adaptPoblacion(){
		funcFit.adapt(poblacion, peorIndividuo.getFitness(), minimizacion);
	}
	
	private void setRangoVar(ArrayList<Par<Double>> rangoVar) {
		this.rangoVar = rangoVar;
	}

	public void setFuncFit(FuncionFitness funcFit) {
		this.funcFit = funcFit;
	}

	public void setFuncCruz(FuncionCruce funcCruz) {
		this.funcCruz = funcCruz;
	}

	public void setFuncMuta(FuncionMutacion funcMuta) {
		this.funcMuta = funcMuta;
	}

	public void setFuncSelec(FuncionSeleccion funcSelec) {
		this.funcSelec = funcSelec;
	}
}
