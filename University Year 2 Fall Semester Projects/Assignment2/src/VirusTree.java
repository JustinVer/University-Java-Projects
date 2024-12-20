import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A general tree for storing virus tree
 */
public class VirusTree {
    private TreeNode root;

    /**
     * Gets the distance between two viruses
     *
     * @param virus1 name of the first virus
     * @param virus2 name of the second virus
     */
    public void distance(String virus1, String virus2) {
        NodeQueue queue = new NodeQueue();
        queue.push(root);
        TreeNode lastAncestor = root;
        //Finds the ancestor of the two viruses
        while (!queue.isEmpty()) {
            TreeNode current = queue.pop();
            if (search(virus1, current.firstChild) != null && search(virus2, current.firstChild) != null) {
                lastAncestor = current;
                current = current.firstChild;
                queue.clear();
                while (current != null) {
                    queue.push(current);
                    current = current.nextSibling;
                }
            }
        }
        //prints out the information
        System.out.println("The distance between " + virus1 + " and " + virus2 + " is " + Math.max(distanceBetweenAncestor(lastAncestor, virus1), distanceBetweenAncestor(lastAncestor, virus2)) + ". They have a common ancestor " + lastAncestor.virusName);
    }

    private int distanceBetweenAncestor(TreeNode ancestor, String virusName){
        int height = 0;
        boolean virusFound = false;
        TreeNode temp = ancestor;
        //finds the distance between virus1 and the ancestor
        while (!virusFound) {
            //Checks if the virus is a child of this node or which child of this node has the virus as a subchild
            temp = temp.firstChild;
            while (true) {
                if (search(virusName, temp) != null && (search(virusName, temp.firstChild) != null || java.util.Objects.equals(virusName, temp.virusName))) {
                    break;
                } else {
                    temp = temp.nextSibling;
                }
            }
            //Increases the height
            height++;
            //If the node was found break
            if (java.util.Objects.equals(temp.virusName, virusName)) {
                virusFound = true;
            }
        }
        return height;
    }


    /**
     * Displays the tree by using an iterative preOrder traversal
     */
    public void preOrder() {
        //Creates the initial conditions
        TreeNode current = root;
        NodeStack stack = new NodeStack();
        stack.push(root);
        //while there is still nodes to traverse, traverse the tree
        while (!stack.isEmpty()) {
            if (!stack.isEmpty()){
                current = stack.pop();
            }
            //Print out the current node
            current.printName();
            TreeNode temp = current.firstChild;
            //Create a temporary stack since we want to push to the stack in right to left order not left to right
            NodeStack tempStack = new NodeStack();
            while (temp!= null){
                tempStack.push(temp);
                temp = temp.nextSibling;
            }
            //Reverse the temp stack since we want to push to the stack in right to left order not left to right
            while (!tempStack.isEmpty()){
                stack.push(tempStack.pop());
            }
        }
    }

    /**
     * Displays the tree by using an iterative post order traversal
     */
    public void postOrder() {
        //create initial conditions
        NodeStack stack1 = new NodeStack();
        NodeStack stack2 = new NodeStack();
        stack1.push(root);
        TreeNode current;

        //Traverse through all the nodes in the tree
        while(!stack1.isEmpty()){
            current = stack1.pop();
            //Push the nodes to the second stack in the correct order
            stack2.push(current);

            //Add all the children to the first stack
            current = current.firstChild;
            while (current!=null){
                stack1.push(current);
                current = current.nextSibling;
            }
        }
        //Actually print all the now properly ordered nodes
        while(!stack2.isEmpty()){
            stack2.pop().printName();
        }
    }

    /**
     * Displays the tree using an iterative breadth first search
     */
    public void breadthFirst() {
        NodeQueue queue = new NodeQueue();
        queue.push(root);
        //Continues while there is still a node in the queue
        while (!queue.isEmpty()) {
            //Prints out the node
            TreeNode current = queue.pop();
            current.printName();
            current = current.firstChild;
            //Adds all the children of the node to the queue
            while (current != null) {
                queue.push(current);
                current = current.nextSibling;
            }
        }
    }

    /**
     * Gets the height of the passed through node by recursion
     *
     * @param node The node to find the height of
     * @return the height of the node
     */
    public int height(TreeNode node) {
        if (node == null) {
            return -1;
        }
        int height = -1;
        TreeNode temp = node.firstChild;
        if (temp != null) {
            while (temp != null) {
                height = Math.max(height, height(temp));
                temp = temp.nextSibling;
            }
        }
        return 1 + height;
    }

    /**
     * Creates the tree from the filePath
     *
     * @param filePath the file to be read from to create the tree
     * @return the root of the tree
     * @throws FileNotFoundException if the file could not be found
     */
    public TreeNode create(String filePath) throws FileNotFoundException {
        //Creates the scanner
        Scanner reader = new Scanner(new File(filePath));

        //Reads the first line to be used as the root
        String line = reader.nextLine();
        String[] nodes = line.split(",");
        if (nodes.length > 1) {
            root = new TreeNode(nodes[0], new TreeNode(nodes[1], null, null), null);
        } else if (nodes.length == 1) {
            root = new TreeNode(nodes[0], null, null);
        }
        //Get all the roots children
        for (int i = 2; i < nodes.length; i++) {

            TreeNode temp = root.firstChild;
            temp.nextSibling = new TreeNode(nodes[i], null, null);
        }
        root.printChildren();

        //Reads all the lines from the file and puts them into the tree
        while (reader.hasNext()) {
            //Reads the line
            line = reader.nextLine();
            //Splits the line into each virus name
            nodes = line.split(",");

            //If there is nodes to add, add them to the tree
            if (nodes.length > 0) {
                //Gets the parent node
                TreeNode startNode = search(nodes[0], root);
                TreeNode temp = startNode;
                //Adds each child to the parent
                if (nodes.length > 1) {
                    //If the first child is null add the first child to the parent node
                    if (temp.firstChild == null) {
                        temp.firstChild = new TreeNode(nodes[1], null, null);
                        temp = temp.firstChild;
                    }
                    //Otherwise add the first child to the end of the siblings
                    else {
                        temp = temp.firstChild;
                        while (temp.nextSibling != null) {
                            temp = temp.nextSibling;
                        }
                        temp.nextSibling = new TreeNode(nodes[1], null, null);
                    }
                    //Add the rest of the children from the file to the end of the list
                    for (int i = 2; i < nodes.length; i++) {
                        temp.nextSibling = new TreeNode(nodes[i], null, null);
                        temp = temp.nextSibling;
                    }
                }
                //Prints all the children as per assignment requirements
                startNode.printChildren();
            }

        }
        return root;
    }

    /**
     * Serches the tree for the node with the passed virus name. Uses recursion
     *
     * @param virusName name of the virus to search for
     * @param node      node to start searching at. Also is used for recursion
     * @return the node with the passed virus name
     */
    public TreeNode search(String virusName, TreeNode node) {
        if (node == null) {
            return null;
        }
        if (java.util.Objects.equals(node.virusName, virusName)) {
            return node;
        }
        TreeNode next = search(virusName, node.nextSibling);
        if (next == null) {
            return search(virusName, node.firstChild);
        } else {
            return next;
        }
    }
}