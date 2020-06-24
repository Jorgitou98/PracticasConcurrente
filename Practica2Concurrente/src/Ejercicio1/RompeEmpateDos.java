package Ejercicio1;


public class RompeEmpateDos {

	private final int M = 1, N = 100000;
	public int n = 0;
	public volatile boolean in1 = false, in2 = false;
	public volatile int last = 1;
	
	public void ejecutaHilos() throws InterruptedException {
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
	private void funcionInc() {
		for (int i = 0; i < N; ++i) {
			in1 = true;
			last = 1;
			while(in2 && last == 1);
			n++;
			in1 = false;
		}
	}

	private void funcionDec() {
		for (int i = 0; i < N; ++i) {
			in2 = true;
			last = 2;
			while(in1 && last == 2);
			n--;
			in2 = false;
		}
	}	
}
