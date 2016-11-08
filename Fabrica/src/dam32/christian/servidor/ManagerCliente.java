package dam32.christian.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import dam32.christian.Producto;

public class ManagerCliente extends Thread {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Server server;
	private Socket cliente;
	
	public ManagerCliente(Server server, Socket cliente) {
		this.server = server;
		this.cliente = cliente;
		
		try {
			ois = new ObjectInputStream(cliente.getInputStream());
			oos = new ObjectOutputStream(cliente.getOutputStream());
		} catch(IOException e) {
			System.out.println("(ManagerCliente) Error al crear los streams: "+e.getMessage());
		}
		
		start();
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Producto producto = (Producto) ois.readUnshared();
				System.out.println("(ManagerCliente) Petici�n recibida! Creando producto");
				server.getFabrica().crearProducto(producto);
				System.out.println("(ManagerCliente) Producto creado! Devolviendo al cliente");
				oos.writeUnshared(producto);
			}
		} catch(IOException e) {
			System.out.println("(ManagerCliente) Error con el cliente: "+e.getMessage());
		} catch(ClassNotFoundException e) {
			System.out.println("(ManagerCliente) Error al leer el objeto: "+e.getMessage());
		} finally {
			try {
				ois.close();
				oos.close();
				cliente.close();
			} catch(IOException ex) {
				System.out.println("(ManagerCliente) Error al cerrar los streams: "+ex.getMessage());
			}
		}
		server.borrarCliente(cliente);
	}
}