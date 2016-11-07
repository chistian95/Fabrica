package dam32.christian.cliente;

public enum EstadoCliente {
	CONECTANDO(0, "Conectando..."),
	CONECTADO(1, "Conectado"),
	ESPERANDO(2, "Esperando..."),
	ERROR(3, "Error!");
	
	private int valor;
	private String nombre;
	
	private EstadoCliente(int valor, String nombre) {
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
	public String toString(){
		return("Valor: " +valor+ ", Nombre: " +nombre);
	}
}
