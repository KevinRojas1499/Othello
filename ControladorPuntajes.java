import java.io.*;

/*!
 * @brief Automatiza el manejo de los archivos de puntajes.
 * 
 * Esta clase automatiza el manejo de los archivos de puntajes.
 * 
 * Primero se debe inicializar la instancia especificando el nombre del archivo
 * donde se desea trabajar. Esto automáticamente carga los puntajes desde el archivo,
 * y si el archivo no existe, lo crea con una lista vacía.
 * 
 * Una vez cargados los puntajes, se pueden leer mediante el método getPuntaje(), y
 * se puede agregar un puntaje mediante agregarPuntaje() (que lo coloca ordenado
 * según puntaje y descarta los puntajes que exceden los 10 espacios).
 * 
 * Los puntajes se guardan de vuelta al archivo especificado
 * con cada llamada al método agregarPuntaje().
 * 
 */
public class ControladorPuntajes{
	private Puntaje[] listaPuntajes;
	private File archivo;
	private int recordMinimo;
	
	/*!
	 * @brief Crea un objeto que automatiza el manejo de puntajes en el archivo indicado.
	 * @param nombreArchivo El nombre del archivo donde se desea trabajar.
	 * 
	 */
	public ControladorPuntajes(String nombreArchivo){
		archivo = new File(nombreArchivo);
		listaPuntajes = new Puntaje[10];
		try{
			cargarPuntajes();
		}
		catch(Exception e){
			/* Si no puede ejecutar el método anterior, debe haber un error misterioso
			 * que no se puede prevenir.
			 */
			 System.err.println("Imposible cargar los puntajes del archivo: " + nombreArchivo);
		}
		int minimo = 0;
		for(int i = 0; i < listaPuntajes.length; i++){			
			if(listaPuntajes[i] != null){
				if (listaPuntajes[i].getPuntos()<minimo) {
					minimo = listaPuntajes[i].getPuntos();
				}
			}
		}
		recordMinimo = minimo;
	}
	
	public int getMinimo() {
		return recordMinimo;
	}
	
	
	
	private void cargarPuntajes() throws Exception{
		if(archivo.exists()){
			ObjectInputStream entradaObj = new ObjectInputStream(new FileInputStream(archivo));
			listaPuntajes = (Puntaje []) entradaObj.readObject();
			entradaObj.close();
		}
		else{
			/* Si el archivo no existe, créelo, y como no
			 * hay información, escriba la lista vacía sobre el archivo.
			 */
			
			archivo.createNewFile();
			
			guardarPuntajes();
		}
	}
	
	private void guardarPuntajes() throws Exception{
		ObjectOutputStream salidaObj = new ObjectOutputStream(new FileOutputStream(archivo));
		salidaObj.writeObject(listaPuntajes);
		salidaObj.close();
	}
	
	/*!
	 * @brief Agrega el puntaje especificado a la lista.
	 * 
	 * Agrega el puntaje especificado a la lista, insertándolo en la posición
	 * correcta de manera que quede ordenado según el puntaje. Las entradas que exceden
	 * los diez campos se descartan.
	 * 
	 * Si el nuevo puntaje tiene la misma cantidad
	 * de puntos que otro puntaje ya existente, se coloca el nuevo puntaje sobre
	 * todas las entradas con el mismo puntaje.
	 * 
	 * Si el nuevo puntaje tiene menos puntos que todas las entradas, no se
	 * agrega el puntaje.
	 * 
	 * Al final, los puntajes son guardados de vuelta al archivo de puntajes que se está
	 * trabajando.
	 */
	 
	public void agregarPuntaje(Puntaje p){
		int insertarPosicion = listaPuntajes.length;
		
		for(int i = 0; i < listaPuntajes.length; i++){
			if(listaPuntajes[i] == null || listaPuntajes[i].getPuntos() <= p.getPuntos()){
				insertarPosicion = i;
				break;
			}
		}
		
		
		for(int i = listaPuntajes.length-1; i > insertarPosicion; i--){
			listaPuntajes[i]=listaPuntajes[i-1];
		}
		
		if(insertarPosicion < listaPuntajes.length){
			listaPuntajes[insertarPosicion] = p;
		}
		
		try{
			guardarPuntajes();
		}
		catch(Exception e){
			/* Si no puede ejecutar el método anterior, debe haber un error misterioso
			 * que no se puede prevenir.
			 */
			 System.err.println("Imposible guardar los puntajes al archivo: " + archivo.getName());
		}
	}
	
	public Puntaje getPuntaje(int n){
		return listaPuntajes[n];
	}
	
	public String toString(){
		String lista = "";
		
		lista +="\n           Puntajes";
		lista +="\n--------------------------------";
		lista +="\nNo.  Nombre   Tamano   Puntaje";
		
		for(int i = 0; i < listaPuntajes.length; i++){
			if(listaPuntajes[i] != null){
				lista +="\n"+(i+1)+"    "+listaPuntajes[i].toString();
			}
		}
		lista += "\n";
		return lista;
	}
	
}
