package Parte2;

import java.util.List;

@SuppressWarnings("serial")
public class MensajeSolicitaConexion extends Mensaje{
	
	// Lista de ficheros que ha indicado que tiene el usuario que quiere conectarse
	private List<String> listaFicheros;
	
	public MensajeSolicitaConexion(String origen, String fin,List<String> listaFicheros) {
		super(origen, fin);
		this.listaFicheros = listaFicheros;
	}
	
	public List<String> getListaFicheros() {
		return listaFicheros;
	}
	@Override
	public Tipo getTipo() {
		return Tipo.SOLICITACONEXION;
	}
	
}
