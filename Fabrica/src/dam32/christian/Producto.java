package dam32.christian;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Producto implements Serializable {
	private static final long serialVersionUID = 3375208487150160466L;
	
	private TipoProducto tipo;
	private ColorProducto color;
	
	public Producto(TipoProducto tipo, ColorProducto color) {
		this.tipo = tipo;
		this.color = color;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		
		oos.writeInt(tipo.getValor());
		oos.writeInt(color.getValor());
	}
	
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		ois.defaultReadObject();

		tipo = TipoProducto.getTipo(ois.readInt());
		color = ColorProducto.getColor(ois.readInt());
	}
	
	@Override
	public String toString() {
		return "Tipo: "+tipo.getNombre()+", Color: "+color.getNombre();
	}
	
	public TipoProducto getTipo() {
		return tipo;
	}
	
	public void setColor(ColorProducto color) {
		this.color = color;
	}
	
	public ColorProducto getColor() {
		return color;
	}
}
