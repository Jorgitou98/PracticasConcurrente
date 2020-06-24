package Ejercicio2;


public class IncDecProtegido {

	private final int M = 100, N = 10;
	public int n = 0;
	private LockRompeEmpate lock1 = new LockRompeEmpate(M);
	private LockTicket lock2 = new LockTicket(M);
	private LockBakery lock3 = new LockBakery(M);

	public void ejecutaHilos() throws InterruptedException {
		Thread[] hilos = new Thread[2 * M];

		for (int i = 0; i < M; ++i) {
			int j = i;
			hilos[i] = new Thread(() -> funcionInc(j));
			hilos[i].start();
		}

		for (int i = M; i < 2 * M; ++i) {
			int j = i;
			hilos[i] = new Thread(() -> funcionDec(j));
			hilos[i].start();
		}

		for (int i = 0; i < 2 * M; ++i) {
			hilos[i].join();
		}

		System.out.println("Ya han acabado\n");
		System.out.println(n);
	}

	private void funcionInc(int i) {
		for (int j = 0; j < N; ++j) {
			lock3.takeLock(i);
			n++;
			lock3.releaseLock(i);
		}
	}

	private void funcionDec(int i) {
		for (int j = 0; j < N; ++j) {
			lock3.takeLock(i);
			n--;
			lock3.releaseLock(i);
		}
	}
}
