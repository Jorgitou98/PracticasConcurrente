package Parte2;

@SuppressWarnings("serial")
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
