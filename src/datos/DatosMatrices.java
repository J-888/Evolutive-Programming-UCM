package datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DatosMatrices {
	
	public static final List<List<Integer>> matrix1; 
	public static final List<List<Integer>> flujos1; 

	public static final List<List<Integer>> matrix2;
	public static final List<List<Integer>> flujos2; 
	
	public static final List<List<Integer>> matrix3;
	public static final List<List<Integer>> flujos3; 
	
	public static final List<List<Integer>> matrix4;
	public static final List<List<Integer>> flujos4; 
	
	static {
		//Leer matrices
		Scanner scanner = null;
		int dim;
		
		try {
			scanner = new Scanner(new File("data/ajuste.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("No existe el archivo ajuste.txt");
		}
		dim = scanner.nextInt();
		matrix1 = Collections.unmodifiableList(leeMatriz(scanner, dim));
		flujos1 = Collections.unmodifiableList(leeMatriz(scanner, dim));
	
		try {
			scanner = new Scanner(new File("data/datos12.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("No existe el archivo datos12.txt");
		}
		dim = scanner.nextInt();
		matrix2 = Collections.unmodifiableList(leeMatriz(scanner, dim));
		flujos2 = Collections.unmodifiableList(leeMatriz(scanner, dim));
		
		try {
			scanner = new Scanner(new File("data/datos15.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("No existe el archivo datos15.txt");
		}
		dim = scanner.nextInt();
		matrix3 = Collections.unmodifiableList(leeMatriz(scanner, dim));
		flujos3 = Collections.unmodifiableList(leeMatriz(scanner, dim));
		
		try {
			scanner = new Scanner(new File("data/datos30.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("No existe el archivo datos30.txt");
		}
		dim = scanner.nextInt();
		matrix4 = Collections.unmodifiableList(leeMatriz(scanner, dim));
		flujos4 = Collections.unmodifiableList(leeMatriz(scanner, dim));
	}
	
	private static List<List<Integer>> leeMatriz(Scanner scanner, int n){
		List<List<Integer>> matrix = new ArrayList<List<Integer>>(n);
		for (int i = 0; i < n; i++) {
			List<Integer> temp = new ArrayList<Integer>(n);
			for (int j = 0; j < n; j++) {
				temp.add(scanner.nextInt());
			}
			matrix.add(Collections.unmodifiableList(temp));
		}
		return matrix;
	}
	
	public static int getMatrixDim(String cual){
		if (cual == "ajuste")
			return matrix1.size();
		else if (cual == "datos12")
			return matrix2.size();
		else if (cual == "datos15")
			return matrix3.size();
		else if (cual == "datos30")
			return matrix4.size();
		else {
			System.err.println("fob (matrices n)");
			return (Integer) null;
		}
	}
		
	public static List<List<Integer>> getMatrizDist(String matName){
		if(matName == "ajuste")
			return matrix1;
		else if(matName == "datos12")
			return matrix2;
		else if(matName == "datos15")
			return matrix3;
		else if(matName == "datos30")
			return matrix4;
		else{
			System.err.println("fob no existe dicha matriz de dists");
			return null;
		}
	}
	
	public static List<List<Integer>> getMatrizFlux(String matName){
		if(matName == "ajuste")
			return flujos1;
		else if(matName == "datos12")
			return flujos2;
		else if(matName == "datos15")
			return flujos3;
		else if(matName == "datos30")
			return flujos4;
		else{
			System.err.println("fob no existe dicha matriz de fluho");
			return null;
		}
	}
	
}
