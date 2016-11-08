package dam32.christian;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class StockMadera extends Thread implements Pintable {
	public static final int TOPE = 100;
	
	private static final boolean VERBOSE = false;
	private Fabrica fabrica;
	private int cantidad;
	
	public StockMadera(Fabrica fabrica) {
		this.fabrica = fabrica;
		cantidad = 0;
		start();
	}
	
	public void meterMadera(int c) {
		cantidad += c;
		cantidad = cantidad > TOPE ? TOPE : cantidad;
		
		if(VERBOSE)
			System.out.println("(StockMadera) Meter madera! Stock: "+cantidad);
		synchronized(fabrica.getMolde()) {
			fabrica.getMolde().notify();
		}
	}
	
	public boolean sacarMadera() {
		if(cantidad >= 10) {
			cantidad -= 10;			
			if(VERBOSE)
				System.out.println("(StockMadera) Sacar madera! Stock: "+cantidad);
			return true;
		}
		return false;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep((long) (Math.random()*5000 + 3000)); 
				int rnd = (int) (Math.random()*10 + 5);
				meterMadera(rnd);
			}
		} catch(InterruptedException e) {
			
		}	
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY.darker());
		g.fillRect(550, 250, 75, 100);
		
		int y = 350 - cantidad;
		g.setColor(Color.ORANGE.darker().darker());
		g.fillRect(550, y, 75, cantidad);
		
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(5));
		g.drawRect(550, 250, 75, 100);		
	}
}
