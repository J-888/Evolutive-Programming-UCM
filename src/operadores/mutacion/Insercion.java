package operadores.mutacion;

import geneticos.Cromosoma;
import geneticos.Individuo;
import geneticos.TipoCromosoma;
import util.Utiles;

public class Insercion extends FuncionMutacion {

	@Override
	public void mutarInd(Individuo ind) {
		Cromosoma c = ind.getCromosoma();
		
		if(c.getTipo() == TipoCromosoma.PERMINT){
			int genToInsertLocation, insertLocation = -1;
			int tamCrom = c.getGenes().size();
			genToInsertLocation = Utiles.randomIntNO()%(tamCrom);
			while(insertLocation == -1 || insertLocation == genToInsertLocation)
				insertLocation = Utiles.randomIntNO()%(tamCrom);
			
			//insertLocation = 5; //TODO la grafica muere al inducirle error

			int genToInsert = (int) c.getGenes().get(genToInsertLocation).getBases().get(0);
			
			if (genToInsertLocation > insertLocation) {	//insert to the left
				for (int i = genToInsertLocation; i > insertLocation; i--) {
					c.getGenes().get(i).getBases().set(0, c.getGenes().get(i - 1).getBases().get(0)); //replace with previous
				}
				c.getGenes().get(insertLocation).getBases().set(0, genToInsert);
			}
			else {	//insert to the right
				for (int i = genToInsertLocation; i < insertLocation; i++) {
					c.getGenes().get(i).getBases().set(0, c.getGenes().get(i + 1).getBases().get(0)); //replace with next
				}
				c.getGenes().get(insertLocation).getBases().set(0, genToInsert);
			}
		}
		else{
			if(prob > 0)
				System.err.println("CROMOSOMA DE TIPO DESCONOCIDO O NO VALIDO EN MUTACION POR INSERCION");
		}
	}

	@Override
	public String toString() {
		return "Insercion";
	}

}
