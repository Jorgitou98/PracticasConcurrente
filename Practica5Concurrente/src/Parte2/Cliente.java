package Parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
		
		// Establecemos el canal y los flujos para comunicarnos
		Socket socket = new Socket(dirHost, puerto);
		ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
		
		// Iniciamos un proceso que se encarga de uscuchar al oyente cliente
		(new OyenteServidor(reader, writer, this)).start();
		
		System.out.println("Dame la lista de los ficheros de los que eres propietario en una linea.");
		System.out.println("(Si ya estas registrado, da igual lo que escribas, solo mete un salto de linea)");
		
		Scanner teclado = new Scanner(System.in);
		List<String> ficheros = Arrays.asList(teclado.nextLine().split(" "));
		
		System.out.println("Estableciendo conexion...");
		
		// Enviamos un mensaje para solicitar conexion
		writer.writeObject(new MensajeSolicitaConexion(idUsuario, "OyenteCliente", ficheros));
		
		System.out.println("Escoge tu siguiente acción:");
		System.out.println("1- Consultar lista de usuarios");
		System.out.println("2- Pedir fichero");
		System.out.println("3- Desconectarse");
		
		// Array con las opciones validas del menu
		String[] validas = {"1","2","3"};
		while(true) {
			String eleccion = teclado.nextLine();
			
			// Si el string introducido no es ninuna de las 3 opciones validas mostamos un error y volvemos otra eleccion
			if(!Arrays.asList(validas).contains(eleccion)) {
				System.out.println("Opcion incorrecta. Elige una de las opciones entre 1 y 3");
				continue;
			}
			// Si la opcion fu adecuada la convertimosa entero y hacemos lo que corresponda en cada caso
			switch(Integer.parseInt(eleccion)) {
			case 1:
				writer.writeObject(new MensajeSolicitaListaUsuarios(idUsuario, "Oyente Cliente"));
				break;
			case 2:
				System.out.println("¿Que fichero quieres pedir?");
				String fichero = teclado.nextLine();
				writer.writeObject(new MensajePedirFichero(idUsuario, "Oyente Cliente", fichero));
				break;
			case 3:
				writer.writeObject(new MensajeCerrarConexion(idUsuario, "Oyente Cliente"));
				break;
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