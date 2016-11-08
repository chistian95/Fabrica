package dam32.christian;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dam32.christian.pantalla.Pintable;

public class StockMadera extends Thread implements Pintable {
	public static final int TOPE = 60;
	
	private static final boolean VERBOSE = false;
	private BufferedImage textura;
	private EstadoGeneral estado;
	private int cantidad;
	
	public StockMadera() {
		estado = EstadoGeneral.FUNCIONANDO;
		cantidad = 0;
		
		try {
			textura = ImageIO.read(new File("src/res/log.png"));
		} catch(IOException e) {
			System.out.println("(StockMadera) Error al cargar la textura!");
		}
		
		start();
	}
	
	public synchronized void meterMadera(int c) {
		try {
			while(!estado.equals(EstadoGeneral.FUNCIONANDO)) {
				wait();
			}
			estado = EstadoGeneral.CARGANDO;
			for(int i=0; i<c; i++) {
				cantidad++;
				cantidad = cantidad > TOPE ? TOPE : cantidad;
				Thread.sleep(100);
			}
			
			estado = EstadoGeneral.FUNCIONANDO;
			if(VERBOSE)
				System.out.println("(StockMadera) Meter madera! Stock: "+cantidad);
			notifyAll();
		} catch(InterruptedException e) {
			
		}
	}
	
	public synchronized boolean sacarMadera() {
		try {
			while(!estado.equals(EstadoGeneral.FUNCIONANDO)) {
				wait();
			}
			if(cantidad > 0) {
				cantidad--;			
				if(VERBOSE)
					System.out.println("(StockMadera) Sacar madera! Stock: "+cantidad);
				return true;
			}
		} catch(InterruptedException e) {
			
		}
		return false;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep((long) (Math.random()*5000 + 3000)); 
				synchronized(this) {
					while(!estado.equals(EstadoGeneral.FUNCIONANDO)) {
						wait();
					}
					int rnd = (int) (Math.random()*10 + 5);
					meterMadera(rnd);
				}				
			}
		} catch(InterruptedException e) {
			
		}	
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY.darker());
		g.fillRect(550, 250, 75, 100);
		
		for(int i=1; i<=cantidad/10; i++) {
			int y = 350 - 15*i;
			for(int j=0; j<5; j++) {
				int x = 550 + 15*j;
				g.drawImage(textura, x, y, 15, 15, null);
			}
		}
		int y = 335 - 15 * (cantidad/10);
		for(int i=0; i<(cantidad%10)/2; i++) {
			int x = 550 + 15*i;
			g.drawImage(textura, x, y, 15, 15, null);
		}
		
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(5));
		g.drawRect(550, 250, 75, 100);	
		
		g.drawString(cantidad+"", 550, 50);
	}
}
