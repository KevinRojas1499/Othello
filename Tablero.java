
/*!
 * @brief El tablero donde se almacena la información del juego.
 * 
 * Cada instancia de esta clase se inicializa especificando el tamaño 
 * (cantidad de filas y columnas). Automáticamente se colocan las 
 * cuatro fichas centrales.
 * 
 * El tablero consiste en una matriz cuadrada con los tres posibles 
 * valores NEGRO, BLANCO y VACIO.
 * 
 * Esta clase se encarga de indicar si se puede o no colocar una ficha 
 * en un lugar especificado y poner las fichas cuando se le indica 
 * (realizando las sustituciones de las piezas que come).
 */
public class Tablero{
	
	/*!
	 * @brief Representa una casilla con una ficha negra.
	 */
	public static final int NEGRO=1;
	
	/*!
	 * @brief Representa una casilla con una ficha blanca.
	 */
	public static final int BLANCO=-1;
	
	/*!
	 * @brief Representa una casilla vacía.
	 */
	public static final int VACIO=0;
	
	/*!
	 * @brief Invierte el color de NEGRO a BLANCO y viceversa.
	 * @param color El color que se desea invertir.
	 * @return Si color es BLANCO retorna NEGRO. Si color es NEGRO 
	 * retorna BLANCO. En cualquier otro caso retorna VACIO.
	 */
	public static int colorOpuesto(int color){
		if(color == NEGRO){
			return BLANCO;
		}
		else if(color == BLANCO){
			return NEGRO;
		}
		else{
			return VACIO;
		}
	}
	
	private int[][] fichas;
	
	private int tamano;
	
	/*!
	 * @brief Inicializa el tablero con el tamaño indicado y las cuatro 
	 * fichas centrales.
	 * 
	 * @param tamano El tamaño del tablero.
	 */
	public Tablero(int tamano){
		if(tamano%2==0 && tamano >=4){
			this.tamano = tamano;
			fichas = new int[tamano][tamano];
			
			// Inicializar las cuatro fichas del centro.
			fichas[tamano/2][tamano/2]=BLANCO;
			fichas[tamano/2-1][tamano/2]=NEGRO;
			fichas[tamano/2][tamano/2-1]=NEGRO;
			fichas[tamano/2-1][tamano/2-1]=BLANCO;
		}
		else{
			throw new IllegalArgumentException("El tamano debe ser par.");
		}
	}
	
	/*!
	 * @brief Retorna el tamaño del tablero.
	 */
	public int getTamano(){
		return tamano;
	}
	
	private int cantidad(int color){
		int n = 0;
		for(int i = 0; i < tamano; i++){
			for(int j = 0; j < tamano; j++){
				if(fichas[i][j]==color){
					n++;
				}
			}
		}
		
		return n;
	}
	
	/*!
	 * @brief Cuenta la cantidad de fichas negras.
	 * 
	 * Itera sobre los cuadros del tablero y retorna la cantidad de fichas negras.
	 * 
	 * @return La cantidad de fichas negras sobre el tablero.
	 * 
	 */
	public int cantidadNegras(){
		return cantidad(NEGRO);
	}
	
	/*!
	 * @brief Cuenta la cantidad de fichas blancas.
	 * 
	 * Itera sobre los cuadros del tablero y retorna la cantidad de fichas blancas.
	 *  
	 * @return La cantidad de fichas blancas sobre el tablero.
	 * 
	 */
	public int cantidadBlancas(){
		return cantidad(BLANCO);
	}
	
	/*!
	 * @brief Devuelve el color de un cuadro en el tablero.
	 * @param fila La fila de donde se quiere obtener el color.
	 * @param columna La columna de donde se quiere obtener el color.
	 * @return Retorna alguno de los valores Tablero.VACIO, 
	 * Tablero.NEGRO o Tablero.BLANCO, según corresponda.
	 */
	public int getColor(int fila, int columna){
		return fichas[fila][columna];
	}
	
	private boolean puedeComerDireccion(int color, int fila, int columna, int dFila, int dColumna){
		int fichasEnMedio = 0;
		boolean ultimaFichaColor = false;
		int i = fila;
		int j = columna;
		
		i+=dFila;
		j+=dColumna;
		while(0<=i && i < tamano && 0<=j && j<tamano){
			
			if(fichas[i][j]==VACIO){
				fichasEnMedio=0;
				break;
			}
			else if(fichas[i][j] == color){
				ultimaFichaColor = true;
				break;
			}
			else if(fichas[i][j] == colorOpuesto(color)){
				fichasEnMedio++;
			}
			
			i+=dFila;
			j+=dColumna;
		}
		
		if(fichasEnMedio>0 && ultimaFichaColor && getColor(fila, columna)==VACIO){
			return true;
		}
		else{
			return false;
		}
	}
	
