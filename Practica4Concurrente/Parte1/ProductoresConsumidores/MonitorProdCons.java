package ProductoresConsumidores;

public class MonitorProdCons {
	private final int TAM = 1000;
	private Producto[] buff = new Producto[TAM];
	private int ini = 0, fin = 0, cont = 0;
	
	synchronized void producir(Producto prod) throws InterruptedException {
		while(cont == TAM) wait();
		buff[ini] = prod;
		ini = (ini + 1) % TAM;
		cont++;
		notify();
	}
	
	synchronized Producto consumir() throws InterruptedException  {
		Producto p;
		while(cont == 0) wait();
		p = buff[fin];
		fin= (fin + 1) % TAM;
		cont--;
		notify();
		return p;
	}
}
