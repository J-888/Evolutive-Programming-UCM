package operadores.mutacion;

import java.util.ArrayList;

import util.Par;
import util.Utiles;
import geneticos.Cromosoma;
import geneticos.Gen;
import geneticos.Individuo;
import geneticos.TipoCromosoma;

public class Inversion extends FuncionMutacion {

	@Override
	public void mutarInd(Individuo ind) {
		Cromosoma c = ind.getCromosoma();
		
		if(c.getTipo() == TipoCromosoma.PERMINT){
			int pCorte1, pCorte2 = -1;
			int tamCrom = c.getGenes().size();
			pCorte1 = Utiles.randomIntNO()%(tamCrom+1);
			while(pCorte2 == -1 || pCorte2 == pCorte1)
				pCorte2 = Utiles.randomIntNO()%(tamCrom+1);
			
			if(pCorte1 > pCorte2){
				int t = pCorte2;
				pCorte2 = pCorte1;
				pCorte1 = t;
			}
			
			for(int i = pCorte1; i < pCorte1 + ((pCorte2-pCorte1) /2); i++){
				int t = (int) c.getGenes().get(i).getBases().get(0);
				c.getGenes().get(i).getBases().set(0, c.getGenes().get(pCorte2-1-(i-pCorte1)).getBases().get(0));
				c.getGenes().get(pCorte2-1-(i-pCorte1)).getBases().set(0, t);
			}
		}
		else{
			if(prob > 0)
				System.err.println("CROMOSOMA DE TIPO DESCONOCIDO O NO VALIDO EN MUTACION POR INVERSION");
		}
	}

	@Override
	public String toString() {
		return "Inversión";
	}

}
