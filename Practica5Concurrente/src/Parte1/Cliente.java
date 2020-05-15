package Parte1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		if (args.length < 1) throw new RuntimeException("Número de argumentos insuficiente. Indica el puerto");
		InetAddress dirHost = InetAddress.getLocalHost();
		int puerto = Integer.parseInt(args[0]);
		Socket socket = new Socket(dirHost, puerto);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
		
		System.out.println("Indica el nombre del fichero que quieres obtener:");
		Scanner teclado = new Scanner(System.in);
		String fichero = teclado.nextLine();
		teclado.close();
		writer.println(fichero);
		System.out.println("---------------------------------------");
		System.out.println("He pedido el fichero " + fichero);
		System.out.println("---------------------------------------");
		
		if(reader.readLine().equals("Encontrado")) {
			System.out.println("Todo ok!");
			System.out.println("---------------------------------------");
		}
		else {
			System.err.println("Fichero solicitado no encontrado");
			System.exit(1);
		}
		
		String linea, ficheroRecibido = "";
		while((linea=reader.readLine()) != null) {
			ficheroRecibido += linea + '\n';
		};
		System.out.println("He leído el fichero: ");
		System.out.println(ficheroRecibido);
		System.out.println("---------------------------------------");
		socket.close();
	    reader.close();
	    writer.close();
	}

}
