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

	
	//MAIN PARA PRUEBA DE CRUCES
	
	/*public static void main(String[] args){
		ArrayList<Par<Double>> rangos = new ArrayList<Par<Double>>();
		rangos.add(new Par(0,7));
		CromosomaBin c1 = new CromosomaBin(rangos);
		CromosomaBin c2 = new CromosomaBin(rangos);
		
		c1.genes = new ArrayList<Gen>();
		c2.genes = new ArrayList<Gen>();
		
		ArrayList<Object> b1 = new ArrayList<Object>();
		b1.add(true);
		b1.add(true);
		b1.add(true);
		b1.add(true);
		b1.add(true);
		Gen ge1 = new GenBin(5);
		ge1.bases = b1;
		c1.genes.add(ge1);
		
		
		ArrayList<Object> b11 = new ArrayList<Object>();
		b11.add(true);
		b11.add(true);
		b11.add(true);
		b11.add(true);
		b11.add(true);
		Gen ge11 = new GenBin(5);
		ge11.bases = b11;
		c1.genes.add(ge11);

		
			
		ArrayList<Object> b2 = new ArrayList<Object>();
		b2.add(false);
		b2.add(false);
		b2.add(false);
		b2.add(false);
		b2.add(false);
		Gen ge2 = new GenBin(5);
		ge2.bases = b2;
		c2.genes.add(ge2);

		
		ArrayList<Object> b22 = new ArrayList<Object>();
		b22.add(false);
		b22.add(false);
		b22.add(false);
		b22.add(false);
		b22.add(false);
		Gen ge22 = new GenBin(5);
		ge22.bases = b22;
		c2.genes.add(ge22);

		
		
		c1.cruce(c1, c2);
	}*/
}
