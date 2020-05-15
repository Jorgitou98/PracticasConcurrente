package Parte2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorInformacionFichero {
	private Map<String, List<String>> informacionFichero  = new HashMap<>();
	
	synchronized boolean estaElFichero(String fichero) {
		return informacionFichero.containsKey(fichero);
	}
	
	synchronized void anadePropietario(String fichero, String propietario) {
		if(informacionFichero.containsKey(fichero)) informacionFichero.get(fichero).add(propietario);
		else {
			List <String> listaUsuarios = new ArrayList<>();
			listaUsuarios.add(propietario);
			informacionFichero.put(fichero, listaUsuarios);
		}
	}
	synchronized List<String> dameListaPropietarios(String fichero){
		if(informacionFichero.containsKey(fichero)) return informacionFichero.get(fichero);
		return null;
	}
	
}
