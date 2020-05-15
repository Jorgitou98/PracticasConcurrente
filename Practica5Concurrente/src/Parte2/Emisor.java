
package Parte2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
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
			Socket socket = serverSocket.accept();
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			Reader lecturaFichero= new InputStreamReader(new FileInputStream(nombreFichero));
		    lecturaFichero.transferTo(writer);
		    writer.flush();
		    lecturaFichero.close();
		    writer.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
