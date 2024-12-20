/**
 * Class for a linked list that stores a TreeNode
 * Used for the queue and stack classes
 */
public class NodeList {
    public TreeNode item;
    public NodeList next;
    NodeList(TreeNode item, NodeList next){
        this.item = item;
        this.next = next;
    }
}
