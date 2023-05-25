package src.listas.lockfree;

import java.util.concurrent.atomic.AtomicMarkableReference;

import src.listas.Lista;

public class ListaLockFree implements Lista {
	
    AtomicMarkableReference<NodeLockFree> head;
    AtomicMarkableReference<NodeLockFree> tail;

    public ListaLockFree() {
	NodeLockFree headNode = new NodeLockFree(Integer.MIN_VALUE);
	NodeLockFree tailNode = new NodeLockFree(Integer.MAX_VALUE);
        head = new AtomicMarkableReference<>(headNode, false);
        tail = new AtomicMarkableReference<>(tailNode, false);
	headNode.next = new AtomicMarkableReference<>(tailNode, false);
	tailNode.next = new AtomicMarkableReference<>(null, false);

    }

    private Window find(Object o){
        int key = o.hashCode();
        NodeLockFree pred, curr, succ;
        boolean[] marked ={false};
        boolean snip;
        retry:
        while (true) {
            pred = head.getReference();
            curr = pred.next.getReference();
            while (true) {
                if(curr == tail.getReference()){
                    return new Window(pred, curr);
                }
                succ = curr.next.get(marked);
                while (marked[0]){
                    snip = pred.next.compareAndSet(curr, succ, false, false);
                    if (!snip) continue retry;
                    curr = succ;
                    succ = curr.next.get(marked);
                }
                if (curr.key >= key) {
                    return new Window(pred, curr);
                }
                pred = curr;
                curr = succ;
            }
        }
    }

    public boolean add(Object o) {
        int key = o.hashCode();
        while (true){
            Window window = find(o);
            NodeLockFree pred = window.pred;
            NodeLockFree curr = window.curr;
            if (curr.key == key){
                return false;
            } else{
                NodeLockFree node = new NodeLockFree(o);
                node.next = new AtomicMarkableReference<>(curr,false);
                if (pred.next.compareAndSet(curr, node, false, false)){
                    return true;
                }
            }
        }
    }


    public boolean remove (Object o) {
        int key = o.hashCode();
        boolean snip;
        while (true) {
            Window window = find(o);
            NodeLockFree pred = window.pred;
            NodeLockFree curr = window.curr;
            if (curr.key != key)
                return false;
            else {
                NodeLockFree succ = curr.next.getReference();
                snip = curr.next.attemptMark(succ, true);
                if (!snip)
                    continue;
                pred.next.compareAndSet(curr, succ, false, false);
                return true;
            }
        }
    }

    public void printLista() {
        NodeLockFree pred, curr;
        pred = head.getReference();
        curr = pred.next.getReference();
        while (curr.key != Integer.MAX_VALUE){
			System.out.printf("%s\t", curr.item);
            pred = curr;
            curr = curr.next.getReference();
        }
        System.out.println("");
    }

}
