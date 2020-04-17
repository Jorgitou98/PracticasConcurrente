package MultiBufferProdConsSync;

import java.util.ArrayList;


public class MainMultiBufferSync {
	private static final int M = 100;
	public static void main(String[] args) throws InterruptedException {
		ArrayList<Thread> hilos = new ArrayList<Thread>();
		MonitorMultiBuffer monitor = new MonitorMultiBuffer();
		for (int i = 0; i < M; ++i) {
			//El proceso i mete i elementos con el valor i en un array. Será los que produce
			ArrayList<Producto> prod = new ArrayList<Producto>();
			for(int j = 0; j < i; ++j) {
				prod.add(new Producto(i));
			}
			hilos.add(new Thread(()->{
				try {
					monitor.producir(prod);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
			hilos.get(i).start();
		}
		for (int i = M; i < 2*M; ++i) {
			int j = i;
			//El consumidor i consume i-M datos
			hilos.add(new Thread(()->{
				try {
					monitor.consumir(j - M);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
			hilos.get(i).start();
		}
	}
}
