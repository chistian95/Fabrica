package dam32.christian;

public class StockPiedra extends Thread {
	public static final int TOPE = 100;
	
	private Fabrica fabrica;
	private int cantidad;
	
	public StockPiedra(Fabrica fabrica) {
		this.fabrica = fabrica;
		cantidad = 0;
		start();
	}
	
	public void meterPiedras(int c) {
		cantidad += c;
		cantidad = cantidad > TOPE ? TOPE : cantidad;
		
		System.out.println("(StockPiedra) Piedras metidas! Stock: "+cantidad);
	}
	
	public void sacarPiedra() {
		if(cantidad <= 0) {
			return;
		}
		cantidad--;
		
		System.out.println("(StockPiedra) Piedra sacada! Stock: "+cantidad);
		fabrica.getCrusher().aplastarRoca();
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				int rnd = (int) (Math.random()*10 + 3);
				meterPiedras(rnd);
				Thread.sleep((long) (Math.random()*2000 + 500)); 
			}
		} catch(InterruptedException e) {
			
		}		
	}
}
