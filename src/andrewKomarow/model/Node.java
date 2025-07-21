package andrewKomarow.model;

public class Node {
    public Task item;
    public Node next;
    public Node prev;

    public Node(Node prev, Task item, Node next) {
        this.item = item;
        this.next = next;
        this.prev = prev;
    }
}
