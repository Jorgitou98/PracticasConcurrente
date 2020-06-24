package Parte2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MonitorInfoUsuarioSistema {
	
	/*
	 * Tabla que para cada usuario lleva un conjunto (no ordenado) de los ficheros que posee
	 */
	private Map<String, Set<String>> infoUsuariosDelSistema = new HashMap<>();
	
	
	/* Operacion para añadir un usuario y su lista de ficheros al sistema.
	 * Si el usuario está ya solo añado los ficheros para que estén los que no estuvieran ya
	 * Si no añado al nuevo usuario y su lista de fihceros
	 */
	synchronized void anadeUsuarioAlSistema(String usuario, List<String> listaDeFicheros) {
		
		if(!infoUsuariosDelSistema.containsKey(usuario)) {
			infoUsuariosDelSistema.put(usuario, new HashSet<>(listaDeFicheros));
		}
		else {
				infoUsuariosDelSistema.get(usuario).addAll(listaDeFicheros);
		}
	}
	
	/*
	 * Operación para devolver la lista de ficheros de un usuario
	 */
	synchronized Set<String> dameListaFicheros(String usuario) {
		return infoUsuariosDelSistema.get(usuario);
	}

}
