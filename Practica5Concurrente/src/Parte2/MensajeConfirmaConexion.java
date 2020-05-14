package Parte2;

public class MensajeConfirmaConexion extends Mensaje {
	
	public MensajeConfirmaConexion(String origen, String fin) {
		super(origen, fin);
	}

	@Override
	public Tipo getTipo() {
		return Tipo.CONFIRMACONEXION;
	}

}
