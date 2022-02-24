import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*!
 * @brief Esta clase muestra las opciones de los 3
 * tableros para elegir en cual jugar
 */
public class MenuTableros extends JFrame{
	private final int ANCHO = 660;
	private final int LARGO = 600;		
	private JButton ocho, diez,doce,volver,salir;
	private boolean computadora;
	
	
	/*!
	 * @brief Construye una instancia de la Interfaz
	 * @param Recibe booleano que indica si se juega o no contra la computadora. 
	 * Ademas pone los botones de los tableros, salir y volver al menu.
	 */
	public MenuTableros(boolean c) {
		super("Othello");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,1));
		this.setLayout(null);
		setSize(ANCHO,LARGO);
		setVisible(true);
		setResizable(false);
		ocho = new JButton("8x8");
		panel.add(ocho);
		diez = new JButton("10x10");
		panel.add(diez);
		doce = new JButton("12x12");
		panel.add(doce);
		volver = new JButton("Atras");
		panel.add(volver);
		salir = new JButton("Salir");
		panel.add(salir);		
		this.add(panel);
		panel.setBounds(270,300,100,125);
		ManejadorBoton manejador = new ManejadorBoton();
		ocho.addActionListener(manejador);
		diez.addActionListener(manejador);
		doce.addActionListener(manejador);
		volver.addActionListener(manejador);
		salir.addActionListener(manejador);
		computadora  = c;
	}
	
	
	
	
	/*
	 * Clase privada que maneja los eventos de los botones
	 */
	
	private class ManejadorBoton implements ActionListener{
		public void actionPerformed(ActionEvent evento) {
			if("8x8".equals(evento.getActionCommand())) {			
				if(computadora) {
					Tablero tablero = new Tablero(8);
					new InterfazGrafica(tablero,true);
				}
				else{
					Tablero tablero = new Tablero(8);
					new InterfazGrafica(tablero,false);
				}
				setVisible(false);
				dispose();
			}
			else if("10x10".equals(evento.getActionCommand())) {
				if(computadora) {
					Tablero tablero = new Tablero(10);
					new InterfazGrafica(tablero,true);
				}
				else{
					Tablero tablero = new Tablero(10);
					new InterfazGrafica(tablero,false);
				}
				setVisible(false);
				dispose();
			}
			else if("12x12".equals(evento.getActionCommand())) {
				if(computadora) {
					Tablero tablero = new Tablero(12);
					new InterfazGrafica(tablero,true);
				}
				else{
					Tablero tablero = new Tablero(12);
					new InterfazGrafica(tablero,false);
				}
				setVisible(false);
				dispose();
			}
			else if("Atras".equals(evento.getActionCommand())) {
				new MenuPrincipal();
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
	 * Este metodo se encarga de pintar un titulo para el menu
	 */
	
	public void paint (Graphics g) {
		super.paint(g);
		g.setFont(new Font("Monospaced",Font.BOLD,100));
		g.drawString("OTHELLO", 125, 200);
		if(computadora) {
			g.setFont(new Font("Monospaced",Font.BOLD,25));
			g.drawString("1 Jugador vs Computadora", 150, 300);
		}
		else{
			g.setFont(new Font("Monospaced",Font.BOLD,25));
			g.drawString("1 Jugador vs 1 Jugador", 150, 300);
		}
			
	}
}
