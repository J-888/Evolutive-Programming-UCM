package util;

import geneticos.Poblacion;

public class Contractividad {
	private boolean minimizacion;
	private int count4Skip;
	private boolean skipping;
	private String tipo;
	private Poblacion poblacionPrevia;
	private boolean iterValida;
	
	public Contractividad(String contractString, boolean minimizacion){
		this.minimizacion = minimizacion;
		poblacionPrevia = null;
		count4Skip = 0;
		skipping = false;
		if(contractString.equals("No"))
			tipo = "disabled";
		else if(contractString.equals("Actualizando población"))
			tipo = "act";
		else if(contractString.equals("Sin actualizar población"))
			tipo = "sinact";
		else
			System.err.println("Fallo en contractividad (constructor)");
	}

	public boolean iteracionValida() {
		if(skipping)
			return true;
		else
			return iterValida;
	}

	public boolean execute(Poblacion nuevaPob){ //Devuelve true si hay que preprocesar, es decir, si la nuevaPob es aceptada

		boolean ret;
		
		if(skipping){
			ret = true;
		}
		else{
		
			if (!tipo.equals("disabled")) {
				if (poblacionPrevia == null) {// Caso primera iteracion
					poblacionPrevia = nuevaPob;
					iterValida = true;
					ret = true;
				} else {// No primera iteracion
	
					iterValida = esMejor(nuevaPob);
					controlSkipping(iterValida);
					
					if (tipo.equals("act")) { //Con actualizacion siempre se acepta la nueva poblacion, y hay que preprocesarla
						poblacionPrevia = nuevaPob;
						ret = true;
					} else if (tipo.equals("sinact")) { //Sin actualizacion solo se preprocesa si se acepta, es decir, ha habido mejoria
						if (iterValida){
							poblacionPrevia = nuevaPob;
							ret = true;
						}
						else {
							nuevaPob.setPoblacion(poblacionPrevia.getPoblacion());
							nuevaPob.setMejorAbsoluto(poblacionPrevia.getMejorAbsoluto());
							nuevaPob.setMejorIndividuo(poblacionPrevia.getMejorIndividuo());
							nuevaPob.setPeorIndividuo(poblacionPrevia.getPeorIndividuo());
							nuevaPob.setPobAvgFitness(poblacionPrevia.getPobAvgFitness());
							ret = false;
						}
					} else{
						ret = (Boolean) null;
						System.err.println("Fallo en contractividad (newPobResults)");
					}
				}
			}
			else{
				iterValida = true;
				ret = true; //Disabled, todo adelante
			}
			
		}
		
		return ret;
	}

	private void controlSkipping(boolean iterValida) {
		if(iterValida)
			count4Skip = 0;
		else{
			count4Skip++;
			if(count4Skip == 10)
				skipping = true;
		}
	}

	private boolean esMejor(Poblacion nuevaPob) {
		
		if (minimizacion) {
			if(nuevaPob.getMejorAbsoluto().getFitness() < poblacionPrevia.getMejorAbsoluto().getFitness())
				return true;
			else
				return nuevaPob.getPobAvgFitness() < poblacionPrevia.getPobAvgFitness();
		} else {
			if(nuevaPob.getMejorAbsoluto().getFitness() > poblacionPrevia.getMejorAbsoluto().getFitness())
				return true;
			else
				return nuevaPob.getPobAvgFitness() > poblacionPrevia.getPobAvgFitness();
		}
			
	}
	
}
