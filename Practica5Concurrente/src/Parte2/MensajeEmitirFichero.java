package Parte2;

@SuppressWarnings("serial")
public class MensajeEmitirFichero extends Mensaje {
	
	// Nombre del fichero que pedimos al oyente servidor que emita
	private String ficheroAEmitir;
	
	// Puerto que el oyentecliente ha reservado para que se realice el peer to peer para enviar el fichero
	private int puerto;

	public MensajeEmitirFichero(String origen, String fin, String ficheroAEmitir, int puerto) {
		super(origen, fin);
		this.ficheroAEmitir = ficheroAEmitir;
		this.puerto = puerto;
	}
	
	
	
	public int getPuerto() {
		return puerto;
	}

	public String getFicheroAEmitir() {
		return ficheroAEmitir;
	}

	@Override
	public Tipo getTipo() {
		return Tipo.EMITIRFICHERO;
	}
	
}
