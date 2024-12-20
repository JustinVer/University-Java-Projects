import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * This class...
 *
 * IDE used:IntelliJ
 *
 * @Author
 * ST#
 * @Version
 * @Since
 */

public class Lab4 {
    private static final DrugGraph dg;

    static {
        try {
            dg = new DrugGraph();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        DrugGraph dg = new DrugGraph();
        dg.readData("dockedApproved_1801.tab", "dis_mat_1801.tab", "adj_mat_1801.tab");
        dg.findShortestPath("DB01050","DB00316","unweighted");
        dg.findShortestPath("DB01050","DB00316","weighted");
    }// main
}// Lab4