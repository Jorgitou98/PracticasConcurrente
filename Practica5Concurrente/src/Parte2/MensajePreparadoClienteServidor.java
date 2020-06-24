package Parte2;

import java.net.InetAddress;

@SuppressWarnings("serial")
public class MensajePreparadoClienteServidor extends Mensaje {
	
	// Host del Emisor para que el receptor puede obtener el socket con él
	InetAddress dirHost;
	
	// Puerto donde está el Emisor esperando al receptor
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
