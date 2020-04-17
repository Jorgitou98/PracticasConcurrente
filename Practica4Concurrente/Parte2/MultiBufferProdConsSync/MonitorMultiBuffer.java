package MultiBufferProdConsSync;

import java.util.ArrayList;

public class MonitorMultiBuffer {
	private final int TAM = 500;
	private Producto[] buff = new Producto[TAM];
	private int ini = 0, fin = 0, cont = 0;
	
	synchronized void producir(ArrayList<Producto> prod) throws InterruptedException {
		while(TAM - cont < prod.size()) wait();
		for(Producto p : prod) {
			buff[ini] = p;
			ini = (ini + 1) % TAM;
			cont++;
		}
		notifyAll();
	}
	
	synchronized ArrayList<Producto> consumir(int cantidad) throws InterruptedException {
		while(cont < cantidad) wait();
		ArrayList<Producto> consumidos = new ArrayList<Producto>();
		for(int i = 0; i < cantidad; ++i) {
			consumidos.add(buff[fin]);
			fin = (fin + 1) % TAM;
			cont--;
		}
		// Muestro los elementos consumidos para comprobar yo que funciona bien
		System.out.print("Proceso que quería " + cantidad+ " elementos ha consumido: ");
		for(Producto elem: consumidos) {
			System.out.print(elem.getValor() + " ");
		}
		System.out.println();
		notifyAll();
		return consumidos;
	}
}
