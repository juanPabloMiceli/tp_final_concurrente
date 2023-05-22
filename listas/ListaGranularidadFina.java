package tp.listas;

public class ListaGranularidadFina implements Lista {
	
    Node head;

    public ListaGranularidadFina() {
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);
    }

    public boolean add(Object o){
        Node pred, curr;
        int key = o.hashCode();
        pred = head;
        head.lock();
        try {
            curr = pred.next;
            curr.lock();
            try {
                while (curr.key < key){
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (key == curr.key) {
                    return false;
                }
                Node node = new Node(o);
                node.next = curr;
                pred.next = node;
                return true;
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    public boolean remove(Object o){
        int key = o.hashCode();
        Node pred, curr;
        pred = head;
        head.lock();
        try {
            curr = pred.next;
            curr.lock();
            try {
                while (curr.key < key){
                    pred.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock();
                }
                if (key == curr.key) {
                    pred.next = curr.next;
                    return true;
                }
                return false;
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    public void printLista() {
        Node pred, curr;
        pred = head;
        curr = pred.next;
        while (curr.key != Integer.MAX_VALUE){
			System.out.printf("%s\t", curr.item);
            pred = curr;
            curr = curr.next;
        }
        System.out.println("");
    }

	
}
