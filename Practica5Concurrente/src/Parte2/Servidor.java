package Parte2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;


public class Servidor {
	
	/*
	 *  Llevaremos un monitor para proteger la tabla usuarios. Que recoja todas las operaciones sobre la misma, en exclusión mutua.
	 *  Que para cada usuario guarda flujos (con evoltorio ya) para  poder comunicarme con él.
	 *  La llevaremos ordenada (TreeMap) para mostrar envia la lista de usuarios registrados por orden alfabetico con facilidad
	 *  que parece mejor para comunicarse con el usuario
	 */
	protected static MonitorUsuarios monitorUsuarios = new MonitorUsuarios();
	
	/*
	 * Llevaremos 2 tablas Hash donde guardaremos, para cada usuario registrado, la lista de ficheros de la que es propietario.
	 * Y otra para llevar para cada fichero una lista con sus propietarios en el sistema.
	 * Estas tablas, donde añadiremos nuevos usuarios que no estuviese inicialmente registrados las protegeremos mediante semáforos mutex.
	 * Inicialmanete estarán cargadas con la información presente en "users.txt"
	 */
	
	protected static Semaphore semInfoUsuariosDelSistema = new Semaphore(1);
	protected static Semaphore semInformacionFichero = new Semaphore(1);
	
	protected static Map<String, List<String>> infoUsuariosDelSistema = new HashMap<>();
	protected static Map<String, List<String>> informacionFichero  = new HashMap<>();
	
	
	// Guardaremos el puerto y el serverSocket. La direccion IP será siempre InetAddress.getLocalHost()
	private static int puerto;
	private static ServerSocket serverSocket;
	
	public static void main(String[] args) throws IOException {
		if (args.length < 1) throw new RuntimeException("Número de argumentos insuficiente. Falta el puerto");
		
		puerto = Integer.parseInt(args[0]);
		
		/* Inicializo las tablas con los datos que hay en "users.txt". 
		 * Asumo que en el archivo viene una linea con el id de un Usuario y en la siguiente una lista con el nombre de todos 
		 * los ficheros de los que es propietario */
		
		/*
		 * Cargamos en as tablas infoUsuariosDelSistema y informacionFichero los datos del los usuarios registrados
		 * que tengamos en "users.txt". Aquí no es necesario obtener el mutex pues aún o hay proceso concurrentes a este hilo principal-
		 */
		
		BufferedReader readerDatosUsuarios= new BufferedReader(new FileReader("users.txt"));
		String usuario;
		while((usuario=readerDatosUsuarios.readLine()) != null) {
			List<String> ficheros = Arrays.asList(readerDatosUsuarios.readLine().split(" "));
			infoUsuariosDelSistema.put(usuario, ficheros);
			
			// Me guardo para cada fichero que este usuario es propietario suyo.
			for (String fichero: ficheros) {
				if(informacionFichero.containsKey(fichero)) informacionFichero.get(fichero).add(usuario);
				else {
					List <String> listaUsuarios = new ArrayList<>();
					listaUsuarios.add(usuario);
					informacionFichero.put(fichero, listaUsuarios);
				}
			}
		};
		readerDatosUsuarios.close();
		
		serverSocket = new ServerSocket(puerto);
		
		while(true) {
			Socket socket = serverSocket.accept();
			(new OyenteCliente(socket)).start();
		}
	}
}
