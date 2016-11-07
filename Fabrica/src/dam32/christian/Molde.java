package dam32.christian;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Molde implements Pintable {
	private Fabrica fabrica;	
	private boolean mineral;
	private boolean madera;
	private TipoProducto tipoProducto;
	
	public Molde(Fabrica fabrica) {
		this.fabrica = fabrica;
		mineral = false;
		madera = false;
	}
	
	public boolean aceptaMadera() {
		return !madera;
	}
	
	public synchronized Producto crearProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
		Producto prod = null;
		try {
			System.out.println("(Molde) Comenzando a crear producto...");
			while(!mineral) {
				System.out.println("(Molde) Obteniendo mineral del horno...");
				mineral = fabrica.getHorno().quitarCantidad(10);
				if(!mineral) {
					wait();
				}
			}
			while(!madera) {
				System.out.println("(Molde) Obteniendo madera del stock...");
				madera = fabrica.getStockMadera().sacarMadera();
				if(!madera) {
					wait();
				}
			}
			mineral = false;
			madera = false;
			prod = new Producto(tipoProducto, null);
			System.out.println("(Molde) Producto creado!");
		} catch(InterruptedException e) {
			System.out.println("(Molde) Error en el molde: "+e.getMessage());
		}	
		return prod;
	}
	
	@Override
	public void pintar(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(325, 325, 50, 50);
		
		if(tipoProducto != null) {
			g.drawImage(tipoProducto.getTextura(), 330, 330, 40, 40, null);
		}
		
		g.setColor(Color.GRAY.darker());
		g.setStroke(new BasicStroke(5));
		g.drawRect(325, 325, 50, 50);
	}
}
