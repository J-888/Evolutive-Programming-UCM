package problemas;

import java.util.TreeSet;

import javax.swing.JFrame;

import datos.DatosMatrices;
import geneticos.CromosomaPermInt;
import geneticos.Individuo;
import geneticos.TipoCromosoma;
import operadores.cruce.FuncionCruce;
import operadores.fitness.FitnessPr2;
import operadores.mutacion.FuncionMutacion;
import operadores.seleccion.FuncionSeleccion;
import util.Par;

public class Pr2tai100a extends Problema {
	
	public Pr2tai100a(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, boolean escalado, JFrame gui){
		super(funcCruz, funcMuta, funcSelec, elite0to1, numGenerations, tamPob, 1, escalado, gui);
		
		this.rangoVar.add(new Par<Double>((double)DatosMatrices.getMatrixDim("tai100a") , (double)DatosMatrices.getMatrixDim("tai100a"))); //reminder to update rangoSize on super constructor s
		this.minimizacion = true;
		this.funcFit = new FitnessPr2("tai100a", this.minimizacion);
		this.funcMuta.setFuncionFitness(this.funcFit);
	}
	
	
	public void generaPobIni() {
		boolean elemsSingulares;
		
		//Evaluación de factibilidad de no repetición de individuos (hay >2 veces más posibles cromosomas que población a rellenar)
		elemsSingulares = singularFact(tamPob*2, rangoVar.get(0).getN1().intValue(), rangoVar.get(0).getN1().intValue()-1);
			
		//Generación con elementos diferentes
		if(elemsSingulares){
			System.out.println("Generando población inicial de elementos diferentes");
			TreeSet<CromosomaPermInt> mapping = new TreeSet<CromosomaPermInt>();
			int i = 0;
			while(i < tamPob){
				CromosomaPermInt newCromo = new CromosomaPermInt(rangoVar, TipoCromosoma.PERMINT);
				newCromo.randomizeCromosome(tolerancia);
				
				if(!mapping.contains(newCromo)){
					mapping.add(newCromo);
					Individuo newInd = new Individuo(newCromo);
					poblacion.add(newInd);
					i++;
				}
			}
		}
		//Generación con elementos repetidos
		else{
			System.out.println("Generando población inicial de elementos aleatorios (Población demasiado grande en relación al tamaño del cromosoma para garantizar diferencia)");
			for (int i = 0; i < tamPob; i++) {
				CromosomaPermInt newCromo = new CromosomaPermInt(rangoVar, TipoCromosoma.PERMINT);
				newCromo.randomizeCromosome(tolerancia);
				Individuo newInd = new Individuo(newCromo);
				poblacion.add(newInd);	
			}
		}
	}

	public Double getOptimo(){
		return 21052466.0;
	}
}