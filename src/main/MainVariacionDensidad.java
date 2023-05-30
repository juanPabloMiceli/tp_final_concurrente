package src.main;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
import java.io.IOException;

import src.listas.*;
import src.threads.*;
import src.utils.*;

public class MainVariacionDensidad {
    public static void main(String[] args) {

        int numIterations = 50;
        int numOperations = 10000;

        runExperiment(numIterations, numOperations, ListaType.LOCK_FREE);
        runExperiment(numIterations, numOperations, ListaType.LOCK_OPTIMISTA);
        runExperiment(numIterations, numOperations, ListaType.GRANULARIDAD_FINA);
    }

    private static void runExperiment(int numIterations, int numOperacionesPorHilo, ListaType listaType) {
        List<CSVRecordVariacionDensidad> mediciones = new ArrayList<>();
        for(int iter = 0; iter < numIterations; iter++) {
            for(int densidadAdders = 1; densidadAdders <= 9; densidadAdders++) {
                Lista lista = ListaFactory.getLista(listaType);
                List<Thread> adders = new ArrayList<>();
                for(int i = 0; i < densidadAdders; i++){
                    Runnable runnable = new AdderRunnable(lista, numOperacionesPorHilo, "ADD Thread"+i);
                    adders.add(new Thread(runnable));
                }
                List<Thread> removers = new ArrayList<>();

                for(int i = 0; i < 10 - densidadAdders; i++){
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
                mediciones.add(new CSVRecordVariacionDensidad(iter, numOperacionesPorHilo, densidadAdders, 10 - densidadAdders, milliseconds));
            }
            System.out.printf("Iter: %d\n", iter);
        }
        String[] headers = {"Iteracion", "operacionesPorHilo", "numAdders", "numRemovers", "Time (ms)"};
        CSVWriter.writeCSV(mediciones, headers, "data/MainVariacionDensidad_"+ listaType.name() +".csv" );
    }
}

