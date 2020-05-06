package MultiBufferProdConsSync;

import java.util.ArrayList;

import MultiBufferProdConsLockCond.Producto;


public class MainMultiBufferSync {
	private static final int M = 100;
	static MonitorMultiBuffer monitor = new MonitorMultiBuffer();
	public static void main(String[] args) throws InterruptedException {
		ArrayList<Thread> hilos = new ArrayList<Thread>();
		for (int i = 0; i < M; ++i) {
			int j = i;
			//El proceso i mete i elementos con el valor i en un array. Será los que produce
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
			//El consumidor i consume i-M datos
			hilos.add(new Thread(()->{
				try {
					consumir(j - M);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
			hilos.get(i).start();
		}
	}
	private static void producir(int idHilo) throws InterruptedException {
		while(true) {
			//El proceso i mete i elementos con el valor i en un array. Será los que produce
			ArrayList<Producto> prod = new ArrayList<Producto>();
			for(int j = 0; j < idHilo; ++j) {
				prod.add(new Producto(idHilo));
			}
	
			monitor.producir(prod);
		}
	}
	private static void consumir(int cantidad) throws InterruptedException {
		while(true) {
			monitor.consumir(cantidad);
		}
	}
	
}
