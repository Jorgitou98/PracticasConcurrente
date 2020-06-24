package Parte2;

import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class MensajeConfirmaListaUsuarios extends Mensaje{

	// El mensaje de confirmacion de lista de usurios lleva la lista de conectados
	// y para cada uno de ellos un conjunto con los ficheros que posee
	private List<Pair <String, Set<String>>> listaUsuariosConectados;
	
	public MensajeConfirmaListaUsuarios(String origen, String fin, List<Pair <String, Set<String>>> listaUsuariosConectados) {
		super(origen, fin);
		this.listaUsuariosConectados = listaUsuariosConectados;
	}

	
	public List<Pair<String, Set<String>>> getListaUsuariosConectados() {
		return listaUsuariosConectados;
	}


	@Override
	public Tipo getTipo() {
		return Tipo.CONFIRMALISTAUSUARIOS;
	}

}
