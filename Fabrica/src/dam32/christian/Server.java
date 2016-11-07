package dam32.christian;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
	public static final int PUERTO = 5000;
	
	private Fabrica fabrica;
	private ServerSocket server;
	private List<Socket> clientes;
	
	public Server(Fabrica fabrica) {
		System.out.println("(Server) Iniciando servidor...");
		this.fabrica = fabrica;
		clientes = new ArrayList<Socket>();			
		start();
	}
	
	public void borrarCliente(Socket cliente) {
		try {
			cliente.close();
			clientes.remove(cliente);
		} catch(IOException e) {
			System.out.println("(Server) Error al cerrar cliente: "+e.getMessage());
		}		
	}
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(5000);
			while(true) {
				System.out.println("(Server) Esperando clientes...");
				Socket cliente = server.accept();
				System.out.println("(Server) Cliente recibido! Comenzando conexión!");
				clientes.add(cliente);
				new ManagerCliente(this, cliente);
			}
		} catch(IOException e) {
			System.out.println("(Server) Error con el servidor: "+e.getMessage());
		}		
	}
	
	public Fabrica getFabrica() {
		return fabrica;
	}
	
	public int getNumClientes() {
		return clientes.size();
	}
}
