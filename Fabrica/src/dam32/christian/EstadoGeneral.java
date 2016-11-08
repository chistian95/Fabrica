package dam32.christian;

public enum EstadoGeneral {
	ESPERANDO(0, "Esperando"),
	FUNCIONANDO(1, "Funcionando"),
	CARGANDO(2, "Cargando"),
	VACIANDO(3, "Vaciando");
	
	private int valor;
	private String nombre;
	
	private EstadoGeneral(int valor, String nombre) {
		this.valor = valor;
		this.nombre = nombre;
	}
	
	public int getValor() {
		return valor;
	}
	
	public String getNombre() {
		return nombre;
	}
}
