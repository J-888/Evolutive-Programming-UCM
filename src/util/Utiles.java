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
	
	public static double randomDoubleGauss0Mean(){
		Random rn = new Random();
		return rn.nextGaussian();
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
		
		for (int i = 0; i < rango.getN2(); i++) {
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
	
	public static void main(String[] args) {	
		ArrayList<Par<Double>> rangs = new ArrayList<>();
		rangs.add(new Par<Double>(10.0, 10.0));
		CromosomaPermInt origCromo = new CromosomaPermInt(rangs, TipoCromosoma.PERMINT);
		origCromo.randomizeCromosome(0);

		origCromo.getGenes().get(0).getBases().set(0, 0);
		origCromo.getGenes().get(1).getBases().set(0, 4);
		origCromo.getGenes().get(2).getBases().set(0, 5);
		origCromo.getGenes().get(3).getBases().set(0, 2);
		origCromo.getGenes().get(4).getBases().set(0, 1);
		origCromo.getGenes().get(5).getBases().set(0, 8);
		origCromo.getGenes().get(6).getBases().set(0, 7);
		origCromo.getGenes().get(7).getBases().set(0, 6);
		origCromo.getGenes().get(8).getBases().set(0, 9);
		origCromo.getGenes().get(9).getBases().set(0, 3);
		
		CromosomaPermInt newCromo1 = new CromosomaPermInt(rangs, TipoCromosoma.PERMINT);
		newCromo1.setGenes(Utiles.encodeOrdinal(origCromo.getGenes()));
		System.out.println("ya");
	}
	
}