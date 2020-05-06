package Ejercicio2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LockBakery {
	private static volatile int[] turnos;
	private int m;
	public LockBakery(int m) {
		super();
		this.m = m;
		turnos = new int[2*m];
		Arrays.fill(turnos,  -1);
		
	}
	
	public boolean mayor(Pair<Integer,Integer> p1, Pair<Integer,Integer> p2 ) {
		return (p1.getKey() > p2.getKey() || (p1.getKey() == p2.getKey() && p1.getValue() > p2.getValue()));
	}
	public void takeLock(int i) {
		turnos[i] = 0;
		turnos = turnos;
		turnos[i] = Arrays.stream(turnos).max().getAsInt() +1;
		turnos = turnos;
		for (int j = 0; j < 2*m; ++j) {
			if (j != i) {
				while(turnos[j] != -1 && mayor(new Pair(turnos[i], i), new Pair(turnos[j], j))) {
					Thread.yield(); //Esto es una cosa que dijo Arturo para que suelte el procesador (?)
				}
			}
		}
	}
	public void releaseLock(int i) {
		turnos[i] = -1;
		turnos = turnos;
	}
}
