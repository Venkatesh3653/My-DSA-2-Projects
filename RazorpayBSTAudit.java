import java.util.*;

public class RazorpayBSTAudit {

    static class Node {
        String key;
        Node left, right;

        Node(String key) {
            this.key = key;
        }
    }

    static class BST {
        Node root;

        Node insert(Node node, String key) {
            if (node == null)
                return new Node(key);

            if (key.compareTo(node.key) < 0)
                node.left = insert(node.left, key);
            else if (key.compareTo(node.key) > 0)
                node.right = insert(node.right, key);

            return node;
        }

        void insert(String key) {
            root = insert(root, key);
        }

        int height(Node node) {
            if (node == null)
                return -1;

            return 1 + Math.max(height(node.left), height(node.right));
        }

        int height() {
            return height(root);
        }
    }

    static String generateId(Random random) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder("pay_");

        for (int i = 0; i < 12; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        int n = 30000;

        BST bst = new BST();

        Random random = new Random();

        for (int i = 0; i < n; i++) {
            bst.insert(generateId(random));
        }

        int actualHeight = bst.height();

        double expectedHeight =
                1.39 * (Math.log(n) / Math.log(2));

        System.out.println("Transactions: " + n);
        System.out.printf("Expected Height: %.2f%n", expectedHeight);
        System.out.println("Observed Height: " + actualHeight);

        if (actualHeight > expectedHeight + 3) {
            System.out.println("Tree appears more unbalanced than expected.");
        } else {
            System.out.println("Tree height is within expected range.");
        }
    }
}
