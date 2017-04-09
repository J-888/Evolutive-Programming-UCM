package geneticos;

import java.util.ArrayList;

import util.Par;
import util.Utiles;

public class GenInt extends Gen{

	private double rangIni;
	private double rangFin;
	private double tolerancia;
	
	public GenInt(Double rangIni, Double rangFin) {
		this.rangIni = rangIni;
		this.rangFin = rangFin;
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
		ArrayList<Object> nuevasBases = new ArrayList<Object>(1);
		Gen nuevGen = new GenInt(rangIni, rangFin);
		nuevasBases.add(Integer.valueOf((Integer) bases.get(0)));
		nuevGen.setBases(nuevasBases);	
		return nuevGen;
	}

	public String toString() {
		return Integer.toString((int) bases.get(0));
	}
	
	public Par<Double> getRango(){
		return new Par<Double>(rangIni, rangFin);
	}

}
