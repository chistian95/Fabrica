package dam32.christian;

public class Molde extends Thread {
	private Fabrica fabrica;	
	private boolean mineral;
	private boolean madera;
	
	public Molde(Fabrica fabrica) {
		this.fabrica = fabrica;
		mineral = false;
		madera = false;
		start();
	}
	
	public boolean aceptaMadera() {
		return !madera;
	}
	
	public synchronized void crearProducto(TipoProducto tipoProducto, ColorProducto color) {
		try {
			System.out.println("(Molde) Comenzando a crear producto...");
			while(!mineral) {
				System.out.println("(Molde) Obteniendo mineral del horno...");
				mineral = fabrica.getHorno().quitarCantidad(10);
				if(!mineral) {
					wait();
				}
			}
			while(!madera) {
				System.out.println("(Molde) Obteniendo madera del stock...");
				madera = fabrica.getStockMadera().sacarMadera();
				if(!madera) {
					wait();
				}
			}
			mineral = false;
			madera = false;
			Producto producto = new Producto(tipoProducto, null);
			System.out.println("(Molde) Producto creado!");
			fabrica.getPintar().pintarProducto(producto, color);
		} catch(InterruptedException e) {
			System.out.println("(Molde) Error en el molde: "+e.getMessage());
		}		
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				int rnd = (int) (Math.random()*TipoProducto.values().length);
				TipoProducto tipo = TipoProducto.values()[rnd];
				rnd = (int) (Math.random()*ColorProducto.values().length);
				ColorProducto color = ColorProducto.values()[rnd];
				
				crearProducto(tipo, color);
				Thread.sleep((long) Math.random()*10000 + 5000); 
			}
		} catch(InterruptedException e) {
			
		}
	}
}
