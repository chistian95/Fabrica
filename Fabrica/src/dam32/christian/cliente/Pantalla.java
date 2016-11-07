package dam32.christian.cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Pantalla extends JFrame implements KeyListener {
	private static final long serialVersionUID = -4184553873079307165L;
	
	public final int WIDTH = 300;
	public final int HEIGHT = 500;
	
	private Cliente cliente;
	private BufferedImage bf;
	private Boton btTipo;
	private Boton btColor;
	private Boton aceptar;
	
	public Pantalla(Cliente cliente) {
		this.cliente = cliente;
		bf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        
        addKeyListener(this);
        
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		dispose();
        		System.exit(0);
        	}
        });
        
	    btTipo = new Boton(this, "tipo");
	    btColor = new Boton(this, "color");
	    aceptar = new Boton(this, "aceptar");
        
	    this.add(btTipo);
	    this.add(btColor);
	    this.add(aceptar);
        comenzar();
	}
	
	public void cambiarTipo() {
		cliente.setTipo(cliente.getTipo().next());
	}
	
	public void cambiarColor() {
		cliente.setColor(cliente.getColor().next());
	}
	
	public void paint(Graphics g) {
		Graphics2D bff = (Graphics2D) bf.getGraphics();
		
		bff.setColor(new Color(229, 182, 144));
		bff.fillRect(0, 0, WIDTH, HEIGHT);
		
		switch(cliente.getEstado()) {
		case CONECTANDO:
		case ERROR:
			pintarTexto(bff);
			break;
		case CONECTADO:
			pintarInterfaz(bff);
			break;
		case ESPERANDO:
			pintarInterfaz(bff);
			pintarTexto(bff);
			break;
		}
		
		g.drawImage(bf, 0, 0, null);
	}
	
	private void pintarTexto(Graphics2D g) {
		Font fuente = new Font("Times new roman", Font.BOLD, 20);
		FontMetrics metrics = g.getFontMetrics(fuente);
		
		String texto = cliente.getEstado().getNombre();
		int x = (WIDTH - metrics.stringWidth(texto))/2;
		int y = ((HEIGHT - metrics.getHeight())/2) + metrics.getAscent();
		g.setFont(fuente);
		g.setColor(Color.BLACK);
		g.drawString(texto, x, y);
	}
	
	private void pintarInterfaz(Graphics2D g) {
		Font fuente = new Font("Times new roman", Font.BOLD, 20);
		FontMetrics metrics = g.getFontMetrics(fuente);
		
		String texto = "Elige un producto y un color";
		int x = (WIDTH - metrics.stringWidth(texto))/2;
		int y = metrics.getAscent()+50;
		g.setFont(fuente);
		g.setColor(Color.WHITE);
		g.drawString(texto, x, y);
		
		x = WIDTH/2 - 35;
		g.drawImage(cliente.getTipo().getTextura(), x, 100, 70, 70, null);
		
		g.setColor(cliente.getColor().getColor());
		g.fillOval(x, 275, 70, 70);
		
		btTipo.pintar(g);
		btColor.pintar(g);
		aceptar.pintar(g);
	}
	
	public void comenzar() {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();   
			}
		};
		Timer timer = new Timer(20, listener);
		timer.setRepeats(true);
		timer.start();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cliente.salir();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public Cliente getCliente() {
		return cliente;
	}
}
