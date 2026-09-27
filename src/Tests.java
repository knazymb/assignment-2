public class Tests {

    public static void main(String[] args) {

        testDynamicArray();
        testLinkedList();
        testMinHeap();

        System.out.println("All tests passed.");
    }

    private static void testDynamicArray() {

        DynamicArray array = new DynamicArray();

        if (array.size() != 0) {
            throw new RuntimeException("Empty array test failed");
        }

        array.add(10);

        if (array.get(0) != 10) {
            throw new RuntimeException("One element test failed");
        }

        array.add(20);
        array.add(30);
        array.add(20);

        if (!array.contains(20)) {
            throw new RuntimeException("Contains test failed");
        }

        array.add(1, 15);

        if (array.get(1) != 15) {
            throw new RuntimeException("Insert test failed");
        }

        array.remove(1);

        if (array.get(1) != 20) {
            throw new RuntimeException("Remove test failed");
        }

        try {
            array.get(-1);
            throw new RuntimeException("Invalid index test failed");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            array.get(100);
            throw new RuntimeException("Invalid index test failed");
        } catch (IndexOutOfBoundsException e) {
        }

        DynamicArray largeArray = new DynamicArray();

        for (int i = 0; i < 100000; i++) {
            largeArray.add(i);
        }

        if (largeArray.size() != 100000) {
            throw new RuntimeException("Large input test failed");
        }

        System.out.println("DynamicArray tests passed.");
    }

    private static void testLinkedList() {

        LinkedList list = new LinkedList();

        if (list.size() != 0) {
            throw new RuntimeException("Empty list test failed");
        }

        list.add(10);

        if (list.get(0) != 10) {
            throw new RuntimeException("One element test failed");
        }

        list.add(20);
        list.add(30);
        list.add(20);

        if (!list.contains(20)) {
            throw new RuntimeException("Contains test failed");
        }

        list.add(1, 15);

        if (list.get(1) != 15) {
            throw new RuntimeException("Insert test failed");
        }

        list.remove(1);

        if (list.get(1) != 20) {
            throw new RuntimeException("Remove test failed");
        }

        try {
            list.get(-1);
            throw new RuntimeException("Invalid index test failed");
        } catch (IndexOutOfBoundsException e) {
        }

        try {
            list.get(100);
            throw new RuntimeException("Invalid index test failed");
        } catch (IndexOutOfBoundsException e) {
        }

        LinkedList largeList = new LinkedList();

        for (int i = 0; i < 100000; i++) {
            largeList.add(i);
        }

        if (largeList.size() != 100000) {
            throw new RuntimeException("Large input test failed");
        }

        System.out.println("LinkedList tests passed.");
    }

    private static void testMinHeap() {

        MinHeap heap = new MinHeap();

        if (heap.size() != 0) {
            throw new RuntimeException("Empty heap test failed");
        }

        heap.insert(10);

        if (heap.peekMin() != 10) {
            throw new RuntimeException("Peek test failed");
        }

        heap.insert(5);
        heap.insert(20);
        heap.insert(3);
        heap.insert(3);
        heap.insert(15);

        if (!heap.isHeapPropertyValid()) {
            throw new RuntimeException("Heap property failed after insertion");
        }

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {
            int current = heap.extractMin();

            if (current < previous) {
                throw new RuntimeException("Heap extraction order failed");
            }

            previous = current;

            if (!heap.isHeapPropertyValid()) {
                throw new RuntimeException("Heap property failed after extraction");
            }
        }

        try {
            heap.peekMin();
            throw new RuntimeException("Empty heap peek test failed");
        } catch (IllegalStateException e) {
        }

        try {
            heap.extractMin();
            throw new RuntimeException("Empty heap extraction test failed");
        } catch (IllegalStateException e) {
        }

        MinHeap largeHeap = new MinHeap();

        for (int i = 100000; i >= 1; i--) {
            largeHeap.insert(i);
        }

        if (!largeHeap.isHeapPropertyValid()) {
            throw new RuntimeException("Large heap test failed");
        }

        System.out.println("MinHeap tests passed.");
    }
}