package Ejercicio3;

public class main {

	private static final int M = 10000, N = 1000;
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread[] hilos = new Thread[2*M];
		AlmacenImp almacen = new AlmacenImp();
		for (int i = 0; i < M; ++i) {
			int j = i;
			Producto prod = new Producto(i);
			hilos[i] = new Thread(()->{try {
				almacen.almacenar(prod);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}});
			hilos[i].start();
		}
		
		for (int i = M; i < 2*M; ++i) {
			int j = i;
			hilos[i] = new Thread(()->{try {
				System.out.println(almacen.extraer().getValor());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}});
			hilos[i].start();
		}
		
		for (int i = 0; i < 2*M; ++i) {
			hilos[i].join();
		}
		
		System.out.println("Ya han acabado\n");
	}
}
