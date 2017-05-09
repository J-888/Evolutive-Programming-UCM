package operadores.cruce;

import java.util.ArrayList;

import geneticos.Gen;
import geneticos.TipoCromosoma;
import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaStd;
import util.Par;
import util.Utiles;

public class Uniforme extends FuncionCruce{

	protected Par<Cromosoma> cruceCromosomas(Cromosoma pp1, Cromosoma pp2) {
		CromosomaStd p1 = (CromosomaStd) pp1;
		CromosomaStd p2 = (CromosomaStd) pp2;
		
		int totalLenght = 0;
		
		ArrayList<Gen> genesp1 = p1.getGenes();
		ArrayList<Gen> genesp2 = p2.getGenes();
		
		if(p1.getTipo() == TipoCromosoma.PERMINT || p2.getTipo() == TipoCromosoma.PERMINT) {	//redundante: los dos cromosomas siempre seran del mismo tipo
			genesp1 = Utiles.encodeOrdinal(genesp1);
			genesp2 = Utiles.encodeOrdinal(genesp2);
		}
		
		for (int i = 0; i < genesp1.size(); i++) {
			totalLenght += genesp1.get(i).getBases().size();
		}
		
		ArrayList<Boolean> exchangeBases = new ArrayList<Boolean>(totalLenght);
		
		for (int i = 0; i < totalLenght; i++) {
			exchangeBases.add((Utiles.randomIntNO()%2) == 0);
		}

		Utiles.exchangeBases(exchangeBases, 0, p1, p2);
		
		if(p1.getTipo() == TipoCromosoma.PERMINT || p2.getTipo() == TipoCromosoma.PERMINT) {	//redundante: los dos cromosomas siempre seran del mismo tipo
			p1.setGenes(Utiles.decodeOrdinal(genesp1));
			p2.setGenes(Utiles.decodeOrdinal(genesp2));
		}
		
		return new Par<Cromosoma>(p1.clone(),p2.clone());
	}

	@Override
	public String toString() {
		return "Uniforme";
	}

}
