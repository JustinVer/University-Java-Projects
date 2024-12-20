import java.io.IOException;

/**
 * Creates a graph and calls different pgraph related methods
 * <p>
 * IDE used: IntelliJ
 *
 * @Author Justin Verhoog
 * ST# 7756034
 * @Version 1.0
 * @Since 2024-11-29
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        graph.readData("nodes.txt", "adjmat.csv");
        graph.findPathways();
        graph.findShortestPath('D','J');
        graph.topologicalSort();
    }
}
