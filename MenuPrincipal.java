import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.*;
import java.awt.event.*;

/*!
 * @brief La clase del menu principal es la que contiene el main 
 * Esta cuenta con las opciones para jugar dos jugadores o solo 
 * contra la computadora. Tambien tiene la opcion de entrar a los puntajes
 * y salir del juego.
 */
public class MenuPrincipal extends JFrame{
	private final int ANCHO = 660;
	private final int LARGO = 600;	
	
	private JButton Jugador1, Jugador2,Puntajes,salir;
	
	
	/*!
	 * @brief Construye una instancia del menu 
	 * Esta inicializa los botones.
	 */
	
	
	public MenuPrincipal() {
		super("Othello");
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,1));
		Jugador1 = new JButton("1 Jugador");
		panel.add(Jugador1);
		Jugador2 = new JButton("2 Jugadores");
		panel.add(Jugador2);
		Puntajes = new JButton("Puntajes");
		panel.add(Puntajes);
		salir = new JButton("Salir");
		panel.add(salir);
		panel.setBounds(270,300,125,100);

		this.setLayout(null);
		this.setSize(ANCHO,LARGO);
		this.setVisible(true);
		this.setResizable(false);
		

		this.add(panel);
		ManejadorBoton manejador = new ManejadorBoton();
		Jugador1.addActionListener(manejador);
		Jugador2.addActionListener(manejador);
		Puntajes.addActionListener(manejador);
		salir.addActionListener(manejador);
	}
	
	
	/*
	 * Clase privada que maneja los eventos de los botones
	 */
	
	private class ManejadorBoton implements ActionListener{
		public void actionPerformed(ActionEvent evento) {
			if("1 Jugador".equals(evento.getActionCommand())) {
				new MenuTableros(true);
				setVisible(false);
				dispose();
			}
			else if("2 Jugadores".equals(evento.getActionCommand())) {
				new MenuTableros(false);
				setVisible(false);
				dispose();
			}
			else if("Puntajes".equals(evento.getActionCommand())) {
				new PuntajesComputadoraOJugador();
			}
			else if("Salir".equals(evento.getActionCommand())) {
				setVisible(false);
				dispose();
			}			
		}
	}
	
	
	
	/*
	 * Este metodo se encarga de pintar un titulo.
	 */
	
	public void paint (Graphics g) {
		super.paint(g);
		g.setFont(new Font("Monospaced",Font.BOLD,100));
		g.drawString("OTHELLO", 125, 200);
	}
	
	public static void main (String args []) {
		new MenuPrincipal();
	}
}
