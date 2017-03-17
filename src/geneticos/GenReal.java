package geneticos;

import java.util.ArrayList;

import util.Utiles;

public class GenReal extends Gen {

	private double rangIni;
	private double rangFin;
	private double tolerancia;
	
	public GenReal(Double rangIni, Double rangFin, double tolerancia) {
		this.rangIni = rangIni;
		this.rangFin = rangFin;
		this.tolerancia = tolerancia;
	}

	public void initializeRandomGen() {
		bases = new ArrayList<Object>(1);
		double ancho = rangFin - rangIni;
		double base = Utiles.randomDouble01()*ancho;
		base = Math.floor(base * (1/tolerancia)) / (1/tolerancia);
		bases.add(base+rangIni);
	}
	
	public String toString() {
		return Double.toString((double) bases.get(0));
	}
	
	public Gen clone(){
		ArrayList<Object> nuevasBases = new ArrayList<Object>();
		Gen nuevGen = new GenReal(rangIni, rangFin, tolerancia);
		nuevasBases.add(Double.valueOf((Double) bases.get(0)));
		nuevGen.setBases(nuevasBases);	
		return nuevGen;
	}

}
