package util.pg;

import operadores.fitness.FitnessPr3MuxN;
import problemas.Pr3MuxN;
import util.Utiles;

public class UtilesPG {
	
	private static final double alpha = 0.6;
	
	public static Node genTreeCreciente(int profundidad, int profundidadMax){
		Node nodo = null;

		if(profundidad < profundidadMax && Math.random() < alpha){
			nodo = new NonLeafNode();
			nodo.setEsRaiz(false);
			nodo.setTipo(Pr3MuxN.NTDisponibles.get(Utiles.randomIntNO()%Pr3MuxN.NTDisponibles.size()));
			
			int rnd = Utiles.randomIntNO();
			int numHijos;
			
			if(nodo.tipo.getMaxEnts() == nodo.tipo.getMinEnts())
				numHijos = nodo.tipo.getMaxEnts();//lel
			else
				numHijos = nodo.tipo.getMinEnts() + (rnd % (nodo.tipo.getMaxEnts() - nodo.tipo.getMinEnts()));
			
			for(int i = 0; i < numHijos; i++){
				Node hijo = genTreeCreciente(profundidad+1, profundidadMax);
				hijo.setNumHijo(i);
				((NonLeafNode)nodo).addChildren(hijo);
			}
		}
		else{
			nodo = new LeafNode(Utiles.randomIntNO()%FitnessPr3MuxN.valoresSelectsYEnts.size());
			nodo.setEsRaiz(false);
		}
		return nodo;
	}

	public static Node genTreeCompleta(int profundidad, int profundidadMax) {
		Node nodo = null;

		if (profundidad < profundidadMax) {

			nodo = new NonLeafNode();
			nodo.setEsRaiz(false);
			nodo.setTipo(Pr3MuxN.NTDisponibles.get(Utiles.randomIntNO() % Pr3MuxN.NTDisponibles.size()));

			//TODO: Probar con random entre minents y maxents
			int numHijos = nodo.tipo.getMaxEnts();
			for (int i = 0; i < numHijos; i++) {
				Node hijo = genTreeCompleta(profundidad + 1, profundidadMax);
				hijo.setNumHijo(i);
				((NonLeafNode) nodo).addChildren(hijo);
			}

		} else {
			nodo = new LeafNode(Utiles.randomIntNO() % FitnessPr3MuxN.valoresSelectsYEnts.size());
			nodo.setEsRaiz(false);
		}

		return nodo;

	}
}
