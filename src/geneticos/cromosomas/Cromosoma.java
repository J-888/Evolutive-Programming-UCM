package geneticos.cromosomas;

import java.util.ArrayList;

import geneticos.TipoCromosoma;

public abstract class Cromosoma {
	protected TipoCromosoma tipo;
	
	
	public TipoCromosoma getTipo() {
		return tipo;
	}
	
	public abstract Cromosoma clone();
	public abstract ArrayList<?> toFenotipo();

}
