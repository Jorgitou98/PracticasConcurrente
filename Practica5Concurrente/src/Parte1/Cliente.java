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
		// Obetenemos como argumento el puerto para comunicarnos con el servidor
		int puerto = Integer.parseInt(args[0]);
		
		// Creamos el canal de comunicación con el servidor
		Socket socket = new Socket(dirHost, puerto);
		
		// Obetenemos flujos de entrada y salida para poder comunicarnos por el canal
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);
		
		// Pedimos el fichero que el cliente desea obtener
		System.out.println("Indica el nombre del fichero que quieres obtener:");
		Scanner teclado = new Scanner(System.in);
		String fichero = teclado.nextLine();
		teclado.close();
		
		// Enviamos el nombre del fichero por el canal a modo de peticion al servidor
		writer.println(fichero);
		System.out.println("---------------------------------------");
		System.out.println("He pedido el fichero " + fichero);
		System.out.println("---------------------------------------");
		
		// Si nos responden con "Encontrado" está todo bien y seguimos
		// Si no, es que el fichero no se pudo encontrar. Informamos por pantala y finalizamos la ejecución
		if(reader.readLine().equals("Encontrado")) {
			System.out.println("Todo ok!");
			System.out.println("---------------------------------------");
		}
		else {
			System.err.println("Fichero solicitado no encontrado");
			System.exit(1);
		}
		
		// Si se encontró y continuamos, leemos linea a linea mediante el flujo de lectura y recomponemos el fichero en un string
		String linea = reader.readLine(), ficheroRecibido = "";
		while(linea != null) {
			ficheroRecibido += linea;
			linea = reader.readLine();
			if(linea != null) ficheroRecibido += "\n";
		};
		
		// Mostramos por pantalla el fichero obtenido
		System.out.println("He leído el fichero: ");
		System.out.println(ficheroRecibido);
		System.out.println("---------------------------------------");
		socket.close();
	    reader.close();
	    writer.close();
	}

}
