package Parte2;

public class main {

private static final int M = 10000, N = 100000;
public static int n = 0;
	
	public static void main(String [] args) throws InterruptedException {
		Thread[] hilos = new Thread[2*M];
		for (int i = 0; i < M; ++i) {
			hilos[i] = new Thread(()->funcionInc());
			hilos[i].start();
		}
		for (int i = M; i < 2*M; ++i) {
			hilos[i] = new Thread(()->funcionDec());
			hilos[i].start();
		}
		
		for (int i = 0; i < 2*M; ++i) {
			hilos[i].join();
		}
		System.out.println("Ya han acabado\n");
		System.out.println(n);
	}

	public static void funcionInc() {
		for (int i = 0; i < N; ++i) n++;
	}

	public static void funcionDec() {
		for (int i = 0; i < N; ++i) n--;
	}
}
