
package Parte2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Emisor extends Thread{
	
	private int puerto;
	private String nombreFichero;
	public Emisor(int puerto, String nombreFichero) {
		this.puerto = puerto;
		this.nombreFichero = nombreFichero;
	}
	
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(puerto);
			
			// Esperamos al receptor para que se cree el canal
			Socket socket = serverSocket.accept();
			
			// Obtenemos un flujo para escribirleal Receptor en el canal
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			try {
			// Abrimos y obtenemos un flujo de lectura del fichero que tenemos que enviar
			BufferedReader lecturaFichero= new BufferedReader(new InputStreamReader(new FileInputStream(nombreFichero)));
			
			// Si se pudo abrir enviamos "Existe"
			writer.println("Existe");
			
			//Enviamos el fichero linea a linea
			String linea;
			while((linea = lecturaFichero.readLine()) != null ) {
				writer.write(linea + "\n");
			}
		    writer.flush();
		    
		    lecturaFichero.close();
		    writer.close();
			serverSocket.close();
			
			}
			catch(FileNotFoundException e) {
				// Si no se pudo abrir el fichero le enviamos al cliente "No existe" para que lo sepa
				// Es posible que en el sistema este usuario aparezca como propietario de un fichero que en realidad no exista
				// Por tanto no podrá abrirlo. Lo solucionamos así
				writer.println("No existe");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
