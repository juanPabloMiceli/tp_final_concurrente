package src.listas;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class ListaLockFree implements Lista {
	
    AtomicMarkableReference<NodeLockFree> head;
    AtomicMarkableReference<NodeLockFree> tail;

    public ListaLockFree() {
        head = new AtomicMarkableReference<>(new NodeLockFree(Integer.MIN_VALUE), false);
        tail = new AtomicMarkableReference<>(new NodeLockFree(Integer.MAX_VALUE), false);
        head.getReference().next = tail;
    }

    private NodeLockFree[] find(Object o){
        int key = o.hashCode();
        NodeLockFree pred, curr, succ;
        boolean[] marked ={false};
        boolean snip;
        retry: while (true) {
            pred = head.getReference();
            curr = pred.next.getReference();
            while (true) {
                if(curr == tail.getReference()){
                    NodeLockFree[] res = new NodeLockFree[2];
                    res[0] = pred;
                    res[1] = curr;
                    return res;
                }
                try {
                    succ = curr.next.get(marked);
                } catch (NullPointerException e){
                    System.out.printf("IntegerMax: %d, curr: %d\n", Integer.MAX_VALUE, curr.key);
                    throw new RuntimeException("NINOS");
                }
                while (marked[0]){
                    snip = pred.next.compareAndSet(curr, succ, false, false);
                    if (!snip) continue retry;
                    curr = succ;
                    succ = curr.next.get(marked);
                }
                if (curr.key >= key) {
                    NodeLockFree[] res = new NodeLockFree[2];
                    res[0] = pred;
                    res[1] = curr;
                    return res;
                }
                pred = curr;
                curr = succ;
            }
        }
    }

    public boolean add(Object o) {
        int key = o.hashCode();
        NodeLockFree[] predAndCurr;
        NodeLockFree pred, curr;
        while (true){
            predAndCurr = find(o);
            pred = predAndCurr[0];
            curr = predAndCurr[1];
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
        NodeLockFree[] predAndCurr;
        NodeLockFree pred, curr;
        boolean snip;
        while (true) {
            predAndCurr = find(o);
            pred = predAndCurr[0];
            curr = predAndCurr[1];
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
