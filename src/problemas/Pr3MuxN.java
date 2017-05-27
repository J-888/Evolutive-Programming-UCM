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
import operadores.fitness.BloatControl;
import operadores.fitness.BloatControl.AntibloatingMethod;
import operadores.fitness.FitnessPr3MuxN;
import operadores.mutacion.FuncionMutacion;
import operadores.seleccion.FuncionSeleccion;
import util.pg.Node;
import util.pg.TipoNodo;
import util.pg.UtilesPG;
import view.GUI;

public class Pr3MuxN extends Problema {
	
	String pobIniGenMethod = null;
	public static int profundidadMax;
	public static List<TipoNodo> NTDisponibles;
	public static int maxEntsPorNodo;
	public static int tamMux;
	public static int nSelects;
	
	public Pr3MuxN(FuncionCruce funcCruz, FuncionMutacion funcMuta, FuncionSeleccion funcSelec, double elite0to1, int numGenerations, int tamPob, GUI gui){
		super(funcCruz, funcMuta, funcSelec, elite0to1, numGenerations, tamPob, 1, gui);
		
		this.pobIniGenMethod = gui.getPobIniGenMethod();
		this.profundidadMax = gui.getProfundidadMax();
		
		this.minimizacion = false;
		this.tamMux = gui.getTamMux();
		
		nSelects = 1;
		boolean oK = false;
		while(!oK){
			if(Math.pow(2,nSelects) == tamMux){
				oK = true;
			}
			else
				nSelects++;
		}

		this.bloatControl = new BloatControl(AntibloatingMethod.ADVANCED);
		
		this.funcFit = new FitnessPr3MuxN(this.minimizacion, tamMux, nSelects);

		this.funcCruz.setFuncionFitness(this.funcFit);
		this.funcMuta.setFuncionFitness(this.funcFit);
		
		this.NTDisponibles = gui.getNTDisponibles();
		this.maxEntsPorNodo = gui.getMaxentsPorNodo();
	}
	
	
	public void generaPobIni() {
		if(pobIniGenMethod.equals("Creciente")){
			for (int i = 0; i < tamPob; i++) {
				Node crecTree = UtilesPG.genTreeCreciente(0, profundidadMax);
				crecTree.setEsRaiz(true);
				crecTree.actualizarArbol();
				CromosomaPG cromCrec = new CromosomaPG(crecTree, tamMux, profundidadMax);
				IndividuoPG indCrec = new IndividuoPG(cromCrec);
				poblacion.add(indCrec);
			}
		}
		else if(pobIniGenMethod.equals("Completa")){
			for (int i = 0; i < tamPob; i++) {
				Node comTree = UtilesPG.genTreeCompleta(0, profundidadMax);
				comTree.setEsRaiz(true);
				comTree.actualizarArbol();
				CromosomaPG cromCom = new CromosomaPG(comTree, tamMux, profundidadMax);
				IndividuoPG indCom = new IndividuoPG(cromCom);
				poblacion.add(indCom);
			}
		}
		else if(pobIniGenMethod.equals("Ramped&Half")){

			int numGrupos = profundidadMax - 1;
			int pobAsignada = 0;
			
			while(pobAsignada < tamPob){
				for(int i = 0; i < numGrupos && pobAsignada < tamPob; i++){
					Node crecTree = UtilesPG.genTreeCreciente(0, i + 2);
					crecTree.setEsRaiz(true);
					crecTree.actualizarArbol();
					CromosomaPG cromCrec = new CromosomaPG(crecTree, tamMux, profundidadMax);
					IndividuoPG indCrec = new IndividuoPG(cromCrec);
					poblacion.add(indCrec);
					pobAsignada++;
	
					if(pobAsignada < tamPob){
						Node comTree = UtilesPG.genTreeCompleta(0, i + 2);
						comTree.setEsRaiz(true);
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
		return new Double(Math.pow(2,(tamMux + nSelects)));
	}
}
