package Parte1;

public class Parte1 {

	private static final int N = 10, T = 100;

	
	public static void main(String [] args) throws InterruptedException {
		Thread[] hilos = new Thread[N];
		for (int i = 0; i < N; ++i) {
			hilos[i] = new Thread(()->funcion());
			hilos[i].start();
		}
		
		for (int i = 0; i < N; ++i) {
			hilos[i].join();
		}
		System.out.println("Ya han acabado\n");
	}
	
	public static void funcion() {
		Thread currentThread = Thread.currentThread();
		System.out.println(currentThread.getId());
		try {
			Thread.sleep(T);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(currentThread.getId());
	}

}

