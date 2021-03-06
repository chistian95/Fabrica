package dam32.christian.pantalla;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.Timer;

import dam32.christian.fabrica.Fabrica;

public class Pantalla extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;	
	
	public final int WIDTH = 700;
	public final int HEIGHT = 500;
	
	private Fabrica fabrica;
	private BufferedImage bf;
	private List<Entidad> bloques;
	
	public Pantalla(Fabrica fabrica) {
		this.fabrica = fabrica;
		bloques = new ArrayList<Entidad>();
		bf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		setUndecorated(true);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        
        addKeyListener(this);
        
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		dispose();
        		System.exit(0);
        	}
        });
        
        comenzar();
	}
	
	public void paint(Graphics g) {
		Graphics2D bff = (Graphics2D) bf.getGraphics();
		
		bff.setColor(Color.WHITE);
		bff.fillRect(0, 0, WIDTH, HEIGHT);
		
		fabrica.pintar(bff);
		
		for(int i=0; i<bloques.size(); i++) {
			Entidad bloque = bloques.get(i);
			bloque.pintar(bff);
		}
		
		fabrica.getStockPiedra().pintar(bff);
		fabrica.getCrusher().pintar(bff);
		fabrica.getHorno().pintar(bff);
		fabrica.getBasura().pintar(bff);
		fabrica.getStockMadera().pintar(bff);
		fabrica.getMolde().pintar(bff);
		fabrica.getImpresora().pintar(bff);	
		
		g.drawImage(bf, 0, 0, null);
	}
	
	public void comenzar() {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();   
			}
		};
		Timer timer = new Timer(20, listener);
		timer.setRepeats(true);
		timer.start();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public List<Entidad> getBloques() {
		return bloques;
	}
	
	public void añadirBloque(Entidad bloque) {
		bloques.add(bloque);
	}
	
	public void borrarBloque(Entidad bloque) {
		bloques.remove(bloque);
	}
}
