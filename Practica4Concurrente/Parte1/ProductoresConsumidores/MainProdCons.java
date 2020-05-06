package ProductoresConsumidores;

import java.util.ArrayList;

public class MainProdCons {
	private static final int M = 10;
	static MonitorProdCons monitor = new MonitorProdCons();
	public static void main(String[] args) throws InterruptedException {
		ArrayList<Thread> hilos = new ArrayList<Thread>();
		for (int i = 0; i < M; ++i) {
			int j = i;
			hilos.add(new Thread(()->{
				try {
					producir(j);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
			hilos.get(i).start();
		}
		for (int i = M; i < 2*M; ++i) {
			int j = i;
			hilos.add(new Thread(()->{
				try {
					consumir();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}));
			hilos.get(i).start();
		}
	}
	private static void producir(int idHilo) throws InterruptedException {
		while(true) {
			monitor.producir(new Producto(idHilo));
		}
	}
	private static void consumir() throws InterruptedException {
		while(true) {
			monitor.consumir();
		}
	}
	
}
