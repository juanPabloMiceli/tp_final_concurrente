package src.threads;

import src.listas.Lista;
import java.util.Random;

public class RemoverRunnable implements Runnable {

    private Lista lista = null;
    private int N = 0;
    private String id = "";
    private Random random = null;

    public RemoverRunnable(Lista lista, int N, String id) {
        random = new Random();
        this.lista = lista;
        this.N = N;
        this.id = id;
    }

	@Override
	public void run() {
        for(int i = 0; i < N; i++){
            boolean exito = lista.remove(String.format("elem %d", random.nextInt(N)));
        }
	}

}
