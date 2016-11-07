package dam32.christian;

public class Horno {
	public static final int TOPE = 100;
	
	private Fabrica fabrica;
	private boolean fundido;
	private int cantidad;
	
	public Horno(Fabrica fabrica) {
		this.fabrica = fabrica;
		fundido = false;
		cantidad = 0;
	}
	
	public boolean isFundido() {
		return fundido;
	}
	
	public void añadirCantidad(int c) {
		if(!fundido) {
			cantidad += c;
		}
		if(cantidad >= TOPE) {
			cantidad = TOPE;
			fundido = true;
			synchronized(fabrica.getMolde()) {
				fabrica.getMolde().notify();
			}			
		}
	}
	
	public boolean quitarCantidad(int c) {
		if(fundido) {
			cantidad -= c;
			
			if(cantidad <= 0) {
				cantidad = 0;
				fundido = false;
			}
			
			return true;
		}	
		return false;
	}
}
