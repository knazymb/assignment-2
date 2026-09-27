public class DynamicArray {

    private int[] data;
    private int size;

    private long accesses;
    private long movements;
    private long comparisons;

    public DynamicArray() {
        data = new int[10];
        size = 0;
    }

    public void add(int x) {
        if (size == data.length) {
            resize();
        }

        data[size] = x;
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        if (size == data.length) {
            resize();
        }

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            movements++;
        }

        data[index] = x;
        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        int removed = data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            movements++;
        }

        size--;

        return removed;
    }

    public int get(int index) {
        checkIndex(index);

        accesses++;

        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            comparisons++;

            if (data[i] == x) {
                return true;
            }
        }

        return false;
    }

    private void resize() {
        int[] newData = new int[data.length * 2];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
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