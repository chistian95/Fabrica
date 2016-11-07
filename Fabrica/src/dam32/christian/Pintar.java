package dam32.christian;

public class Pintar {
	private Fabrica fabrica;
	
	public Pintar(Fabrica fabrica) {
		this.fabrica = fabrica;
	}
	
	public void pintarProducto(Producto producto, ColorProducto color) {
		producto.setColor(color);
		System.out.println("(Pintar) Producto pintado!");
	}
}
