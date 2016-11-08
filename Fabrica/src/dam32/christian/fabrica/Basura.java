package dam32.christian.fabrica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dam32.christian.pantalla.Pintable;

public class Basura implements Pintable {
	public static final int TOPE = 50;
	
	private static final boolean VERBOSE = false;
	private BufferedImage textura;
	private int cantidad;
	
	public Basura() {
		cantidad = 0;
		
		try {
			textura = ImageIO.read(new File("src/res/cobblestone.png"));
		} catch(IOException e) {
			System.out.println("(Basura) Error al cargar la textura: "+e.getMessage());
		}
	}
	
	public void meterBasura(int c) {
		cantidad += c;
		if(cantidad >= TOPE) {
			if(VERBOSE)
				System.out.println("(Basura) Basura llena! Vaciando...");
			cantidad = 0;
		} else {
			if(VERBOSE)
				System.out.println("(Basura) Meter basura! Stock: "+cantidad);
		}
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY.darker());
		g.fillRect(150, 325, 50, 50);
		
		for(int i=1; i<=cantidad/10; i++) {
			int y = 375 - 10*i;
			for(int j=0; j<5; j++) {
				int x = 150 + 10*j;
				g.drawImage(textura, x, y, 10, 10, null);
			}
		}
		int y = 365 - 10 * (cantidad/10);
		for(int i=0; i<(cantidad%10)/2; i++) {
			int x = 150 + 10*i;
			g.drawImage(textura, x, y, 10, 10, null);
		}
		
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(5));
		g.drawRect(150, 325, 50, 50);
	}
}
