package Parte2;

@SuppressWarnings("serial")

/*Este mensaje lo he creado por si nos piden un fichero que no se puede recuperar
 * O bien porque no existe en el sistema
 * O bien porque ninguno de sus propietarios está conectado
 * */
public class MensajeFicheroSinPropietario extends Mensaje{
	String nombreFicheroSinPropietario;

	public MensajeFicheroSinPropietario(String origen, String fin, String nombreFicheroSinPropietario) {
		super(origen, fin);
		this.nombreFicheroSinPropietario = nombreFicheroSinPropietario;
	}

	@Override
	public Tipo getTipo() {
		return Tipo.FICHEROSINPROPIETARIO;
	}

	public String getNombreFicheroSinPropietario() {
		return nombreFicheroSinPropietario;
	}
	
	
}
