package Parte1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) throws IOException {
		if (args.length < 1) throw new RuntimeException("Número de argumentos insuficiente. Falta el puerto");
		
		int puerto = Integer.parseInt(args[0]);
		
		ServerSocket serverSocket = new ServerSocket(puerto);
		
		while(true) {
			// Esperamos al cliente y obtenemos así el canal de comunicación con él
			Socket socket = serverSocket.accept();
			
			// Creamos un nuevo proceso que gestione la petición del cliente
			(new GestionaCliente(socket)).start();
		}
	}

}
