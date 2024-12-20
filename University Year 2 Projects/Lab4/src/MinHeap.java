import java.util.Arrays;

/**
 * A minheap class that stores Indexs based on a key
 */
public class MinHeap {
    int[] IndexHeap = new int[5000];
    double[] keyHeap = new double[5000];
    int size = 0;

    /**
     * Inserts a new Index into the heap
     *
     * @param Index the Index to be inserted
     * @param key       the key of the Index
     */
    public void insert(int Index, double key) {
        size++;
        IndexHeap[size] = Index;
        keyHeap[size] = key;
        trickleUp(size);
    }

    /**
     * Removes and returns the Index with the lowest key
     *
     * @return the Index with the lowest key
     */
    public int removeMin() {
        int min = IndexHeap[1];
        //System.out.println("Min " + keyHeap[1]);
        if (min == 0) {
            size = 0;
            return 0;
        }
        swap(1, size);
        keyHeap[size] = 0;
        IndexHeap[size] = 0;
        size--;
        trickleDown(1);

        return min;
    }

    /**
     * Trickles down starting at index i
     *
     * @param i the index of the node to trickle down
     */
    private void trickleDown(int i) {
        boolean restored = false;
        while (!restored) {
            double key = keyHeap[i];
            double leftChildKey;
            double rightChildKey;
            if (2 * i < keyHeap.length && keyHeap[2 * i] != 0) {
                leftChildKey = keyHeap[2 * i];
            } else {
                leftChildKey = 9999999;
            }
            if ((2 * i) + 1 < keyHeap.length && keyHeap[(2 * i) + 1] != 0) {
                rightChildKey = keyHeap[(2 * i) + 1];
            } else {
                rightChildKey = 999999999;
            }

            if (key > leftChildKey && leftChildKey < rightChildKey) {
                swap(i, 2 * i);
                i = i * 2;
            } else if (key > rightChildKey && rightChildKey < leftChildKey) {
                swap(i, (2 * i) + 1);
                i = (i * 2) + 1;
            } else {
                restored = true;
            }
        }
    }

    /**
     * Trickles up the node of index i
     *
     * @param i the index of the node to trickle up
     */
    private void trickleUp(int i) {
        boolean restored = false;
        while (!restored) {
            double key = keyHeap[i];

            if (key < keyHeap[i / 2]) {
                swap(i, i / 2);
                i = i / 2;
            } else {
                restored = true;
            }
        }
    }

    /**
     * Swaps nodes index i and j in the heap
     *
     * @param i index of node 1
     * @param j index of node 2
     */
    private void swap(int i, int j) {
        int tempIndex = IndexHeap[i];
        double tempKey = keyHeap[i];
        IndexHeap[i] = IndexHeap[j];
        IndexHeap[j] = tempIndex;
        keyHeap[i] = keyHeap[j];
        keyHeap[j] = tempKey;
    }

    /**
     * checks if the heap is empty
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size < 1;
    }

    @Override
    public String toString() {
        return "MinHeap{" +
                "IndexHeap=" + Arrays.toString(IndexHeap) +
                ", keyHeap=" + Arrays.toString(keyHeap) +
                ", size=" + size +
                '}';
    }
}
