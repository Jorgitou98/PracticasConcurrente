package Parte2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Receptor extends Thread {
	private InetAddress dirHost;
	private int puerto;
	public Receptor(InetAddress dirHost, int puerto) {
		super();
		this.dirHost = dirHost;
		this.puerto = puerto;
	}
	
	public void run() {
		try {
			Socket socket = new Socket(dirHost, puerto);
			
			// Obtenemos un flujo para leer el fichero que nos va a enviar el emisor
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			if(reader.readLine().equals("Existe")){
			
			// Leemos linea a linea el fichero
			String linea = reader.readLine(), ficheroRecibido = "";
			while(linea != null) {
				ficheroRecibido += linea;
				linea = reader.readLine();
				if(linea != null) ficheroRecibido += "\n";
			};
			
			// Escribimos por pantalla el fichero leído
			System.out.println("Fichero recibido:");
			System.out.println("---------------------------------------");
			System.out.println(ficheroRecibido);
			System.out.println("---------------------------------------");
		}
			else {
				System.out.println("El fichero solicitado no existe y no se puede abrir");
			}
			reader.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
