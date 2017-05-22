package problemas;

import java.util.ArrayList;
import java.util.List;

import geneticos.Individuo;
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
import util.pg.TipoNodo;
import util.pg.UtilesPG;
import view.GUI;

public class Pr3MuxN extends Problema {
	
	String pobIniGenMethod = null;
	int profundidadMax;
	public static List<TipoNodo> NTDisponibles;
	public static int maxEntsPorNodo;
	public static int tamMux;
	
	public Pr3MuxN(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, GUI gui){
		super(funcCruz, funcMuta, funcSelec, elite0to1, numGenerations, tamPob, 1, gui);
		
		this.pobIniGenMethod = gui.getPobIniGenMethod();
		this.profundidadMax = gui.getProfundidadMax();
		
		this.minimizacion = false;
		this.funcFit = new FitnessPr3MuxN(this.minimizacion, 4);

		this.funcCruz.setFuncionFitness(this.funcFit);
		this.funcMuta.setFuncionFitness(this.funcFit);
		
		this.NTDisponibles = gui.getNTDisponibles();
		this.maxEntsPorNodo = gui.getMaxentsPorNodo();
		this.tamMux = gui.getTamMux();
	}
	
	
	public void generaPobIni() {
		if(pobIniGenMethod.equals("Creciente")){
			for (int i = 0; i < tamPob; i++) {
				Node crecTree = UtilesPG.genTreeCreciente(0, profundidadMax);
				crecTree.actualizarArbol();
				CromosomaPG cromCrec = new CromosomaPG(crecTree, tamMux, profundidadMax);
				IndividuoPG indCrec = new IndividuoPG(cromCrec);
				poblacion.add(indCrec);
			}
		}
		else if(pobIniGenMethod.equals("Completa")){
			for (int i = 0; i < tamPob; i++) {
				Node comTree = UtilesPG.genTreeCompleta(0, profundidadMax);
				comTree.actualizarArbol();
				CromosomaPG cromCom = new CromosomaPG(comTree, tamMux, profundidadMax);
				IndividuoPG indCom = new IndividuoPG(cromCom);
				poblacion.add(indCom);
			}
		}
		else if(pobIniGenMethod.equals("RampedAndHalf")){	//TODO: comprobar

			int numGrupos = profundidadMax - 1;
			int pobAsignada = 0;
			
			while(pobAsignada < tamPob){
				for(int i = 0; i < numGrupos && pobAsignada < tamPob; i++){
					Node crecTree = UtilesPG.genTreeCreciente(0, i + 2);
					crecTree.actualizarArbol();
					CromosomaPG cromCrec = new CromosomaPG(crecTree, tamMux, profundidadMax);
					IndividuoPG indCrec = new IndividuoPG(cromCrec);
					poblacion.add(indCrec);
					pobAsignada++;
	
					if(pobAsignada < tamPob){
						Node comTree = UtilesPG.genTreeCompleta(0, i + 2);
						comTree.actualizarArbol();
						CromosomaPG cromCom = new CromosomaPG(comTree, tamMux, profundidadMax);
						IndividuoPG indCom = new IndividuoPG(cromCom);
						poblacion.add(indCom);
						pobAsignada++;
					}
				}
			}
			
		}
		else{
			System.err.println("Error en la selección de generación de pob ini");
		}
	}

	public Double getOptimo(){
		int numSelects = (int) Math.sqrt(tamMux);
		return (double) (2^(tamMux + numSelects));
	}
}
