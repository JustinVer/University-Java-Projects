import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

/**
 * Class for storing and doing operations on a graph using vertices and an adjacency matrix
 */
public class Graph {
    //Stores the vertices of the graph
    Vertex[] vertices;
    //Stores the adjacency matrix for the graph
    int[][] adjmat;

    //Stores the vertices in the pathway for the BFS method
    Vertex[] verticesInPathway;
    //A count of the number of vertices added to the pathway
    int BFSVerticesCount;

    /**
     * Does a topological sort on the graph
     * @throws IOException if there is an error writing to the file
     */
    public void topologicalSort() throws IOException {
        resetVisited();
        //Set indegree for each vertex
        for (int i = 0; i < adjmat.length; i++) {
            for (int j = 0; j < adjmat[i].length; j++) {
                vertices[j].indegree += adjmat[i][j];
            }
        }
        Vertex[] sorted = new Vertex[vertices.length];
        int verticesSorted = 0;
        //Actual topological sort
        for (int i = 0; i < vertices.length; i++) {
            int v = -1;
            //Finds a vertex with a 0 indegree
            for (int j = 0; j < vertices.length; j++) {
                if (vertices[j].indegree == 0 && !vertices[j].wasVisited){
                    v = j;
                    break;
                }
            }
            if (v != -1){
                sorted[verticesSorted] = vertices[v];
                verticesSorted++;
                vertices[v].wasVisited = true;
                for (int j = 0; j < adjmat[v].length; j++) {
                    vertices[j].indegree -= adjmat[v][j];
                }
            }
        }
        writeNodesToFile(sorted, "topologically_sorted.txt");
    }

    /**
     * Finds all the pathways in the graph and prints them to their own files
     * @throws IOException if there is an error righting to files
     */
    public void findPathways() throws IOException {
        int pathwayCount = 0;
        for (int i = 0; i < vertices.length; i++) {
            //Checks if the vertex is a zero out degree vertex
            boolean isZeroOutDegree = true;
            for (int j = 0; j < adjmat.length; j++) {
                if (adjmat[i][j] == 1){
                    isZeroOutDegree = false;
                    break;
                }
            }
            //If the vertex is a zero out degree vertex that means it is the end of a path so create the path
            if (isZeroOutDegree){
                pathwayCount++;
                //Gets the pathway
                Vertex[] pathway = backwardDFS(vertices[i].key);
                //Writes the nodes to the file
                writeNodesToFile(pathway, "pathway_" + (pathwayCount-1) + "_nodes.txt");
                //Create the adjacency matrix for the path and print to the file
                int[] pathwayIndexes = VertexToIndex(pathway);
                writeAdjmatToFile(getAdjacencyMatrix(pathwayIndexes), "pathway_" + (pathwayCount -1) + "_adjmat.txt");
            }
        }
    }

    /**
     * Creates an adjacency matrix for an array of vertice indexes
     * @param pathwayIndexes the indexes of the vertices to create a new adjacency matrix for
     * @return the adjacency matrix
     */
    private int[][] getAdjacencyMatrix(int[] pathwayIndexes){
        int[][] pathwayAdjmat = new int[pathwayIndexes.length][pathwayIndexes.length];
        for (int j = 0; j < adjmat.length; j++) {
            //Check if the current index is part of the pathway
            int indexesIndex1 = -1;
            for (int l = 0; l < pathwayIndexes.length; l++) {
                if (j == pathwayIndexes[l]){
                    indexesIndex1 = l;
                }
            }
            //if it is part of the pathway go through the adjacency matrix and add the value if it is part of the pathway
            if (indexesIndex1 != -1) {
                for (int k = 0; k < adjmat.length; k++) {
                    int indexesIndex2 = -1;
                    for (int l = 0; l < pathwayIndexes.length; l++) {
                        if (k == pathwayIndexes[l]){
                            indexesIndex2 = l;
                        }
                    }
                    if (indexesIndex2 != -1){
                        pathwayAdjmat[indexesIndex1][indexesIndex2] = adjmat[j][k];
                    }
                }
            }
        }
        return pathwayAdjmat;
    }

