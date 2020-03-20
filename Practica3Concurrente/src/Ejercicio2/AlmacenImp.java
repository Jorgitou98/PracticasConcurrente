package Ejercicio2;

import java.util.concurrent.Semaphore;

public class AlmacenImp implements Almacen{
	
	public Semaphore lleno = new Semaphore(0), vacio = new Semaphore(1);
	public Producto buff;
	
	@Override
	public void almacenar(Producto producto) throws InterruptedException {
		vacio.acquire();
		buff = producto;
		lleno.release();
		
	}

	@Override
	public Producto extraer() throws InterruptedException {
		Producto p;
		lleno.acquire();
		p = buff;
		vacio.release();
		return p;
	}

}
