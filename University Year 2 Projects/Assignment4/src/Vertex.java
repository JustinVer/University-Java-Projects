/**
 * A class for storing vertex information
 */
public class Vertex {
    //key (to store the label such as “A” in the graph)
    char key;
    //wasVisited (to be used in findShortestPath method)
    boolean wasVisited = false;
    // dist (to be used in findShortestPath method)
    int dist = 2100000000;
    // path (to be used in findShortestPath method)
    Vertex path = null;
    // indegree (to be used in topologicalSort and backwardDFS methods)
    int indegree = 0;

    // outdegree ( to be used in backwardDFS method)

    // index (indicating index in the vertex array in the Graph class; will help in findPathways method)
    int index = 0;

    /**
     * constructor to set the key of the vertex
     * @param key the key of the vertex
     */
    public Vertex(char key){
        this.key = key;
    }

    /**
     * Resets all the variables used in algorithms to their default value
     */
    public void reset(){
        wasVisited = false;
        dist = 2100000000;
        path = null;
        index = 0;
        indegree = 0;
    }
}
