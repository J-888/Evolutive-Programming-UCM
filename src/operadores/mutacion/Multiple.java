package operadores.mutacion;

import geneticos.Individuo;
import geneticos.IndividuoPG;
import geneticos.cromosomas.CromosomaPG;
import util.Utiles;

public class Multiple extends FuncionMutacion{

	private FuncionMutacion[] mutations = {new Terminal(), new Terminal(), new Funcion(), new Subarbol()};
	private boolean hardcore;
	
	public Multiple(boolean i) {
		super();
		this.hardcore = i;
	}

	public void mutarInd(Individuo ind2) {
		IndividuoPG ind = (IndividuoPG) ind2;
		CromosomaPG crompg = (CromosomaPG)ind.getCromosoma();
		if(!hardcore){
			int selectedMut = Utiles.randomIntNO()%mutations.length;
			mutations[selectedMut].mutarInd(ind2);
		}
		else{
			for(int i = 0; i < crompg.getArbol().getNumNodos()/10; i++){
				int selectedMut = Utiles.randomIntNO()%mutations.length;
				mutations[selectedMut].mutarInd(ind2);
			}
		}
	}

	@Override
	public String toString() {
		return "Multiple";
	}

}
