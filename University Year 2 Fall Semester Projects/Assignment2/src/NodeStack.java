/**
 * Stack that stores tree nodes
 */
public class NodeStack {

    private NodeList head = null;

    /**
     * push elements to the front of the stack
     * @param node node to add to the stack
     */
    public void push(TreeNode node) {
            if (head == null){
                head = new NodeList(node, null);
            }
            else{
                NodeList temp = head;
                head = new NodeList(node, temp);
            }
        }

    /**
     * pop elements to the front of the stack
     * @return the popped element
     */
    public TreeNode pop() {
            if (head == null){
                return null;
            }else {
                TreeNode item = head.item;
                head = head.next;
                return item;
            }
        }

    /**
     * Checks if the stack is empty
     * @return true if stack is empty false otherwise
     */
    public Boolean isEmpty() {
            return head == null;
        }
}
