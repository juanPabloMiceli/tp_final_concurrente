package src.main;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
import java.io.IOException;

import src.listas.*;
import src.threads.*;
import src.utils.*;
public class MainVariacionHilosOperacionesPorHiloConstantes {
    public static void main(String[] args) {

        int numIterations = 50;
        int numOperacionesPorHilo = 10000;
        
        runExperiment(numIterations, numOperacionesPorHilo, ListaType.LOCK_FREE);
        runExperiment(numIterations, numOperacionesPorHilo, ListaType.LOCK_OPTIMISTA);
        runExperiment(numIterations, numOperacionesPorHilo, ListaType.GRANULARIDAD_FINA);
    }

    private static void runExperiment(int numIterations, int numOperacionesPorHilo, ListaType listaType) {
        List<CSVRecordVariacionHilosOperacionesPorHiloConstantes> mediciones = new ArrayList<>();
        for(int iter = 0; iter < numIterations; iter++) {
            for(int hilos = 1; hilos <= 5; hilos++) {
                Lista lista = ListaFactory.getLista(listaType);
                List<Thread> adders = new ArrayList<>();
                for(int i = 0; i < hilos; i++){
                    Runnable runnable = new AdderRunnable(lista, numOperacionesPorHilo, "ADD Thread"+i);
                    adders.add(new Thread(runnable));
                }
                List<Thread> removers = new ArrayList<>();

                for(int i = 0; i < hilos; i++){
                    Runnable runnable = new RemoverRunnable(lista, numOperacionesPorHilo, "RM Thread"+i);
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
                mediciones.add(new CSVRecordVariacionHilosOperacionesPorHiloConstantes(iter, numOperacionesPorHilo, hilos*2, milliseconds));
            }
            System.out.printf("Iter: %d\n", iter);
        }
        String[] headers = {"Iteracion", "OperacionesPorHilo", "CantidadHilos", "Time (ms)"};
        CSVWriter.writeCSV(mediciones, headers, "data/MainVariacionHilosOperacionesPorHiloConstantes_"+ listaType.name() +".csv" );
    }
}

