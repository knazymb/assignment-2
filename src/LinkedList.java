public class LinkedList {

    private class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    private long accesses;
    private long movements;
    private long comparisons;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public void add(int x) {
        Node newNode = new Node(x);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;

            while (current.next != null) {
                current = current.next;
                accesses++;
            }

            current.next = newNode;
            movements++;
        }

        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        Node newNode = new Node(x);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
            movements++;
            size++;
            return;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            accesses++;
        }

        newNode.next = current.next;
        current.next = newNode;

        movements += 2;
        size++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("invalid index");
        }

        if (index == 0) {
            int removed = head.value;
            head = head.next;
            movements++;
            size--;
            return removed;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            accesses++;
        }

        int removed = current.next.value;

        current.next = current.next.next;
        movements++;

        size--;

        return removed;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("invalid index");
        }

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
            accesses++;
        }

        accesses++;

        return current.value;
    }

    public boolean contains(int x) {
        Node current = head;

        while (current != null) {
            comparisons++;

            if (current.value == x) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    public int size() {
        return size;
    }

    public long getAccesses() {
        return accesses;
    }

    public long getMovements() {
        return movements;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void resetMetrics() {
        accesses = 0;
        movements = 0;
        comparisons = 0;
    }
}