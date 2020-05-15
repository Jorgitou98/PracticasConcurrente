package Parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Cliente{
	private int puerto;
	private String idUsuario;
	private InetAddress dirHost;
	
	public Cliente(int puerto, String idUsuario, InetAddress dirHost) {
		super();
		this.puerto = puerto;
		this.idUsuario = idUsuario;
		this.dirHost = dirHost;
	}
	public void ejecutaCliente() throws IOException {
		
		Socket socket = new Socket(dirHost, puerto);
		ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
		(new OyenteServidor(reader, writer, this)).start();
		
		System.out.println("Dame la lista de los ficheros de los que eres propietario en una linea.");
		System.out.println("(Si ya estas registrado, da igual lo que escribas, solo mete un salto de linea)");
		
		Scanner teclado = new Scanner(System.in);
		List<String> ficheros = Arrays.asList(teclado.nextLine().split(" "));
		
		System.out.println("Estableciendo conexion...");
		
		writer.writeObject(new MensajeSolicitaConexion("cliente", "OyenteCliente", idUsuario, ficheros));
		
		System.out.println("Escoge tu siguiente acción:");
		System.out.println("1- Consultar lista de usuarios");
		System.out.println("2- Pedir fichero");
		System.out.println("3- Salir");
		
		while(true) {
			int eleccion = Integer.parseInt(teclado.nextLine());
			switch(eleccion) {
			case 1:
				writer.writeObject(new MensajeSolicitaListaUsuarios(idUsuario, "OyenteCliente"));
				break;
			case 2:
				System.out.println("¿Que fichero quieres pedir?");
				String fichero = teclado.nextLine();
				writer.writeObject(new MensajePedirFichero(idUsuario, "Servidor", fichero));
				break;
			case 3:
				writer.writeObject(new MensajeCerrarConexion(idUsuario, "OyenteCliente"));
				break;
			default:
				System.out.println("Opcion incorrecta. Elige una de las opciones entre 1 y 3");
			}
		}
	}
	
	public InetAddress getDirHost() {
		return dirHost;
	}
	
	public int getPuerto() {
		return puerto;
	}

	public String getIdUsuario() {
		return idUsuario;
	}
}