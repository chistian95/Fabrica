package dam32.christian.fabrica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dam32.christian.EstadoGeneral;
import dam32.christian.pantalla.Pintable;

public class Horno implements Pintable {
	public static final int TOPE = 50;
	
	private BufferedImage textura;
	private EstadoGeneral estado;
	private int alfa;
	private boolean fundido;
	private int cantidad;
	
	public Horno() {
		estado = EstadoGeneral.FUNCIONANDO;
		alfa = 0;
		fundido = false;
		cantidad = 0;
		
		try {
			textura = ImageIO.read(new File("src/res/iron_ingot.png"));
		} catch(IOException e) {
			System.out.println("(Horno) Error al cargar la textura: "+e.getMessage());
		}
	}
	
	public boolean isFundido() {
		return fundido;
	}
	
	public synchronized void añadirCantidad(int c) {
		if(cantidad >= TOPE) {
			return;
		}
		if(!fundido) {
			cantidad += c;
		}
		if(cantidad >= TOPE) {
			cantidad = TOPE;
			estado = EstadoGeneral.ESPERANDO;
			animacionFundido();			
			estado = EstadoGeneral.FUNCIONANDO;
			fundido = true;
			notifyAll();		
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
	
	private void animacionFundido() {
		alfa = 0;
		try {
			while(alfa < 255) {
				alfa++;
				Thread.sleep(20);
			}
		} catch(InterruptedException e) {
			
		}		
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY.darker());
		g.fillRect(300, 175, 100, 125);		
		
		for(int i=1; i<=cantidad/10; i++) {
			int y = 300 - 20*i;
			for(int j=0; j<5; j++) {
				int x = 300 + 20*j;
				g.drawImage(textura, x, y, 20, 20, null);
			}
		}		
		for(int i=0; i<(cantidad%10)/2; i++) {
			int y = 280 - 20 * (cantidad/10);
			int x = 300 + 20*i;
			g.drawImage(textura, x, y, 20, 20, null);
		}
		
		int y = 300 - cantidad*2;
		g.setColor(new Color(255, 136, 0, alfa));
		g.fillRect(300, y, 100, cantidad*2);
		
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(5));
		g.drawRect(300, 175, 100, 125);
	}
	
	public EstadoGeneral getEstado() {
		return estado;
	}
}
