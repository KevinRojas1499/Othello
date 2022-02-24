import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*!
 * @brief Este es el menu que contiene los puntajes cuenta con los botones
 * muestra en la pantalla los puntajes de los 3 distintos tableros.
 */

public class MenuPuntajes extends JFrame {
	private final int ANCHO = 660;
	private final int LARGO = 600;		
	private JButton volver,salir;
	private boolean computadora;
	
	
	/*!
	 * @brief Construye una instancia del menu de puntajes
	 * esta recibe si es puntajes con o sin computadora y inicializa los
	 * botones de atras y salir.
	 */
	
	public MenuPuntajes(boolean c) {
		super("Othello");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		this.setLayout(null);
		setSize(ANCHO,LARGO);
		setVisible(true);
		setResizable(false);
		volver = new JButton("Atras");
		panel.add(volver);
		salir = new JButton("Salir");
		panel.add(salir);		
		this.add(panel);
		panel.setBounds(270,500,100,50);
		ManejadorBoton manejador = new ManejadorBoton();
		volver.addActionListener(manejador);
		salir.addActionListener(manejador);
		computadora  = c;
	}
	
	
	/*
	 * Clase privada que maneja los eventos de los botones
	 */
	private class ManejadorBoton implements ActionListener{
		public void actionPerformed(ActionEvent evento) {
			if("Atras".equals(evento.getActionCommand())) {
				new PuntajesComputadoraOJugador();
				setVisible(false);
				dispose();

			}
			else if("Salir".equals(evento.getActionCommand())) {
				setVisible(false);
				dispose();
			}			
		}
	}
	/*
	 * Este metodo se encarga de pintar el titulo
	 */
	public void paint (Graphics g) {
		super.paint(g);
		if(!computadora) {
			ControladorPuntajes ocho =  new ControladorPuntajes("8Jugador");
			ControladorPuntajes diez =  new ControladorPuntajes("10Jugador");
			ControladorPuntajes doce =  new ControladorPuntajes("12Jugador");

			g.setFont(new Font("Monospaced",Font.BOLD,35));
			g.drawString("Puntajes con Computadora", 100, 75);
			g.setFont(new Font("Monospaced",Font.BOLD,20));
			g.drawString("8x8", 110, 140);
			g.drawString("10x10", 300, 140);
			g.drawString("12x12", 500, 140);
			g.setFont(new Font("Monospaced",Font.BOLD,10));
			drawString(g,ocho.toString(),30,150);
			drawString(g,diez.toString(),230,150);
			drawString(g,doce.toString(),430,150);
		}			
		else{
			ControladorPuntajes ocho =  new ControladorPuntajes("8Computadora");
			ControladorPuntajes diez =  new ControladorPuntajes("10Computadora");
			ControladorPuntajes doce =  new ControladorPuntajes("12Computadora");
			g.setFont(new Font("Monospaced",Font.BOLD,35));
			g.drawString("Puntajes con Computadora", 100, 75);
			g.setFont(new Font("Monospaced",Font.BOLD,20));
			g.drawString("8x8", 110, 140);
			g.drawString("10x10", 300, 140);
			g.drawString("12x12", 500, 140);
			g.setFont(new Font("Monospaced",Font.BOLD,10));
			drawString(g,ocho.toString(),30,150);
			drawString(g,diez.toString(),230,150);
			drawString(g,doce.toString(),430,150);
		}
			
	}
	/*
	 * Metodo que sobreescribe la funcionalidad drawString para que reciba enters
	 */
    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
}
