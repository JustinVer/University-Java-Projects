/**
 * This class contains all the code for the doubly linked list node structure
 *
 * IDE used:IntelliJ
 *
 * @Author Kevin Olenic
 * ST# 6814974
 * @Version 1.0
 * @Since 2024-08-10
 */

class Node {
    Node prev;// node storing the pointer to the previous node in the doubly-linked list
    int data;// variable holding the integer data contained in the node
    int id;// variable holding the id of the node
    Node next;// node storing the pointer to the next node in the doubly-list
    Node(Node p, int d, int i, Node n){
        // assign the parameter to its corresponding global-parameter for proper storage/access
        prev = p;
        data = d;
        id = i;
        next = n;
    }// constructor
}// class