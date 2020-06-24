package Ejercicio2;


import java.util.Arrays;

public class LockRompeEmpate {
	private static int M;
	public volatile int[] in, last;

	public LockRompeEmpate(int m) {
		super();
		M = m;
		in = new int[2 * M];
		last = new int[2 * M];
		Arrays.fill(in, -1);
		Arrays.fill(last, -1);
	}

	public void takeLock(int i) {
		for (int j = 0; j < 2 * M; ++j) {
			in[i] = j;
			in = in;
			last[j] = i;
			last = last;
			for (int k = 0; k < 2 * M; ++k) {
				if (k == i) continue;
				while (in[k] >= in[i] && last[j] == i) {
					Thread.yield();
				}
			}
		}
	}

	public void releaseLock(int i) {
		in[i] = -1;
		in = in;
	}

}
