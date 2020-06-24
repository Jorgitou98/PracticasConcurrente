package Ejercicio3;


import java.util.concurrent.Semaphore;

public class AlmacenImp implements Almacen{
	
	private static final int TAM = 10;
	private Semaphore lleno = new Semaphore(0), vacio = new Semaphore(TAM);
	private Semaphore mutexp = new Semaphore(1), mutexc = new Semaphore(1);
	private volatile Producto[] buff = new Producto[TAM];
	private volatile int ini = 0, fin = 0;
	@Override
	public void almacenar(Producto producto) throws InterruptedException {
		vacio.acquire();
		mutexp.acquire();
		buff[ini] = producto;
		buff = buff;
		System.out.println("Elemento producido: " + producto.getValor());
		ini = (ini + 1) % TAM;
		mutexp.release();
		lleno.release();
		
	}

	@Override
	public Producto extraer() throws InterruptedException {
		Producto p;
		lleno.acquire();
		mutexc.acquire();
		p = buff[fin];
		System.out.println("Elemento consumido: " + p.getValor());
		fin = (fin + 1) % TAM;
		mutexc.release();
		vacio.release();
		return p;
	}

}
