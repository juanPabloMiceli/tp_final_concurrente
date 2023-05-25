package src.listas;

import src.listas.lockfree.ListaLockFree;
import src.listas.lockbound.granularidad_fina.ListaGranularidadFina;
import src.listas.lockbound.optimista.ListaLockOptimista;

public class ListaFactory {

    public static Lista getLista(ListaType listaType){
        switch(listaType) {
            case LOCK_FREE:
                return new ListaLockFree();
            case LOCK_OPTIMISTA:
                return new ListaLockOptimista();
            case GRANULARIDAD_FINA:
                return new ListaGranularidadFina();
            default:
                throw new IllegalArgumentException("Invalid ListaType: " + listaType);
        }
    }
}
