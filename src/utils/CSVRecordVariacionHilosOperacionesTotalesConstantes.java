package src.utils;

public class CSVRecordVariacionHilosOperacionesTotalesConstantes {

    private int iteration;
    private int operacionesTotales;
    private int operacionesPorHilo;
    private int cantidadHilos;
    private double time_milliseconds;

    public CSVRecordVariacionHilosOperacionesTotalesConstantes(int iteration, int operacionesTotales,
            int operacionesPorHilo, int cantidadHilos, double time_milliseconds) {
        this.iteration = iteration;
        this.operacionesTotales = operacionesTotales;
        this.operacionesPorHilo = operacionesPorHilo;
        this.cantidadHilos = cantidadHilos;
        this.time_milliseconds = time_milliseconds;
    }

    public int getIteration() {
        return iteration;
    }

    public int getOperacionesTotales() {
        return operacionesTotales;
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
