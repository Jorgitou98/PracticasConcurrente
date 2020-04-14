package MultiBufferProdConsLockCond;

import java.util.ArrayList;


public class MainMultiBuffer {
	private static final int M = 100;
	public static void main(String[] args) throws InterruptedException {
		ArrayList<Thread> hilos = new ArrayList<Thread>();
		MonitorMultiBufferLockCond monitor = new MonitorMultiBufferLockCond();
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
		// Me guardo los resultados para mostrarlos por pantalla y comprobar que funciona
		// Puede faltar alguno, el add al vector no es atómico y no se hace en exclusión mutua
		ArrayList<ArrayList<Producto>> result = new ArrayList<>();
		for (int i = M; i < 2*M; ++i) {
			int j = i;
			//El consumidor i consume i-M datos
			hilos.add(new Thread(()->{
				try {
					result.add(monitor.consumir(j - M));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}));
			hilos.get(i).start();
		}
		for (int i = M; i < 2*M; ++i) {
			hilos.get(i).join();
		}
		for(ArrayList<Producto> sols: result) {
			System.out.println("Conjunto de consumidos de longitud " + sols.size() + ": ");
			for(Producto p: sols) {
				System.out.print(p.getValor() + ",");
			}
			System.out.println();
		}
	}
}
