import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.*;
import java.awt.event.*;

/*!
 * @brief Esta clase muestra el tablero y permite jugar en el
 * Cuenta con los modos de juego con computadora y 2 jugadores
 * Esta es un frame y recibe interaccion con el usuario mediante 
 * el mouse. Este ultimo es el que tiene la informacion de cuando
 * se termina el juego y realiza las operaciones de poner fichas. 
 */



public class InterfazGrafica  extends JFrame {
	private int CASILLA = 50;
	private final int ANCHO = 660;
	private final int LARGO = 600;
	private final int X = 65;
	private final int Y = 150;
	private JPanel fichaValida;
	public Tablero tablero;
	private boolean computadora;
	private Computadora AI;
	
	
	/*!
	 * @brief Construye una instancia de la Interfaz
	 * @param recibe el tablero sobre el cual se va a jugar 
	 * @param y un booleano que indica si se juega o no contra la computadora. 
	 * Ademas pone los botones de salir y volver al menu
	 */
	public InterfazGrafica(Tablero t,Boolean c) {		
		super("Othello");
		if (t.getTamano()== 8) 
			CASILLA = 50;
		else if (t.getTamano() == 10)
			CASILLA = 40;
		else
			CASILLA = 35;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		menu = new JButton("Menu");
		panel.add(menu);
		salir = new JButton("Salir");
		panel.add(salir);
		this.add(panel);
		panel.setBounds(520,400,75,50);
		

		this.setLayout(null);
		this.setSize(ANCHO,LARGO);
		this.setVisible(true);
		this.setResizable(false);
		this.tablero = t;
		
		ManejadorBoton manejador = new ManejadorBoton();
		menu.addActionListener(manejador);
		salir.addActionListener(manejador);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(new MouseInput(t,CASILLA));
		computadora = c;
		AI = new Computadora(t, 1, -1);
	}
	
	
	
	private JButton salir, menu;
	
	/*
	 * Clase privada que maneja los eventos de los botones
	 */
	
	private class ManejadorBoton implements ActionListener{
		public void actionPerformed(ActionEvent evento) {
			if("Salir".equals(evento.getActionCommand())) {
				setVisible(false); 
				dispose();
			}
			if("Menu".equals(evento.getActionCommand())) {
				MenuPrincipal menu = new MenuPrincipal();
				dispose();
			}
		}
	}
	
	/*
	 * Este metodo se encarga de pintar el tablero y sus fichas
	 * Ademas mantiene un track del score.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		g.setFont(new Font("Monospaced",Font.BOLD,100));
		g.drawString("OTHELLO", 50, 120);
		g.setFont(new Font("Monospaced",Font.BOLD,25));
		g.drawString("Puntajes", 500, 180);
		g.drawOval(513, 203, 50-5, 50-5);
		g.setColor(Color.BLACK);
		g.fillOval(563, 203, 50-5, 50-5);
		g.drawRect(510, 200, 2*50, 2*50);
		g.drawLine(510,250,610,250);
		g.drawLine(560,200,560,300);
		g.setFont(new Font("Monospaced",Font.BOLD,25));		
		g.drawString(""+tablero.cantidadBlancas(), 520, 280);
		g.drawString(""+tablero.cantidadNegras(),570,280);
		g.setFont(new Font("Monospaced",Font.BOLD,25));
		for (int i = 0; i< tablero.getTamano(); i++) {
			for (int j = 0; j < tablero.getTamano(); j++) {
				if(i%2 == 1 && j%2 ==1) {
					g.setColor(Color.darkGray);
					g.fillRect(i*CASILLA+X,j*CASILLA+Y,CASILLA,CASILLA);
				}
				else if(i%2 == 0 && j%2 ==0) {
					g.setColor(Color.darkGray);
					g.fillRect(i*CASILLA+X,j*CASILLA+Y,CASILLA,CASILLA);
				}
				else {
					g.setColor(Color.gray);
					g.fillRect(i*CASILLA+X,j*CASILLA+Y,CASILLA,CASILLA);
				}
			}
		}
		for (int i = 0 ; i < tablero.getTamano(); i++) {
			for (int j = 0; j < tablero.getTamano(); j++) {
				if(tablero.getColor(i,j) == -1) {
					g.setColor(Color.WHITE);
					g.fillOval(i*CASILLA+X, j*CASILLA+Y, CASILLA, CASILLA);
				}
				else if	(tablero.getColor(i,j) == 1) {
					g.setColor(Color.BLACK);
					g.fillOval(i*CASILLA+X,j*CASILLA+Y,CASILLA,CASILLA);
				}
			}
		}		
	}

	/*
	 * Clase privada que permite reicibir eventos del mouse. Esta permite jugar al Othello.
	 * Esta trabaja unicamente con el evento del click. Se asegura que existan jugadas validas
	 * y de no existir entonces termina el juego. Hace las jugadas de los jugadores en la casilla
	 * que solicitan y de ser necesario hace las jugadas de la computadora
	 */
	
