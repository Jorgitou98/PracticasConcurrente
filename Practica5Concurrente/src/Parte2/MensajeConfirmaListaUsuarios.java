package Parte2;

import java.util.List;

@SuppressWarnings("serial")
public class MensajeConfirmaListaUsuarios extends Mensaje{

	private List<Pair <String, List<String>>> listaUsuariosConectados;
	
	public MensajeConfirmaListaUsuarios(String origen, String fin, List<Pair <String, List<String>>> listaUsuariosConectados) {
		super(origen, fin);
		this.listaUsuariosConectados = listaUsuariosConectados;
	}

	
	public List<Pair<String, List<String>>> getListaUsuariosConectados() {
		return listaUsuariosConectados;
	}


	@Override
	public Tipo getTipo() {
		return Tipo.CONFIRMALISTAUSUARIOS;
	}

}
