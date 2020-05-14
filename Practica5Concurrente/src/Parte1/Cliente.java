package Parte1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		if (args.length < 2) throw new RuntimeException("Número de argumentos insuficiente");
		InetAddress dirHost = InetAddress.getLocalHost();
		int puerto = Integer.parseInt(args[0]);
		Socket socket = new Socket(dirHost, puerto);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
		
		writer.println(args[1]);
		System.out.println("He pedido el fichero " + args[1]);
		
		String linea, fichero = "";
		while((linea=reader.readLine()) != null) {
			fichero += linea + '\n';
		};
		System.out.println("Y he leído el fichero: ");
		System.out.println(fichero);
		
	    reader.close();
	    writer.close();
	}

}
