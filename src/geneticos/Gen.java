package geneticos;

import java.util.ArrayList;

public abstract class Gen {
	protected ArrayList<Object> bases;
	
	public abstract void initializeRandomGen();

	public ArrayList<Object> getBases() {
		return bases;
	}

	public void setBases(ArrayList<Object> bases) {
		this.bases = bases;
	}
	
	public abstract Gen clone();
	public abstract String toString();
	
}


