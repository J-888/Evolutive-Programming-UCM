package view;

import datos.DatosMatrices;

public class Main {

	public static void main(String[] args) {
		//Para la practica 2
		DatosMatrices.leeMatrices();
		//
		
		GUI p = new GUI();
		p.setSize(1000, 600);
		p.setLocationRelativeTo(null);
		p.setVisible(true);	
	}
	
}
