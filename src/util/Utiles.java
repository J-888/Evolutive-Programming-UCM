package util;

import java.util.ArrayList;
import java.util.Random;

import geneticos.CromosomaPermInt;
import geneticos.Gen;
import geneticos.GenInt;
import geneticos.TipoCromosoma;
import view.GUI;

public class Utiles {

	public static int randomIntNO(){
		Random rn = new Random();
		return Math.abs(rn.nextInt());
	}
	
	public static double randomDouble01(){
		Random rn = new Random();
		return rn.nextDouble();
	}
	
	public static double randomDoubleGauss0Mean(){	//check at http://homepage.stat.uiowa.edu/~mbognar/applets/normal.html
		Random rn = new Random();
		return rn.nextGaussian();
	}
	
	public static double randomDoubleGaussLOffside(double minIncludedVal, double maxIncludedVal){
		Random rn = new Random();
		double rnVal = Double.NEGATIVE_INFINIY;
		while(rnVal < -1 || rnVal > 1)	//15,866% chances it is too low and 15,866% it is too big
			rnVal = rn.nextGaussian();
		rnVal = (rnVal/2.0 + 0.5) * (maxIncludedVal - minIncludedVal)	//scale range
		rnVal += minIncludedVal;	//desp range
		
		return rnVal;
	}
	
	public static double randomDoubleGaussSOffside(double minIncludedVal, double maxIncludedVal){
		Random rn = new Random();
		double rnVal = Double.NEGATIVE_INFINIY;
		while(rnVal < -2 || rnVal > 2)	//2,275% chances it is  too low and 2,275% chances it too big
			rnVal = rn.nextGaussian();
		rnVal = (rnVal/4.0 + 0.5) * (maxIncludedVal - minIncludedVal)	//scale range
		rnVal += minIncludedVal;	//desp range
		
		return rnVal;
	}
	
	public static double randomDoubleGaussLOff01(){
		return Utiles.randomDoubleGaussLOffside(0, 1);
	}
	
	public static double randomDoubleGaussSOff01(){
		return Utiles.randomDoubleGaussSOffside(0, 1);
	}
	
	public static int bin_dec(String n){
		//n = grayToBinary(n); 	//descomentar para emplear codificacion binaria de grey
		return Integer.parseInt(n, 2);
	}
	
	public static String grayToBinary(String gray){
	    String binary  = "";
	 
	    //  MSB of binary code is same as gray code
	    binary += gray.charAt(0);	 
	 
	    // Compute remaining bits
	    for (int i = 1; i < gray.length(); i++){
	        // If current bit is 0, concatenate previous bit
	        if (gray.charAt(i) == '0')
	            binary += binary.charAt(i - 1);
	 
	        // Else, concatenate invert of previous bit
	        else
	            binary += flipBinary(binary.charAt(i - 1));
	    }
	    return binary;
	}
	
	public static char flipBinary(char c) { 
		return (c == '0')? '1': '0';
	}
	
	public static ArrayList<Gen> encodeOrdinal(ArrayList<Gen> original) {
		int size = original.size();
		ArrayList<Gen> encoded = new ArrayList<>(size);
		ArrayList<Integer> codeList = new ArrayList<>(size);
		Par<Double> rango = ((GenInt) original.get(0)).getRango();
		
		for (int i = 0; i < size; i++) {
			codeList.add(i);
		}
		
		for (int i = 0; i < size; i++) {
			GenInt nuevoGen = new GenInt(rango.getN1(), rango.getN2());
			nuevoGen.instanceBases();
			int location = codeList.indexOf(original.get(i).getBases().get(0));
			codeList.remove(location);
			nuevoGen.getBases().add(location);
			
			encoded.add(nuevoGen);
		}
		
		return encoded; 
	}
	
	public static ArrayList<Gen> decodeOrdinal(ArrayList<Gen> encoded) {
		int size = encoded.size();
		ArrayList<Gen> decoded = new ArrayList<>(size);
		ArrayList<Integer> codeList = new ArrayList<>(size);
		Par<Double> rango = ((GenInt) encoded.get(0)).getRango();
		
		for (int i = 0; i < size; i++) {
			codeList.add(i);
		}
		
		for (int i = 0; i < size; i++) {
			GenInt nuevoGen = new GenInt(rango.getN1(), rango.getN2());
			nuevoGen.instanceBases();
			int location = ((int) encoded.get(i).getBases().get(0)) % size;
			int value = codeList.get(location);
			codeList.remove(location);
			nuevoGen.getBases().add(value);
			
			decoded.add(nuevoGen);
		}
		
		return decoded; 
	}
	
	public static void main(String[] args) {	
		ArrayList<Par<Double>> rangs = new ArrayList<>();
		rangs.add(new Par<Double>(10.0, 10.0));
		CromosomaPermInt origCromo = new CromosomaPermInt(rangs, TipoCromosoma.PERMINT);
		origCromo.randomizeCromosome(0);

		/*origCromo.getGenes().get(0).getBases().set(0, 0);
		origCromo.getGenes().get(1).getBases().set(0, 4);
		origCromo.getGenes().get(2).getBases().set(0, 5);
		origCromo.getGenes().get(3).getBases().set(0, 2);
		origCromo.getGenes().get(4).getBases().set(0, 1);
		origCromo.getGenes().get(5).getBases().set(0, 8);
		origCromo.getGenes().get(6).getBases().set(0, 7);
		origCromo.getGenes().get(7).getBases().set(0, 6);
		origCromo.getGenes().get(8).getBases().set(0, 9);
		origCromo.getGenes().get(9).getBases().set(0, 3);*/
		
		origCromo.getGenes().get(0).getBases().set(0, 0);
		origCromo.getGenes().get(1).getBases().set(0, 1);
		origCromo.getGenes().get(2).getBases().set(0, 2);
		origCromo.getGenes().get(3).getBases().set(0, 3);
		origCromo.getGenes().get(4).getBases().set(0, 4);
		origCromo.getGenes().get(5).getBases().set(0, 5);
		origCromo.getGenes().get(6).getBases().set(0, 6);
		origCromo.getGenes().get(7).getBases().set(0, 7);
		origCromo.getGenes().get(8).getBases().set(0, 8);
		origCromo.getGenes().get(9).getBases().set(0, 9);
		
		CromosomaPermInt newCromo1 = new CromosomaPermInt(rangs, TipoCromosoma.PERMINT);
		newCromo1.setGenes(Utiles.encodeOrdinal(origCromo.getGenes()));
		System.out.println("ya1");
		
		CromosomaPermInt newCromo2 = new CromosomaPermInt(rangs, TipoCromosoma.PERMINT);
		newCromo2.setGenes(Utiles.decodeOrdinal(newCromo1.getGenes()));
		System.out.println("ya2");
	}
	
}
