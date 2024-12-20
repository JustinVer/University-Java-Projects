/**
 * A minheap class that stores passengers based on a key
 */
public class MinHeap {
    Passenger[] passengerHeap = new Passenger[34];
    int[] keyHeap = new int[34];
    int size = 0;

    /**
     * Inserts a new passenger into the heap
     *
     * @param passenger the passenger to be inserted
     * @param key       the key of the passenger
     */
    public void insert(Passenger passenger, int key) {
        size++;
        passengerHeap[size] = passenger;
        keyHeap[size] = key;
        trickleUp(size);
    }

    /**
     * Removes and returns the passenger with the lowest key
     *
     * @return the passenger with the lowest key
     */
    public Passenger removeMin() {
        Passenger min = passengerHeap[1];
        if (min == null) {
            return null;
        }
        swap(1, size);
        keyHeap[size] = 0;
        passengerHeap[size] = null;
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
            int key = keyHeap[i];
            int leftChildKey;
            int rightChildKey;
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
            int key = keyHeap[i];

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
        Passenger tempPassenger = passengerHeap[i];
        int tempKey = keyHeap[i];
        passengerHeap[i] = passengerHeap[j];
        passengerHeap[j] = tempPassenger;
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

    /**
     * Builds a heap out of an array
     *
     * @param passengerArray the array to build the heap from
     * @param keyArray       the keys of the array to build the heap from
     */
    public void buildHeap(Passenger[] passengerArray, int[] keyArray) {
        size = passengerArray.length;
        keyHeap = new int[keyArray.length + 1];
        passengerHeap = new Passenger[passengerArray.length + 1];
        for (int i = 1; i < keyArray.length + 1 && 1 < passengerArray.length + 1 && i < keyHeap.length; i++) {
            passengerHeap[i] = passengerArray[i - 1];
            keyHeap[i] = keyArray[i - 1];
        }
        for (int i = keyHeap.length / 2; i > 0; i--) {
            trickleDown(i);
        }
    }

    /**
     * Prints out the contents of the heap in breadth first order to the console
     */
    public void printContents() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Passenger passenger : passengerHeap) {
            if (passenger != null) {
                stringBuilder.append(passenger).append(", ");
            }
        }
        if (stringBuilder.length() >= 1) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        System.out.println("Heap: [" + stringBuilder + "]");
    }
}
