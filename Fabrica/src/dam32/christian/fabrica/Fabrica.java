package dam32.christian.fabrica;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dam32.christian.EstadoGeneral;
import dam32.christian.Producto;
import dam32.christian.pantalla.Pantalla;
import dam32.christian.pantalla.Pintable;
import dam32.christian.servidor.Server;

public class Fabrica extends Thread implements Pintable {	
	private StockPiedra stockPiedra;
	private StockMadera stockMadera;
	private Crusher crusher;
	private Basura basura;
	private Horno horno;
	private Molde molde;
	private Impresora pintar;
	private BufferedImage fondo;
	private Server server;
	private Pantalla pantalla;
	
	public Fabrica() {		
		stockPiedra = new StockPiedra(this);
		stockMadera = new StockMadera(this);
		crusher = new Crusher(this);
		basura = new Basura();
		horno = new Horno(this);
		molde = new Molde(this);
		pintar = new Impresora();		
		
		try {
			fondo = ImageIO.read(new File("src/res/fabrica.png"));
		} catch (IOException e) {
			System.out.println("(Fabrica) Error al cargar fondo: "+e.getMessage());
		}	
		
		server = new Server(this);
		pantalla = new Pantalla(this);
		
		start();
	}
	
	public synchronized Producto crearProducto(Producto producto) {
		Producto prod = molde.crearProducto(producto.getTipo());		
		pintar.pintarProducto(prod, producto.getColor());
		return prod;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				if(!horno.isFundido()) {
					while(!horno.getEstado().equals(EstadoGeneral.FUNCIONANDO)) {
						wait();
					}
					stockPiedra.sacarPiedra();
				}
				Thread.sleep((long) Math.random()*1000 + 200); 
			}
		} catch(InterruptedException e) {
			
		}		
	}
	
	@Override
	public void pintar(Graphics2D g) {
		if(pantalla != null) {
			g.drawImage(fondo, 0, 0, pantalla.WIDTH, pantalla.HEIGHT, null);
		}
		
		Font fuente = new Font("Times new roman", Font.BOLD, 18);
		FontMetrics metrics = g.getFontMetrics(fuente);
		String texto = "Clientes: "+server.getNumClientes();
		
		g.setFont(fuente);
		g.setColor(Color.WHITE);
		g.drawString(texto, 10, metrics.getAscent());
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
	
	public Impresora getImpresora() {
		return pintar;
	}
	
	public Pantalla getPantalla() {
		return pantalla;
	}
}
