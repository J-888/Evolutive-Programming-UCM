package operadores.cruce;

import java.util.ArrayList;
import geneticos.Cromosoma;
import geneticos.Gen;
import util.Par;
import util.Utiles;

public class Monopunto extends FuncionCruce{

	protected Par<Cromosoma> cruceCromosomas(Cromosoma p1, Cromosoma p2) {
		int totalLenght = 0;
		ArrayList<Gen> genesp1 = p1.getGenes();
		ArrayList<Gen> genesp2 = p2.getGenes();
		
		for (int i = 0; i < genesp1.size(); i++) {
			totalLenght += genesp1.get(i).getBases().size();
		}
		
		//Los valores resultantes son 0 a totalLenght - 2 
		int puntocruce = Utiles.randomIntNO()%(totalLenght-1);

		boolean stop = false;
		int currentBase = 0;

		for (int j = 0; j < genesp1.size() && !stop; j++) {
			//Caso en el que el cruce cae entre genes
			if (currentBase + genesp1.get(j).getBases().size() == puntocruce+1) {
				Gen aux = genesp1.get(j);
				genesp1.set(j, genesp2.get(j));
				genesp2.set(j, aux);
				
				stop = true;
			}//Caso en el que el cruce esta en un gen posterior al que estamos
			else if (currentBase + genesp1.get(j).getBases().size() < puntocruce+1) {
				Gen aux = genesp1.get(j);
				genesp1.set(j, genesp2.get(j));
				genesp2.set(j, aux);
			}
			else {//Caso en el que el cruce esta en nuestro gen
				int pnt = 0;
				
				while (currentBase <= puntocruce) {
					if(genesp1.get(j).getBases().size() <= pnt || genesp1.size() <= j)
						System.out.println("!!");
					
					boolean aux1 = (boolean) genesp1.get(j).getBases().get(pnt);
					genesp1.get(j).getBases().set(pnt, genesp2.get(j).getBases().get(pnt));
					genesp2.get(j).getBases().set(pnt, aux1);
					
					pnt++;
					currentBase++;
				}
				stop = true;
			}
			currentBase += genesp1.get(j).getBases().size();
		}
		
		return new Par<Cromosoma>(p1.clone(),p2.clone());
	}

	@Override
	public String toString() {
		return "Monopunto";
	}

}
