package src.listas.lockfree;

class Window {
    public NodeLockFree pred;
    public NodeLockFree curr;

    public Window(NodeLockFree pred, NodeLockFree curr){
        this.pred = pred;
        this.curr = curr;
    }
}
