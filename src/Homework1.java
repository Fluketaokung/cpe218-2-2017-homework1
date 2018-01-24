package homework1;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Stack;
import javax.swing.JTree;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.net.URL;
import java.io.IOException;

public class Homework1 extends JPanel
        implements TreeSelectionListener {

    private JEditorPane htmlPane;
    private JTree tree;
    private static Node root;
    private static String g;
    private static boolean useleaf = false;
    private static String lineStyle = "Horizontal";

    public Homework1() {
        super(new GridLayout(1, 0));

        //Create the nodes.
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(root);

        //Create a tree that allows one selection at a time.
        createNodes(top, root);
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);


        //Create the scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane(tree);

        //Create the HTML viewing pane.
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        JScrollPane htmlView = new JScrollPane(htmlPane);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);

        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100);
        splitPane.setPreferredSize(new Dimension(500, 300));

        //Add the split pane to this panel.
        add(splitPane);
        ImageIcon leafIcon = createImageIcon("middle.gif");
        if (leafIcon != null) {
            DefaultTreeCellRenderer renderer
                    = new DefaultTreeCellRenderer();
            renderer.setClosedIcon(leafIcon);
            renderer.setOpenIcon(leafIcon);
            tree.setCellRenderer(renderer);
        }
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Homework1.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
   private class BookInfo {
        public String bookName;
        public URL bookURL;
        public String prefix = "file:" 
                               + System.getProperty("user.dir")
                               + System.getProperty("file.separator");
        public BookInfo(String book, String filename) {
            bookName = book;
            try {
                bookURL = new URL(prefix + filename);
            } catch (java.net.MalformedURLException exc) {
                System.err.println("Attempted to create a BookInfo "
                                   + "with a bad URL: " + bookURL);
                bookURL = null;
            }
        }

        @Override
        public String toString() {
            return bookName;
        }
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        useleaf = node.isLeaf();
        Object nodeInfo = node.getUserObject();
        DisplayNode((Node) nodeInfo);

    }
    public static String input;

    public static void main(String[] args) {
        // input = args[0];
        input = "251-*32*+";

        char[] Newinput = input.toCharArray();

        root = Stack(Newinput);
        g = inflix(root);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }
    

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Binary Tree Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new Homework1());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void DisplayNode(Node n) {
        g = inflix(n);
        if (!useleaf) {
            g = g + "=" + calculate(n);
        }
        htmlPane.setText(g);
    }

    public static Node Stack(char Newinput[]) {

        Stack<Node> data = new Stack();
        Node a, num1, num2;

        for (int i = 0; i < Newinput.length; i++) {

            if (Number(Newinput[i])) {
                a = new Node(Newinput[i]);
                data.push(a);
            } else {
                a = new Node(Newinput[i]);
                num2 = data.pop();
                num1 = data.pop();
                a.left = num1;
                a.right = num2;
                data.push(a);
            }
        }
        a = data.peek();
        return a;
    }

    public void createNodes(DefaultMutableTreeNode top, Node n) {
        DefaultMutableTreeNode left;
        DefaultMutableTreeNode right;
//        if (n.right != null) {
//        if(Number(n.Newinput)){
//            left = new DefaultMutableTreeNode(new BookInfo(n.left+"","tutorial.html"));
//            top.add(left);
//            right = new DefaultMutableTreeNode(new BookInfo(n.right+"","tutorial.html"));
//            top.add(right);
//        }
//        else{        
//            right = new DefaultMutableTreeNode(n.right);
//            top.add(right);
//            createNodes(right, n.right);
//        }
//        if (n.left != null) {
//            if(Number(n.Newinput)){
//            left = new DefaultMutableTreeNode(new BookInfo(n.left+"","tutorial.html"));
//            top.add(left);
//            right = new DefaultMutableTreeNode(new BookInfo(n.right+"","tutorial.html"));
//            top.add(right);
//        }
//        }else{
//            left = new DefaultMutableTreeNode(n.left);
//            top.add(left);
//            createNodes(left, n.left);
//        }
        
        if (n.left != null) {
            left = new DefaultMutableTreeNode(n.left);
            top.add(left);
           createNodes(left, n.left);
        }
        if (n.right != null) {
            right = new DefaultMutableTreeNode(n.right);
            top.add(right);
           createNodes(right, n.right);

        }

    }
  //  }

    public static void Inorder(Node a, StringBuilder inflix) {
        if (a != null) {
            inflix.append('(');
            switch (a.Newinput) {
                case '+':
                    Inorder(a.left, inflix);
                    inflix.append('+');
                    Inorder(a.right, inflix);
                    break;
                case '-':
                    Inorder(a.left, inflix);
                    inflix.append('-');
                    Inorder(a.right, inflix);
                    break;
                case '*':
                    Inorder(a.left, inflix);
                    inflix.append('*');
                    Inorder(a.right, inflix);
                    break;
                case '/':
                    Inorder(a.left, inflix);
                    inflix.append('/');
                    Inorder(a.right, inflix);
                    break;
                default:
                    inflix.append(a.Newinput);
            }
            inflix.append(')');
        }
    }

    public static boolean Operator(char Newinput) {
        switch (Newinput) {
            case '+':
                return true;
            case '-':
                return true;
            case '*':
                return true;
            case '/':
                return true;
            default:
                return false;
        }
    }

    public static boolean Number(char Newinput) {
        switch (Newinput) {
            case '0':
                return true;
            case '1':
                return true;
            case '2':
                return true;
            case '3':
                return true;
            case '4':
                return true;
            case '5':
                return true;
            case '6':
                return true;
            case '7':
                return true;
            case '8':
                return true;
            case '9':
                return true;
            default:
                return false;
        }
    }

    public static int calculate(Node a) {
        if (a != null) {
            switch (a.Newinput) {
                case '+':
                    return calculate(a.left) + calculate(a.right);
                case '-':
                    return calculate(a.left) - calculate(a.right);
                case '*':
                    return calculate(a.left) * calculate(a.right);
                case '/':
                    return calculate(a.left) / calculate(a.right);
                default:
                    return Integer.parseInt((String.valueOf(a.Newinput)));
            }

        } else {
            return 0;
        }
    }

    public static String inflix(Node a) {
        StringBuilder inflix = new StringBuilder();
        Inorder(a, inflix);

        inflix.deleteCharAt(0);
        inflix.deleteCharAt(inflix.length() - 1);
        for (int i = 1; i < inflix.length() - 1; i++) {

            if (Number(inflix.charAt(i))) {
                inflix.deleteCharAt(i - 1);

                if (i + 1 <= inflix.length()) {
                    inflix.deleteCharAt(i);
                }

            }
        }
        return inflix.toString();
    }

    public static class Node {

        Node left;
        Node right;
        char Newinput;
        
        Node(char x) {

            Newinput = x;
            left = null;
            right = null;

        }

    }
}
