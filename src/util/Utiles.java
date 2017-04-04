package util;

import java.util.Random;

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
	
}