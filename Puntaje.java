import java.io.*;

/*!
 * @brief Información de una fila en cada tabla de puntajes.
 * 
 * Esta clase es un contenedor de la información de cada fila en las tablas
 * puntajes.
 * 
 */
public class Puntaje implements Serializable{
	private String nombre;
	private int puntos;
	private int tamano;
	
	/*!
	 * @brief Construye una fila de datos con la información indicada.
	 * 
	 * @param nombre El nombre del jugador. El nombre es truncado a 15 caracteres.
	 * @param puntos Los puntos que obtuvo.
	 * @param tamano El modo de juego donde hizo los puntos (el tamaño del
	 * tablero).
	 * 
	 */
	public Puntaje(String nombre, int puntos, int tamano){
		// La longitud máxima para el nombre es 15 caracteres
		nombre+="                                     ";
		this.nombre = nombre.substring(0,Math.min(9,nombre.length()));
		this.puntos = puntos;
		this.tamano = tamano;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public int getPuntos(){
		return puntos;
	}
	
	public int getTamano(){
		return tamano;
	}
	
	public String toString(){
		String espacioDespuesNombre = "";
		
		return "	"+nombre + "(" + tamano + "x" + tamano + ")     " + puntos + "pts.";
	}
}
