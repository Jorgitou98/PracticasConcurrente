package Parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class OyenteServidor extends Thread{
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	
	// Me guardo tambien una referencia al cliente que gestiono para tener acceso a su dir IP y su puerto cuando necesite mandarlo
	private Cliente miCliente;
	public OyenteServidor(ObjectInputStream reader, ObjectOutputStream writer, Cliente miCliente) {
		this.reader = reader;
		this.writer = writer;
		this.miCliente = miCliente;
	}
	public void run() {
			try {
				while(true) {
					Mensaje m = (Mensaje) reader.readObject();
					switch(m.getTipo()) {
					case CONFIRMACONEXION:
						MensajeConfirmaConexion mCC = (MensajeConfirmaConexion) m;
						System.out.println("----------------------------------------------------");
						System.out.println("Conexión establecida por parte del usuario " + mCC.getFin());
						System.out.println("----------------------------------------------------");
						break;
					case CONFIRMALISTAUSUARIOS:
						MensajeConfirmaListaUsuarios mLU = (MensajeConfirmaListaUsuarios) m;	
						
						// Mostramos la lista de usuarios pertienentemente (cada usuario y después lo ficheros de los que es propietario)
						System.out.println();
						System.out.println("Lista de usuarios pedida por " + mLU.getFin() + ":");
						System.out.println("---------------------------------------");
						int numUsuarioMostrado = 1;
						for (Pair <String, List<String>> par: mLU.getListaUsuariosConectados()) {
							System.out.println("Usuario " + numUsuarioMostrado + ": " + par.getKey());
							System.out.println("Ficheros de " + par.getKey() + ":");
							for(String fichero: par.getValue()) {
								System.out.print(fichero + " ");
							}
							numUsuarioMostrado++;
							System.out.println();
							System.out.println("---------------------------------------");
						}
						break;				
					case EMITIRFICHERO:
						MensajeEmitirFichero mEF = (MensajeEmitirFichero) m;
						
						// Devolvemos un MensajePreparadoClienteServidor a nuestro OyenteCliente. Ponemos en su campo fin el id del cliente que nos lo pidio: mEF.getOrigen()
						// Y el puerto donde la esperamos para mandale el fichero en otro porceso Emisor
						writer.writeObject(new MensajePreparadoClienteServidor("OyenteServidor", mEF.getOrigen() , miCliente.getDirHost(), mEF.getPuerto()));
						System.out.println("---------------------------------------");
						System.out.println("Mi cliente " + miCliente.getIdUsuario() + " va a emitir el fichero " + mEF.getFicheroAEmitir());
						System.out.println("---------------------------------------");
						
						// Creamos un proceso emisor que esperará al otro proceso Receptor para mandar el fichero de forma Peer to Peer al cliente que lo pidio
						(new Emisor(mEF.getPuerto(), mEF.getFicheroAEmitir())).start();
						break;
					case PREPARADOSERVIDORCLIENTE:
						MensajePreparadoServidorCliente mPSC = (MensajePreparadoServidorCliente) m;
						
						// Creamos un proceso Receptor que recibirá el fichero directamente del proceso Emidor creado por el cliente propietario del fichero
						(new Receptor(mPSC.getDirHost(), mPSC.getPuerto())).start();
						break;
					case FICHEROSINPROPIETARIO:
						MensajeFicheroSinPropietario mFSP = (MensajeFicheroSinPropietario) m;
						
						// Si nos informan de que el fichero pedido no tiene propietario avisamos por pantalla
						System.out.println("---------------------------------------");
						System.out.println("El fichero " + mFSP.getNombreFicheroSinPropietario() + " no tiene propietario conectado");
						System.out.println("---------------------------------------");
						break;
					case CONFIRMACIERRE:
						System.out.println("---------------------------------------");
						System.out.println("Conexión cerrada. Adios!");
						System.out.println("---------------------------------------");
						break;
					default:
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
	}
}
