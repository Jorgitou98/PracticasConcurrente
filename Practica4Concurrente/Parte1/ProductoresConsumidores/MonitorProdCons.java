package ProductoresConsumidores;


public class MonitorProdCons {
	private final int TAM = 1000;
	private volatile Producto[] buff = new Producto[TAM];
	private volatile int ini = 0, fin = 0, cont = 0;
	
	public synchronized void producir(Producto prod) throws InterruptedException {
		while(cont == TAM) wait();
		buff[ini] = prod;
		buff = buff;
		System.out.println("Producido: " + prod.getValor());
		ini = (ini + 1) % TAM;
		cont++;
		notifyAll();
	}
	
	public synchronized Producto consumir() throws InterruptedException  {
		Producto p;
		while(cont == 0) wait();
		p = buff[fin];
		// Muestro el producto consumido por consola. (Esto es solo para mí, para comprobar que funciona)
		System.out.println("Consumido: " + p.getValor());
		fin= (fin + 1) % TAM;
		cont--;
		notifyAll();
		return p;
		
	}
}
