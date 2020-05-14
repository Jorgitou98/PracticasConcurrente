package Parte1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;

public class GestionaCliente extends Thread {
	private Socket socket;

	public GestionaCliente(Socket socket) {
		super();
		this.socket = socket;
	}
	
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

			String nombreFich = reader.readLine();
			System.out.println("He recibido el fichero " + nombreFich);
			
			Reader lecturaFichero= new InputStreamReader(new FileInputStream(nombreFich));
			System.out.println("Y he mandado el fichero");
		    lecturaFichero.transferTo(writer);
		    writer.flush();
		    
		    reader.close();
		    writer.close();
		    lecturaFichero.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
