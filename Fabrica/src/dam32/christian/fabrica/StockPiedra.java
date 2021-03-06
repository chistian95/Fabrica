package dam32.christian.fabrica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dam32.christian.EstadoGeneral;
import dam32.christian.pantalla.Entidad;
import dam32.christian.pantalla.Pintable;

public class StockPiedra extends Thread implements Pintable {
	public static final int TOPE = 60;
	
	private static final boolean VERBOSE = false;
	private static final int VELOCIDAD = 5;
	private BufferedImage textura;
	private EstadoGeneral estado;
	private Fabrica fabrica;
	private int cantidad;
	
	public StockPiedra(Fabrica fabrica) {
		this.fabrica = fabrica;
		estado = EstadoGeneral.FUNCIONANDO;
		cantidad = 0;
		
		try {
			textura = ImageIO.read(new File("src/res/iron_ore.png"));
		} catch(IOException e) {
			System.out.println("(StockPiedra) Error al cargar textura!");
		}
		
		start();
	}
	
	public synchronized void meterPiedras(int c) {
		try {
			while(!estado.equals(EstadoGeneral.FUNCIONANDO)) {
				wait();
			}
			estado = EstadoGeneral.CARGANDO;
			if(VERBOSE)
				System.out.println("(StockPiedra) Comenzando a meter piedras...");
			for(int i=0; i<c; i++) {
				cantidad++;
				cantidad = cantidad > TOPE ? TOPE : cantidad;
				Thread.sleep(100);
			}
			estado = EstadoGeneral.FUNCIONANDO;
			notifyAll();
			if(VERBOSE)
				System.out.println("(StockPiedra) Piedras metidas! Stock: "+cantidad);
		} catch (InterruptedException e) {
			
		}
	}
	
	public synchronized void sacarPiedra() {
		try {
			while(!estado.equals(EstadoGeneral.FUNCIONANDO)) {
				if(VERBOSE)
					System.out.println("(StockPiedra) No puedo sacar piedras. A esperar!");
				wait();
			}
			while(!fabrica.getCrusher().getEstado().equals(EstadoGeneral.FUNCIONANDO)) {
				if(VERBOSE)
					System.out.println("(StockPiedra) No puedo sacar piedras. A esperar!");
				wait();
			}
			if(cantidad <= 0) {
				return;
			}
			
			estado = EstadoGeneral.VACIANDO;
			cantidad--;
			moverPiedra();
			estado = EstadoGeneral.FUNCIONANDO;
			
			if(VERBOSE)
				System.out.println("(StockPiedra) Piedra sacada! Stock: "+cantidad);
			fabrica.getCrusher().aplastarRoca();
		} catch (InterruptedException e) {
			
		}
	}
	
	private void moverPiedra() {
		Entidad bloque = new Entidad(fabrica, "src/res/iron_ore.png", 105, 165, 15, 15);
		try {
			while(bloque.getX() < 193) {
				bloque.setX(bloque.getX() + 1);
				Thread.sleep(VELOCIDAD);
			}
		} catch(InterruptedException e) {
			
		}
		bloque.finalizar();
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				synchronized(this) {
					if(VERBOSE)
						System.out.println("(StockPiedra) Intentando meter piedras...");
					while(!estado.equals(EstadoGeneral.FUNCIONANDO)) {
						if(VERBOSE)
							System.out.println("(StockPiedra) No se puede meter piedras. A esperar!");
						wait();
					}
					if(VERBOSE)
						System.out.println("(StockPiedra) Ya puedo meter piedras!");
					int rnd = (int) (Math.random()*10 + 10);
					meterPiedras(rnd);
				}				
				Thread.sleep((long) (Math.random()*5000 + 3000)); 
			}
		} catch(InterruptedException e) {
			
		}		
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY.darker());
		g.fillRect(30, 50, 75, 100);
		
		for(int i=1; i<=cantidad/10; i++) {
			int y = 150 - 15*i;
			for(int j=0; j<5; j++) {
				int x = 30 + 15*j;
				g.drawImage(textura, x, y, 15, 15, null);
			}
		}
		int y = 135 - 15 * (cantidad/10);
		for(int i=0; i<(cantidad%10)/2; i++) {
			int x = 30 + 15*i;
			g.drawImage(textura, x, y, 15, 15, null);
		}
		
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(5));
		g.drawRect(30, 50, 75, 100);
	}
}
