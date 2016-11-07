package dam32.christian.cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import dam32.christian.Pintable;
import dam32.christian.Producto;

public class Boton extends JComponent implements Pintable, MouseListener {
	private static final long serialVersionUID = 1L;
	
	private Pantalla pantalla;
	private String modo;
	private int x, y, x2, y2;
	
	public Boton(Pantalla pantalla, String modo) {
		this.pantalla = pantalla;
		this.modo = modo;
		this.pantalla.addMouseListener(this);
		x = pantalla.WIDTH / 2 - 50;
		switch(modo) {
		case "tipo":
			y = 200;
			break;
		case "color":
			y = 375;
			break;
		case "aceptar":
			y = 450;
			break;
		}		
		x2 = 100;
		y2 = 25;
		
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(x, y, x2, y2);
		
		Font fuente = new Font("Times new Roman", Font.BOLD, 13);
		FontMetrics metrics = g.getFontMetrics(fuente);
		String texto = "SIGUIENTE"; 
		if(modo.equals("aceptar")) {
			texto = "ACEPTAR";
		}
				
		int fuenteX = x + (x2 - metrics.stringWidth(texto))/2;
		int fuenteY = y + ((y2 - metrics.getHeight())/2) + metrics.getAscent();
		g.setColor(Color.WHITE);
		g.setFont(fuente);
		g.drawString(texto, fuenteX, fuenteY);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!pantalla.getCliente().getEstado().equals(EstadoCliente.CONECTADO)) {
			return;
		}
		Point punto = e.getPoint();
		Rectangle rec = new Rectangle(x, y, x2, y2);
		if(!rec.contains(punto)) {
			return;
		}
		switch(modo) {
		case "tipo":
			pantalla.cambiarTipo();
			break;
		case "color":
			pantalla.cambiarColor();
			break;
		case "aceptar":			
			pantalla.getCliente().setEstado(EstadoCliente.ESPERANDO);
			
			Producto prod = new Producto(pantalla.getCliente().getTipo(), pantalla.getCliente().getColor());
			pantalla.getCliente().pedirProducto(prod);
			
			pantalla.getCliente().setEstado(EstadoCliente.CONECTADO);
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
