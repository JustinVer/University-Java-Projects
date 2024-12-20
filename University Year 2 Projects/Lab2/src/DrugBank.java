import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.lang.Math;

/**
 * Justin Verhoog
 * 7756034
 * 2024-10-03
 */
//Name, Student ID, Student Number, Date

public class DrugBank {

    File iFile;
    Scanner sc;
    Drug[] data;
    BinaryNode root;
    FileWriter oFile;
    BufferedWriter writer;

    /**
     * Initializes everything necessary for the class
     * @throws IOException
     */
    public DrugBank(String filePath) throws IOException {
        iFile = new File(filePath);
        sc = new Scanner(iFile);
        oFile = new FileWriter("output");
        writer = new BufferedWriter(oFile);
        readData();
    }

    /**
     * Reads the data from the file and puts it into an array
     * @throws FileNotFoundException
     */
    public void readData() throws FileNotFoundException {
        Scanner tempScanner1 = sc;
        int count = 0;
        while (tempScanner1.hasNext()) {
            tempScanner1.nextLine();
            count++;
        }
        data = new Drug[count-1];
        Scanner tempScanner = new Scanner(iFile);
        tempScanner.useDelimiter("\t");
        tempScanner.nextLine();
        for (int i = 0; i < data.length; i++) {
            String genericName = tempScanner.next();
            //System.out.print("Generic name: " + genericName + " ");
            String SMILES = tempScanner.next();
            //System.out.print("SMILES: " + SMILES + " ");
            String drugBankID = tempScanner.next();
            //System.out.print("Drug bank id: " + drugBankID + " ");
            tempScanner.next();
            String drugGroups = tempScanner.next();
            //System.out.println("Drug groups: " + drugGroups);
            tempScanner.nextLine();
            data[i] = new Drug(drugBankID, genericName, SMILES, drugGroups);
        }
    }

    /**
     * Adds all the data from the array into the binary tree
     * @return returns the binary tree filled with the data
     */
    public BinaryNode create() {
        for (Drug data : data) {
            insert(new BinaryNode(null, data, null));
        }
        return root;
    }

    /**
     * inserts a node into the binary tree
     * @param node the node to be inserted
     */
    public void insert(BinaryNode node) {
        if (root == null) {
            root = node;
            return;
        }
        BinaryNode temp = root;
        boolean placed = false;
        while (!placed) {
            if (Objects.equals(node.myDrug.drugBankID, temp.myDrug.drugBankID)) {
                throw new RuntimeException("Error, drug already exists in the search tree");
            } else {
                String ID1 = node.myDrug.drugBankID;
                String ID2 = temp.myDrug.drugBankID;
                for (int i = 0; true; i++) {
                    //System.out.println("looped " + i + " " + ID1 + " " + ID2);
                    if ((int) ID1.charAt(i) < (int) ID2.charAt(i)) {
                        if (temp.left == null) {
                            temp.left = node;
                            placed = true;
                            break;
                        } else {
                            temp = temp.left;
                            break;
                        }
                    } else if ((int) ID1.charAt(i) > (int) ID2.charAt(i)) {
                        if (temp.right == null) {
                            temp.right = node;
                            placed = true;
                            break;
                        } else {
                            temp = temp.right;
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Traverses the tree using an inoder traversal and prints the traversal into a text file
     * @param node The root of the binary tree
     * @throws IOException
     */
    public void inOrderTraverse(BinaryNode node) throws IOException {
        if (node == null) {
            return;
        }
        inOrderTraverse(node.left);
        writer.write(node.myDrug.genericName + "\t" + node.myDrug.SMILES + "\t" + node.myDrug.drugBankID + "\t" + node.myDrug.drugGroups);
        System.out.println(node.myDrug.genericName + "\t" + node.myDrug.SMILES + "\t" + node.myDrug.drugBankID + "\t" + node.myDrug.drugGroups);
        writer.newLine();
        inOrderTraverse(node.right);
    }

    /**
     * Searches the binary tree for the drug with the passed through ID
     * @param ID the ID to be searched for
     */
    public void search(String ID) {
        BinaryNode temp = root;
        boolean found = false;

        while (!found) {
            String ID1 = ID;
            String ID2 = temp.myDrug.drugBankID;

            for (int i = 0; true; i++) {
                if (Objects.equals(ID, temp.myDrug.drugBankID)) {
                    temp.myDrug.displayDrug();
                    found = true;
                    break;
                }
                if ((int) ID1.charAt(i) < (int) ID2.charAt(i)) {
                    if (temp.left == null) {
                        throw new RuntimeException("ID not found");
                    } else {
                        temp = temp.left;
                        break;
                    }
                } else if ((int) ID1.charAt(i) > (int) ID2.charAt(i)) {
                    if (temp.right == null) {
                        throw new RuntimeException("ID not found");
                    } else {
                        temp = temp.right;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Gets the depth of the given node
     * @param node the node to find the depth of
     * @param dep the current count of the depth
     * @return the depth of the node
     */
    public int depth(BinaryNode node, int dep) {
        if (node == null){
            return dep;
        }
        return Math.max(depth(node.left, dep+1), depth(node.right,dep+1));
    }
}
