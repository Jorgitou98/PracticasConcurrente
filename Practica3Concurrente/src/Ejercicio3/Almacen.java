package Ejercicio3;


public interface Almacen {
	/**
	* Almacena (como ultimo) un producto en el almac´en. Si no hay
	* hueco el proceso que ejecute el m´etodo bloquear´a hasta que lo
	* haya.
	 * @throws InterruptedException 
	*/
	public void almacenar(Producto producto) throws InterruptedException;
	/**
	* Extrae el primer producto disponible. Si no hay productos el
	* proceso que ejecute el método bloqueará hasta que se almacene un
	* dato.
	 * @throws InterruptedException 
	*/
	public Producto extraer() throws InterruptedException;
	
}
