package dam32.christian;

public class Fabrica extends Thread {
	private StockPiedra stockPiedra;
	private StockMadera stockMadera;
	private Crusher crusher;
	private Basura basura;
	private Horno horno;
	private Molde molde;
	private Pintar pintar;
	
	public Fabrica() {
		stockPiedra = new StockPiedra(this);
		stockMadera = new StockMadera(this);
		crusher = new Crusher(this);
		basura = new Basura();
		horno = new Horno(this);
		molde = new Molde(this);
		pintar = new Pintar(this);
		start();
		
		new Pantalla(this);
	}
	
	public static void main(String[] args) {
		new Fabrica();
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				if(!horno.isFundido()) {
					stockPiedra.sacarPiedra();
				}
				Thread.sleep((long) Math.random()*1000 + 200); 
			}
		} catch(InterruptedException e) {
			
		}		
	}
	
	public StockPiedra getStockPiedra() {
		return stockPiedra;
	}
	
	public StockMadera getStockMadera() {
		return stockMadera;
	}
	
	public Crusher getCrusher() {
		return crusher;
	}
	
	public Basura getBasura() {
		return basura;
	}
	
	public Horno getHorno() {
		return horno;
	}
	
	public Molde getMolde() {
		return molde;
	}
	
	public Pintar getPintar() {
		return pintar;
	}
}
