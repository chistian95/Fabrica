package dam32.christian;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum TipoProducto {
	ESPADA(0, "espada"),
	PICO(1, "pico"),
	HACHA(2, "hacha"),
	PALA(3, "pala"),
	AZADA(4, "azada");
	
	private int valor;
	private String nombre;
	private BufferedImage textura;
	
	private TipoProducto(int valor, String nombre) {
		this.valor = valor;
		this.nombre = nombre;
		
		try {
			textura = ImageIO.read(new File("src/res/"+nombre+".png"));
		} catch(IOException e) {
			System.out.println("(TipoProducto) Error al cargar textura: "+e.getMessage());
		}
	}
	
	public TipoProducto next() {
		int val = this.valor + 1 >= TipoProducto.values().length ? 0 : this.valor + 1;
		return TipoProducto.values()[val];
	}
	
	@Override
	public String toString() {
		return "(TipoProducto) "+nombre;
	}
	
	public int getValor() {
		return valor;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public BufferedImage getTextura() {
		return textura;
	}
	
	public static TipoProducto getTipo(int valor) {
		for(int i=0; i<TipoProducto.values().length; i++) {
			if(TipoProducto.values()[i].getValor() == valor) {
				return TipoProducto.values()[i];
			}
		}
		return null;
	}
}
