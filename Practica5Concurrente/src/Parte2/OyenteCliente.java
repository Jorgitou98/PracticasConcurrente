package Parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class OyenteCliente extends Thread{
	
	private Socket socket;
	
	// Me guardo, cuando lo tenga, el id de mi cliente para ponerlo en el atributo destino de los mensjaes que le envíe
	private String idMiCliente;
	
	public OyenteCliente(Socket socket) {
		super();
		this.socket = socket;
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
					
					// Me guardo el id del cliente que está intentando establecer la conexión conmigo, sera el cliente que yo gestione
					idMiCliente = mSC.getIdCliente();
					
					/* Compruebo si el usuario está o no registrado. 
					 * En caso de que lo esté, simplemente lo añado a los conectados, pero no cambio nada de sus lista de ficheros por ejemplo.
					 * Si no, actualizo la tablas llamando a los métodos adecuados de los monitores.
					 * Si se registra un nuevo usuario que no estaba en el sistema añado en la otra tabla a cada uno de sus ficheros a él como propietario
					 */
					
					if(Servidor.monitorInfoUsuarioSistema.anadeUsuarioAlSistema(idMiCliente, mSC.getListaFicheros())) {
						for(String fichero: mSC.getListaFicheros()) {
							Servidor.monitorInfoFichero.anadePropietario(fichero, idMiCliente);
						}
					}			
					// En ambos casos lo añado como conectado
					Servidor.monitorUsuarios.anadeUsuarioConectado(idMiCliente, reader, writer);
					
					// Confirmamos que se ha establecido la conexión
					writer.writeObject(new MensajeConfirmaConexion("Oyente cliente", idMiCliente));
					
					break;
				
				case SOLICITALISTAUSUARIOS:
					
					/* Accediendo a operaciones del monitor de usuarios conectados, mandamos una lista de los mismos
					 *  y las listas de las que son propietarios */
					
					List<Pair<String, List<String>>> usuariosConectadosConListaFicheros = new ArrayList<>();
					List<String> listaUsuariosConectados = Servidor.monitorUsuarios.listaUsuariosConectados();
					
					//Para cada usuario registrado le añadimos la lista de los ficheros de los que es propietario
					
					for(String clienteConectado: listaUsuariosConectados) {
						usuariosConectadosConListaFicheros.add(new Pair<>(clienteConectado, Servidor.monitorInfoUsuarioSistema.dameListaFicheros(clienteConectado)));
					}					
					// Enviamos la lista elaborada
					writer.writeObject(new MensajeConfirmaListaUsuarios("Oyente cliente", idMiCliente,  usuariosConectadosConListaFicheros));
					break;
					
				case PEDIRFICHERO:
					
					MensajePedirFichero mPF = (MensajePedirFichero) m;
					
					// Obtenemos el ObjectOutputStream para escribir al usuario que tiene el fichero
					String fichero = mPF.getNombreFicheroPedido(), propietarioPedir = null;
					
					// Busco un propietario del fichero que este conectado, de lo contrario no tendré flujo para comunicarme con él
					// Obtengo la lista de propietarios del fichero. null si no hay ninguno
					List<String> listaFicheros = Servidor.monitorInfoFichero.dameListaPropietarios(fichero);
					
					// Si no es null, la recorro en busca de algun propietario que guardo en propietarioPedir
					if(listaFicheros != null) {
						for(String propietario: listaFicheros) {
							if(Servidor.monitorUsuarios.estaConectado(propietario)) {
								propietarioPedir = propietario;
								break;
							}
						}
					}
					
					/* Si no hemos encontrado ningún propietario del fichero pedido mandamos un MensajeFicheroSinPropietario
					 * Si lo hemos encontrado buscamos el flujo para contactar con el propietario y le mandamos un MensajeEmitirFichero
					 * En este segundo caso obtenemos un puerto disponible para realizar el envío, dicho puerto lo pasamos en el propio mensaje
					 */
					if(propietarioPedir == null) writer.writeObject(new MensajeFicheroSinPropietario("Oyente cliente", idMiCliente, fichero));
					else Servidor.monitorUsuarios.obtenerWrite(propietarioPedir).writeObject(new MensajeEmitirFichero(idMiCliente, propietarioPedir, fichero, Servidor.obtenerPrimerPuertoDisp()));
					
					break;
				case PREPARADOCLIENTESERVIDOR:
					MensajePreparadoClienteServidor mPCS = (MensajePreparadoClienteServidor) m;
					// Obetnemos el flujo con el cliente que pidio el fichero (lo lleva el propio mensaje) y le enviamos un MensajePreparadoServidorCliente
					ObjectOutputStream out = Servidor.monitorUsuarios.obtenerWrite(mPCS.getFin());
					out.writeObject(new MensajePreparadoServidorCliente("OyenteCliente", mPCS.getFin(), mPCS.getDirHost(), mPCS.getPuerto()));
					break;
				case CERRARCONEXION:
					
					// Borramos la información del usuario como conectado. Pero no como registrado
					Servidor.monitorUsuarios.eliminaUsuarioConectado(idMiCliente);

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
