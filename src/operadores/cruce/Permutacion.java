package operadores.cruce;

import geneticos.cromosomas.Cromosoma;
import util.Par;

public class Permutacion extends FuncionCruce {

	@Override
	protected Par<Cromosoma> cruceCromosomas(Cromosoma p1, Cromosoma p2) {
		return new Par<Cromosoma>(p1, p2);
	}

	@Override
	public String toString() {
		return "Permutación";
	}

}
