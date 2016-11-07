package dam32.christian;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Crusher extends Thread implements Pintable {
	private Fabrica fabrica;
	private boolean subir;
	private int y;
	
	public Crusher(Fabrica fabrica) {
		this.fabrica = fabrica;
		subir = false;
		y = 0;
		start();
	}
	
	public void aplastarRoca() {
		int mineral = (int) (Math.random()*3 + 1);	
		int basura = (int) (Math.random()*3 + 1);
		
		System.out.println("(Crusher) Roca aplastada! Mineral: "+mineral+" Basura: "+basura);
		
		fabrica.getBasura().meterBasura(basura);
		fabrica.getHorno().añadirCantidad(mineral);
	}
	
	private void animar() {
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
				animar();   
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
}
