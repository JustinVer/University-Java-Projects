import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * This class...
 * <p>
 * IDE used:IntelliJ
 *
 * @Author ST#
 * @Version
 * @Since
 */
public class DrugGraph {

    Vertex[] vertices;
    double[][] distancematrix;
    boolean[][] adjacencyMatrix;

    /**
     * This constructor...
     *
     * @throws FileNotFoundException occurs when the file is not found
     */
    public DrugGraph() throws FileNotFoundException {

    }// constructor

    /**
     * This method...
     *
     * @throws FileNotFoundException if one of the files is not found in the local directory
     */
    public void readData(String filepathData, String filepathDistance, String filepathAdjacency) throws FileNotFoundException {
        //Reads the vertex data
        File file = new File(filepathData);
        int lineCount = 0;
        try (Stream<String> stream = Files.lines(Path.of(filepathData))) {
            lineCount = (int) stream.count();
        } catch (IOException ignored) {
        }
        Scanner scanner = new Scanner(file);
        vertices = new Vertex[lineCount - 1];
        scanner.nextLine();
        scanner.useDelimiter("\t");

        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(scanner.next(), scanner.next(), scanner.next(), scanner.next(), scanner.next(), Double.parseDouble(scanner.nextLine().replace("\t", "")),i);
        }
        scanner.close();

        //Reads the distance matrix
        file = new File(filepathDistance);
        lineCount = 0;
        try (Stream<String> stream = Files.lines(Path.of(filepathDistance))) {
            lineCount = (int) stream.count();
        } catch (IOException ignored) {
        }
        scanner = new Scanner(file);
        distancematrix = new double[lineCount][lineCount];
        scanner.useDelimiter("\t");

        for (int i = 0; i < distancematrix.length; i++) {
            for (int j = 0; j < distancematrix[i].length - 1; j++) {
                try {
                    distancematrix[i][j] = Double.parseDouble(scanner.next());
                } catch (Exception e) {
                    System.out.println(i + " " + j);
                }
            }
            distancematrix[i][distancematrix.length - 1] = Double.parseDouble(scanner.nextLine().replace("\t", ""));
        }

        //reads the adjacency matrix
        file = new File(filepathAdjacency);
        lineCount = 0;
        try (Stream<String> stream = Files.lines(Path.of(filepathAdjacency))) {
            lineCount = (int) stream.count();
        } catch (IOException ignored) {
        }
        scanner = new Scanner(file);
        adjacencyMatrix = new boolean[lineCount][lineCount];
        scanner.useDelimiter("\t");

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length - 1; j++) {
                try {
                    adjacencyMatrix[i][j] = Integer.parseInt(scanner.next()) != 0;
                } catch (Exception e) {
                    System.out.println(i + " " + j);
                }
            }
            adjacencyMatrix[i][adjacencyMatrix.length - 1] = Integer.parseInt(scanner.nextLine().replace("\t", "")) != 0;
        }
    }

    /**
     * This method...
     *
     * @param i
     */
    public void BFS(int i) {
        resetVisited();
        IntQueue indexQueue = new IntQueue();
        vertices[i].wasVisited = true;
        indexQueue.push(i);
        int index;
        //Search
        while (!indexQueue.isEmpty()) {
            index = indexQueue.pop();
            for (int j = 0; j < adjacencyMatrix[index].length; j++) {
                if (adjacencyMatrix[index][j] && !vertices[j].wasVisited) {
                    vertices[j].wasVisited = true;
                    vertices[j].path = vertices[index];
                    indexQueue.push(j);
                }
            }
        }
    }// BFS

    /**
     * This method...
     *
     * @param i
     */
    public void Dij(int i) {
        resetVisited();
        vertices[i].distance = 0;
        PriorityQueue pq = new PriorityQueue();
        pq.add(vertices[i]);
        int index;
        while (!pq.isEmpty()) {
            index = pq.poll().index;
            if (!vertices[index].wasVisited) {
                vertices[index].wasVisited = true;
                for (int j = 0; j < distancematrix[index].length; j++) {
                    if (distancematrix[index][j] < 10000) {
                        if (vertices[j].distance > vertices[index].distance + distancematrix[index][j]) {
                            vertices[j].distance = vertices[index].distance + distancematrix[index][j];
                            vertices[j].path = vertices[index];
                            pq.add(vertices[j]);
                        }
                    }
                }
            }
        }
    }


    private void resetVisited() {
        //Reset all the visiteds to false
        for (Vertex vertex : vertices) {
            vertex.wasVisited = false;
        }
    }

    /**
     * This method...
     *
     * @param fromDrug
     * @param toDrug
     * @param method
     */
    public void findShortestPath(String fromDrug, String toDrug, String method) {
       if (Objects.equals(method, "unweighted")) {
            findShortestPathUnWeighted(fromDrug, toDrug);
             int i1 = 0;
            int i2 = 0;
            for (int i = 0; i < vertices.length; i++) {
                if (Objects.equals(vertices[i].drugBankId, "DB01050")) {
                    i1 = i;
                } else if (Objects.equals(vertices[i].drugBankId, "DB13167")) {
                    i2 = i;
                }
            }
        } else {
            findShortestPathWeighted(fromDrug, toDrug);
        }
    }

    private void findShortestPathWeighted(String fromDrug, String toDrug) {
        int indexTo = -1;
        int indexFrom = -1;
        for (int i = 0; i < vertices.length; i++) {
            if (Objects.equals(vertices[i].drugBankId, toDrug)) {
                indexTo = i;
            } else if (Objects.equals(vertices[i].drugBankId, fromDrug)) {
                indexFrom = i;
            }
        }
        Dij(indexTo);
        Vertex v = vertices[indexFrom];
        StringBuilder builder = new StringBuilder();
        builder.append(v.drugBankId);
        while (true) {
            v = v.path;
            if (v != vertices[indexTo]) {
                builder.append(", ").append(v.drugBankId).append(" ").append(v.distance);
            } else {
                builder.append(", ").append(v.drugBankId);
                break;
            }
        }
        System.out.println(builder);
    }

    private void findShortestPathUnWeighted(String fromDrug, String toDrug) {
        int indexTo = -1;
        int indexFrom = -1;
        for (int i = 0; i < vertices.length; i++) {
            if (Objects.equals(vertices[i].drugBankId, toDrug)) {
                indexTo = i;
            } else if (Objects.equals(vertices[i].drugBankId, fromDrug)) {
                indexFrom = i;
            }
        }
        BFS(indexTo);
        Vertex v = vertices[indexFrom];
        StringBuilder builder = new StringBuilder();
        builder.append(v.drugBankId);
        while (true) {
            v = v.path;
            if (v != vertices[indexTo]) {
                builder.append(", ").append(v.drugBankId);
            } else {
                builder.append(", ").append(v.drugBankId);
                break;
            }
        }
        System.out.println(builder);
    }
}// DrugGraph
