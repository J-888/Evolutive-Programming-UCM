package view;

public class Main {

	public static void main(String[] args) {
		GUI p = new GUI();
		p.setSize(1000, 600);
		p.setLocationRelativeTo(null);
		p.setVisible(true);	
	}
	
}//TODO MEMORIA
/*
descripcion de la aplicacion : estructura, metodos y algoritmos utilizados
graficas de evolucion de cada funcion y conclusiones
*/

/*
 * Popup para cnd no se usa elite
 * Popup para sacar el mejor obtenido
 * 
 * MutaGen aparte de MutaBase (MutaGen llama a mutaBase si es binario o si tal)
 * Mutacion real
 * 
 * 
 * 
 * PARA MEM:
 * En el 4, ejecutado sin mutacion y con cruce monopunto (intercambio de x's), la mejora se produce unicamente al formar cromosomas cruzando los elementos de la poblacion inicial
 * con lo que el maximo alcanzable esta predeterminado en la poblacion inicial. Se puede comprobar que el optimo alcanzado depende
 * del tamaño de la poblacion.
 */
