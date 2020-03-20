package Ejercicio2;

public class main {

private static final int M = 1000, N = 10;
public static int n = 0;
private static LockRompeEmpate lock1 = new LockRompeEmpate(M);
private static LockTicket lock2 = new LockTicket(M);
private static LockBakery lock3 = new LockBakery(M);

	
	public static void main(String [] args) throws InterruptedException {
		Thread[] hilos = new Thread[2*M];
		
		for (int i = 0; i < M; ++i) {
			int j = i;
			hilos[i] = new Thread(()->funcionInc(j));
			hilos[i].start();
		}
		
		for (int i = M; i < 2*M; ++i) {
			int j = i;
			hilos[i] = new Thread(()->funcionDec(j));
			hilos[i].start();
		}
		
		for (int i = 0; i < 2*M; ++i) {
			hilos[i].join();
		}
		
		System.out.println("Ya han acabado\n");
		System.out.println(n);
	}

	public static void funcionInc(int i) {
		for (int j = 0;j < N; ++j) {
			lock3.takeLock(i);
			n++;
			lock3.releaseLock(i);
		}
	}

	public static void funcionDec(int i) {
		for (int j = 0;j < N; ++j) {
			lock3.takeLock(i);
			n--;
			lock3.releaseLock(i);
		}
	}
}
