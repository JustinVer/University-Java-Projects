/**
 * Queue that stores Tree Nodes
 */
public class NodeQueue {

    private NodeList head = null;
    private NodeList tail = null;

    /**
     * push elements to the end of the queue
     * @param node the node to be added to the queue
     */
    public void push(TreeNode node) {
        if (head == null){
            head = new NodeList(node, null);
            tail = head;
        }
        else{
            tail.next = new NodeList(node, null);
            tail = tail.next;
        }
    }

    /**
     * pop elements from the front of the queue
     * @return the element that was popped
     */
    public TreeNode pop() {
        if (head == null){
            return null;
        }else {
            TreeNode item = head.item;
            head = head.next;
            if (head == null){
                tail = null;
            }
            return item;
        }
    }

    public void clear(){
        while (!isEmpty()){
            pop();
        }
    }

    /**
     * check if the queue is empty
     * @return true if stack is empty false otherwise
     */
    public Boolean isEmpty() {
        return head == null;
    }
}
