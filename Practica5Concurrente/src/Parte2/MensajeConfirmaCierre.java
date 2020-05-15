package Parte2;

@SuppressWarnings("serial")
public class MensajeConfirmaCierre extends Mensaje{

	public MensajeConfirmaCierre(String origen, String fin) {
		super(origen, fin);
	}

	@Override
	public Tipo getTipo() {
		return Tipo.CONFIRMACIERRE;
	}

}
