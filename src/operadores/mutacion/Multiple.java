package operadores.mutacion;

import geneticos.Individuo;
import util.Utiles;

public class Multiple extends FuncionMutacion{

	private FuncionMutacion[] mutations = {new Terminal(), new Terminal(), new Terminal(), new Funcion(), new Subarbol()};
	
	public void mutarInd(Individuo ind) {
		int selectedMut = Utiles.randomIntNO()%mutations.length;
		mutations[selectedMut].mutarInd(ind);
	}

	@Override
	public String toString() {
		return "Multiple";
	}

}
