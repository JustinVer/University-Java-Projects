/**
 * Node of a general tree
 */
public class TreeNode {

    public String virusName;
    public TreeNode firstChild;
    public TreeNode nextSibling;

    /**
     * Constructor
     * @param virusName name of the virus to store
     * @param firstChild first child of the node
     * @param nextSibling next sibling of the node
     */
    TreeNode(String virusName, TreeNode firstChild, TreeNode nextSibling){
        this.virusName = virusName;
        this.firstChild = firstChild;
        this.nextSibling = nextSibling;
    }

    public void printName(){
        System.out.println(virusName);
    }

    /**
     * prints all the nodes children
     */
    public void printChildren(){
        System.out.print(this.virusName + ": ");
        TreeNode temp = this.firstChild;
        while (temp != null) {
            System.out.print(temp.virusName);
            temp = temp.nextSibling;
            if (temp != null){
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

}
