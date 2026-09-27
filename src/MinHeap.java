public class MinHeap {

    private int[] data;
    private int size;

    private long comparisons;

    public MinHeap() {
        data = new int[10];
        size = 0;
    }

    public void insert(int x) {
        if (size == data.length) {
            resize();
        }

        data[size] = x;

        int current = size;
        size++;

        while (current > 0) {
            int parent = (current - 1) / 2;

            comparisons++;

            if (data[parent] <= data[current]) {
                break;
            }

            swap(parent, current);

            current = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        return data[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int min = data[0];

        data[0] = data[size - 1];
        size--;

        int current = 0;

        while (true) {
            int left = current * 2 + 1;
            int right = current * 2 + 2;

            if (left >= size) {
                break;
            }

            int smallerChild = left;

            if (right < size) {
                comparisons++;

                if (data[right] < data[left]) {
                    smallerChild = right;
                }
            }

            comparisons++;

            if (data[current] <= data[smallerChild]) {
                break;
            }

            swap(current, smallerChild);

            current = smallerChild;
        }

        return min;
    }

    private void swap(int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private void resize() {
        int[] newData = new int[data.length * 2];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
    }

    public int size() {
        return size;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void resetMetrics() {
        comparisons = 0;
    }

    public boolean isHeapPropertyValid() {
        for (int i = 0; i < size; i++) {
            int left = i * 2 + 1;
            int right = i * 2 + 2;

            if (left < size && data[i] > data[left]) {
                return false;
            }

            if (right < size && data[i] > data[right]) {
                return false;
            }
        }

        return true;
    }
}