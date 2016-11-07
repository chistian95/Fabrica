package dam32.christian;

public enum TipoProducto {
	ESPADA(0, "Espada"),
	PICO(1, "Pico"),
	HACHA(2, "Hacha"),
	PALA(3, "Pala"),
	AZADA(4, "Azada");
	
	private int valor;
	private String nombre;
	
	private TipoProducto(int valor, String nombre) {
		this.valor = valor;
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return "(TipoProducto) "+nombre;
	}
	
	public int getValor() {
		return valor;
	}
	
	public String getNombre() {
		return nombre;
	}
}
