package MultiBufferProdConsLockCond;

import java.util.ArrayList;
import java.util.concurrent.locks.*;

public class MonitorMultiBufferLockCond {
	final Lock lock = new ReentrantLock();
	final Condition esperaProd = lock.newCondition();
	final Condition esperaCons = lock.newCondition();
	private final int TAM = 500;
	private Producto[] buff = new Producto[TAM];
	private int ini = 0, fin = 0, cont = 0;
	
	public void producir(ArrayList<Producto> prod) throws InterruptedException {
		lock.lock();
		while(TAM - cont < prod.size()) esperaProd.await();
		for(Producto p : prod) {
			buff[ini] = p;
			ini = (ini + 1) % TAM;
			cont++;
		}
		esperaCons.signalAll();
		lock.unlock();
	}
	
	public ArrayList<Producto> consumir(int cantidad) throws InterruptedException {
		lock.lock();
		while(cont < cantidad) wait();
		ArrayList<Producto> consumidos = new ArrayList<Producto>();
		for(int i = 0; i < cantidad; ++i) {
			consumidos.add(buff[fin]);
			fin = (fin + 1) % TAM;
			cont--;
		}
		esperaProd.signalAll();
		lock.unlock();
		return consumidos;
		
	}
}
