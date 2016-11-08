package dam32.christian.fabrica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import dam32.christian.EstadoGeneral;
import dam32.christian.pantalla.Entidad;
import dam32.christian.pantalla.Pintable;

public class Crusher extends Thread implements Pintable {
	private static final boolean VERBOSE = false;
	private static final int VELOCIDAD = 5;
	private Fabrica fabrica;
	private EstadoGeneral estado;
	private boolean subir;
	private int y;
	
	public Crusher(Fabrica fabrica) {
		this.fabrica = fabrica;
		estado = EstadoGeneral.FUNCIONANDO;
		subir = false;
		y = 0;
		start();
	}
	
	public synchronized void aplastarRoca() {
		estado = EstadoGeneral.ESPERANDO;
		final int mineral = (int) (Math.random()*3 + 1);	
		final int basura = (int) (Math.random()*3 + 1);
		
		if(VERBOSE)
			System.out.println("(Crusher) Roca aplastada! Mineral: "+mineral+" Basura: "+basura);
		
		Thread threadBasura = new Thread() {
			public void run() {
				moverBasura(basura);
			}
		};
		Thread threadMineral = new Thread() {
			public void run() {
				moverMineral(mineral);
			}
		};
		threadBasura.start();
		threadMineral.start();
		
		try {
			threadBasura.join();
			threadMineral.join();
		} catch(InterruptedException e) {
			
		}		
		estado = EstadoGeneral.FUNCIONANDO;
		notifyAll();
	}
	
	private void moverBasura(int c) {
		List<Entidad> bloques = new ArrayList<Entidad>();
		for(int i=0; i<c; i++) {
			Entidad bloque = new Entidad(fabrica, "src/res/cobblestone.png", 193, 160+i*17, 15, 15);
			bloques.add(bloque);
		}
		while(bloques.size() > 0) {
			try {
				for(int i=0; i<bloques.size(); i++) {					
					Entidad bloque = bloques.get(i);
					bloque.setY(bloque.getY() + 1);
					if(bloque.getY() > 300) {
						bloque.finalizar();
						bloques.remove(bloque);
						fabrica.getBasura().meterBasura(1);
					}	
				}
				Thread.sleep(VELOCIDAD);
			} catch(InterruptedException e) {
				
			}
		}
	}
	
	private void moverMineral(int c) {
		List<Entidad> bloques = new ArrayList<Entidad>();
		for(int i=0; i<c; i++) {
			Entidad bloque = new Entidad(fabrica, "src/res/iron_ingot.png", 180+i*17, 170, 15, 15);
			bloques.add(bloque);
		}
		while(bloques.size() > 0) {
			try {
				for(int i=0; i<bloques.size(); i++) {
					Entidad bloque = bloques.get(i);
					bloque.setX(bloque.getX() + 1);
					if(bloque.getX() > 350) {
						bloque.finalizar();
						bloques.remove(bloque);
						fabrica.getHorno().añadirCantidad(1);
					}
				}
				Thread.sleep(VELOCIDAD);
			} catch(InterruptedException e) {
				
			}
		}
	}
	
	private void animarCrusher() {
		if(!subir) {
			y++;
			if(y > 50) {
				subir = true;
			}
		} else {
			y--;
			if(y < 0) {
				subir = false;
			}
		}		
	}
	
	@Override
	public void run() {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animarCrusher();   
			}
		};
		Timer timer = new Timer(5, listener);
		timer.setRepeats(true);
		timer.start();
	}
	
	@Override
	public void pintar(Graphics2D g) {		
		g.setColor(Color.GRAY.brighter());
		g.fillRect(150, 100+y, 100, 30);
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawRect(150, 100+y, 100, 30);
		
		g.setColor(Color.GRAY.brighter());
		g.fillRect(175, 90+y, 50, 10);
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawRect(175, 90+y, 50, 10);
		
		g.setColor(Color.GRAY.brighter());
		g.fillRect(190, y-50, 20, 140);
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawRect(190, y-50, 20, 140);
		
		g.setColor(Color.GRAY.darker().darker());
		g.fillRect(180, 0, 40, 75);
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawRect(180, 0, 40, 75);
	}
	
	public EstadoGeneral getEstado() {
		return estado;
	}
}
