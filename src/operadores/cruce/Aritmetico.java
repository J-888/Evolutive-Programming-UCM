package operadores.cruce;

import geneticos.Gen;
import geneticos.GenReal;
import geneticos.TipoCromosoma;
import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaStd;
import util.Par;

public class Aritmetico extends FuncionCruce {

	private double alpha = 0.7;
	
	@Override
	protected Par<Cromosoma> cruceCromosomas(Cromosoma pp1, Cromosoma pp2) {
		CromosomaStd p1 = (CromosomaStd) pp1;
		CromosomaStd p2 = (CromosomaStd) pp2;
		for(int i = 0; i < p1.getGenes().size(); i++){
			Gen gen1 = p1.getGenes().get(i);
			Gen gen2 = p2.getGenes().get(i);
			
			if(p1.getTipo() == TipoCromosoma.REAL){
				double base1 = (double) gen1.getBases().get(0);
				double base2 = (double) gen2.getBases().get(0);
				
				double offsp1 = alpha*base1 + (1-alpha)*base2;
				double offsp2 = (1-alpha)*base1 + alpha*base2;
				
				gen1.getBases().set(0, offsp1);
				gen2.getBases().set(0, offsp2);
			}
			else{
				System.err.println("Cruce aritmetico solo válido para cromosomas reales");
			}	
		}
		
		return new Par<Cromosoma>(p1,p2);
	}

	@Override
	public String toString() {
		return "Aritmetico";
	}
	
	
}
