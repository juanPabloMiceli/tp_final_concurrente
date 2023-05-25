package src.utils;

public class CSVRecordVariacionDensidad {

    private int iteration;
    private int operacionesPorHilo;
    private int n_adders;
    private int n_removers;
    private double time_milliseconds;
    
    public CSVRecordVariacionDensidad(int iteration, int operacionesPorHilo, int n_adders, int n_removers, double time_milliseconds) {
        this.iteration = iteration;
        this.operacionesPorHilo = operacionesPorHilo;
        this.n_adders = n_adders;
        this.n_removers = n_removers;
        this.time_milliseconds = time_milliseconds;
    }

    public int getIteration() {
        return iteration;
    }

    public int getOperacionesPorHilo() {
        return operacionesPorHilo;
    }

    public int getN_adders() {
        return n_adders;
    }

    public int getN_removers() {
        return n_removers;
    }

    public double getTime_milliseconds() {
        return time_milliseconds;
    }

    

}
