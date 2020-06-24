package MultiBufferProdConsLockCond;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.*;

public class MonitorMultiBufferLockCond {
	private final Lock lock = new ReentrantLock();
	private final Condition esperaProd = lock.newCondition();
	private final Condition esperaCons = lock.newCondition();
	private final int TAM = 500;
	private volatile Producto[] buff = new Producto[TAM];
	private volatile int ini = 0, fin = 0, cont = 0;
	
	public void producir(List<Producto> prod) throws InterruptedException {
		lock.lock();
		while(TAM - cont < prod.size()) esperaProd.await();
		for(Producto p : prod) {
			buff[ini] = p;
			ini = (ini + 1) % TAM;
			cont++;
		}
		// Muestro los elementos producidos para comprobar yo que funciona bien
		System.out.print("Proceso que producía " + prod.size() + " elementos ha producido: ");
		for(Producto elem: prod) {
			System.out.print(elem.getValor() + " ");
		}
		System.out.println();
		esperaCons.signalAll();
		lock.unlock();
	}
	
	public List<Producto> consumir(int cantidad) throws InterruptedException {
		lock.lock();
		while(cont < cantidad) esperaCons.await();
		ArrayList<Producto> consumidos = new ArrayList<Producto>();
		// Consume la cantidad de elementos que quiere
		for(int i = 0; i < cantidad; ++i) {
			consumidos.add(buff[fin]);
			fin = (fin + 1) % TAM;
			cont--;
		}
		// Muestro los elementos consumidos para comprobar yo que funciona bien
		System.out.print("Proceso que quería " + cantidad+ " elementos ha consumido: ");
		for(Producto elem: consumidos) {
			System.out.print(elem.getValor() + " ");
		}
		System.out.println();
		
		esperaProd.signalAll();
		lock.unlock();
		return consumidos;		
	}
}
