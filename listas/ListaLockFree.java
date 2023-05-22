package tp.listas;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class ListaLockFree implements Lista {
	
    AtomicMarkableReference<NodeLockFree> head;

    public ListaLockFree() {
        head = new AtomicMarkableReference(new NodeLockFree(Integer.MIN_VALUE), false);
        head.getReference().next = new AtomicMarkableReference(new NodeLockFree(Integer.MAX_VALUE), false);
    }

    private NodeLockFree[] find(Object o){
        NodeLockFree pred, curr;
        boolean[] marked ={false};
        boolean snip;
        int key = o.hashCode();
        tryAgain: while (true) {
            pred = head.getReference();
            while (true) {
                curr = pred.next.get(marked);
                while (marked[0]){
                    NodeLockFree succ = curr.next.getReference();
                    snip = pred.next.compareAndSet(curr, succ, false, false);
                    if (!snip) continue tryAgain;
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
                curr = pred.next.getReference();
            }
        }
    }

    public boolean add(Object o) {
        NodeLockFree[] predAndCurr;
        NodeLockFree pred, curr;
        int key = o.hashCode();
        while (true){
            predAndCurr = find(o);
            pred = predAndCurr[0];
            curr = predAndCurr[1];
            if (curr.key == key){
                return false;
            } else{
                NodeLockFree node = new NodeLockFree(o);
                node.next = new AtomicMarkableReference(curr,false);
                if (pred.next.compareAndSet(curr, node, false, false)){
                    return true;
                }
            }
        }
    }


    public boolean remove (Object o) {
        NodeLockFree[] predAndCurr;
        NodeLockFree pred, curr;
        int key = o.hashCode();
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
