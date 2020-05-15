package Parte2;

@SuppressWarnings("serial")
public class MensajeEmitirFichero extends Mensaje {
	
	private String ficheroAEmitir;
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
