package ProductoresConsumidores;


import java.util.ArrayList;

public class HilosProdCons {
	private final int M = 10;
	private MonitorProdCons monitor = new MonitorProdCons();
	
	public void ejecutaHilos() throws InterruptedException {
		ArrayList<Thread> hilos = new ArrayList<Thread>();
		for (int i = 0; i < M; ++i) {
			int j = i;
			hilos.add(new Thread(()->{
				try {
					producir(j);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}));
			hilos.get(i).start();
		}
		for (int i = M; i < 2*M; ++i) {
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
	private void producir(int idHilo) throws InterruptedException {
		while(true) {
			monitor.producir(new Producto(idHilo));
		}
	}
	private void consumir() throws InterruptedException {
		while(true) {
			monitor.consumir();
		}
	}
	
}
