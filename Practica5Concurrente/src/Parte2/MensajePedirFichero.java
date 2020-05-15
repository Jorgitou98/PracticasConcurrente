package Parte2;

@SuppressWarnings("serial")
public class MensajePedirFichero extends Mensaje{
	
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
