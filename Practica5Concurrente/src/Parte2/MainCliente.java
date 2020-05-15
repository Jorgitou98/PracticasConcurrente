package Parte2;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class MainCliente {
	
	public static void main(String[] args) throws IOException {
		if (args.length < 1) throw new RuntimeException("Número de argumentos insuficiente");
		System.out.println("¿Cual es tu nombre de usuario?");
		Scanner teclado = new Scanner(System.in);
		String idUsuario = teclado.nextLine();
		(new Cliente(Integer.parseInt(args[0]), idUsuario, InetAddress.getLocalHost())).ejecutaCliente();
	}
	
}
