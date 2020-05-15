package Parte2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorInfoUsuarioSistema {
	private Map<String, List<String>> infoUsuariosDelSistema = new HashMap<>();
	
	
	// Si el usuario ya lo tengo almacenado no hago nada. Si no, lo añado con la lista de ficheros que me haya dicho
	// Devuelvo un booleano indicando si se ha insertado un nuevo Usuario al sistema o no
	synchronized boolean anadeUsuarioAlSistema(String usuario, List<String> listaDeFicheros) {
		
		if(!infoUsuariosDelSistema.containsKey(usuario)) {
			infoUsuariosDelSistema.put(usuario, listaDeFicheros);
			return true;
		}
		return false;
	}
	
	synchronized List<String> dameListaFicheros(String usuario) {
		return infoUsuariosDelSistema.get(usuario);
	}

}
