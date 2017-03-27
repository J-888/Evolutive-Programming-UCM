package geneticos;

import java.util.ArrayList;

import util.Utiles;

public class GenInt extends Gen{

	private double rangIni;
	private double rangFin;
	private double tolerancia;
	
	public GenInt(Double rangIni, Double rangFin, double tolerancia) {
		this.rangIni = rangIni;
		this.rangFin = rangFin;
		this.tolerancia = tolerancia;
	}
	
	public void instanceBases(){
		bases = new ArrayList<Object>(1);
	}
	
	public void initializeRandomGen() {
		bases = new ArrayList<Object>(1);
		double ancho = rangFin - rangIni;
		double base = Utiles.randomDouble01()*ancho;
		base = Math.floor(base * (1/tolerancia)) / (1/tolerancia);
		bases.add((int)Math.floor(base+rangIni));
	}

	public Gen clone() {
		ArrayList<Object> nuevasBases = new ArrayList<Object>();
		Gen nuevGen = new GenInt(rangIni, rangFin, tolerancia);
		nuevasBases.add(Integer.valueOf((Integer) bases.get(0)));
		nuevGen.setBases(nuevasBases);	
		return nuevGen;
	}

	public String toString() {
		return Integer.toString((int) bases.get(0));

	}

}
