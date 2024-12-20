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
public class Vertex implements Comparable<Vertex>{

    String drugBankId;
    String genericName;
    String SMILES;
    String URL;
    String drugGroups;
    double score;
    boolean wasVisited;
    double distance;
    Vertex path;
    int index = -1;


    public Vertex(){
        reset();
    }
    public Vertex(String genericName, String SMILES, String drugBankId, String URL, String drugGroups, double score, int index){
        this.drugBankId = drugBankId;
        this.genericName = genericName;
        this.SMILES = SMILES;
        this.URL = URL;
        this.drugGroups = drugGroups;
        this.score = score;
        this.index = index;
        reset();
    }

    public void reset(){
        this.wasVisited = false;
        this.distance = 10000;
        this.path = null;
    }

    public void displayDrug(){
        //String genericName, String SMILES, String drugBankId, String URL, String drugGroups, double score
        System.out.println(genericName + ", " + SMILES + ", " + drugBankId + ", " + URL + ", " + drugGroups + ", " + score);
    }

    @Override
    public int compareTo(Vertex o) {
        return Double.compare(this.distance, o.distance);
    }
}// Vertex
