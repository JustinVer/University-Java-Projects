/**
 * A stack for storing chars
 */
public class CharStack {

    private CharList head = null;
    private int size = 0;

    /**
     * push elements to the front of the stack
     * @param node node to add to the stack
     */
    public void push(char node) {
        if (head == null){
            head = new CharList(node, null);
        }
        else{
            CharList temp = head;
            head = new CharList(node, temp);
        }
        size++;
    }

    /**
     * pop elements to the front of the stack
     * @return the popped element
     */
    public char pop() {
        if (head == null){
            return '\0';
        }else {
            char item = head.item;
            head = head.next;
            size--;
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

    /**
     * Gets the size of the stack
     * @return the size of the stack
     */
    public int getSize(){
        return size;
    }
}