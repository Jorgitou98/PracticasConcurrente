package Ejercicio1;

import java.util.concurrent.Semaphore;

public class main {

	private static final int M = 10000, N = 1000;
	public static int n = 0;
	public static Semaphore sem = new Semaphore(1);

		
		public static void main(String [] args) throws InterruptedException{
			Thread[] hilos = new Thread[2*M];
			
			for (int i = 0; i < M; ++i) {
				int j = i;
				hilos[i] = new Thread(()->{
					try {
						funcionInc(j);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				hilos[i].start();
			}
			
			for (int i = M; i < 2*M; ++i) {
				int j = i;
				hilos[i] = new Thread(()->{
					try {
						funcionDec(j);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				hilos[i].start();
			}
			
			for (int i = 0; i < 2*M; ++i) {
				hilos[i].join();
			}
			
			System.out.println("Ya han acabado\n");
			System.out.println(n);
		}

		public static void funcionInc(int i) throws InterruptedException {
			for (int j = 0;j < N; ++j) {
				sem.acquire();
				n++;
				sem.release();
			}
		}

		public static void funcionDec(int i) throws InterruptedException {
			for (int j = 0;j < N; ++j) {
				sem.acquire();
				n--;
				sem.release();
			}
		}

}
