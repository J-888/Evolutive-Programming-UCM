package problemas;

import geneticos.IndividuoPG;
import geneticos.IndividuoStd;
import geneticos.TipoCromosoma;
import geneticos.cromosomas.CromosomaPG;
import geneticos.cromosomas.CromosomaPermInt;

import javax.swing.JFrame;

import operadores.cruce.FuncionCruce;
import operadores.fitness.FitnessPr3MuxN;
import operadores.mutacion.FuncionMutacion;
import operadores.seleccion.FuncionSeleccion;
import util.pg.Node;
import util.pg.UtilesPG;

public class Pr3Mux4 extends Problema {
	
	String pobIniGenMethod = null;
	int profundidadMax;
	
	public Pr3Mux4(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, JFrame gui){
		super(funcCruz, funcMuta, funcSelec, elite0to1, numGenerations, tamPob, 1, gui);
		
		//this.pobIniGenMethod = gui.getPobIniGenMethod();
		//this.profundidadMax = gui.getProfundidadMax();
		
		this.minimizacion = false;
		this.funcFit = new FitnessPr3MuxN(this.minimizacion);

		this.funcCruz.setFuncionFitness(this.funcFit);
		this.funcMuta.setFuncionFitness(this.funcFit);
	}
	
	
	public void generaPobIni() {
		if(pobIniGenMethod.equals("Creciente")){
			for (int i = 0; i < tamPob; i++) {
				Node crecNode = UtilesPG.genNodeCreciente(profundidadMax);
				CromosomaPG cromCrec = new CromosomaPG(crecNode);
				IndividuoPG indCrec = new IndividuoPG(cromCrec);
				poblacion.add(indCrec);
			}
		}
		else if(pobIniGenMethod.equals("Completa")){
			for (int i = 0; i < tamPob; i++) {
				Node comNode = UtilesPG.genNodeCompleta(profundidadMax);
				CromosomaPG cromCom = new CromosomaPG(comNode);
				IndividuoPG indCom = new IndividuoPG(cromCom);
				poblacion.add(indCom);
			}
		}
		else if(pobIniGenMethod.equals("RampedAndHalf")){	//TODO: comprobar

			int numGrupos = profundidadMax - 1;
			
			for(int i = 0; i < numGrupos; i++){
				for (int j = 0; j < tamPob; j++) {
					if (j%0 == 0){
						Node crecNode = UtilesPG.genNodeCreciente(i+2);
						CromosomaPG cromCrec = new CromosomaPG(crecNode);
						IndividuoPG indCrec = new IndividuoPG(cromCrec);
						poblacion.add(indCrec);
					}		
					else{
						Node comNode = UtilesPG.genNodeCompleta(i+2);
						CromosomaPG cromCom = new CromosomaPG(comNode);
						IndividuoPG indCom = new IndividuoPG(cromCom);
						poblacion.add(indCom);
					}
				}
			}
		}
		else{
			System.err.println("Error en la selección de generación de pob ini");
		}
	}

	public Double getOptimo(){
		return 64.0;
	}
}
