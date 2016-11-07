package dam32.christian;

import java.awt.Color;

public enum ColorProducto {
	ROJO(0, "Rojo", Color.RED),
	AZUL(1, "Azul", Color.BLUE),
	VERDE(2, "Verde", Color.GREEN),
	BLANCO(3, "Blanco", Color.WHITE);
	
	private int valor;
	private String nombre;
	private Color color;
	
	private ColorProducto(int valor, String nombre, Color color) {
		this.valor = valor;
		this.nombre = nombre;
		this.color = color;
	}
	
	public ColorProducto next() {
		int val = this.valor + 1 >= ColorProducto.values().length ? 0 : this.valor + 1;
		return ColorProducto.values()[val];
	}
	
	public int getValor() {
		return valor;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Color getColor() {
		return color;
	}
	
	public static ColorProducto getColor(int valor) {
		for(int i=0; i<ColorProducto.values().length; i++) {
			if(ColorProducto.values()[i].getValor() == valor) {
				return ColorProducto.values()[i];
			}
		}
		return null;
	}
}
