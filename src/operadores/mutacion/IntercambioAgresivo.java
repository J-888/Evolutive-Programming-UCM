package operadores.mutacion;

import geneticos.Cromosoma;
import geneticos.Individuo;
import geneticos.TipoCromosoma;
import util.Utiles;

//Función basada en la teoría de que para que un individuo mejore y por lo tanto tenga mas oportunidades de ser
//seleccionado en la siguiente iteración, puede necesitar varios intercambios en una misma generación.

public class IntercambioAgresivo extends FuncionMutacion {

	double ALPHA; //% 0-1 de continuar mutando
	
	public IntercambioAgresivo(double a) {
		super();
		this.ALPHA = a;
	}
	
	@Override
	public void mutarInd(Individuo ind) {
		
		this.contador++;

		Cromosoma c = ind.getCromosoma();
		
		if(c.getTipo() == TipoCromosoma.PERMINT){
			int tamCrom = c.getGenes().size();
			
			int maxMutationApplied = 2; //Para un cromosoma de tamaño 20, como mucho se intercambiara 20/2 = 10 veces
			int delta = deltaFunction(tamCrom/maxMutationApplied, 0);
			if(delta == 0)
				delta++; //Al menos una vez
			
			for(int i = 0; i < delta; i++)
				intercambiar(c);
		}
		else{
			if(prob > 0)
				System.err.println("CROMOSOMA DE TIPO DESCONOCIDO O NO VALIDO EN MUTACION POR INTERCAMBIO AGRESIVO");
		}
	}

	@Override
	public String toString() {
		return "Intercambio agresivo";
	}
	
	private void intercambiar(Cromosoma c){
		int pInterc1, pInterc2 = -1;
		int tamCrom = c.getGenes().size();
		pInterc1 = Utiles.randomIntNO()%(tamCrom);
		while(pInterc2 == -1 || pInterc2 == pInterc1)
			pInterc2 = Utiles.randomIntNO()%(tamCrom);

		int t = (int) c.getGenes().get(pInterc1).getBases().get(0);
		c.getGenes().get(pInterc1).getBases().set(0, c.getGenes().get(pInterc2).getBases().get(0));
		c.getGenes().get(pInterc2).getBases().set(0, t);
	}
	

	private int deltaFunction(int maxMuts, int acc) {
		
		if(acc == maxMuts)
			return 0;
		
		if(Utiles.randomDouble01() < ALPHA)
			return 1 + deltaFunction(maxMuts, acc+1);
		else
			return 0;
	}
}
