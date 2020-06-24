package Ejercicio2;


import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket {
	private volatile int next = 0;
	private volatile AtomicInteger number = new AtomicInteger(0);
	private volatile int turno[];
	
	public LockTicket(int m) {
		turno = new int [2*m];
	}
	
	public void takeLock(int i) {
		turno[i] = number.getAndAdd(1);
		turno = turno;
		while(turno[i]!= next) {
			Thread.yield();
		}
	}
	
	public void releaseLock() {
		next = next+1;
	}
	
}
