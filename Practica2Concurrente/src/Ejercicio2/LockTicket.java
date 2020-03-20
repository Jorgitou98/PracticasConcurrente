package Ejercicio2;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket {
	private int m;
	private static volatile int next = 0;
	private static volatile AtomicInteger number = new AtomicInteger(0);
	private static volatile int turno[];
	public LockTicket(int m) {
		super();
		this.m = m;
		turno = new int [2*m];
		Arrays.fill(turno, -1);
	}
	
	public void takeLock(int i) {
		turno[i] = number.getAndAdd(1);
		turno = turno;
		while(turno[i]!= next) {
			Thread.yield();
		}
	}
	
	public void releaseLock(int i) {
		next = next+1;
	}
	
}