	private class MouseInput implements MouseListener {
		

		private int CASILLA;
		private int jugadorActual;

		
		public MouseInput(Tablero t, int casilla) {
			CASILLA = casilla;
			jugadorActual = 1;

		}

		public void mouseClicked(MouseEvent e) {
			int mx  =  ((e.getX()-65)/CASILLA);
			int my  =  ((e.getY()-150)/CASILLA);
			


			if (!computadora) {
				if(!tablero.existenJugadas(1)&& !tablero.existenJugadas(-1)) {
					//Final del juego ninguno tiene jugadas
					ControladorPuntajes puntajes = new ControladorPuntajes(""+tablero.getTamano()+"Jugador");
					int maximo  = Math.max(tablero.cantidadBlancas(), tablero.cantidadNegras());
					
					if (maximo > puntajes.getMinimo()) {
						String nombre = JOptionPane.showInputDialog("Ud acaba de hacer un nuevo record\n Ingrese su nombre para la lista de records");
						if (nombre == null) {
							nombre =  "Invitado";
						}
						Puntaje puntaje =  new Puntaje(nombre,maximo,tablero.getTamano());
						puntajes.agregarPuntaje(puntaje);
						String ganador = tablero.cantidadBlancas()>tablero.cantidadNegras() ? "el blanco" : "el negro";
						JOptionPane.showMessageDialog(null, "El juego ha terminado, el ganador es "+ ganador);
						new MenuPrincipal();
						dispose();
					}
					else {
						String ganador = tablero.cantidadBlancas()>tablero.cantidadNegras() ? "el blanco" : "el negro";
						JOptionPane.showMessageDialog(null, "El juego ha terminado, el ganador es "+ ganador);
						new MenuPrincipal();
						dispose();
					}
				}
			}
			else {
				if(!tablero.existenJugadas(1) && !tablero.existenJugadas(-1)) {
					//Final del juego, ninguno tiene jugadas
					ControladorPuntajes puntajes = new ControladorPuntajes(""+tablero.getTamano()+"Computadora");
					
					if (tablero.cantidadBlancas()<tablero.cantidadNegras() && tablero.cantidadNegras() > puntajes.getMinimo() ) {
						String nombre = JOptionPane.showInputDialog("Ud acaba de hacer un nuevo record\n Ingrese su nombre para la lista de records");
						if (nombre == null) {
							nombre =  "Invitado";
						}
						Puntaje puntaje =  new Puntaje(nombre,tablero.cantidadNegras(),tablero.getTamano());
						puntajes.agregarPuntaje(puntaje);
						String ganador = tablero.cantidadBlancas()>tablero.cantidadNegras() ? "la computadora" : "ud";
						JOptionPane.showMessageDialog(null, "El juego ha terminado, el ganador es "+ganador);
						new MenuPrincipal();
						dispose();
					}
					else {
						String ganador = tablero.cantidadBlancas()>tablero.cantidadNegras() ? "la computadora" : "ud";
						JOptionPane.showMessageDialog(null, "El juego ha terminado, el ganador es "+ganador);
						new MenuPrincipal();
						dispose();
					}
				}
			}
			


			if(0 <= mx && mx<tablero.getTamano() && 0 <= my && my<tablero.getTamano()) {
				if(tablero.puedePoner(jugadorActual, mx, my)) {
					if (!computadora){						
						tablero.poner(jugadorActual,mx,my);
						repaint();
						do {
							if(tablero.existenJugadas(Tablero.colorOpuesto(jugadorActual))){
								jugadorActual = Tablero.colorOpuesto(jugadorActual);
							}
						}while(!tablero.existenJugadas(jugadorActual)&&tablero.existenJugadas(Tablero.colorOpuesto(jugadorActual)));					

					}
					else {
						tablero.poner(jugadorActual,mx,my);
						repaint();
						do {
							AI.jugada();
							repaint();
						}while(!tablero.existenJugadas(jugadorActual)&&tablero.existenJugadas(Tablero.colorOpuesto(jugadorActual)));
					}					
				}
				
			}		
		}

		public void mousePressed(MouseEvent e) {

		}


		public void mouseReleased(MouseEvent e) {
			

		}


		public void mouseEntered(MouseEvent e) {

			
		}

		public void mouseExited(MouseEvent e) {

			
		}	



	}

}
