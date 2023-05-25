package src.utils;

public class CSVRecordVariacionHilosOperacionesPorHiloConstantes {

    private int iteration;
    private int operacionesPorHilo;
    private int cantidadHilos;
    private double time_milliseconds;

    public CSVRecordVariacionHilosOperacionesPorHiloConstantes(int iteration, int operacionesPorHilo, int cantidadHilos,
            double time_milliseconds) {
        this.iteration = iteration;
        this.operacionesPorHilo = operacionesPorHilo;
        this.cantidadHilos = cantidadHilos;
        this.time_milliseconds = time_milliseconds;
    }

    public int getIteration() {
        return iteration;
    }

    public int getOperacionesPorHilo() {
        return operacionesPorHilo;
    }

    public int getCantidadHilos() {
        return cantidadHilos;
    }

    public double getTime_milliseconds() {
        return time_milliseconds;
    }
}
