package Parte2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MonitorUsuarios{
	private Map<String, Pair<ObjectInputStream, ObjectOutputStream>> usuarios  = new TreeMap<String, Pair<ObjectInputStream, ObjectOutputStream>>();
	synchronized void anadeUsuarioConectado(String usuario, ObjectInputStream reader, ObjectOutputStream writer) {
		usuarios.put(usuario, new Pair<>(reader, writer));
	}
	
	synchronized void eliminaUsuarioConectado(String usuario) {
		usuarios.remove(usuario);
	}
	
	/* Ya se encarga OyenteCliente de obtener para cada uno de ellos la lista de sus ficheros e incluirla en el mensaje */
	synchronized List<String> listaUsuariosConectados(){
		List<String> lista = new ArrayList<>();
		for(String s: usuarios.keySet()) {
			lista.add(s);
		}
		return lista;
	}
	
	synchronized boolean estaConectado(String usuario) {
		return usuarios.containsKey(usuario);
	}
	
	synchronized ObjectInputStream obtenerRead(String usuario) {
		return usuarios.get(usuario).getKey();
	}
	synchronized ObjectOutputStream obtenerWrite(String usuario) {
		return usuarios.get(usuario).getValue();
	}
	
}
