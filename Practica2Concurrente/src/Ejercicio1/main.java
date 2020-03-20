package Ejercicio1;



public class main {

	private static final int M = 1, N = 100000;
	public static int n = 0;
	public volatile static boolean in1 = false, in2 = false;
	public volatile static int last = 1;
	
	public static void main(String[] args) throws InterruptedException {
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
		for (int i = 0; i < N; ++i) {
			in1 = true;
			last = 1;
			while(in2 && last == 1);
			n++;
			in1 = false;
		}
	}

	public static void funcionDec() {
		for (int i = 0; i < N; ++i) {
			in2 = true;
			last = 2;
			while(in1 && last == 2);
			n--;
			in2 = false;
		}
	}	
	

}
