package operadores.mutacion;

import java.util.ArrayList;

import util.Par;
import util.Utiles;
import geneticos.Gen;
import geneticos.Individuo;
import geneticos.IndividuoStd;
import geneticos.TipoCromosoma;
import geneticos.cromosomas.CromosomaBin;
import geneticos.cromosomas.CromosomaReal;
import geneticos.cromosomas.CromosomaStd;

public class BaseABase extends FuncionMutacion {

	public void mutarInd(Individuo ind){
		
		this.contador++;
		
		CromosomaStd c = (CromosomaStd) ind.getCromosoma();
		
		if(c.getTipo() == TipoCromosoma.BIN){
			for(int i = 0; i < c.getGenes().size(); i++){
				Gen g = c.getGenes().get(i);
				ArrayList<Object> bases = g.getBases();
				
				for(int k = 0; k < bases.size(); k++){
					double rand = Utiles.randomDouble01();
					if (rand < prob){
						boolean base = (boolean) bases.get(k);
						if (base)
							base = false;
						else
							base = true;
						
						bases.set(k, base);
					}
				}
			}
		}
		else if (c.getTipo() == TipoCromosoma.REAL){
			double ALPHA = 0.2;
			
			for(int i = 0; i < c.getGenes().size(); i++){
				Gen g = c.getGenes().get(i);
				double baseReal = (double) g.getBases().get(0);
				
				double rand = Utiles.randomDouble01();
				if (rand < prob){
					//Increase or decrease
					if(Utiles.randomDouble01() >= 0.5)
						ALPHA *= (-1);
					
					//Longitud del desplazamiento
					double desp = ALPHA * baseReal;
					
					//Aplicar desplazamiento
					baseReal += desp;
					
					//Encajar en rangos admisibles si procede
					Par<Double> rangoGen = c.getRango().get(i);
					if(baseReal > rangoGen.getN2())
						baseReal = rangoGen.getN2();
					else if(baseReal < rangoGen.getN1())
						baseReal = rangoGen.getN1();
					
					//Set de la base mutada
					g.getBases().set(0, baseReal);
				}
			}
		}
		else{
			if(prob > 0)
				System.err.println("CROMOSOMA DE TIPO DESCONOCIDO O NO VALIDO EN MUTACION BASE A BASE");
		}
	
	}

	public String toString() {
		return "Base a base";
	}
	
}
