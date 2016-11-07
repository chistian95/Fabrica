package dam32.christian;

public class StockMadera extends Thread {
	public static final int TOPE = 100;
	
	private Fabrica fabrica;
	private int cantidad;
	
	public StockMadera(Fabrica fabrica) {
		this.fabrica = fabrica;
		cantidad = 0;
		start();
	}
	
	public void meterMadera(int c) {
		cantidad += c;
		cantidad = cantidad > TOPE ? TOPE : cantidad;
		
		System.out.println("(StockMadera) Meter madera! Stock: "+cantidad);
		synchronized(fabrica.getMolde()) {
			fabrica.getMolde().notify();
		}
	}
	
	public boolean sacarMadera() {
		if(cantidad > 0) {
			cantidad--;			
			System.out.println("(StockMadera) Sacar madera! Stock: "+cantidad);
			return true;
		}
		return false;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep((long) (Math.random()*5000 + 3000)); 
				int rnd = (int) (Math.random()*50 + 25);
				meterMadera(rnd);
			}
		} catch(InterruptedException e) {
			
		}	
	}
}
