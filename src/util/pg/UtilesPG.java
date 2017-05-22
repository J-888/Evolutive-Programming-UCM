package util.pg;

import operadores.fitness.FitnessPr3MuxN;
import problemas.Pr3MuxN;
import util.Utiles;

public class UtilesPG {
	
	private static final double alpha = 0.6;
	
	public static Node genTreeCreciente(int profundidad, int profundidadMax){
		Node nodo = null;

		if(profundidad < profundidadMax){
			if(Math.random() < alpha){
				nodo = new NonLeafNode();
				nodo.setTipo(Pr3MuxN.NTDisponibles.get(Utiles.randomIntNO()%Pr3MuxN.NTDisponibles.size()));
				
				int numHijos = Utiles.randomIntNO() % Pr3MuxN.maxEntsPorNodo;
				for(int i = 0; i < numHijos; i++){
					Node hijo = genTreeCreciente(profundidad+1, profundidadMax);
					((NonLeafNode)nodo).addChildren(hijo);
					
				}
			}
			else{
				nodo = new LeafNode(Utiles.randomIntNO()%FitnessPr3MuxN.valorEnts.size());
			}
		}
		return nodo;
	}

	public static Node genTreeCompleta(int profundidad, int profundidadMax) {
		Node nodo = null;

		if (profundidad < profundidadMax) {

			nodo = new NonLeafNode();
			nodo.setTipo(Pr3MuxN.NTDisponibles.get(Utiles.randomIntNO() % Pr3MuxN.NTDisponibles.size()));

			int numHijos = Pr3MuxN.maxEntsPorNodo;
			for (int i = 0; i < numHijos; i++) {
				Node hijo = genTreeCompleta(profundidad + 1, profundidadMax);
				((NonLeafNode) nodo).addChildren(hijo);
			}

		} else {
			nodo = new LeafNode(Utiles.randomIntNO() % FitnessPr3MuxN.valorEnts.size());
		}

		return nodo;

	}
}
