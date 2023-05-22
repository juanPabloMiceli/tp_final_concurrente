package tp.listas;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class NodeLockFree {
    public Object item;
    public int key;
    public AtomicMarkableReference<NodeLockFree> next;

    public NodeLockFree(int key) {
        this.item = null;
        this.key = key;
        this.next = null;
    }

    public NodeLockFree(Object item) {
        this.item = item;
        this.key = item.hashCode();
        this.next = null;
    }
}
