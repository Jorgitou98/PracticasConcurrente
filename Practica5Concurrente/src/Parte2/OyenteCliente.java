package Parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
					 * En caso de que esté, simplemente lo añado a los conectados.
					 * Si no, actualizo la tablas de registrados cogiendo sendos mutex y actualizo también la de conectados.
					 * 
					 */
					Servidor.semInfoUsuariosDelSistema.acquire();
					if(Servidor.infoUsuariosDelSistema.containsKey(idMiCliente)) {
						Servidor.semInfoUsuariosDelSistema.release();
					}
					else {
						Servidor.infoUsuariosDelSistema.put(idMiCliente, mSC.getListaFicheros());
						Servidor.semInfoUsuariosDelSistema.release();
						Servidor.semInformacionFichero.acquire();
						for(String fichero: mSC.getListaFicheros()) {
							if(Servidor.informacionFichero.containsKey(fichero)) {
								Servidor.informacionFichero.get(fichero).add(idMiCliente);
							}
							else {
								List<String> usuariosFich = new ArrayList<>();
								usuariosFich.add(idMiCliente);
								Servidor.informacionFichero.put(fichero, usuariosFich);
							}
						}
						Servidor.semInformacionFichero.release();
						Servidor.monitorUsuarios.anadeUsuarioConectado(idMiCliente, reader, writer);
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
					
					Servidor.semInfoUsuariosDelSistema.acquire();
					for(String clienteConectado: listaUsuariosConectados) {
						usuariosConectadosConListaFicheros.add(new Pair<>(clienteConectado, Servidor.infoUsuariosDelSistema.get(clienteConectado)));
					}
					Servidor.semInfoUsuariosDelSistema.release();
					
					// Enviamos la lista elaborada
					writer.writeObject(new MensajeConfirmaListaUsuarios("Oyente cliente", idMiCliente,  usuariosConectadosConListaFicheros));
					break;
					
				case PEDIRFICHERO:
					
					MensajePedirFichero mPF = (MensajePedirFichero) m;
					
					// Obtenemos el ObjectOutputStream para escribir al usuario que tiene el fichero
					String fichero = mPF.getNombreFicheroPedido(), propietarioPedir = null;
					
					// Busco un propietario del fichero que este conectado, de lo contrario no tendré flujo para comunicarme con él
					Servidor.semInformacionFichero.acquire();
					if(Servidor.informacionFichero.containsKey(fichero)) {
						for(String propietario: Servidor.informacionFichero.get(fichero)) {
							if(Servidor.monitorUsuarios.estaConectado(propietario)) {
								propietarioPedir = propietario;
								break;
							}
						}
					}
					Servidor.semInformacionFichero.release();
					
					/* Si no hemos encontrado ningún propietario del fichero pedido mandamos un MensajeFicheroSinPropietario
					 * Si lo hemos encontrado bucamos el flujo par contactar con el propietario y le mandamos un MensajeEmitirFichero
					 */
					if(propietarioPedir == null) writer.writeObject(new MensajeFicheroSinPropietario("Oyente cliente", idMiCliente, fichero));
					else Servidor.monitorUsuarios.obtenerWrite(propietarioPedir).writeObject(new MensajeEmitirFichero(idMiCliente, propietarioPedir, fichero));
					
					break;
				case PREPARADOCLIENTESERVIDOR:
					MensajePreparadoClienteServidor mPCS = (MensajePreparadoClienteServidor) m;
					// Obetnemos el flujo con el cliente que pidio el fichero (lo lleva el propio mensaje) y le enviamos un MensajePreparadoServidorCliente
					ObjectOutputStream out = Servidor.monitorUsuarios.obtenerWrite(mPCS.getFin());
					out.writeObject(new MensajePreparadoServidorCliente("OyenteCliente", mPCS.getFin(), mPCS.getDirHost(), mPCS.getPuerto()));
					break;
				case CERRARCONEXION:
					
					// Borramos la información del usuario como conectado. Pero no como registrado
					Servidor.monitorUsuarios.eiminaUsuarioConectado(idMiCliente);

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
