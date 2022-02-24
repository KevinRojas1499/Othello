/*!
 * @brief Colección de estrategias para la computadora.
 * 
 * Cada instancia se debe construir especificando sobre cuál tablero debe
 * jugar, qué estrategia debe usar y cuál color usa.
 * 
 * Para que la computadora realice una jugada, se debe invocar al método
 * jugada().
 * 
 * Actualmente existen dos estrategias:
 * - La estrategia número 0 pone la ficha en el primer lugar que encuentra, buscando
 * de derecha a izquierda y de arriba a abajo.
 * - La estrategia no. 1 busca la posición donde pueda comer la mayor cantidad
 * de fichas posible.
 * 
 */
public class Computadora{
	private int estrategia;
	private int color;
	private Tablero tablero;
	
	/*!
	 * @brief Construye una instancia de Computadora que trabaja con el tablero, estrategia y color indicado.
	 * @param tablero El tablero sobre el cual debe realizar las jugadas.
	 * @param estrategia El número de la estrategia que debe seguir.
	 * @param color El color con el cual juega.
	 */
	public Computadora(Tablero tablero, int estrategia, int color){
		this.tablero = tablero;
		this.estrategia = estrategia;
		this.color = color;
	}
	
	/*!
	 * @brief Realiza una juada.
	 * 
	 * Este método debe invocarse cada vez que se desee que la computadora
	 * realice una jugada.
	 */
	public void jugada(){
		switch(estrategia){
			case 0:
				estrategia0();
			break;
			case 1:
				estrategia1();
			break;
		}
	}
	
	/*
	 * Pone en el primer lugar que encuentra.
	 */
	private void estrategia0(){
		int tamano = tablero.getTamano();
		
		for(int i = 0; i < tamano; i++){
			for(int j = 0; j < tamano; j++){
				if(tablero.puedePoner(color, i, j)){
					tablero.poner(color, i, j);
					return;
				}
			}
		}
	}
	
	/*
	 * Pone en el lugar donde come más piezas.
	 */
	private int cuantasComeDireccion(int fila, int columna, int dFila, int dColumna){
		int r = 0;
		int i = fila;
		int j = columna;
		boolean cierraColor = false;
		int tamano = tablero.getTamano();
		
		i+=dFila;
		j+=dColumna;
		
		while(0 <= i && i < tamano && 0 <= j && j < tamano){
			int colorActual = tablero.getColor(i, j);
			if(colorActual == Tablero.VACIO){
				r = 0;
				break;
			}
			else if(colorActual == color){
				cierraColor = true;
				break;
			}
			else if(colorActual == Tablero.colorOpuesto(color)){
				r++;
			}
			
			i+=dFila;
			j+=dColumna;
		}
		
		if(!cierraColor){
			r = 0;
		}
		
		return r;
	}
	
	private int cuantasCome(int fila, int columna){
		int r = 0;
		if(tablero.getColor(fila, columna)==Tablero.VACIO){
			r += cuantasComeDireccion(fila, columna, 0, 1);
			r += cuantasComeDireccion(fila, columna, -1, 1);
			r += cuantasComeDireccion(fila, columna, -1, 0);
			r += cuantasComeDireccion(fila, columna, -1, -1);
			r += cuantasComeDireccion(fila, columna, 0, -1);
			r += cuantasComeDireccion(fila, columna, 1, -1);
			r += cuantasComeDireccion(fila, columna, 1, 0);
			r += cuantasComeDireccion(fila, columna, 1, 1);
		}
		return r;
	}
	
	private void estrategia1(){
		int maximo = 0;
		int jMax = -1;
		int iMax = -1;
		int tamano = tablero.getTamano();
		
		for(int i = 0; i < tamano; i++){
			for(int j = 0; j < tamano; j++){
				int cuantas = cuantasCome(i,j);
				if(cuantas > maximo){
					iMax = i;
					jMax = j;
					maximo = cuantas;
				}
			}
		}
		
		if(maximo > 0){
			tablero.poner(color, iMax, jMax);
		}
	}
}
