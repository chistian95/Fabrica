package dam32.christian.cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import dam32.christian.ColorProducto;
import dam32.christian.Producto;
import dam32.christian.TipoProducto;

public class Cliente {
	public static final int PUERTO = 5000;
	
	private String ip;
	private Socket cliente;
	private EstadoCliente estado;
	private TipoProducto tipo;
	private ColorProducto color;
	private ObjectInputStream ois;
	private DataInputStream dis;
	private ObjectOutputStream oos;
	private String texto;
	private String textoError;
	
	public Cliente(String ip) {
		this.ip = ip;
		estado = EstadoCliente.CONECTANDO;
		tipo = TipoProducto.ESPADA;
		color = ColorProducto.ROJO;
		textoError = "";
		new Pantalla(this);
		conectar();		
	}
	
	public static void main(String[] args) {
		String ip = JOptionPane.showInputDialog(null, "Introduce la IP");
		if(ip == null) {
			ip = "localhost";
		}
		new Cliente(ip);
	}
	
	public void pedirProducto(final Producto prod) {
		Thread t = new Thread() {
			public void run() {
				try {
					estado = EstadoCliente.ESPERANDO;
					texto = "Esperando...";
					System.out.println("(Cliente) Enviando peticion de producto...");
					oos.writeUnshared(prod);
					
					for(int i=0; i<5; i++) {
						texto = dis.readUTF();
					}
					
					Producto fin = (Producto) ois.readUnshared();
					System.out.println("(Cliente) Producto recibido! "+fin);
					estado = EstadoCliente.CONECTADO;
				} catch (IOException e) {
					System.out.println("(Cliente) Error al procesar producto: "+e.getMessage());
					textoError = e.getMessage();
					estado = EstadoCliente.ERROR;
				} catch (ClassNotFoundException e) {
					System.out.println("(Cliente) Error al leer el producto: "+e.getMessage());
					textoError = e.getMessage();
					estado = EstadoCliente.ERROR;
				}
			}
		};
		t.start();
	}
	
	private void conectar() {
		try {
			System.out.println("(Cliente) Conectando al servidor...");
			cliente = new Socket(ip, PUERTO);
			System.out.println("(Cliente) Abriendo streams...");
			oos = new ObjectOutputStream(cliente.getOutputStream());
			ois = new ObjectInputStream(cliente.getInputStream());
			dis = new DataInputStream(cliente.getInputStream());
			System.out.println("(Cliente) Conectado!");
			estado = EstadoCliente.CONECTADO;
		} catch (UnknownHostException e) {
			System.out.println("(Cliente) Error al crear el cliente: "+e.getMessage());
			textoError = e.getMessage();
			estado = EstadoCliente.ERROR;
		} catch (IOException e) {
			System.out.println("(Cliente) Error al crear el cliente: "+e.getMessage());
			textoError = e.getMessage();
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
	
	public void setEstado(EstadoCliente estado) {
		this.estado = estado;
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
	
	public String getTexto() {
		return texto;
	}
	
	public String getTextoError() {
		return textoError;
	}
}
