/**
 * Class for a linked list that stores a TreeNode
 * Used for the queue and stack classes
 */
public class CharList {
    public char item;
    public CharList next;
    CharList(char item, CharList next){
        this.item = item;
        this.next = next;
    }
}