	/*!
	 * @brief Indica si se puede colocar una ficha de un color dado en un cuadro del tablero.
	 * 
	 * Este método verifica que la casilla donde se quiere colocar la ficha esté
	 * vacía y que la jugada sea válida, es decir, que pueda comer piezas
	 * del color opuesto al colocar la ficha.
	 * 
	 * @param color El color de la ficha que se quiere poner.
	 * @param fila La fila de donde se quiere obtener el color.
	 * @param columna La columna de donde se quiere obtener el color.
	 * @return true si puede se puede poner una ficha del 'color' en el lugar especificado
	 * 
	 */
	public boolean puedePoner(int color, int fila, int columna){
		if(color != NEGRO && color != BLANCO)
			throw new IllegalArgumentException("El color es invalido.");
		
		if(fila < 0 || fila >= tamano)
			throw new IllegalArgumentException("La fila esta fuera de los limites del tablero.");
		if(columna < 0 || columna >= tamano)
			throw new IllegalArgumentException("La columna esta fuera de los limites del tablero.");
		
		boolean puede = false;
		int[] dirX = {0,-1,-1,-1, 0, 1, 1,1};
		int[] dirY = {1, 1, 0,-1,-1,-1, 0,1};
		if(fichas[fila][columna]==VACIO){
			for(int i = 0; i<8; i++){
				puede|= puedeComerDireccion(color,fila,columna,dirX[i],dirY[i]);
			}
		}
		return puede;
	}
	
	/*!
	 * @brief Indica si existen jugadas válidas para un color dado en 
	 * algún lugar del tablero.
	 * 
	 * Esta función itera sobre cada casilla del tablero, invocando al 
	 * método puedePoner() y retorna true si se puede poner en algún 
	 * lugar y false si no.
	 * 
	 * @param color El color de la ficha que se quiere colocar.
	 * @return true si existe alguna jugada en el tablero, false si no.
	 * 
	 */
	public boolean existenJugadas(int color){
		if(color != NEGRO && color != BLANCO){
			throw new IllegalArgumentException("El color es invalido.");
		}
		
		boolean existen = false;
		
		for(int i = 0; i < tamano && !existen; i++){
			for(int j = 0; j < tamano && !existen; j++){
				existen |= puedePoner(color,i,j);
			}
		}
		
		
		return existen;
	}
	
	private void comerDireccion(int color, int fila, int columna, int dFila, int dColumna){
		int i = fila;
		int j = columna;
		
		i+=dFila;
		j+=dColumna;
		while(0<=i && i < tamano && 0<=j && j<tamano){
			
			if(fichas[i][j]==color){
				break;
			}
			else if(fichas[i][j]==colorOpuesto(color)){
				fichas[i][j] = color;
			}
			
			i+=dFila;
			j+=dColumna;
		}
	}
	
	
	/*!
	 * @brief Coloca la pieza del color indicado en la posición 
	 * indicada.
	 * 
	 * Coloca la pieza del color indicado en la posición indicada y 
	 * sustituye las piezas que se come por el nuevo color. Invocar 
	 * este método en un lugar inválido (fuera del tablero o que no 
	 * admita una jugada válida) resulta en una excepción.
	 * 
	 * @param color El color de la ficha que se desea poner.
	 * @param fila La fila de la casilla.
	 * @param columna La columna de la casilla.
	 * 
	 */
	public void poner(int color, int fila, int columna){
		if(color != NEGRO && color != BLANCO)
			throw new IllegalArgumentException("El color es invalido.");
		
		if(fila < 0 || fila >= tamano)
			throw new IllegalArgumentException("La fila esta fuera de los limites del tablero.");
		if(columna < 0 || columna >= tamano)
			throw new IllegalArgumentException("La columna esta fuera de los limites del tablero.");
		
		boolean pudoPoner = false;
		
		int[] dirX = {0,-1,-1,-1, 0, 1, 1,1};
		int[] dirY = {1, 1, 0,-1,-1,-1, 0,1};
		for(int i = 0; i<8; i++){
			if(puedeComerDireccion(color,fila,columna,dirX[i],dirY[i])){
				comerDireccion(color,fila,columna,dirX[i],dirY[i]);
				pudoPoner = true;
			}

		}
		if(pudoPoner){
			fichas[fila][columna] = color;
		}
		else
			throw new RuntimeException("No se puede poner una ficha en el lugar especificado.");
	}
}
