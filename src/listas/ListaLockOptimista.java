package src.listas;

public class ListaLockOptimista implements Lista {
	
    Node head;

    public ListaLockOptimista() {
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);
    }

    public boolean add(Object o){
        int key = o.hashCode();
        while(true){
            Node pred = head;
            Node curr = head.next;
            while(curr.key < key){
                pred = curr;
                curr = curr.next;
            }
            pred.lock(); 
            curr.lock();
            try {
                if(validate(pred, curr)){
                    if(curr.key == key){
                        return false;
                    }else{
                        Node node = new Node(o);
                        node.next = curr;
                        pred.next = node;
                        return true;
                    }
                }
            } finally {
                pred.unlock();
                curr.unlock();
            }
        }

    }

    public boolean remove(Object o){
        int key = o.hashCode();
        while(true){
            Node pred = head;
            Node curr = head.next;
            while(curr.key < key){
                pred = curr;
                curr = curr.next;
            }
            pred.lock(); 
            curr.lock();
            try {
                if(validate(pred, curr)){
                    if(curr.key == key){
                        pred.next = curr.next;
                        return true;
                    }else{
                        return false;
                    }
                }
            } finally {
                pred.unlock();
                curr.unlock();
            }
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

    private boolean validate(Node pred, Node curr){
        Node aux = head;
        while(aux.key <= pred.key){
            if (aux == pred){
                return pred.next == curr;
            }
            aux = aux.next;
        }
        return false;
    }
}
