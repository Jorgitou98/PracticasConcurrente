package Parte2;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MonitorUsuarios{
	/*
	 * Mapa que lleva los usuarios conectados (no todos los registrados como el de MonitorInfoUsuariosSistema)
	 * Y para cada uno de ellos lleva un flujo de salida para mandarle datos.
	 * (Solo llevo el  ObjectOutputStream, porque el ObjectInputStream no se utilizaba nunca y no tiene sentido llevarlo también)
	 * (Aunque en clase se dijera de llevar los 2 flujos)
	 */
	private Map<String, ObjectOutputStream> usuarios  = new TreeMap<String, ObjectOutputStream>();
	
	/*
	 * Operacion que añade a la tabl un usuario conectado y un flujo para escribirle
	 */
	synchronized void anadeUsuarioConectado(String usuario, ObjectOutputStream writer) {
		usuarios.put(usuario, writer);
	}
	
	/*
	 * Operacion para eliminar a un usuario como conectado
	 */
	synchronized void eliminaUsuarioConectado(String usuario) {
		usuarios.remove(usuario);
	}
	
	/* 
	 * Operacion para devolver la lista de usuarios que hay conectados
	 * Ya se encarga OyenteCliente de obtener para cada uno de ellos la lista de sus ficheros e incluirla en el mensaje
	 */
	synchronized List<String> listaUsuariosConectados(){
		List<String> lista = new ArrayList<>();
		for(String s: usuarios.keySet()) {
			lista.add(s);
		}
		return lista;
	}
	
	/*
	 * Operacion para saber si un cliente esta conectado (para cuando buscamos a quien pedirle un fichero)
	 */
	synchronized boolean estaConectado(String usuario) {
		return usuarios.containsKey(usuario);
	}
	
	/*
	 * Operacion para obtener el flujo para escribirle a un determinado usuario
	 */
	synchronized ObjectOutputStream obtenerWrite(String usuario) {
		return usuarios.get(usuario);
	}
	
}
