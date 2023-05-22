package tp.listas;

import java.util.concurrent.locks.ReentrantLock;

public class Node {
    public Object item;
    public int key;
    public Node next;
    private ReentrantLock lock = new ReentrantLock();

    public Node(int key) {
        this.item = null;
        this.key = key;
        this.next = null;
    }

    public Node(Object item) {
        this.item = item;
        this.key = item.hashCode();
        this.next = null;
    }

    public void lock() {
        lock.lock();
    }
    public void unlock() {
        lock.unlock();
    }

}
