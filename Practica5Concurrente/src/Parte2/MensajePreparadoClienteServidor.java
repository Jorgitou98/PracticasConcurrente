package Parte2;

import java.net.InetAddress;

public class MensajePreparadoClienteServidor extends Mensaje {
	
	InetAddress dirHost;
	int puerto;
	public MensajePreparadoClienteServidor(String origen, String fin, InetAddress dirHost, int puerto) {
		super(origen, fin);
		this.dirHost = dirHost;
		this.puerto = puerto;
	}
	
	public InetAddress getDirHost() {
		return dirHost;
	}

	public int getPuerto() {
		return puerto;
	}

	@Override
	public Tipo getTipo() {
		return Tipo.PREPARADOCLIENTESERVIDOR;
	}

}
