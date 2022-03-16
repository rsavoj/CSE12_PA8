
/**
 * Name: Roya Savoj
 * ID: A16644834
 * Email: rsavoj@ucsd.edu
 * Sources used: Zybooks, and Lecture Slides
 * 
 * The custom tester file implements tests which test methods from the 
 * test the MyBST.java node and MyCalander.java file that are not covered in 
 * the public tester class 
 */
import java.util.*;
import javax.security.auth.callback.ChoiceCallback;
import static org.junit.Assert.*;
import org.junit.*;

/**
 * This file uses J unit test methods from index locations not covered in the
 * public tester and tests which are expected to cause errors
 */
public class CustomTester {
    MyBST<Integer, Integer> emptyTree;
    MyBST<Integer, Integer> basicBalancedTree;
    MyBST<Integer, Integer> complexBalancedTree;
    MyBST<Integer, Integer> unbalancedTree;

    /**
     * Sets up the conditions for the tests below
     */
    @Before
    public void setUp() {
        // 4 test trees are constructed to run tests on
        emptyTree = new MyBST<>();

        basicBalancedTree = new MyBST<>();
        basicBalancedTree.insert(3, 3);
        basicBalancedTree.insert(5, 5);
        basicBalancedTree.insert(4, 4);
        basicBalancedTree.insert(6, 6);
        basicBalancedTree.insert(2, 2);
        basicBalancedTree.insert(1, 1);

        complexBalancedTree = new MyBST<>();
        complexBalancedTree.insert(10, 10);
        complexBalancedTree.insert(20, 10);
        complexBalancedTree.insert(15, 10);
        complexBalancedTree.insert(17, 10);
        complexBalancedTree.insert(22, 10);
        complexBalancedTree.insert(8, 10);
        complexBalancedTree.insert(7, 10);

        unbalancedTree = new MyBST<>();
        unbalancedTree.insert(1, 1);
        unbalancedTree.insert(2, 2);
        unbalancedTree.insert(3, 3);
        unbalancedTree.insert(4, 4);
        unbalancedTree.insert(5, 5);
        unbalancedTree.insert(6, 6);
    }

    /**
     * Tests the insertion of nodes on an empty tree
     */
    @Test
    public void testInsert() {
        assertEquals(0, emptyTree.size);
        // inserts a node at the root
        emptyTree.insert(1, 2);
        assertEquals(Integer.valueOf(1), emptyTree.root.getKey());
        assertEquals(1, emptyTree.size);

        // inserts a node right side
        emptyTree.insert(3, 3);
        // inserts a node right left side
        emptyTree.insert(2, 3);
        assertEquals(Integer.valueOf(2),
                emptyTree.root.getRight().getLeft().getKey());
        assertEquals(3, emptyTree.size);
       
        

    }

    /**
     * Tests the insertion of a node when key is already in the tree
     */
    @Test
    public void testInsertSameKey() {
        emptyTree.insert(1, 2);
        emptyTree.insert(3, 3);
        // test insert when the key is the same
        emptyTree.insert(3, 4);
        assertEquals(Integer.valueOf(4),
                emptyTree.root.getRight().getValue());
    }

    /**
     * Tests the insertion of a null key
     */
    @Test
    public void testInsertNull() {
        // inserts a node at the root
        try {
            emptyTree.insert(null, 1);
            fail();
        } catch (NullPointerException e) {
            System.out.println("Cannot insert a null node into a binary tree");
        }
         
        //insert a node to a tree with a root
        
    }

    /**
     * Tests remove on an empty tree and a tree where the key is not in the
     * tree
     */
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

    /**
     * Tests remove on a tree where the root is removed
     */
    @Test
    public void testRemoveTwo() {
        // tests remove on Tree when the root of the tree is removed

        assertEquals((Integer) 3, basicBalancedTree.remove(3));
        assertEquals((Integer) 4, basicBalancedTree.root.getKey());
        assertEquals(null, basicBalancedTree.root.getRight().getLeft());

    }

    /**
     * Tests remove on an unbalenced tree where multiple one child swaps must
     * take place
     */
    @Test
    public void testRemoveThree() {
        // test remove when nodes must be moved up on an unbalanced tree
        assertEquals((Integer) 2, unbalancedTree.remove(2));
        assertEquals((Integer) (1), unbalancedTree.root.getKey());
        assertEquals((Integer) (3), unbalancedTree.root.getRight().getKey());
        assertEquals((Integer) (4),
                unbalancedTree.root.getRight().getRight().getKey());
        assertEquals((Integer) (5),
                unbalancedTree.root.getRight().getRight().getRight().getKey());

    }

    /**
     * Tests remove on an a balanced tree where the node has two childrem and
     * the successor is not a leaf node
     */
    @Test
    public void testRemoveFour() {
        // tests remove when the successor has a sucessor
        complexBalancedTree.remove(10);
        assertEquals((Integer) 15, complexBalancedTree.root.getKey());
        assertEquals((Integer) 17,
                complexBalancedTree.root.getRight().getLeft().getKey());
    }

    /**
     * Tests searching for null BSTNodes and null key values
     */
    @Test
    public void testSearch() {
        // search for a null should always return null
        assertEquals("Null should be returned", null,
                basicBalancedTree.search(null));
        // search for an element not in the tree should return null
        assertEquals("Null should be returned", null,
                basicBalancedTree.search(0));
    }

    /**
     * Tests an inorder search from an empty tree
     */
    @Test
    public void testInorder() {
        ArrayList<MyBST.MyBSTNode<Integer, Integer>> emptyTreeOutput
         = emptyTree.inorder();
        assertEquals("The arrayList of an empty tree should have no elements",
                0, emptyTreeOutput.size());
    }

    /**
     * Tests booking an event that is within bounds and an event where times
     * overlap.
     */
    @Test
    public void testCalendar() {
        MyCalendar cal = new MyCalendar();
        cal.book(10, 20);
        // book an event that is within both bounds
        assertFalse(cal.book(11, 12));

        // book an event where times overlap
        try{
           cal.book(2, 2);
           fail();
        }
        catch(IllegalArgumentException e){
            System.out.println("cannot add an element with the same start" +
           " and end time ");
        }

        // book an event where start time is less than 0 
        try{
            cal.book(-1, 2);
            fail();
         }
         catch(IllegalArgumentException e){
             System.out.println("cannot have a negative start time");
         }

       
         assertTrue(cal.book(0, 1)); 
         assertTrue(cal.book(2, 3));
          assertFalse(cal.book(0, 1));

       
          assertTrue(cal.book(3, 5));
        
       
    }

}