    /**
     * Converts an array of vertices to an array of their index in the master vertice array
     * @param vertices1 the array of vertices to convert
     * @return an integer array of the corresponding indexes
     */
    private int[] VertexToIndex(Vertex[] vertices1){
        int[] indexes = new int[vertices1.length];
        for (int i = 0; i < vertices1.length; i++) {
            for (int j = 0; j < vertices.length; j++) {
                if (vertices[j].key == vertices1[i].key){
                    indexes[i] = j;
                }
            }
        }
        return indexes;
    }

    /**
     * Does a backwards depth first search starting at the vertex with the corresponding key to create a pathway
     * @param key the vertex key to start the search at
     * @return the pathway for the starting vertex
     */
    public Vertex[] backwardDFS(char key){
        verticesInPathway = new Vertex[vertices.length];
        BFSVerticesCount = 0;
        //Find the index of the corresponding key
        int index = 0;
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].key == key) {
                index = i;
            }
        }
        //Do the backwards search
        backwardDFS(index);
        //Get and format the path so there is no null values
        Vertex[] pathway = new Vertex[BFSVerticesCount];
        int filled = 0;
        for (Vertex vertex : verticesInPathway) {
            if (vertex != null) {
                pathway[filled] = vertex;
                filled++;
            }
        }
        return pathway;
    }

    /**
     * Does a backwards depth first search and sets the pa
     * @param index the index of the starting vertex
     */
    private void backwardDFS(int index){
        addToBFSPath(vertices[index]);
        for (int i = 0; i < adjmat.length; i++) {
            if (adjmat[i][index] == 1){
                backwardDFS(i);
            }
        }
    }

    /**
     * adds vertex v to the path if it isn't already in the path
     * @param v the vertex to add
     */
    private void addToBFSPath(Vertex v){
        boolean found = false;
        for (Vertex vertex : verticesInPathway) {
            if (vertex == v) {
                found = true;
                break;
            }
        }
        if (!found){
            verticesInPathway[BFSVerticesCount] = v;
            BFSVerticesCount++;
        }
    }

    /**
     * Finds the shortest path between startVertex and endVertex
     * @param startVertex the key of the vertex to start at
     * @param endVertex the key of the vertex to end at
     * @throws IOException if there are errors writing to the file
     */
    public void findShortestPath(char startVertex, char endVertex) throws IOException {
        int startIndex = -1;
        int endIndex = -1;
        //Gets the index of the start and end vertices
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].key == startVertex) {
                startIndex = i;
            } else if (vertices[i].key == endVertex) {
                endIndex = i;
            }
        }
        //Do the search to set all the path variables for the vertices
        breadthFirstPathFinding(startIndex);

        //Get and format the path to be writen to the text file
        Vertex current = vertices[endIndex];
        CharStack stack = new CharStack();
        while (current.path != null) {
            stack.push(current.key);
            current = current.path;
        }
        stack.push(current.key);
        char[] path = new char[stack.getSize()];
        for (int i = 0; i < path.length; i++) {
            path[i] = stack.pop();
        }
        writeNodesToFile(path, "shortest_path_" + startVertex + "_" + endVertex + ".txt");
    }

    /**
     * Writes the key of a vertex array to a text file with the correct path
     * @param vertices the vertices to be writen to the file
     * @param path the path of the file
     * @throws IOException if there are errors writing to the file
     */
    private void writeNodesToFile(Vertex[] vertices, String path) throws IOException {
        char[] nodes = new char[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            nodes[i] = vertices[i].key;
        }
        writeNodesToFile(nodes,path);
    }

    /**
     * Writes the contents of a char[] to a text file with the correct path
     * @param nodes the char[] of nodes to be writen to the file
     * @param path the path of the file
     * @throws IOException if there are errors writing to the file
     */
    private void writeNodesToFile(char[] nodes, String path) throws IOException {
        FileWriter outFile = new FileWriter(path);
        BufferedWriter writer = new BufferedWriter(outFile);
        for (int i = 0; i < nodes.length; i++) {
            writer.write(nodes[i]);
            if (i< nodes.length-1){
                writer.newLine();
            }
        }
        writer.close();
    }

    /**
     * writes an adjacency matrix to a file with the correct path
     * @param adjmat the adjacency matrix to be writen to the file
     * @param path the path of the file
     * @throws IOException if there are errors writing to the file
     */
    private void writeAdjmatToFile(int[][] adjmat, String path) throws IOException {
        FileWriter outFile = new FileWriter(path);
        BufferedWriter writer = new BufferedWriter(outFile);
        for (int i = 0; i < adjmat.length; i++) {
            for (int j = 0; j < adjmat[i].length; j++) {
                writer.write(Integer.toString(adjmat[i][j]));
                if (j< adjmat[i].length-1){
                    writer.write(",");
                }
            }
            if (i< adjmat.length-1){
                writer.newLine();
            }
        }
        writer.close();
    }

    /**
     * Sets the path variable of the vertices in the graph using a breadth first search algorithm
     * @param startIndex the index of the starting vertex
     */
    private void breadthFirstPathFinding(int startIndex) {
        resetVisited();
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        vertices[startIndex].wasVisited = true;
        vertices[startIndex].dist = 0;
        queue.add(startIndex);
        int index;
        //Search
        while (!queue.isEmpty()) {
            index = queue.poll();
            vertices[index].wasVisited = true;
            for (int j = 0; j < adjmat[index].length; j++) {
                if (adjmat[index][j] == 1 && vertices[j].dist > vertices[index].dist + 1) {
                    vertices[j].dist = vertices[index].dist + 1;
                    vertices[j].path = vertices[index];
                    queue.add(j);
                }
            }
        }
    }

    /**
     * Resets all the variables of the Vertex that are used in algorithms
     */
    private void resetVisited() {
        for (Vertex vertex : vertices) {
            vertex.reset();
        }
    }

    /**
     * Reads the vertex and adjacency matrix data from text files
     * @param nodesFilePath the path of the file containing the nodes data
     * @param adjmatFilePath the path of the file containing the adjacency matrix data
     */
    public void readData(String nodesFilePath, String adjmatFilePath) {
        File nodesFile = new File(nodesFilePath);
        File adjmatFile = new File(adjmatFilePath);
        Scanner nodesScanner;
        Scanner adjmatScanner;
        try {
            nodesScanner = new Scanner(nodesFile);
            adjmatScanner = new Scanner(adjmatFile);
            adjmatScanner.useDelimiter(",");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
        vertices = new Vertex[getLineCount(nodesFilePath)];
        int adjmatLineCount = getLineCount(adjmatFilePath);
        adjmat = new int[adjmatLineCount][adjmatLineCount];

        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(nodesScanner.nextLine().charAt(0));
        }
        for (int i = 0; i < adjmat.length; i++) {
            for (int j = 0; j < adjmat[i].length; j++) {
                if (j == adjmat[i].length - 1) {
                    adjmat[i][j] = Integer.parseInt(adjmatScanner.nextLine().replace(",", ""));
                } else {
                    adjmat[i][j] = Integer.parseInt(adjmatScanner.next());
                }
            }
        }

    }

    /**
     * Gets the number of lines in a text file
     * @param filePath the path of the file to get the number of lines
     * @return the number of lines in the file
     */
    private int getLineCount(String filePath) {
        int lineCount;
        try (Stream<String> stream = Files.lines(Path.of(filePath))) {
            lineCount = (int) stream.count();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return lineCount;
    }

}
