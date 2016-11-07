package dam32.christian;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	public static final String IP = "localhost";
	public static final int PUERTO = 5000;
	
	public static void main(String[] args) {
		try {
			System.out.println("(Cliente) Conectando al servidor...");
			Socket cliente = new Socket(IP, PUERTO);
			System.out.println("(Cliente) Abriendo streams...");
			ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
			
			System.out.println("(Cliente) Enviando peticion de producto...");
			Producto prod = new Producto(TipoProducto.ESPADA, ColorProducto.ROJO);
			oos.writeUnshared(prod);
			Producto fin = (Producto) ois.readUnshared();
			System.out.println("(Cliente) Producto recibido! "+fin);
			
			ois.close();
			oos.close();
			cliente.close();
		} catch (UnknownHostException e) {
			System.out.println("(Cliente) Error al crear el cliente: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("(Cliente) Error al crear el cliente: "+e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("(Cliente) Error al leer el producto: "+e.getMessage());
		}
	}
}
