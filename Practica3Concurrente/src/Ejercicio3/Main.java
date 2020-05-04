package Ejercicio3;

public class Main {

	private static final int M = 10;
	static AlmacenImp almacen = new AlmacenImp();
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread[] hilos = new Thread[2*M];
		for (int i = 0; i < M; ++i) {
			int j = i;
			hilos[i] = new Thread(()->{try {
				producir(j);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}});
			hilos[i].start();
		}
		
		for (int i = M; i < 2*M; ++i) {
			hilos[i] = new Thread(()->{try {
				consumir();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}});
			hilos[i].start();
		}
	}
	
	public static void producir(int idHilo) throws InterruptedException {
		// Todos los productores crean un producto que contiene el entero con el número de proceso que es
		// Segun su creación. Por ejemplo, el 4º hilo creará repetidamente el producto con valor 4
		while(true) {
			Producto prod = new Producto(idHilo);
			almacen.almacenar(prod);
		}
	}
	
	public static void consumir() throws InterruptedException {
		// No hacemos nada con el producto que nos devuelve la función ya se mostro por pantalla en exclusión mutua
		while(true) {
			almacen.extraer();
		}
	}
	
}
