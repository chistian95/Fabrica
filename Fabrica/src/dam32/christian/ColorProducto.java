package dam32.christian;

import java.awt.Color;

public enum ColorProducto {
	ROJO(0, Color.RED),
	AZUL(1, Color.BLUE),
	VERDE(2, Color.GREEN),
	BLANCO(3, Color.WHITE);
	
	private int valor;
	private Color color;
	
	private ColorProducto(int valor, Color color) {
		this.valor = valor;
		this.color = color;
	}
	
	public int getValor() {
		return valor;
	}
	
	public Color getColor() {
		return color;
	}
}
