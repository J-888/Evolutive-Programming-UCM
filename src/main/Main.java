package main;

import view.GUI;

public class Main {

	public static void main(String[] args) {	
		
		GUI p = new GUI();
		p.setSize(1000, 600);
		p.setLocationRelativeTo(null);
		p.pack();
		p.setVisible(true);	
	}
	
	//TODO: Falta al generar individuos de las diversas formas que el numero de hijos tenga sentido
	//Tal vez que los enumerados tengan una funcion getmin y otra getmax, que devuelvan los numeros de entradas pertinentes
	//Usar esas funciones en la generacion de individuos (y cruces y mutaciones si procede)
	
	//Falta actualizarArbol();
	
	//Recordar actualizarArbol() cada vez que se toque un arbol!!!!! !?!?1?!?1'1'1duheawdhuadwah
	//setParent() no hace falta si los cambios se producen utilizando addChildren()
	
}
