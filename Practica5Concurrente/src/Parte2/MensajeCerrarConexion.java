package Parte2;

public class MensajeCerrarConexion extends Mensaje{

	public MensajeCerrarConexion(String origen, String fin) {
		super(origen, fin);
	}

	@Override
	public Tipo getTipo() {
		return Tipo.CERRARCONEXION;
	}
	
}
