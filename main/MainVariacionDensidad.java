package tp.main;

import java.util.ArrayList;
import java.util.List;

import tp.listas.ListaGranularidadFina;
import tp.listas.ListaLockFree;
import tp.listas.ListaLockOptimista;
import tp.listas.Lista;
import tp.threads.AdderRunnable;
import tp.threads.RemoverRunnable;
// 1. Como estructura general de los escenarios puede considerar que:
// • un escenario consiste de una cantidad fija de hilos que se crean al inicio del programa y comparten
// una ´unica estructura de datos concurrente.
// • todos los hilos realizar´an la misma cantidad fija de operaciones.
// • cada hilo realizar´a un s´olo tipo de operaci´on, pero distintos hilos pueden diferir en las operaciones
// que ejecutan.
// 2. Se deber´a analizar c´omo var´ıa el tiempo de ejecuci´on respecto de:
// • la variaci´on en la proporci´on de hilos que ejecutan determinadas operaciones, manteniendo constante la cantidad de hilos totales.
// • la variaci´on de la cantidad de hilos totales, si se preserva el n´umero total de operaciones.
// • la variaci´on de la cantidad de hilos totales, si se mantiene constante la cantidad de operaciones
// que ejecuta cada hilo.
// Para paliar la variabilidad en los resultados debido a aspectos no controlables, se deber´a replicar la
// ejecuci´on de cada escenario.
// 3. Se solicita:
// a) Implementar en Java las siguientes versiones de conjuntos sobre listas: (i) locks de granularidad
// fina, (ii) sincronizaci´on optimista y (iii) sin locks.
// b) Definir escenarios.
// c) Implementar en Java las clases que permitan ejecutar escenarios y tomar medidas sobre el tiempo
// de ejecuci´on.
// d) Ejecutar los experimentos y recoger resultados sobre el tiempo de ejecuci´on.
// e) Analizar los resultado obtenidos.
public class MainVariacionDensidad {
    public static void main(String[] args) {

        List<Lista> listas = List.of(new ListaGranularidadFina(), new ListaLockOptimista(), new ListaLockFree());
        for(Lista lista : listas) {
            for(int densidadAdders = 1; densidadAdders <= 9; densidadAdders++) {

                // ListaGranularidadFina lista = new ListaGranularidadFina();
                List<Thread> adders = new ArrayList<>();
                for(int i = 0; i < densidadAdders; i++){
                    Runnable runnable = new AdderRunnable(lista, 10000, "ADD Thread"+i);
                    adders.add(new Thread(runnable));
                }
                List<Thread> removers = new ArrayList<>();

                for(int i = 0; i < 10 - densidadAdders; i++){
                    Runnable runnable = new RemoverRunnable(lista, 10000, "RM Thread"+i);
                    removers.add(new Thread(runnable));
                }

                long start = System.nanoTime();
                for(Thread t : adders){
                    t.start();
                }
                for(Thread t : removers){
                    t.start();
                }
                try {
                    for(Thread t : adders){
                        t.join();
                    }
                    for(Thread t : removers){
                        t.join();
                    }
                } catch(Exception e){
                    return;
                }
                long end = System.nanoTime();
                double milliseconds = (double) (end - start) / 1_000_000.0;
                System.out.println("Tipo: " + lista.getClass().getSimpleName() + " Densidad: " + densidadAdders + " Duration: " + milliseconds);
            }
        }
    }
}

