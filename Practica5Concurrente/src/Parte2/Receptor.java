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
		Socket socket;
		try {
			socket = new Socket(dirHost, puerto);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));			
			String linea, fichero = "";
			while((linea=reader.readLine()) != null) {
				fichero += linea + '\n';
			};
			System.out.println("Fichero recibido:");
			System.out.println("---------------------------------------");
			System.out.println(fichero);
			System.out.println("---------------------------------------");
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
