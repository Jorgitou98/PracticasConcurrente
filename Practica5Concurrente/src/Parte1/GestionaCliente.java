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
			// Obetenemos sendos flujos de entrada y salida para comunicarnos con el cliente por el canal
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			// Leemos el nombre del fichero que quiere el cliente por el canal
			String nombreFich = reader.readLine();
			
			System.out.println("He recibido el fichero " + nombreFich);
			try {
				// Abrimos el fichero en cuestión. (Si no se puede abrir tenemos un catch especial para indicarlo por pantalla)
				BufferedReader lecturaFichero= new BufferedReader(new InputStreamReader(new FileInputStream(nombreFich)));
				System.out.println("Y he mandado el fichero");
				
				// Si no hemos saltado al catch es porque el fichero se puede abrir y le escribo al cliente para que lo sepa
				writer.println("Encontrado");
				
				// Enviamos el fichero al cliente linea a linea
				String linea;
				while((linea = lecturaFichero.readLine()) != null ) {
					writer.write(linea + "\n");
				}
				writer.flush();
			    reader.close();
			    writer.close();
			    lecturaFichero.close();
			}
			catch(FileNotFoundException e) {
				// Si no se pudo abrir el fichero le enviamos al cliente "no encontrado" para que lo sepa
				// E informamos por nuestra pantalla
				writer.println("No encontrado");
				System.out.println("No se pudo abrir el archivo " + nombreFich + " pedido");
			}    
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	

	}
}
