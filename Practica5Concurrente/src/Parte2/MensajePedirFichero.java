package Parte2;

@SuppressWarnings("serial")
public class MensajePedirFichero extends Mensaje{
	
	// Nombre de fichero que le pedimos al oyente cliente (para que busque quien lo tiene y le pida que lo emita)
	String nombreFicheroPedido;

	public MensajePedirFichero(String origen, String fin, String nombreFicheroPedido) {
		super(origen, fin);
		this.nombreFicheroPedido = nombreFicheroPedido;
	}

	public String getNombreFicheroPedido() {
		return nombreFicheroPedido;
	}

	@Override
	public Tipo getTipo() {
		return Tipo.PEDIRFICHERO;
	}
	
}
