package Parte2;

public class MensajeEmitirFichero extends Mensaje {
	
	private String ficheroAEmitir;

	public MensajeEmitirFichero(String origen, String fin, String ficheroAEmitir) {
		super(origen, fin);
		this.ficheroAEmitir = ficheroAEmitir;
	}
	
	public String getFicheroAEmitir() {
		return ficheroAEmitir;
	}

	@Override
	public Tipo getTipo() {
		return Tipo.EMITIRFICHERO;
	}
	
}
