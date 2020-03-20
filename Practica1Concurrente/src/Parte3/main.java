package Parte3;

public class main {

private static final int N = 3;
public static int[][] A = new int[N][N];
public static int[][] B = new int[N][N];
public static int[][] C = new int[N][N];
	public static void main(String [] args) throws InterruptedException {
		Thread[] hilos = new Thread[N];
		for (int i = 0; i < N; ++i)
			for (int j = 0; j < N; ++j) {
				A[i][j] = 1;
				B[i][j] = 2;
			}
		
		for (int i = 0; i < N; ++i) {
			int j = i;
			hilos[i] = new Thread(()->mulFila(j, A[j], B));
			hilos[i].start();
		}
		
		for (int i = 0; i < N; ++i) {
			hilos[i].join();
		}
		for (int i = 0; i < N; ++i)
			for (int j = 0; j < N; ++j) {
				System.out.println(C[i][j]);
			}
	} 

	public static void mulFila(int i, int[] fila, int[][] B) {
		int suma;
		for (int j = 0; j < N; ++j) {
			suma = 0;
			for (int k = 0; k < N; ++k) {
				suma += fila[k]*B[k][j];
			}
			C[i][j] = suma;
		}
	}

}
