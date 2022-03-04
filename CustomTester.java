import java.util.*;

import javax.security.auth.callback.ChoiceCallback;

import static org.junit.Assert.*;
import org.junit.*;

public class CustomTester {
    MyBST<Integer, Integer> emptyTree;
    MyBST<Integer, Integer> basicBalancedTree;
    MyBST<Integer, Integer> complexBalancedTree;
    MyBST<Integer, Integer> unbalancedTree;

    @Before
    public void setUp() {
        emptyTree = new MyBST<>();

        basicBalancedTree = new MyBST<>();
        basicBalancedTree.insert(3, 3);
        basicBalancedTree.insert(5, 5);
        basicBalancedTree.insert(4, 4);
        basicBalancedTree.insert(6, 6);
        basicBalancedTree.insert(2, 2);
        basicBalancedTree.insert(1, 1);
        /**
         * 3
         * / \
         * 2 5
         * / / \
         * 1 4 6
         */

        complexBalancedTree = new MyBST<>();
        complexBalancedTree.insert(10, 10);
        complexBalancedTree.insert(20, 10);
        complexBalancedTree.insert(15, 10);
        complexBalancedTree.insert(17, 10);
        complexBalancedTree.insert(22, 10);
        complexBalancedTree.insert(8, 10);
        complexBalancedTree.insert(7, 10);
        /**
         * 10
         * / \
         * 8 20
         * / / \
         * 7 15 22
         * \
         * 17
         */

        unbalancedTree = new MyBST<>();
        unbalancedTree.insert(1, 1);
        unbalancedTree.insert(2, 2);
        unbalancedTree.insert(3, 3);
        unbalancedTree.insert(4, 4);
        unbalancedTree.insert(5, 5);
        unbalancedTree.insert(6, 6);
    }

    @Test
    public void testInsert() {
        // inserts a node at the root
        emptyTree.insert(1, 2);
        assertEquals(Integer.valueOf(1), emptyTree.root.getKey());

        // inserts a node right side
        emptyTree.insert(3, 3);
        // inserts a node right left side
        emptyTree.insert(2, 3);
        assertEquals(Integer.valueOf(2),
                emptyTree.root.getRight().getLeft().getKey());

        // test insert when the key is the same
        emptyTree.insert(3, 4);
        assertEquals(Integer.valueOf(4),
                emptyTree.root.getRight().getValue());
    }

    @Test
    public void testInsertNull() {
        // inserts a node at the root
        try {
            emptyTree.insert(null, 1);
            fail();
        } catch (NullPointerException e) {
            System.out.println("Cannot insert a null node into a binary tree");
        }
    }

    @Test
    public void testRemove() {
        // tests remove on an empty tree
        assertEquals(null, emptyTree.remove(1));
        assertEquals(null, emptyTree.root);

        // test remove on a MyBTSNode not in the tree when tree is filled
        assertEquals(null, basicBalancedTree.remove(7));
        assertEquals((Integer) 3, basicBalancedTree.root.getKey());
        assertEquals((Integer) 5,
                basicBalancedTree.root.getRight().getKey());
        assertEquals((Integer) 6,
                basicBalancedTree.root.getRight().getRight().getKey());

    }

    @Test
    public void testRemoveTwo() {
        // tests remove on Tree when the root of the tree is removed

        assertEquals((Integer) 3, basicBalancedTree.remove(3));
        assertEquals((Integer) 4, basicBalancedTree.root.getKey());
        assertEquals(null, basicBalancedTree.root.getRight().getLeft());

        // test remove when nodes must be moved up succesivly on an unbalanced
        // tree
        assertEquals((Integer) 2, unbalancedTree.remove(2));
        assertEquals((Integer) (1), unbalancedTree.root.getKey());
        assertEquals((Integer) (3), unbalancedTree.root.getRight().getKey());
        assertEquals((Integer) (4),
                unbalancedTree.root.getRight().getRight().getKey());
        assertEquals((Integer) (5),
                unbalancedTree.root.getRight().getRight().getRight().getKey());

        // tests remove when the successor has a sucessor
        complexBalancedTree.remove(10);
        assertEquals((Integer) 15, complexBalancedTree.root.getKey());
        System.out.println(complexBalancedTree.root.getRight().getRight().toString());
        assertEquals((Integer) 17, complexBalancedTree.root.getRight().getLeft().getKey());

    }

    @Test
    public void testSearch() {
        // search for a null should always return null
        assertEquals("Null should be returned", null, basicBalancedTree.search(null));
        // search for an element not in the tree should return null
        assertEquals("Null should be returned", null, basicBalancedTree.search(0));
    }

    @Test
    public void testInorder() {
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> emptyTreeExpected 
        = new ArrayList<>();
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> emptyTreeOutput 
        = emptyTree.inorder();
        assertEquals("The arrayList of an empty tree should have no elements",
                0, emptyTreeOutput.size());
    }

}
