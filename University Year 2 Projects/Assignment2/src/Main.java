import java.io.FileNotFoundException;

/**
 * This class reads a file and generates output based on the viruses read
 *
 * IDE used: IntelliJ
 *
 * @Author Justin Verhoog
 * ST# 7756034
 * @Version 1.0
 * @Since 2024-10-11
 */

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        VirusTree vt = new VirusTree();
        TreeNode root = vt.create("tree_of_virus_input - Copy.txt");
        int h = vt.height(root);
        System.out.println("The height of the tree is " + h);
        System.out.println("Breadth First Traversal:");
        vt.breadthFirst();
        System.out.println();
        System.out.println("Pre Order Traversal:");
        vt.preOrder();
        System.out.println();
        System.out.println("Post Order Traversal:");
        vt.postOrder();
        System.out.println();
        vt.distance("HCoV-OC43","Hcov-229E");
        vt.distance("SARS-CoV","Zika virus");
        System.out.println();
        System.out.println("The height() method has a time complexity of O(N). This is because it visits each node in the tree exactly one time (it checks the height of each node but doesn't repeat checking nodes ever)");
    }
}