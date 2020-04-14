package IncrementosDecrementos;

public class MonitorIncDec {
	private int n = 0;
	synchronized void inc() {
		n = n+1;
	}
	synchronized void dec() {
		n = n-1;
	}
	public int getN() {
		return n;
	}
}
