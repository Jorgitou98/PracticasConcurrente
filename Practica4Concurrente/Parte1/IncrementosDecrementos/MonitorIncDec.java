package IncrementosDecrementos;


public class MonitorIncDec {
	private volatile int n = 0;
	public synchronized void inc() {
		n = n+1;
	}
	public synchronized void dec() {
		n = n-1;
	}
	public int getN() {
		return n;
	}
}
