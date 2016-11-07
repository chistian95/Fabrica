package dam32.christian;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class StockPiedra extends Thread implements Pintable {
	public static final int TOPE = 100;
	
	private Fabrica fabrica;
	private int cantidad;
	
	public StockPiedra(Fabrica fabrica) {
		this.fabrica = fabrica;
		cantidad = 0;
		start();
	}
	
	public void meterPiedras(int c) {
		cantidad += c;
		cantidad = cantidad > TOPE ? TOPE : cantidad;
		
		System.out.println("(StockPiedra) Piedras metidas! Stock: "+cantidad);
	}
	
	public void sacarPiedra() {
		if(cantidad <= 0) {
			return;
		}
		cantidad--;
		
		System.out.println("(StockPiedra) Piedra sacada! Stock: "+cantidad);
		fabrica.getCrusher().aplastarRoca();
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep((long) (Math.random()*5000 + 3000)); 
				int rnd = (int) (Math.random()*50 + 25);
				meterPiedras(rnd);
			}
		} catch(InterruptedException e) {
			
		}		
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY.darker());
		g.fillRect(30, 50, 75, 100);
		
		int y = 150 - cantidad;
		g.setColor(Color.GRAY.brighter());
		g.fillRect(30, y, 75, cantidad);
		
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(5));
		g.drawRect(30, 50, 75, 100);
	}
}
