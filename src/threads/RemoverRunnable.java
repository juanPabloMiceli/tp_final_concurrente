package src.threads;

import src.listas.Lista;

public class RemoverRunnable implements Runnable {

    private Lista lista = null;
    private int N = 0;
    private String id = "";

    public RemoverRunnable(Lista lista, int N, String id) {
        this.lista = lista;
        this.N = N;
        this.id = id;
    }

	@Override
	public void run() {
        for(int i = 0; i < N; i++){
            boolean exito = lista.remove(String.format("elem %d", i));
            // if(exito) {
            // System.out.println(String.format("(%s) RM elem %d", id, i));
            // }
        }
	}

}
