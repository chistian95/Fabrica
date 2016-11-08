package dam32.christian;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import dam32.christian.pantalla.Pintable;

public class Horno implements Pintable {
	public static final int TOPE = 100;
	
	private Fabrica fabrica;
	private boolean fundido;
	private int cantidad;
	
	public Horno(Fabrica fabrica) {
		this.fabrica = fabrica;
		fundido = false;
		cantidad = 0;
	}
	
	public boolean isFundido() {
		return fundido;
	}
	
	public void añadirCantidad(int c) {
		if(!fundido) {
			cantidad += c;
		}
		if(cantidad >= TOPE) {
			cantidad = TOPE;
			fundido = true;
			synchronized(fabrica.getMolde()) {
				fabrica.getMolde().notify();
			}			
		}
	}
	
	public boolean quitarCantidad(int c) {
		if(fundido) {
			cantidad -= c;
			
			if(cantidad <= 0) {
				cantidad = 0;
				fundido = false;
			}
			
			return true;
		}	
		return false;
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY.darker());
		g.fillRect(300, 175, 100, 125);
		
		if(fundido) {
			g.setColor(Color.ORANGE);
		} else {
			g.setColor(Color.GRAY.brighter());
		}
		int y = 300 - cantidad;
		g.fillRect(300, y, 100, cantidad);
		
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(5));
		g.drawRect(300, 175, 100, 125);
	}
}
