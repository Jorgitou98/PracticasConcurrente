package MultiBufferProdConsLockCond;

//Autores: Jorge Villarrubia, Beatriz Herguedas y Pablo Hernández

public class MainMultiBufferLockCond {
	public static void main(String[] args) throws InterruptedException {
		(new HilosMultiBuffer()).ejecutaHilos();
	}
}
