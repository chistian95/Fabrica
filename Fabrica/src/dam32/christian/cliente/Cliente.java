package dam32.christian.cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import dam32.christian.ColorProducto;
import dam32.christian.Producto;
import dam32.christian.TipoProducto;

public class Cliente {
	public static final String IP = "localhost";
	public static final int PUERTO = 5000;
	
	private Socket cliente;
	private EstadoCliente estado;
	private TipoProducto tipo;
	private ColorProducto color;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public Cliente() {
		estado = EstadoCliente.CONECTANDO;
		tipo = TipoProducto.ESPADA;
		color = ColorProducto.ROJO;
		new Pantalla(this);
		conectar();		
	}
	
	public static void main(String[] args) {
		new Cliente();
	}
	
	public void pedirProducto(Producto prod) {
		try {
			System.out.println("(Cliente) Enviando peticion de producto...");
			oos.writeUnshared(prod);
			Producto fin = (Producto) ois.readUnshared();
			System.out.println("(Cliente) Producto recibido! "+fin);
		} catch (IOException e) {
			System.out.println("(Cliente) Error al procesar producto: "+e.getMessage());
			estado = EstadoCliente.ERROR;
		} catch (ClassNotFoundException e) {
			System.out.println("(Cliente) Error al leer el producto: "+e.getMessage());
			estado = EstadoCliente.ERROR;
		}
	}
	
	private void conectar() {
		try {
			System.out.println("(Cliente) Conectando al servidor...");
			cliente = new Socket(IP, PUERTO);
			System.out.println("(Cliente) Abriendo streams...");
			oos = new ObjectOutputStream(cliente.getOutputStream());
			ois = new ObjectInputStream(cliente.getInputStream());
			System.out.println("(Cliente) Conectado!");
			estado = EstadoCliente.CONECTADO;
		} catch (UnknownHostException e) {
			System.out.println("(Cliente) Error al crear el cliente: "+e.getMessage());
			estado = EstadoCliente.ERROR;
		} catch (IOException e) {
			System.out.println("(Cliente) Error al crear el cliente: "+e.getMessage());
			estado = EstadoCliente.ERROR;
		}
	}
	
	public void salir() {
		try {
			ois.close();
			oos.close();
			cliente.close();	
		} catch (NullPointerException e) {
			
		} catch (IOException e) {
			System.out.println("(Cliente) Error al cerrar el cliente: "+e.getMessage());
		} finally {
			System.exit(0);
		}
	}
	
	public EstadoCliente getEstado() {
		return estado;
	}
	
	public TipoProducto getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoProducto tipo) {
		this.tipo = tipo;
	}
	
	public ColorProducto getColor() {
		return color;
	}
	
	public void setColor(ColorProducto color) {
		this.color = color;
	}
}
