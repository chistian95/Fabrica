package dam32.christian.pantalla;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dam32.christian.fabrica.Fabrica;

public class Entidad implements Pintable {
	
	private Fabrica fabrica;
	private BufferedImage textura;
	private int x;
	private int y;
	private int ancho;
	private int alto;
	
	public Entidad(Fabrica fabrica, String ruta, int x, int y, int ancho, int alto) {
		this.fabrica = fabrica;
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		
		try {
			textura = ImageIO.read(new File(ruta));
		} catch(IOException e) {
			System.out.println("(Entidad) Error al cargar la textura: "+e.getMessage());
		}
		
		fabrica.getPantalla().añadirBloque(this);		
	}
	
	public void finalizar() {
		fabrica.getPantalla().borrarBloque(this);
	}

	@Override
	public void pintar(Graphics2D g) {
		g.drawImage(textura, x, y, ancho, alto, null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
}
