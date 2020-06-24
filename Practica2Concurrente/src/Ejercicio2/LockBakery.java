package Ejercicio2;


import java.util.Arrays;

public class LockBakery {
	private volatile int[] turnos;
	private int m;

	public LockBakery(int m) {
		this.m = m;
		turnos = new int[2 * m];
		Arrays.fill(turnos, -1);

	}

	private boolean mayor(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
		return (p1.getKey() > p2.getKey() || (p1.getKey() == p2.getKey() && p1.getValue() > p2.getValue()));
	}

	public void takeLock(int i) {
		turnos[i] = 0;
		turnos = turnos;
		turnos[i] = Arrays.stream(turnos).max().getAsInt() + 1;
		turnos = turnos;
		for (int j = 0; j < 2 * m; ++j) {
			if (j == i) continue;
			while (turnos[j] != -1 && mayor(new Pair<>(turnos[i], i), new Pair<>(turnos[j], j))) {
				Thread.yield();
			}
		}
	}


	public void releaseLock(int i) {
		turnos[i] = -1;
		turnos = turnos;
	}
}
