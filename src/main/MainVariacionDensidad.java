package src.main;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import java.io.FileWriter;
import java.io.IOException;

import src.listas.*;
import src.threads.*;
public class MainVariacionDensidad {
    public static void main(String[] args) {

        int numIterations = 10;
        int numOperations = 1000;
        double[] iterationTimes = new double[9];
        //Lista Granularidad Fina
        // Arrays.fill(iterationTimes, 0);
        // for(int iter = 0; iter < numIterations; iter++) {
        //     for(int densidadAdders = 1; densidadAdders <= 9; densidadAdders++) {
        //         ListaGranularidadFina lista = new ListaGranularidadFina();
        //         List<Thread> adders = new ArrayList<>();
        //         for(int i = 0; i < densidadAdders; i++){
        //             Runnable runnable = new AdderRunnable(lista, numOperations, "ADD Thread"+i);
        //             adders.add(new Thread(runnable));
        //         }
        //         List<Thread> removers = new ArrayList<>();

        //         for(int i = 0; i < 10 - densidadAdders; i++){
        //             Runnable runnable = new RemoverRunnable(lista, numOperations, "RM Thread"+i);
        //             removers.add(new Thread(runnable));
        //         }

        //         long start = System.nanoTime();
        //         for(Thread t : adders){
        //             t.start();
        //         }
        //         for(Thread t : removers){
        //             t.start();
        //         }
        //         try {
        //             for(Thread t : adders){
        //                 t.join();
        //             }
        //             for(Thread t : removers){
        //                 t.join();
        //             }
        //         } catch(Exception e){
        //             return;
        //         }
        //         long end = System.nanoTime();
        //         double milliseconds = (double) (end - start) / 1_000_000.0;
        //         // System.out.println("Tipo: " + lista.getClass().getSimpleName() + " Densidad: " + densidadAdders + " Duration: " + milliseconds);
        //         iterationTimes[densidadAdders - 1] += milliseconds;
        //     }
        //     System.out.printf("Iter: %d\n", iter);
        // }
        // for (int i = 0; i < iterationTimes.length; i++){
        //     iterationTimes[i] /= numIterations;
        // }
        // saveToCSV(iterationTimes, "outputGranularidadFina.csv");

        //Lista no concurrente (TEST)
        // try {
        //     ListaLockFree lista1 = new ListaLockFree();
        //     Thread t1 = new Thread(new AdderRunnable(lista1, numOperations, "ADD1"));
        //     Thread t2 = new Thread(new AdderRunnable(lista1, numOperations, "ADD2"));
        //     Thread t3 = new Thread(new RemoverRunnable(lista1, numOperations, "RM"));
        //     t1.start();
        //     t1.join();
        //     t2.start();
        //     t2.join();
        //     t3.start();
        //     t3.join();

        // } catch(Exception e){
        //     System.out.println("AAA");
        // }
        // Lista Lock Free
        Arrays.fill(iterationTimes, 0);
        for(int iter = 0; iter < numIterations; iter++) {
            for(int densidadAdders = 1; densidadAdders <= 9; densidadAdders++) {
                ListaLockFree lista = new ListaLockFree();
                List<Thread> adders = new ArrayList<>();
                for(int i = 0; i < densidadAdders; i++){
                    Runnable runnable = new AdderRunnable(lista, numOperations, "ADD Thread"+i);
                    adders.add(new Thread(runnable));
                }
                List<Thread> removers = new ArrayList<>();

                for(int i = 0; i < 10 - densidadAdders; i++){
                    Runnable runnable = new RemoverRunnable(lista, numOperations, "RM Thread"+i);
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
                // System.out.println("Tipo: " + lista.getClass().getSimpleName() + " Densidad: " + densidadAdders + " Duration: " + milliseconds);
                iterationTimes[densidadAdders - 1] += milliseconds;
            }
            System.out.printf("Iter: %d\n", iter);
        }
        for (int i = 0; i < iterationTimes.length; i++){
            iterationTimes[i] /= numIterations;
        }
        saveToCSV(iterationTimes, "outputLockFree.csv");

        // Lista Lock Optimista
        // Arrays.fill(iterationTimes, 0);
        // for(int iter = 0; iter < numIterations; iter++) {
        //     for(int densidadAdders = 1; densidadAdders <= 9; densidadAdders++) {
        //         ListaLockOptimista lista = new ListaLockOptimista();
        //         List<Thread> adders = new ArrayList<>();
        //         for(int i = 0; i < densidadAdders; i++){
        //             Runnable runnable = new AdderRunnable(lista, numOperations, "ADD Thread"+i);
        //             adders.add(new Thread(runnable));
        //         }
        //         List<Thread> removers = new ArrayList<>();

        //         for(int i = 0; i < 10 - densidadAdders; i++){
        //             Runnable runnable = new RemoverRunnable(lista, numOperations, "RM Thread"+i);
        //             removers.add(new Thread(runnable));
        //         }

        //         long start = System.nanoTime();
        //         for(Thread t : adders){
        //             t.start();
        //         }
        //         for(Thread t : removers){
        //             t.start();
        //         }
        //         try {
        //             for(Thread t : adders){
        //                 t.join();
        //             }
        //             for(Thread t : removers){
        //                 t.join();
        //             }
        //         } catch(Exception e){
        //             return;
        //         }
        //         long end = System.nanoTime();
        //         double milliseconds = (double) (end - start) / 1_000_000.0;
        //         // System.out.println("Tipo: " + lista.getClass().getSimpleName() + " Densidad: " + densidadAdders + " Duration: " + milliseconds);
        //         iterationTimes[densidadAdders - 1] += milliseconds;
        //     }
        //     System.out.printf("Iter: %d\n", iter);
        // }
        // for (int i = 0; i < iterationTimes.length; i++){
        //     iterationTimes[i] /= numIterations;
        // }
        // saveToCSV(iterationTimes, "outputLockOptimista.csv");
    }

    private static void saveToCSV(double[] data, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Iteration,ExecutionTime(ms)\n");

            for (int i = 0; i < data.length; i++) {
                writer.write((i + 1) + "," + data[i] + "\n");
            }

            System.out.println("Benchmark results saved to: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

