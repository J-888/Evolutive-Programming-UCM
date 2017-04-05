package view;

public class Main {

	public static void main(String[] args) {	
		
		GUI p = new GUI();
		p.setSize(1000, 600);
		p.setLocationRelativeTo(null);
		p.setVisible(true);	
	}
	
	//TODO: Quitar clones innecesarios en las funciones de cruce. Ya se pasan los padres clonados.
}
