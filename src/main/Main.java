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
	
}