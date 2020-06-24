package Parte2;

import java.io.IOException;

public class MainServidor {

	public static void main(String[] args) throws IOException {
		if (args.length < 1) throw new RuntimeException("Número de argumentos insuficiente. Falta el puerto");
		
		int puerto = Integer.parseInt(args[0]);
		(new Servidor(puerto)).ejecutaServidor();
	}
}
