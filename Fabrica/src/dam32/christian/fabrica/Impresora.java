package dam32.christian.fabrica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import dam32.christian.ColorProducto;
import dam32.christian.Producto;
import dam32.christian.pantalla.Entidad;
import dam32.christian.pantalla.Pintable;

public class Impresora implements Pintable {
	private static final boolean VERBOSE = false;
	private static final int VELOCIDAD = 10;
	private Fabrica fabrica;
	private ColorProducto color;
	private Producto producto;
	
	public Impresora(Fabrica fabrica) {
		this.fabrica = fabrica;
	}
	
	public void pintarProducto(Producto producto, ColorProducto color) {
		this.producto = producto;
		this.color = color;
		this.producto.setColor(color);
		moverProducto();
		if(VERBOSE)
			System.out.println("(Pintar) Producto pintado!");
	}
	
	private void moverProducto() {
		String ruta = "src/res/"+producto.getTipo().getNombre()+".png";
		Entidad bloque = new Entidad(fabrica, ruta, 460, 410, 40, 40);
		try {
			while(bloque.getX() < fabrica.getPantalla().WIDTH) {
				bloque.setX(bloque.getX() + 1);
				Thread.sleep(VELOCIDAD);
			}
		} catch(InterruptedException e) {
			
		}
		bloque.finalizar();
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(425, 400, 100, 75);
		
		if(color != null) {
			g.setColor(color.getColor());
			g.fillOval(455, 417, 20, 20);			
		}	
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawOval(455, 417, 20, 20);
		
		g.setColor(Color.GRAY.darker());
		g.drawRect(425, 400, 100, 75);
	}
	
	public void setColor(ColorProducto color) {
		this.color = color;
	}
}
