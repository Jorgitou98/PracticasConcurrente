package IncrementosDecrementos;

import java.util.ArrayList;

public class MainIncDec {
	private static final int M = 10000;
	public static void main(String[] args) throws InterruptedException {
		ArrayList <Thread> hilos = new ArrayList<Thread>();
		MonitorIncDec monitor = new MonitorIncDec();
		for (int i = 0; i < M; ++i) {
			hilos.add(new Thread(() ->monitor.inc()));
			hilos.get(i).start();
		}
		for (int i = M; i < 2*M; ++i) {
			hilos.add(new Thread(() ->monitor.dec()));
			hilos.get(i).start();
		}

		for (Thread hilo: hilos) {
			hilo.join();
		}
		System.out.println("Resultado final: " + monitor.getN());
	}

}
