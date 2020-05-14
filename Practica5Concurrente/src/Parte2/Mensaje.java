package Parte2;

import java.io.Serializable;

public abstract class Mensaje implements Serializable{
	
	private String origen, fin;
	
	// He preferido llevar un enumerado Tipo con los tipos que un entero. Para que sea más representativo
	public abstract Tipo getTipo();
	
	public Mensaje(String origen, String fin) {
		this.origen = origen;
		this.fin = fin;
	}
	
	public Mensaje(String origen) {
		this.origen = origen;
	}
	

	public String getOrigen() {
		return origen;
	}

	public String getFin() {
		return fin;
	}
	
}
