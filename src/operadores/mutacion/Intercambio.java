package operadores.mutacion;

import geneticos.Cromosoma;
import geneticos.Individuo;
import geneticos.TipoCromosoma;
import util.Utiles;

public class Intercambio extends FuncionMutacion {

	@Override
	public void mutarInd(Individuo ind) {
		
		this.contador++;

		Cromosoma c = ind.getCromosoma();
		
		if(c.getTipo() == TipoCromosoma.PERMINT){
			int pInterc1, pInterc2 = -1;
			int tamCrom = c.getGenes().size();
			pInterc1 = Utiles.randomIntNO()%(tamCrom);
			while(pInterc2 == -1 || pInterc2 == pInterc1)
				pInterc2 = Utiles.randomIntNO()%(tamCrom);

			int t = (int) c.getGenes().get(pInterc1).getBases().get(0);
			c.getGenes().get(pInterc1).getBases().set(0, c.getGenes().get(pInterc2).getBases().get(0));
			c.getGenes().get(pInterc2).getBases().set(0, t);
		}
		else{
			if(prob > 0)
				System.err.println("CROMOSOMA DE TIPO DESCONOCIDO O NO VALIDO EN MUTACION POR INTERCAMBIO");
		}
	}

	@Override
	public String toString() {
		return "Intercambio";
	}

}
