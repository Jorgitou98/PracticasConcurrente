package ProductoresConsumidores;

import java.util.ArrayList;

public class MainProdCons {
	private static final int M = 10000;
	public static void main(String[] args) throws InterruptedException {
		ArrayList<Thread> hilos = new ArrayList<Thread>();
		MonitorProdCons monitor = new MonitorProdCons();
		for (int i = 0; i < M; ++i) {
			int j = i;
			hilos.add(new Thread(()->{
				try {
					monitor.producir(new Producto(j));
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
					monitor.consumir();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
			hilos.get(i).start();
		}
	}
}
