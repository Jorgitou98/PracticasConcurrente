package Parte2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;


public class Servidor {
	
	/*
	 *  Llevaremos un monitor para proteger la tabla usuarios. Que recoja todas las operaciones sobre la misma, en exclusión mutua.
	 *  Que para cada usuario guarda flujos (con evoltorio ya) para  poder comunicarme con él.
	 *  La llevaremos ordenada (TreeMap) para mostrar envia la lista de usuarios registrados por orden alfabetico con facilidad
	 *  que parece mejor para comunicarse con el usuario
	 */
	private MonitorUsuarios monitorUsuarios = new MonitorUsuarios();
	
	/*
	 * Llevaremos 2 tablas Hash donde guardaremos, para cada usuario registrado, la lista de ficheros de la que es propietario.
	 * Y otra para llevar para cada fichero una lista con sus propietarios en el sistema. 
	 * Aunque con unade las 2 sería suficiente, mantener ambas nos permite acceso rápidos y sencillos tanto para mostrar
	 * los usuarios con sus listas de ficheros como para encontrar al propietario de un fichero que nos pidan 
	 * Estas tablas, donde añadiremos nuevos usuarios que no estuviese inicialmente registrados las protegeremos una con un monitor y otra con un semáforo.
	 * Inicialmanete estarán cargadas con la información presente en "users.txt"
	 */
	
	private MonitorInfoUsuarioSistema monitorInfoUsuarioSistema = new MonitorInfoUsuarioSistema();
	
	
	private Semaphore semInfoFichero = new Semaphore(1);
	private Map<String, Set<String>> informacionFichero  = new HashMap<>();
	
	/* Además llevaremos una variable incPrimerPuertoDisp que indica el nº posterior al puerto que usa el servidor disponible para el peer to peer.
	 * El peer to peer tenemos que hacerlo en otro puerto, pero podria haber varios peer to peer a la vez con lo cual no vale asignar otro puerto y ya.
	 * Cuando se haga una peticion de fichero el Servidor asignará un puerto disponible y exclusivo para ese peerto peer ayudandose de esta variable
	 * Como el Receptor no podra modificarla, esta variables se moverá en un rango amplio para que haya suficientes puertos para peer to peer e ira dando vueltas
	 * usando el modulo.
	 * Por ejemplo, si el servidor se conecta en el puerto 1000. Cuando se vaya a hacer un peer to peer el servidor les reservará el puerto 1001, y si llega
	 * otro peer to peer para hacerse concurrentemente lo hará en el puerto 1002 para no colisionar con el primero.
	 * Cuando el puerto a asignar alcance valores muy grandes volverá a 1000 gracias a la operacion modulo
	 * Este incPrimerPuertoDisp lo protegemos con un semáforo mutex */
	
	private Semaphore semPuertoDisp = new Semaphore(1);
	private int incPrimerPuertoDisp;
	
	
	private int puerto;
	
	
	
	public Servidor(int puerto) {
		super();
		this.puerto = puerto;
	}

	public void ejecutaServidor() throws IOException {
		incPrimerPuertoDisp = 1;
		
		/* Inicializo las tablas con los datos que hay en "users.txt". 
		 * Asumo que en el archivo viene una linea con el id de un Usuario y en la siguiente una lista con el nombre de todos 
		 * los ficheros de los que es propietario */
		
		/*
		 * Cargamos en las tablas infoUsuariosDelSistema y informacionFichero los datos del los usuarios registrados
		 * que tengamos en "users.txt". Aquí no es necesario obtener el mutex pues aún o hay proceso concurrentes a este hilo principal-
		 */
		
		BufferedReader readerDatosUsuarios= new BufferedReader(new FileReader("users.txt"));
		String usuario;
		while((usuario = readerDatosUsuarios.readLine()) != null) {
			List<String> ficheros = Arrays.asList(readerDatosUsuarios.readLine().split(" "));
			monitorInfoUsuarioSistema.anadeUsuarioAlSistema(usuario, ficheros);
			
			// Me guardo para cada fichero que este usuario es propietario suyo.
			for (String fichero: ficheros) {
				anadePropietario(fichero, usuario);
			}
		};
		readerDatosUsuarios.close();
		
		ServerSocket serverSocket = new ServerSocket(puerto);
		
		while(true) {
			Socket socket = serverSocket.accept();
			(new OyenteCliente(socket, this)).start();
		}
	}
	
	/*
	 * Operacion para saber si un fichero está en el sistema.
	 * Es lo primero que necesitamos saber cuando nos pidan recuperarlo.
	 */
	protected boolean estaElFichero(String fichero){
		try {
			semInfoFichero.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean esta = informacionFichero.containsKey(fichero);
		semInfoFichero.release();
		return esta;
	}
	
	/*
	 * Operación para añadir un fichero y un propietario.
	 * Si el fichero ya está en el sistema se añade el nuevo propietario a su lista asociada.
	 * Si no, añadimos el fichero con la lista unitaria formada por este propietario
	 */
	protected void anadePropietario(String fichero, String propietario){
		try {
			semInfoFichero.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(informacionFichero.containsKey(fichero)) informacionFichero.get(fichero).add(propietario);
		else {
			Set <String> listaUsuarios = new HashSet<>();
			listaUsuarios.add(propietario);
			informacionFichero.put(fichero, listaUsuarios);
		}
		semInfoFichero.release();
	}
	
	/*
	 * Dado un fichero devuelve la lista de propietarios asociados al mismo
	 */
	protected Set<String> dameListaPropietarios(String fichero){
		Set<String> propietarios = null;
		try {
			semInfoFichero.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(informacionFichero.containsKey(fichero)) propietarios = informacionFichero.get(fichero);
		semInfoFichero.release();
		return propietarios;
	}
	
	
	
	/*
	 *  Operacion que me permite de manera protegida obtener el primer puerto disponible.
	 *  Cada 10.000 puertos vuelve al principio mediante el módulo
	 */
	protected int obtenerPrimerPuertoDisp() throws InterruptedException {
		semPuertoDisp.acquire();
		int primerPuertoDisp = puerto + incPrimerPuertoDisp;
		incPrimerPuertoDisp = 1 + (incPrimerPuertoDisp % 10000);
		semPuertoDisp.release();
		return primerPuertoDisp;
	}

	public MonitorUsuarios getMonitorUsuarios() {
		return monitorUsuarios;
	}


	public MonitorInfoUsuarioSistema getMonitorInfoUsuarioSistema() {
		return monitorInfoUsuarioSistema;
	}

	
	
	
	
}
