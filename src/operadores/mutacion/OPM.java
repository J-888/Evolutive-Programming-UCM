package operadores.mutacion;

import java.util.ArrayList;

import geneticos.Individuo;

public class OPM extends FuncionMutacion{

	private int k = 2;
	private FuncionMutacion[] mutations = {new Terminal(), new Funcion(), new Subarbol()};
	
	public void mutarInd(Individuo ind) {
		ArrayList<Individuo> indList = new ArrayList<>(mutations.length * k);
		for (int i = 0; i < mutations.length * k; i++) {
			Individuo generated = ind.clone();
			mutations[i%mutations.length].mutarInd(ind.clone());
			indList.add(generated);
		}
		ind = this.funFit.evaluate(indList).get(0);
	}

	public String toString() {
		return "OPM";
	}

}
