package datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DatosMatrices {
	
	public static ArrayList<ArrayList<Integer>> matrix1; 
	public static ArrayList<ArrayList<Integer>> flujos1; 

	public static ArrayList<ArrayList<Integer>> matrix2;
	public static ArrayList<ArrayList<Integer>> flujos2; 
	
	public static ArrayList<ArrayList<Integer>> matrix3;
	public static ArrayList<ArrayList<Integer>> flujos3; 
	
	public static ArrayList<ArrayList<Integer>> matrix4;
	public static ArrayList<ArrayList<Integer>> flujos4; 
	
	public static void leeMatrices(){
		//Leer matrices
		Scanner scanner = null;
		int dim;
		
		try {
			scanner = new Scanner(new File("src/datos/ajuste.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("No existe el archivo ajuste.txt");
		}
		dim = scanner.nextInt();
		matrix1 = leeMatriz(scanner, dim);
		flujos1 = leeMatriz(scanner, dim);
	
		try {
			scanner = new Scanner(new File("src/datos/datos12.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("No existe el archivo datos12.txt");
		}
		dim = scanner.nextInt();
		matrix2 = leeMatriz(scanner, dim);
		flujos2 = leeMatriz(scanner, dim);
		
		try {
			scanner = new Scanner(new File("src/datos/datos15.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("No existe el archivo datos15.txt");
		}
		dim = scanner.nextInt();
		matrix3 = leeMatriz(scanner, dim);
		flujos3 = leeMatriz(scanner, dim);
		
		try {
			scanner = new Scanner(new File("src/datos/datos30.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("No existe el archivo datos30.txt");
		}
		dim = scanner.nextInt();
		matrix4 = leeMatriz(scanner, dim);
		flujos4 = leeMatriz(scanner, dim);
		
	}
	
	private static ArrayList<ArrayList<Integer>> leeMatriz(Scanner scanner, int n){
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(n);
		for (int i = 0; i < n; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>(n);
			for (int j = 0; j < n; j++) {
				temp.add(scanner.nextInt());
			}
			matrix.add(temp);
		}
		return matrix;
	}
	
	public static Double getMatrixDim(int cual){
		if (cual == 1)
			return (double)matrix1.size();
		else if (cual == 2)
			return (double)matrix2.size();
		else if (cual == 3)
			return (double)matrix3.size();
		else if (cual == 4)
			return (double)matrix4.size();
		else {
			System.err.println("fob (matrices)");
			return null;
		}
	}
		
}
