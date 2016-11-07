package dam32.christian;

public class Crusher {
	private Fabrica fabrica;
	
	public Crusher(Fabrica fabrica) {
		this.fabrica = fabrica;
	}
	
	public void aplastarRoca() {
		int mineral = (int) (Math.random()*3 + 1);	
		int basura = (int) (Math.random()*3 + 1);
		
		System.out.println("(Crusher) Roca aplastada! Mineral: "+mineral+" Basura: "+basura);
		
		fabrica.getBasura().meterBasura(basura);
		fabrica.getHorno().añadirCantidad(mineral);
	}
}
