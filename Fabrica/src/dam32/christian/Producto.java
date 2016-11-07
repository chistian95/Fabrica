package dam32.christian;

public class Producto {
	private TipoProducto tipo;
	private ColorProducto color;
	
	public Producto(TipoProducto tipo, ColorProducto color) {
		this.tipo = tipo;
		this.color = color;
	}
	
	public TipoProducto getTipo() {
		return tipo;
	}
	
	public void setColor(ColorProducto color) {
		this.color = color;
	}
	
	public ColorProducto getColor() {
		return color;
	}
}
