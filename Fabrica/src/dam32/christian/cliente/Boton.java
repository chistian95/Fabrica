package dam32.christian.cliente;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import dam32.christian.Pintable;

public class Boton extends JComponent implements Pintable, MouseListener {
	private static final long serialVersionUID = 1L;
	
	private Pantalla pantalla;
	private String modo;
	private int x, y, x2, y2;
	
	public Boton(Pantalla pantalla, String modo) {
		this.pantalla = pantalla;
		this.modo = modo;
		this.pantalla.addMouseListener(this);
		x = pantalla.WIDTH / 2 - 100;
		switch(modo) {
		case "tipo":
			y = 350;
			break;
		case "color":
			y = 0;
			break;
		}		
		x2 = 200;
		y2 = 75;
		
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(x, y, x2, y2);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
