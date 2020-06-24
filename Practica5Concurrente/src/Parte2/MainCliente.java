package Parte2;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class MainCliente {
	
	public static void main(String[] args) throws IOException {
		if (args.length < 1) throw new RuntimeException("Número de argumentos insuficiente");
		
		// Pedimos el nombre al cliente que se va a conectar
		System.out.println("¿Cual es tu nombre de usuario?");
		Scanner teclado = new Scanner(System.in);
		String idUsuario = teclado.nextLine();
		
		// Llamamos a ejecutaCliente que raliza al funcionalidad del mismo. De ets aforma podemos llevar atributos no estáticos
		// en la clase cliente, que no podriamos en esta clase Main
		(new Cliente(Integer.parseInt(args[0]), idUsuario, InetAddress.getLocalHost())).ejecutaCliente();
	}
	
}
