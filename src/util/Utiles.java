package util;

import java.util.Random;

public class Utiles {

	public static int randomIntNO(){
		Random rn = new Random();
		return Math.abs(rn.nextInt());
	}
	
	public static double randomDouble01(){
		Random rn = new Random();
		return rn.nextDouble();
	}
	
	public static int bin_dec(String n){
		return Integer.parseInt(n, 2);
	}
	
}