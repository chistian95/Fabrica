package dam32.christian;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StockPiedra extends Thread implements Pintable {
	public static final int TOPE = 60;
	
	private static final boolean VERBOSE = false;
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
			if(cantidad <= 0) {
				return;
			}
			cantidad--;
			
			if(VERBOSE)
				System.out.println("(StockPiedra) Piedra sacada! Stock: "+cantidad);
			fabrica.getCrusher().aplastarRoca();
		} catch (InterruptedException e) {
			
		}
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
		
		g.drawString(cantidad+"", 50, 50);
	}
	
	public EstadoGeneral getEstado() {
		return estado;
	}
}
