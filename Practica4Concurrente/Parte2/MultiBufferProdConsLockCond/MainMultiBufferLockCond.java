package MultiBufferProdConsLockCond;

//Autores: Jorge Villarrubia, Beatriz Herguedas y Pablo Hern�ndez

public class MainMultiBufferLockCond {
	public static void main(String[] args) throws InterruptedException {
		(new HilosMultiBuffer()).ejecutaHilos();
	}
}
