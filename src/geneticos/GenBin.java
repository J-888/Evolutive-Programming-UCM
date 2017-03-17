package geneticos;

import java.util.ArrayList;

import util.Utiles;

public class GenBin extends Gen {

	protected int longitud;
	
	public GenBin(int longitud) {
		this.longitud = longitud;
		bases = new ArrayList<Object>(longitud);
	}
	
	public int getLongitud() {
		return longitud;
	}

	public void initializeRandomGen() {
		bases = new ArrayList<Object>(longitud);
		
		for(int i = 0; i < longitud; i++)
			bases.add((Utiles.randomIntNO())%2==0);
		
	}
	
	public String toString() {
		String str = "";
		for (int i = 0; i < bases.size(); i++) {
			if ((boolean)bases.get(i) == true)
				str += "1";
			else
				str += "0";
		}
		return str;
	}
	
	public Gen clone(){
		ArrayList<Object> nuevasBases = new ArrayList<Object>();
		Gen nuevGen = new GenBin(longitud);
		for(int i = 0; i < longitud; i++){
			if((boolean)bases.get(i) == true)
				nuevasBases.add(true);
			else
				nuevasBases.add(false);
		}
		nuevGen.setBases(nuevasBases);	
		return nuevGen;
	}

}
