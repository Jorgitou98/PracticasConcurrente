package MultiBufferProdConsSync;

import java.util.ArrayList;
import java.util.List;

import MultiBufferProdConsLockCond.Producto;

public class MonitorMultiBuffer {
	private final int TAM = 500;
	private Producto[] buff = new Producto[TAM];
	private int ini = 0, fin = 0, cont = 0;
	
	synchronized void producir(List<Producto> prod) throws InterruptedException {
		while(TAM - cont < prod.size()) wait();
		for(Producto p : prod) {
			buff[ini] = p;
			ini = (ini + 1) % TAM;
			cont++;
		}
		
		// Muestro los elementos producidos para comprobar yo que funciona bien
		System.out.print("Proceso que produc�a " + prod.size() + " elementos ha producido: ");
		for(Producto elem: prod) {
			System.out.print(elem.getValor() + " ");
		}
		System.out.println();
		
		notifyAll();
	}
	
	synchronized List<Producto> consumir(int cantidad) throws InterruptedException {
		while(cont < cantidad) wait();
		List<Producto> consumidos = new ArrayList<Producto>();
		for(int i = 0; i < cantidad; ++i) {
			consumidos.add(buff[fin]);
			fin = (fin + 1) % TAM;
			cont--;
		}
		// Muestro los elementos consumidos para comprobar yo que funciona bien
		System.out.print("Proceso que quer�a " + cantidad+ " elementos ha consumido: ");
		for(Producto elem: consumidos) {
			System.out.print(elem.getValor() + " ");
		}
		System.out.println();
		notifyAll();
		return consumidos;
	}
}
