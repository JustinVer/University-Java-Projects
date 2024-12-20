import java.io.IOException;

public class BST_Main {
    /**
     The main function for our methods
     */
    public static void main (String[] args) throws IOException {
        DrugBank db = new DrugBank("src/dockedApproved.tab");
        BinaryNode root = db.create();
        db.inOrderTraverse(root);
        db.writer.close();
        db.search("DB06771");
        System.out.println(db.depth(root, 0));
    };
}
