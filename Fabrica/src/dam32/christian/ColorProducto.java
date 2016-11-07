package dam32.christian;

public enum ColorProducto {
	ROJO(0, "Rojo"),
	AZUL(1, "Azul"),
	VERDE(2, "Verde"),
	BLANCO(3, "Blanco");
	
	private int valor;
	private String nombre;
	
	private ColorProducto(int valor, String nombre) {
		this.valor = valor;
		this.nombre = nombre;
	}
	
	public int getValor() {
		return valor;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return "(ColorProducto) "+nombre;
	}
}
