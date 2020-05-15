package Parte2;

import java.net.InetAddress;

@SuppressWarnings("serial")
public class MensajePreparadoServidorCliente extends Mensaje {
	
	InetAddress dirHost;
	int puerto;
	public MensajePreparadoServidorCliente(String origen, String fin, InetAddress dirHost, int puerto) {
		super(origen, fin);
		this.dirHost = dirHost;
		this.puerto = puerto;
	}
	@Override
	public Tipo getTipo() {
		// TODO Auto-generated method stub
		return Tipo.PREPARADOSERVIDORCLIENTE;
	}
	public InetAddress getDirHost() {
		return dirHost;
	}
	public int getPuerto() {
		return puerto;
	}
	
}
