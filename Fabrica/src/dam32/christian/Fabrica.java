package dam32.christian;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fabrica extends Thread implements Pintable {
	private StockPiedra stockPiedra;
	private StockMadera stockMadera;
	private Crusher crusher;
	private Basura basura;
	private Horno horno;
	private Molde molde;
	private Impresora pintar;
	private BufferedImage fondo;
	private Pantalla pantalla;
	
	public Fabrica() {
		stockPiedra = new StockPiedra(this);
		stockMadera = new StockMadera(this);
		crusher = new Crusher(this);
		basura = new Basura();
		horno = new Horno(this);
		molde = new Molde(this);
		pintar = new Impresora(this);
		start();
		
		try {
			fondo = ImageIO.read(new File("src/res/fabrica.png"));
		} catch (IOException e) {
			System.out.println("(Fabrica) Error al cargar fondo: "+e.getMessage());
		}
		pantalla = new Pantalla(this);
	}
	
	public static void main(String[] args) {
		new Fabrica();
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				if(!horno.isFundido()) {
					stockPiedra.sacarPiedra();
				}
				Thread.sleep((long) Math.random()*1000 + 200); 
			}
		} catch(InterruptedException e) {
			
		}		
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.drawImage(fondo, 0, 0, pantalla.WIDTH, pantalla.HEIGHT, null);
	}
	
	public StockPiedra getStockPiedra() {
		return stockPiedra;
	}
	
	public StockMadera getStockMadera() {
		return stockMadera;
	}
	
	public Crusher getCrusher() {
		return crusher;
	}
	
	public Basura getBasura() {
		return basura;
	}
	
	public Horno getHorno() {
		return horno;
	}
	
	public Molde getMolde() {
		return molde;
	}
	
	public Impresora getPintar() {
		return pintar;
	}
}
