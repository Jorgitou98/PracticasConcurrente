package Parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OyenteCliente extends Thread{
	
	private Socket socket;
	
	// Me guardo, cuando lo tenga, el id de mi cliente para ponerlo en el atributo destino de los mensjaes que le env�e
	private String idMiCliente;
	private Servidor servidor;
	public OyenteCliente(Socket socket, Servidor servidor) {
		super();
		this.socket = socket;
		this.servidor = servidor;
	}
	public void run() {
		try {
			ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
			while(true) {
				Mensaje m = (Mensaje) reader.readObject();
				switch(m.getTipo()) {
				
				case SOLICITACONEXION:
					MensajeSolicitaConexion mSC = (MensajeSolicitaConexion) m;
					
					// Me guardo el id del cliente que est� intentando establecer la conexi�n conmigo, sera el cliente que yo gestione
					idMiCliente = mSC.getOrigen();
					
					/* Compruebo si el usuario est� o no registrado. 
					 * En caso de que lo est�, simplemente lo a�ado a los conectados, pero no cambio nada de sus lista de ficheros por ejemplo.
					 * Si no, actualizo la tablas llamando a los m�todos adecuados de los monitores.
					 * Si se registra un nuevo usuario que no estaba en el sistema a�ado en la otra tabla a cada uno de sus ficheros a �l como propietario
					 */
					
					servidor.getMonitorInfoUsuarioSistema().anadeUsuarioAlSistema(idMiCliente, mSC.getListaFicheros());
					
					for(String fichero: mSC.getListaFicheros()) {
						servidor.anadePropietario(fichero, idMiCliente);
					}		
					// En ambos casos lo a�ado como conectado
					servidor.getMonitorUsuarios().anadeUsuarioConectado(idMiCliente, writer);
					
					// Confirmamos que se ha establecido la conexi�n
					writer.writeObject(new MensajeConfirmaConexion("Oyente cliente", idMiCliente));
					
					break;
				
				case SOLICITALISTAUSUARIOS:
					
					/* Accediendo a operaciones del monitor de usuarios conectados, mandamos una lista de los mismos
					 *  y las listas de las que son propietarios */
					
					List<Pair<String, Set<String>>> usuariosConectadosConListaFicheros = new ArrayList<>();
					List<String> listaUsuariosConectados = servidor.getMonitorUsuarios().listaUsuariosConectados();
					
					//Para cada usuario registrado le a�adimos la lista de los ficheros de los que es propietario
					
					for(String clienteConectado: listaUsuariosConectados) {
						usuariosConectadosConListaFicheros.add(new Pair<>(clienteConectado, servidor.getMonitorInfoUsuarioSistema().dameListaFicheros(clienteConectado)));
					}					
					// Enviamos la lista elaborada
					writer.writeObject(new MensajeConfirmaListaUsuarios("Oyente cliente", idMiCliente,  usuariosConectadosConListaFicheros));
					break;
					
				case PEDIRFICHERO:
					
					MensajePedirFichero mPF = (MensajePedirFichero) m;
					
					// Obtenemos el ObjectOutputStream para escribir al usuario que tiene el fichero
					String fichero = mPF.getNombreFicheroPedido(), propietarioPedir = null;
					
					// Busco un propietario del fichero que este conectado, de lo contrario no tendr� flujo para comunicarme con �l
					// Obtengo la lista de propietarios del fichero. null si no hay ninguno
					Set<String> listaFicheros = servidor.dameListaPropietarios(fichero);
					
					// Si no es null, la recorro en busca de algun propietario que guardo en propietarioPedir
					if(listaFicheros != null) {
						for(String propietario: listaFicheros) {
							if(servidor.getMonitorUsuarios().estaConectado(propietario)) {
								propietarioPedir = propietario;
								break;
							}
						}
					}
					
					/* Si no hemos encontrado ning�n propietario del fichero pedido mandamos un MensajeFicheroSinPropietario
					 * Si lo hemos encontrado buscamos el flujo para contactar con el propietario y le mandamos un MensajeEmitirFichero
					 * En este segundo caso obtenemos un puerto disponible para realizar el env�o, dicho puerto lo pasamos en el propio mensaje
					 */
					if(propietarioPedir == null) writer.writeObject(new MensajeFicheroSinPropietario("Oyente cliente", idMiCliente, fichero));
					else servidor.getMonitorUsuarios().obtenerWrite(propietarioPedir).writeObject(new MensajeEmitirFichero(idMiCliente, propietarioPedir, fichero, servidor.obtenerPrimerPuertoDisp()));
					
					break;
				case PREPARADOCLIENTESERVIDOR:
					MensajePreparadoClienteServidor mPCS = (MensajePreparadoClienteServidor) m;
					// Obtenemos el flujo con el cliente que pidio el fichero (lo lleva el propio mensaje) y le enviamos un MensajePreparadoServidorCliente
					ObjectOutputStream out = servidor.getMonitorUsuarios().obtenerWrite(mPCS.getFin());
					out.writeObject(new MensajePreparadoServidorCliente("OyenteCliente", mPCS.getFin(), mPCS.getDirHost(), mPCS.getPuerto()));
					break;
				case CERRARCONEXION:
					
					// Borramos la informaci�n del usuario como conectado. Pero no como registrado
					servidor.getMonitorUsuarios().eliminaUsuarioConectado(idMiCliente);

					//Enviamos un mensaje confirmando el cierre de conexion
					writer.writeObject(new MensajeConfirmaCierre("Oyente cliente", idMiCliente));
					break;
					
				default:
				}		
					
			}
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
