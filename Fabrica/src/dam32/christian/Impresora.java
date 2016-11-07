package dam32.christian;

public class Impresora {
	private Fabrica fabrica;
	
	public Impresora(Fabrica fabrica) {
		this.fabrica = fabrica;
	}
	
	public void pintarProducto(Producto producto, ColorProducto color) {
		producto.setColor(color);
		System.out.println("(Pintar) Producto pintado!");
	}
}
