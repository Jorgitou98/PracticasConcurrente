package Parte2;

import java.util.List;

@SuppressWarnings("serial")
public class MensajeSolicitaConexion extends Mensaje{
	
	String idCliente;
	List<String> listaFicheros;
	public MensajeSolicitaConexion(String origen, String fin, String idCliente, List<String> listaFicheros) {
		super(origen, fin);
		this.idCliente = idCliente;
		this.listaFicheros = listaFicheros;
	}
	public String getIdCliente() {
		return idCliente;
	}
	
	public List<String> getListaFicheros() {
		return listaFicheros;
	}
	@Override
	public Tipo getTipo() {
		return Tipo.SOLICITACONEXION;
	}
	
}
