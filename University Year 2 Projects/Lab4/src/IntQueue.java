/**
 * Queue that stores Tree Nodes
 */
public class IntQueue {

    private IntNode head = null;
    private IntNode tail = null;

    /**
     * push elements to the end of the queue
     * @param vertex the node to be added to the queue
     */
    public void push(int vertex) {
        if (head == null){
            head = new IntNode(vertex, null);
            tail = head;
        }
        else{
            tail.next = new IntNode(vertex, null);
            tail = tail.next;
        }
    }

    /**
     * pop elements from the front of the queue
     * @return the element that was popped
     */
    public int pop() {
        if (head == null){
            return 0;
        }else {
            int item = head.data;
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
