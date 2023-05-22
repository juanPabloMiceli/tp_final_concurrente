public class MainLockOptimista {
    public static void main(String[] args) {
        ListaLockOptimista l = new ListaLockOptimista();
        String e1 = new String("Te estas portando mal");
        String e2 = new String("Seras castigada");
        String e3 = new String("Estas estudiando mal");
        String e4 = new String("Seras recursada");
        l.add(e1);
        l.add(e2);
        l.add(e3);
        l.add(e4);
        l.printLista();
        l.remove(e3.hashCode());
        l.remove(e4.hashCode());
        l.printLista();
    }
}
