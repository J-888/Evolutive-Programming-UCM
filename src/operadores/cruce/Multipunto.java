package operadores.cruce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import geneticos.Gen;
import geneticos.TipoCromosoma;
import geneticos.cromosomas.Cromosoma;
import geneticos.cromosomas.CromosomaPermInt;
import geneticos.cromosomas.CromosomaReal;
import geneticos.cromosomas.CromosomaStd;
import util.Par;
import util.Utiles;

public class Multipunto extends FuncionCruce{
	
	private int nPuntos;
	
	public Multipunto(int np) {
		this.nPuntos = np;
	}

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
		
		if(nPuntos >= totalLenght) {
			System.err.println("El numero de puntos es demasiado alto y ha sido reducido de " + nPuntos + " a " + (totalLenght - 1));
			this.nPuntos = totalLenght - 1;
		}
		
		TreeSet<Integer> puntos = new TreeSet<>();
		for (int i = 0; i < nPuntos; i++) {
			int puntocruce;
			do {
				puntocruce = Utiles.randomIntNO()%(totalLenght-1);
			} while (puntos.contains(puntocruce));
			puntos.add(puntocruce);
		}
		
		ArrayList<Boolean> exchangeBases = new ArrayList<Boolean>(totalLenght);
		
		boolean lastBooleanValue = false;
		int lastPoint = -1;	//para que el exchange de la primera base siempre sea false
		int currentPoint;
		int elemNum;
		
		while (puntos.size() != 0) {
			currentPoint = puntos.pollFirst();
			elemNum = currentPoint - lastPoint;
			lastPoint = currentPoint;
			exchangeBases.addAll(Collections.nCopies(elemNum, lastBooleanValue));
			lastBooleanValue = !lastBooleanValue;
		}
		
		exchangeBases.addAll(Collections.nCopies(totalLenght - exchangeBases.size(), lastBooleanValue));

		Utiles.exchangeBases(exchangeBases, 0, p1, p2);
		
		if(p1.getTipo() == TipoCromosoma.PERMINT || p2.getTipo() == TipoCromosoma.PERMINT) {	//redundante: los dos cromosomas siempre seran del mismo tipo
			p1.setGenes(Utiles.decodeOrdinal(genesp1));
			p2.setGenes(Utiles.decodeOrdinal(genesp2));
		}
		
		return new Par<Cromosoma>(p1.clone(),p2.clone());
	}

	@Override
	public String toString() {
		return "Multipunto (" + nPuntos + ")";
	}

}
