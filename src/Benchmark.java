import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int REPETITIONS = 5;

    public static void main(String[] args) throws IOException {

        File resultsFolder = new File("results");

        if (!resultsFolder.exists()) {
            resultsFolder.mkdirs();
        }

        FileWriter writer = new FileWriter(
                "results/benchmark_results.csv"
        );

        writer.write(
                "workload,structure,n,operation,average_time_ns,metric,theoretical_complexity\n"
        );

        workload1(writer);
        workload2(writer);
        workload3(writer);
        workload4(writer);

        writer.close();

        System.out.println("Benchmark finished.");
        System.out.println("Results saved to results/benchmark_results.csv");
    }

    private static void workload1(FileWriter writer) throws IOException {

        System.out.println("Workload 1 - Random Access");

        for (int n : SIZES) {

            int[] values = generateValues(n);
            int[] indices = generateIndices(n, 10000);

            long arrayTime = 0;
            long listTime = 0;

            long arrayAccesses = 0;
            long listAccesses = 0;

            for (int run = 0; run < REPETITIONS; run++) {

                DynamicArray array = new DynamicArray();
                LinkedList list = new LinkedList();

                for (int value : values) {
                    array.add(value);
                    list.add(value);
                }

                array.resetMetrics();
                long start = System.nanoTime();

                for (int index : indices) {
                    array.get(index);
                }

                long end = System.nanoTime();

                arrayTime += end - start;
                arrayAccesses += array.getAccesses();

                list.resetMetrics();
                start = System.nanoTime();

                for (int index : indices) {
                    list.get(index);
                }

                end = System.nanoTime();

                listTime += end - start;
                listAccesses += list.getAccesses();
            }

            long averageArrayTime = arrayTime / REPETITIONS;
            long averageListTime = listTime / REPETITIONS;

            long averageArrayAccesses = arrayAccesses / REPETITIONS;
            long averageListAccesses = listAccesses / REPETITIONS;

            writeRow(
                    writer,
                    "Workload 1",
                    "Dynamic Array",
                    n,
                    "get",
                    averageArrayTime,
                    averageArrayAccesses,
                    "Theta(1)"
            );

            writeRow(
                    writer,
                    "Workload 1",
                    "Linked List",
                    n,
                    "get",
                    averageListTime,
                    averageListAccesses,
                    "Theta(n)"
            );

            System.out.println(
                    "n = " + n +
                            " Array = " + averageArrayTime +
                            " ns, List = " + averageListTime + " ns"
            );
        }
    }

    private static void workload2(FileWriter writer) throws IOException {

        System.out.println("Workload 2 - Search");

        for (int n : SIZES) {

            int[] values = generateValues(n);
            int[] searchValues = generateSearchValues(1000);

            long arrayTime = 0;
            long listTime = 0;

            long arrayComparisons = 0;
            long listComparisons = 0;

            for (int run = 0; run < REPETITIONS; run++) {

                DynamicArray array = new DynamicArray();
                LinkedList list = new LinkedList();

                for (int value : values) {
                    array.add(value);
                    list.add(value);
                }

                array.resetMetrics();

                long start = System.nanoTime();

                for (int value : searchValues) {
                    array.contains(value);
                }

                long end = System.nanoTime();

                arrayTime += end - start;
                arrayComparisons += array.getComparisons();

                list.resetMetrics();

                start = System.nanoTime();

                for (int value : searchValues) {
                    list.contains(value);
                }

                end = System.nanoTime();

                listTime += end - start;
                listComparisons += list.getComparisons();
            }

            long averageArrayTime = arrayTime / REPETITIONS;
            long averageListTime = listTime / REPETITIONS;

            long averageArrayComparisons =
                    arrayComparisons / REPETITIONS;

            long averageListComparisons =
                    listComparisons / REPETITIONS;

            writeRow(
                    writer,
                    "Workload 2",
                    "Dynamic Array",
                    n,
                    "contains",
                    averageArrayTime,
                    averageArrayComparisons,
                    "Theta(n)"
            );

            writeRow(
                    writer,
                    "Workload 2",
                    "Linked List",
                    n,
                    "contains",
                    averageListTime,
                    averageListComparisons,
                    "Theta(n)"
            );

            System.out.println(
                    "n = " + n +
                            " Array = " + averageArrayTime +
                            " ns, List = " + averageListTime + " ns"
            );
        }
    }

    private static void workload3(FileWriter writer) throws IOException {

        System.out.println("Workload 3 - Insertion and Removal");

        for (int n : SIZES) {

            int[] originalValues = generateValues(n);

            runInsertionAndRemoval(
                    writer,
                    n,
                    originalValues,
                    0,
                    "Beginning"
            );

            runInsertionAndRemoval(
                    writer,
                    n,
                    originalValues,
                    n / 2,
                    "Middle"
            );
        }
    }

    private static void runInsertionAndRemoval(
            FileWriter writer,
            int n,
            int[] originalValues,
            int index,
            String position
    ) throws IOException {

        long arrayInsertTime = 0;
        long listInsertTime = 0;

        long arrayRemoveTime = 0;
        long listRemoveTime = 0;

        long arrayInsertMovements = 0;
        long listInsertMovements = 0;

        long arrayRemoveMovements = 0;
        long listRemoveMovements = 0;

        for (int run = 0; run < REPETITIONS; run++) {

            DynamicArray array = new DynamicArray();
            LinkedList list = new LinkedList();

            for (int value : originalValues) {
                array.add(value);
                list.add(value);
            }

            array.resetMetrics();

            long start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                array.add(index, i);
            }

            long end = System.nanoTime();

            arrayInsertTime += end - start;
            arrayInsertMovements += array.getMovements();

            list.resetMetrics();

            start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                list.add(index, i);
            }

            end = System.nanoTime();

            listInsertTime += end - start;
            listInsertMovements += list.getMovements();

            array.resetMetrics();

            start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                array.remove(index);
            }

            end = System.nanoTime();

            arrayRemoveTime += end - start;
            arrayRemoveMovements += array.getMovements();

            list.resetMetrics();

            start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                list.remove(index);
            }

            end = System.nanoTime();

            listRemoveTime += end - start;
            listRemoveMovements += list.getMovements();
        }

        writeRow(
                writer,
                "Workload 3",
                "Dynamic Array",
                n,
                "insert-" + position,
                arrayInsertTime / REPETITIONS,
                arrayInsertMovements / REPETITIONS,
                "Theta(n)"
        );

        writeRow(
                writer,
                "Workload 3",
                "Linked List",
                n,
                "insert-" + position,
                listInsertTime / REPETITIONS,
                listInsertMovements / REPETITIONS,
                "Theta(n)"
        );

        writeRow(
                writer,
                "Workload 3",
                "Dynamic Array",
                n,
                "remove-" + position,
                arrayRemoveTime / REPETITIONS,
                arrayRemoveMovements / REPETITIONS,
                "Theta(n)"
        );

        writeRow(
                writer,
                "Workload 3",
                "Linked List",
                n,
                "remove-" + position,
                listRemoveTime / REPETITIONS,
                listRemoveMovements / REPETITIONS,
                "Theta(n)"
        );
    }

    private static void workload4(FileWriter writer) throws IOException {

        System.out.println("Workload 4 - Priority Processing");

        for (int n : SIZES) {

            int[] values = generateValues(n);

            long insertTime = 0;
            long extractTime = 0;

            long insertComparisons = 0;
            long extractComparisons = 0;

            for (int run = 0; run < REPETITIONS; run++) {

                MinHeap heap = new MinHeap();

                heap.resetMetrics();

                long start = System.nanoTime();

                for (int value : values) {
                    heap.insert(value);
                }

                long end = System.nanoTime();

                insertTime += end - start;
                insertComparisons += heap.getComparisons();

                heap.resetMetrics();

                start = System.nanoTime();

                int previous = Integer.MIN_VALUE;

                for (int i = 0; i < n; i++) {

                    int current = heap.extractMin();

                    if (current < previous) {
                        throw new RuntimeException(
                                "Heap extraction order is incorrect"
                        );
                    }

                    previous = current;
                }

                end = System.nanoTime();

                extractTime += end - start;
                extractComparisons += heap.getComparisons();
            }

            long averageInsertTime =
                    insertTime / REPETITIONS;

            long averageExtractTime =
                    extractTime / REPETITIONS;

            long averageInsertComparisons =
                    insertComparisons / REPETITIONS;

            long averageExtractComparisons =
                    extractComparisons / REPETITIONS;

            writeRow(
                    writer,
                    "Workload 4",
                    "Min-Heap",
                    n,
                    "insert",
                    averageInsertTime,
                    averageInsertComparisons,
                    "Theta(n log n)"
            );

            writeRow(
                    writer,
                    "Workload 4",
                    "Min-Heap",
                    n,
                    "extractMin",
                    averageExtractTime,
                    averageExtractComparisons,
                    "Theta(n log n)"
            );

            System.out.println(
                    "n = " + n +
                            " insert = " + averageInsertTime +
                            " ns, extract = " + averageExtractTime + " ns"
            );
        }
    }

    private static int[] generateValues(int n) {

        Random random = new Random(42);

        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = random.nextInt(1000000);
        }

        return values;
    }

    private static int[] generateIndices(int n, int amount) {

        Random random = new Random(42);

        int[] indices = new int[amount];

        for (int i = 0; i < amount; i++) {
            indices[i] = random.nextInt(n);
        }

        return indices;
    }

    private static int[] generateSearchValues(int amount) {

        Random random = new Random(42);

        int[] values = new int[amount];

        for (int i = 0; i < amount; i++) {
            values[i] = random.nextInt(1000000);
        }

        return values;
    }

    private static void writeRow(
            FileWriter writer,
            String workload,
            String structure,
            int n,
            String operation,
            long time,
            long metric,
            String complexity
    ) throws IOException {

        writer.write(
                workload + "," +
                        structure + "," +
                        n + "," +
                        operation + "," +
                        time + "," +
                        metric + "," +
                        complexity + "\n"
        );
    }
}