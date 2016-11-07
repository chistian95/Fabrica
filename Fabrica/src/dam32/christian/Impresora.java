package dam32.christian;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Impresora implements Pintable {
	private ColorProducto color;
	
	public void pintarProducto(Producto producto, ColorProducto color) {
		this.color = color;
		producto.setColor(color);
		System.out.println("(Pintar) Producto pintado!");
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
}
