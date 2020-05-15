package Parte2;

@SuppressWarnings("serial")
public class MensajeSolicitaListaUsuarios extends Mensaje{

	public MensajeSolicitaListaUsuarios(String origen, String fin) {
		super(origen, fin);
	}

	@Override
	public Tipo getTipo() {
		return Tipo.SOLICITALISTAUSUARIOS;
	}
}
