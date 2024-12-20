import java.io.IOException;

public class Lab3_Main {
    public static void main(String[] args) throws IOException {
        DrugHeap dh = new DrugHeap("dockedApproved.tab");
        dh.readData();
        dh.buildHeap();
        dh.heapSort();
        dh.writer.close();
    }
}