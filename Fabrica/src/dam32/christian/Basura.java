package dam32.christian;

public class Basura {
	public static final int TOPE = 100;
	
	private int cantidad;
	
	public Basura() {
		cantidad = 0;
	}
	
	public void meterBasura(int c) {
		cantidad += c;
		if(cantidad >= TOPE) {
			cantidad = 0;
			System.out.println("(Basura) Basura llena! Vaciando...");
		} else {
			System.out.println("(Basura) Meter basura! Stock: "+cantidad);
		}
	}
}
