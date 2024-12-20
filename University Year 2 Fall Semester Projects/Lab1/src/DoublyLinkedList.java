import java.util.HashSet;
import java.util.Set;

/**
 * This class uses a Doubly-Linked list approach to generate all permutations for a set of numbers.
 *
 * IDE used:IntelliJ
 *
 * @Author Justin Verhoog
 * ST# 7756034
 * @Version 1.0
 * @Since 2024-09-19
 */

public class DoublyLinkedList {
    public int numNodes = 0;

    /**
     * This method generates all permutations of the elements in a doubly linked list
     * @param k is the number of elements in the doubly linked list
     * @param a is the node pointing to the head of the doubly linked list
     */
    public void generateDLL(int k, Node a){
        if (k == 1){
            //Adds the permutation to the Set
            StringBuilder list = new StringBuilder();
            Node temp = a;
            list.append("[");
            for (int i = 0; i<numNodes;i++){
                list.append(temp.data);
                if (i != numNodes -1){
                    list.append(", ");
                }
                temp = temp.next;
            }
            list.append("]");
            System.out.println(list);
        }else{
            for (int i = 0; i<k;i++){
                generateDLL(k-1, a);
                if (k % 2 == 0){
                    //Swaps the two nodes
                    Node Node1 = find(i, a);
                    Node Node2 = find(k-1,a);

                    swap(Node1, Node2, a);

                    Node tempHead = a;
                    for (int j = 0; j < numNodes; j++) {
                        System.out.print(tempHead.data);
                        tempHead = tempHead.next;
                    }
                    System.out.println();
                }else{
                    //Swaps the two nodes
                    Node Node1 = find(0, a);
                    Node Node2 = find(k-1,a);

                    swap(Node1, Node2, a);

                    Node tempHead = a;
                    for (int j = 0; j < numNodes; j++) {
                        System.out.print(tempHead.data);
                        tempHead = tempHead.next;
                    }
                    System.out.println();

                }
            }
        }
    }// generateDLL

    public void swap(Node Node1, Node Node2, Node a){
        if(Node1 == Node2) {
            return;
        }
        if (Node1 == a)
            a = Node2;
        else if (Node2 == a)
            a = Node1;

        Node temp = Node1.next;
        Node1.next = Node2.next;
        Node2.next = temp;

        Node1.next.prev = Node1;
        Node2.next.prev = Node2;

        temp = Node1.prev;
        Node1.prev = Node2.prev;
        Node2.prev = temp;

        Node1.prev.next = Node1;
        Node2.prev.next = Node2;

        int tempId = Node1.id;
        Node1.id = Node2.id;
        Node2.id = tempId;
    }

    /**
     * This method fills a doubly linked list with the provided amount of elements
     * defined by the amount parameter
     * @param amount is the amount of nodes that will be inserted into the doubly linked list
     * @return is the head of the newly created doubly-linked list
     */
    public Node fillList(int amount, Node head){
        //Temp pointer for traversal
        Node temp = head;

        if (amount == 0){
            return null;
        }else if (amount >= 1){
            if (head == null){
                head = new Node(null,1,0,null);
                temp = head;
            }else {
                temp.data = 1;
            }
        }

        //Adds amount number of nodes to the list
        for (int i = 1; i < amount; i++) {
            temp.next = new Node(temp, i+1, i, null);
            temp = temp.next;
        }
        //make the list circular
        temp.next = head;
        head.prev = temp;
        return head;
    }// fillList

    /**
     * This method finds the correct node in the doubly linked list for the swap
     * @param n is the node id being searched for in the doubly linked list
     * @param h is the head node of the list being looked at
     * @return the node being searched for
     */
    public static Node find(int n, Node h){
        Node temp = h;
        while(temp != null){
            if (temp.id == n){
                return temp;
            }
            temp = temp.next;

        }
        return null;
    }// find
}// DoublyLinkedList
