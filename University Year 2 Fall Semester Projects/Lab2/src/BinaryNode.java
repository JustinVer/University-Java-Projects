public class BinaryNode {

    BinaryNode left;
    BinaryNode right;
    Drug myDrug;

    public BinaryNode(BinaryNode l, Drug d, BinaryNode r){
        left = l;
        myDrug = d;
        right = r;
    }

    /**
     *
     */
    public void displayNode(){
        myDrug.displayDrug();
    }
}