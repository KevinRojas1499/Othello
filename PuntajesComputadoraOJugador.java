import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PuntajesComputadoraOJugador extends JFrame {
	
	private final int ANCHO = 660;
	private final int LARGO = 600;	
	private JButton computadora, jugador,atras,salir;
	
	/*!
	 * @brief Construye una instancia del menu.
	 * Anade los botones para elegir si ver puntajes de computadora o 
	 * 2 jugadores
	 */

	public PuntajesComputadoraOJugador() {
		
		super("Othello");
		setLayout(null);
		setSize(ANCHO,LARGO);
		setVisible(true);
		setResizable(false);
		JPanel panel =  new JPanel();
		panel.setLayout(new GridLayout(4,1));
		computadora = new JButton ("Records modo 1 Jugador");
		panel.add(computadora);
		jugador =  new JButton ("Records modo 2 Jugadores");
		panel.add(jugador);
		atras =  new JButton("Volver");
		panel.add(atras);
		salir =  new JButton("Salir");
		panel.add(salir);
		
		panel.setBounds(225,350,200,100);
		panel.setVisible(true);
		this.add(panel);
		ManejadorBoton manejador  = new ManejadorBoton();
		salir.addActionListener(manejador);
		computadora.addActionListener(manejador);
		atras.addActionListener(manejador);
		jugador.addActionListener(manejador);
	}
	
	/*
	 * Este metodo se encarga de pintar un titulo para el menu
	 */
	public void paint (Graphics g) {
		super.paint(g);
		g.setFont(new Font("Monospaced",Font.BOLD,100));
		g.drawString("OTHELLO", 125, 200);
	}
	
	/*
	 * Clase privada que maneja los eventos de los botones
	 */
	
	private class ManejadorBoton implements ActionListener{
		public void actionPerformed(ActionEvent evento) {
			if("Records modo 1 Jugador".equals(evento.getActionCommand())) {
				new MenuPuntajes(true);
				setVisible(false);
				dispose();

			}
			else if("Records modo 2 Jugadores".equals(evento.getActionCommand())) {
				new MenuPuntajes(false);
				setVisible(false);
				dispose();
			}	
			if("Salir".equals(evento.getActionCommand())) {
				setVisible(false);
				dispose();

			}
			else if("Volver".equals(evento.getActionCommand())) {
				new MenuPrincipal();
				setVisible(false);
				dispose();
			}	
		}
	}
}
