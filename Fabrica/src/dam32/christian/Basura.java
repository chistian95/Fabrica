package dam32.christian;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import dam32.christian.pantalla.Pintable;

public class Basura implements Pintable {
	public static final int TOPE = 50;
	
	private static final boolean VERBOSE = false;
	private int cantidad;
	
	public Basura() {
		cantidad = 0;
	}
	
	public void meterBasura(int c) {
		cantidad += c;
		if(cantidad >= TOPE) {
			cantidad = 0;
			if(VERBOSE)
				System.out.println("(Basura) Basura llena! Vaciando...");
		} else {
			if(VERBOSE)
				System.out.println("(Basura) Meter basura! Stock: "+cantidad);
		}
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY.darker());
		g.fillRect(150, 325, 50, 50);
		
		int y = 375 - cantidad;
		g.setColor(Color.GRAY.brighter());
		g.fillRect(150, y, 50, cantidad);
		
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(5));
		g.drawRect(150, 325, 50, 50);
	}
}
