package Parte1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
			Reader lecturaFichero = null;
			try {
				lecturaFichero= new InputStreamReader(new FileInputStream(nombreFich));
				System.out.println("Y he mandado el fichero");
				writer.println("Encontrado");
			    lecturaFichero.transferTo(writer);
			    writer.flush();
			    reader.close();
			    writer.close();
			    lecturaFichero.close();
			}
			catch(FileNotFoundException e) {
				writer.println("No encontrado");
				System.out.println("No se pudo abrir el archivo " + nombreFich + " pedido");
			}    
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		

	}
}
