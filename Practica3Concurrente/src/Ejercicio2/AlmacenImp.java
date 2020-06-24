package Ejercicio2;


import java.util.concurrent.Semaphore;

public class AlmacenImp implements Almacen{
	
	private Semaphore lleno = new Semaphore(0), vacio = new Semaphore(1);
	private volatile Producto buff;
	
	@Override
	public void almacenar(Producto producto) throws InterruptedException {
		vacio.acquire();
		buff = producto;
		System.out.println("Elemento producido: " + producto.getValor());
		lleno.release();		
	}

	@Override
	public Producto extraer() throws InterruptedException {
		Producto p;
		lleno.acquire();
		p = buff;
		System.out.println("Elemento consumido: " + p.getValor());
		vacio.release();
		return p;
	}

}
