
/**
 * Name: Roya Savoj
 * Email: rsavoj@ucsd.edu
 * Sources used: Zyebooks, lecture slides 
 * 
 * This is a class that can create a MyBTS object. This object has MyBSTNodes. 
 * Each node has a key and a value. The nodes are inputed in to the tree such
 * that every node's key to the left of the parents is less than the parents 
 * key and every node that is to the right of the parents key is greater 
 * than the parents key
 */
import java.util.ArrayList;

/**
 * This class creates a MyBST object with size 0 and a null root. BSTNodes
 * can be added using the insert method following the ordering patern. Keys
 * can be found using the search algrithum. Nodes can be removed via the remove
 * mehod
 */
public class MyBST<K extends Comparable<K>, V> {
    public static final int ZERO =0;
    MyBSTNode<K, V> root = null;
    int size = 0;

    /**
     * returns the size of the MyBTS tree
     * 
     * @return The size of the MyBTS tree
     */
    public int size() {
        return size;
    }

    /**
     * Adds a node to the correct location in the tree, so that it follows the
     * rules of a Binary Search tree
     * 
     * @param key   the key of the node being inserted
     * @param value the value of the node being inserted
     * @return Null or the value previously in the key if it was already filled
     */
    public V insert(K key, V value) {
        // if key is null throws a null pointer exception
        if (key == null) {
            throw new NullPointerException();
        }
        // calls the helper method
        return insertHelper(key, value, null, this.root, false);
    }

    /**
     * Recursive helper methos to insert the BTSNode at the correct location.
     * Finds the next null location where all nodes to the left of the
     * node are less than the node and all nodes to the right of the node have
     * a greater key
     * 
     * @param key    the key of the node being inserted
     * @param value  the value of the node being inserted
     * @param parent the node we are currently searching
     * @return Null or the value previously in the key if it was already filled
     */
    private V insertHelper(K key, V value, MyBST.MyBSTNode<K, V> parent,
            MyBST.MyBSTNode<K, V> curr, boolean isLeft) {
        // base case leaf node found will create the BTS node and insert it
        if (curr == null) {
            curr = new MyBSTNode<K, V>(key, value, parent);
            if (parent == null) {
                root = curr;
               // curr.setParent(root);
                size++;
                return null;
            }
            if (isLeft) {
                parent.setLeft(curr);
            } else {
                parent.setRight(curr);
            }
            size++;
            return null;
        }
        // base case for when key is already in the Binary Search tree
        if (curr.key == key) {
            V temp = curr.getValue();
            curr.setValue(value);
            return temp;
        }

        // if the key is less than the current key the insertHelper method
        // will search the left child
        if (key.compareTo(curr.getKey()) < ZERO) {

            return insertHelper(key, value, curr, curr.getLeft(), true);
        }
        // will search the right child

        return insertHelper(key, value, curr, curr.getRight(), false);
    }

    /**
     * Searches for the node with a given key in the tree and returns its
     * value if it is in the tree. If it is not in the tree the function
     * returns null
     * 
     * @param key the key of the node being searched for
     * @return Null if key is not found or the value of the key being searched
     */
    public V search(K key) {
        // if key = null return null
        if (key == null) {
            return null;
        }
        // start at the root then call the helepr method to look for the key
        MyBST.MyBSTNode<K, V> curr = this.root;
        MyBST.MyBSTNode<K, V> item = searchHelper(curr, key);

        // returns null if the item was null
        if (item == null) {
            return null;
        }
        // returns the value
        return item.getValue();
    }

    /**
     * Helper method used to Searcj for the node with a given key in the tree
     * that returns its alue if it is in the tree. If it is not in the tree the
     * function returns null
     * 
     * @param curr the current node being searched
     * @param key  the key of the node being searched for
     * @return Null if key is not found or the value of the key being searched
     */
    private MyBSTNode<K, V> searchHelper(MyBST.MyBSTNode<K, V> curr, K key) {
        // returns null if we have reached the leaf of the tree
        if (curr == null) {
            return null;
        }
        // checks if we have found the key
        K currKey = curr.getKey();
        if (curr.getKey() == key) {
            return curr;
        }
        // if the key is less than our current nodes key than searches the left
        // side of the tree
        if (key.compareTo(currKey) == -1) {
            return searchHelper(curr.getLeft(), key);
        }
        // if the key isg reater than our current nodes key than searches the
        // right side of the tree
        return searchHelper(curr.getRight(), key);

    }

    /**
     * The remove method removes the node of a given key from the tree
     * 
     * @param key the key of the node being searched for
     * @return the value of the key being removed
     */
    public V remove(K key) {
        if(key == null){
            return null;
        }
        // finds the node to remove in the tree
        MyBSTNode<K, V> removing = searchHelper(this.root, key);

       
        // if the node that is found is null than it returns null
        if (removing == null) {
            return null;
        }
        // otherwise returns the remove helper method
        return removeHelper(removing);
    }

    /**
     * The remove helper removes the node of a given key from the tree while
     * maintaining the ordering of the BST
     * 
     * @param removing the node that we are removing from the list
     * @return the value of the key being removed
     */
    public V removeHelper(MyBSTNode<K, V> removing) {
        // if removing is null returns null
        if (removing == null) {
            return null;
        }
        // case where the node has no children
        if (hasNoChildren(removing)) {

            // calls a helper method to check if the node we are removing
            // is the left child
            removeLeaf(removing);
            // node we are removing is a leaf node it is removed and its value
            // is returned
            return removing.getValue();
        }

        // case where the node has 2 children
        if (hasTwoChildren(removing)) {
            MyBSTNode<K, V> successor = removing.successor();
            swap(removing, successor);

            // recursivly call the method until the node we are removing is a
            // leaf node
            return removeHelper(removing);
        }
        // case where the node has one child
        else {
            // if there is a left node swap with the left side
            if (removing.getLeft() != null) {
                swap(removing, removing.getLeft());
            }
            // if there is a right node swap with the right side
            else {
                swap(removing, removing.getRight());
            }
            // recursivly call the method until the node we are removing is a
            // leaf node
            return removeHelper(removing);
        }

    }

    /**
     * The remove helper removes a leaf in the Binary search tree
     * 
     * @param removing the node that we are removing from the list
     */
    private void removeLeaf(MyBSTNode<K, V> removing) {
        size--;
        if (isLeftChild(removing)) {
            // removes left node
            removing.getParent().setLeft(null);
        } else {
            // removes right node
            removing.getParent().setRight(null);
        }
    }

    /**
     * This method resets the pointers in the parent node of a left or a right
     * child to the second node given
     * 
     * @param nodeOneParent a pointer to the parent which has a child node that
     *                      is going to be reset
     * @param nodeTwo       the node that the parent pointer is being set to
     * @param isLeft        if this is true we reset the parent nodes left child
     *                      pointer if this is false reset the parent nodes right
     *                      child
     */
    private void resetParent(MyBST.MyBSTNode<K, V> nodeOneParent,
            MyBST.MyBSTNode<K, V> nodeTwo, boolean isLeft) {
        // if node one was the root resets parent pointers
        if (nodeOneParent == null) {
            root = nodeTwo;
        }
        // if it was the left child resets parent pointers
        else if (isLeft) {
   
            (nodeOneParent).setLeft(nodeTwo);
            System.out.println("To " + nodeOneParent.getLeft());
        }
        // if node one was the right child resents parent pointers
        else {
            (nodeOneParent).setRight(nodeTwo);
        }
    }

    /**
     * This method resets the childs parent pointer to the given node
     * 
     * @param nodeOneChild the child whose parent node pointers are being reset
     * @param nodeTwo      the node that the parent pointer is being set to
     */
    private void resetChild(MyBST.MyBSTNode<K, V> nodeOneChild,
            MyBST.MyBSTNode<K, V> nodeTwo) {
        if (nodeOneChild != null) {
            nodeOneChild.setParent(nodeTwo);
        }
    }

    /**
     * This method will swap the location of two nodes in the tree
     * 
     * @param nodeOne the first node being swaped
     * @param nodeTwo the second node being swaped
     */
    private void swap(MyBST.MyBSTNode<K, V> nodeOne,
            MyBST.MyBSTNode<K, V> nodeTwo) {
        // helper variables
        MyBST.MyBSTNode<K, V> parentTemp = nodeOne.getParent();
        MyBST.MyBSTNode<K, V> rightTemp = nodeOne.getRight();
        MyBST.MyBSTNode<K, V> leftTemp = nodeOne.getLeft();
        MyBST.MyBSTNode<K, V> parentTemp2 = nodeTwo.getParent();
        MyBST.MyBSTNode<K, V> rightTemp2 = nodeTwo.getRight();
        MyBST.MyBSTNode<K, V> leftTemp2 = nodeTwo.getLeft();

        // checks if the first node is the parent of the second node
        // if this is the case it calls a helper method
        if (parentTemp == nodeTwo) {
            swapCaseTwo(nodeTwo, nodeOne);
            return;
        }

        // checks if the second node is the parent of the first node
        // if this is the case it calls a helper method
        if (parentTemp2 == nodeOne) {
            swapCaseTwo(nodeOne, nodeTwo);
            return;
        }

        // Stores data on if the node in question is the right or left node
        boolean isLeftOne = isLeftChild(nodeOne);
        boolean isLeftTwo = isLeftChild(nodeTwo);

        // sets nodeTwo's pointers
        nodeOne.setParent(parentTemp2);
        nodeOne.setRight(rightTemp2);
        nodeOne.setLeft(leftTemp2);

        // sets nodeOne's pointers
        nodeTwo.setParent(parentTemp);
        nodeTwo.setRight(rightTemp);
        nodeTwo.setLeft(leftTemp);

        // resets the pointers of the parent nodes
        resetParent(parentTemp, nodeTwo, isLeftOne);
        resetParent(parentTemp2, nodeOne, isLeftTwo);

        // resets the pointers of node children
        resetChild(rightTemp, nodeTwo);
        resetChild(leftTemp, nodeTwo);
        resetChild(rightTemp2, nodeOne);
        resetChild(leftTemp2, nodeOne);

    }

    /**
     * This method will swap the location of two nodes in the tree if the first
     * node is the parent node and the second node is the child node
     * 
     * @param nodeParent the node that is being swapped and is the parent
     * @param nodeChild  the node that is being swaped and is the child
     */
    private void swapCaseTwo(MyBST.MyBSTNode<K, V> nodeParent,
            MyBST.MyBSTNode<K, V> nodeChild) {
        // stored temporary variables for the two nodes
        boolean whichChild = isLeftChild(nodeChild);
        boolean isLeft = isLeftChild(nodeParent);
        MyBST.MyBSTNode<K, V> rightTemp = nodeParent.getRight();
        MyBST.MyBSTNode<K, V> leftTemp = nodeParent.getLeft();
        MyBST.MyBSTNode<K, V> parentTemp = nodeParent.getParent();
        MyBST.MyBSTNode<K, V> rightTemp2 = nodeChild.getRight();
        MyBST.MyBSTNode<K, V> leftTemp2 = nodeChild.getLeft();
        MyBST.MyBSTNode<K, V> parentTemp2 = nodeChild.getParent();

        // modify parent node pointers
        nodeParent.setParent(nodeChild);
        nodeParent.setRight(rightTemp2);
        nodeParent.setLeft(leftTemp2);

        // modify child node pointers
        if (whichChild) {
            nodeChild.setLeft(nodeParent);
            nodeChild.setRight(rightTemp);
        } else {
            nodeChild.setLeft(leftTemp);
            nodeChild.setRight(nodeParent);
        }
        nodeChild.setParent(parentTemp);

        // modify pointers pointing to parent node
        if (whichChild) {
            resetChild(rightTemp, nodeChild);
        } else {
            resetChild(leftTemp, nodeChild);
        }
        resetParent(parentTemp, nodeChild, isLeft);

        // modify pointer pointing to the child node
        resetChild(rightTemp2, nodeParent);
        resetChild(leftTemp2, nodeParent);
    }

    /**
     * This method checks if the current node has no children
     * 
     * @param curr the node that is being checked
     * @return true if the current node has no children
     */
    private boolean hasNoChildren(MyBST.MyBSTNode<K, V> curr) {
        if (curr.getRight() == null && curr.getLeft() == null) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if the current node has two children
     * 
     * @param curr the node that is being checked
     * @return true if the current node has two children
     */
    private boolean hasTwoChildren(MyBST.MyBSTNode<K, V> curr) {
        if (curr.getRight() != null && curr.getLeft() != null) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if the given node is a left child
     * 
     * @param nodeOne the node that is being checked
     * @return returns true if the node is the left child. Returns false
     *         if the node is a right child or is the root
     */
    private boolean isLeftChild(MyBST.MyBSTNode<K, V> nodeOne) {
        if (nodeOne.getParent() == null) {
            return false;
        }
        MyBST.MyBSTNode<K, V> parentsLeft = nodeOne.getParent().getLeft();

        if (parentsLeft == null || (parentsLeft != nodeOne)) {
            return false;
        }

        return true;
    }

    /**
     * Returns an arrayList containing the keys inorder
     * 
     * @return An arraylist of all the BTSNodes in the tree where their nodes
     *         are in order from smallest key to largest key
     */
    public ArrayList<MyBSTNode<K, V>> inorder() {
        ArrayList<MyBSTNode<K, V>> array = new ArrayList<>();
        inorderHelper(this.root, array);
        return array;
    }

    /**
     * inorder helper method modifies the arraylist adding the nodes and then
     * adding the remaining nodes in inreasesing order via recursive calls
     * 
     * @param curr  the node we are currently adding
     * @param array the array we are adding the nodes too
     */
    private void inorderHelper(MyBSTNode<K, V> curr,
            ArrayList<MyBSTNode<K, V>> array) {
        if (curr == null) {
            return;
        }
        inorderHelper(curr.left, array);
        array.add(curr);
        inorderHelper(curr.right, array);

    }

    /**
     * This class creates a MyBSTNode object. Each My BTS node has a left,
     * right, and parent pointer. Each node has a key which is used to
     * compare nodes and a value.
     */
    static class MyBSTNode<K, V> {
        private static final String TEMPLATE = "Key: %s, Value: %s";
        private static final String NULL_STR = "null";

        private K key;
        private V value;
        private MyBSTNode<K, V> parent;
        private MyBSTNode<K, V> left = null;
        private MyBSTNode<K, V> right = null;

        /**
         * Creates a MyBSTNode<K,V> storing specified data
         * 
         * @param key    the key the MyBSTNode<K,V> will
         * @param value  the data the MyBSTNode<K,V> will store
         * @param parent the parent of this node
         */
        public MyBSTNode(K key, V value, MyBSTNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Return the key stored in the the MyBSTNode<K,V>
         * 
         * @return the key stored in the MyBSTNode<K,V>
         */
        public K getKey() {
            return key;
        }

        /**
         * Return data stored in the MyBSTNode<K,V>
         * 
         * @return the data stored in the MyBSTNode<K,V>
         */
        public V getValue() {
            return value;
        }

        /**
         * Return the parent
         * 
         * @return the parent
         */
        public MyBSTNode<K, V> getParent() {
            return parent;
        }

        /**
         * Return the left child
         * 
         * @return left child
         */
        public MyBSTNode<K, V> getLeft() {
            return left;
        }

        /**
         * Return the right child
         * 
         * @return right child
         */
        public MyBSTNode<K, V> getRight() {
            return right;
        }

        /**
         * Set the key stored in the MyBSTNode<K,V>
         * 
         * @param newKey the key to be stored
         */
        public void setKey(K newKey) {
            this.key = newKey;
        }

        /**
         * Set the data stored in the MyBSTNode<K,V>
         * 
         * @param newValue the data to be stored
         */
        public void setValue(V newValue) {
            this.value = newValue;
        }

        /**
         * Set the parent
         * 
         * @param newParent the parent
         */
        public void setParent(MyBSTNode<K, V> newParent) {
            this.parent = newParent;
        }

        /**
         * Set the left child
         * 
         * @param newLeft the new left child
         */
        public void setLeft(MyBSTNode<K, V> newLeft) {
            this.left = newLeft;
        }

        /**
         * Set the right child
         * 
         * @param newRight the new right child
         */
        public void setRight(MyBSTNode<K, V> newRight) {
            this.right = newRight;
        }

        /**
         * This method returns the in order successor of current node object.
         * It can be served as a helper method when implementing inorder().
         * 
         * @return the successor of current node object
         */
        public MyBSTNode<K, V> successor() {
            // checks that the right node is not null
            if (this.getRight() != null) {
                // saves the right node
                MyBSTNode<K, V> curr = this.getRight();

                // finds the minimum node of the right side of the parent and
                // returns it
                while (curr.getLeft() != null) {
                    curr = curr.getLeft();
                }
                // returns the minimum element on the right side of the tree
                return curr;
            } else {
                // saves the parent and the current node
                MyBSTNode<K, V> parent = this.getParent();
                MyBSTNode<K, V> curr = this;
                // if current node is the right node and not the root curr
                // will be updated to parent and parent will be updated to
                // parents parent
                while (parent != null && curr == parent.getRight()) {
                    curr = parent;
                    parent = parent.getParent();
                }
                // returns the minimum parent on the left side of the our node
                return parent;
            }
        }

        /**
         * This method returns the in order predecessor of current node object.
         * 
         * @return the predecessor of current node object
         */
        public MyBSTNode<K, V> predecessor() {
            // finds the maximum node on the left side of the tree
            if (this.getLeft() != null) {
                MyBSTNode<K, V> curr = this.getLeft();
                while (curr.getRight() != null) {
                    curr = curr.getRight();
                }
                return curr;
            } else {
                // finds the maximum parent node that the given node is a left
                // child or on the left side of.
                MyBSTNode<K, V> parent = this.getParent();
                MyBSTNode<K, V> curr = this;
                while (parent != null && curr == parent.getLeft()) {
                    curr = parent;
                    parent = parent.getParent();
                }
                return parent;
            }
        }

        /**
         * This method compares if two node objects are equal.
         * 
         * @param obj The target object that the currect object compares to.
         * @return Boolean value indicates if two node objects are equal
         */
        public boolean equals(Object obj) {
            if (!(obj instanceof MyBSTNode))
                return false;

            MyBSTNode<K, V> comp = (MyBSTNode<K, V>) obj;

            return ((this.getKey() == null ? comp.getKey() == null :
             this.getKey().equals(comp.getKey()))
                    && (this.getValue() == null ? comp.getValue() == null :
                     this.getValue().equals(comp.getValue())));
        }

        /**
         * This method gives a string representation of node object.
         * 
         * @return "Key:Value" that represents the node object
         */
        public String toString() {
            return String.format(
                    TEMPLATE,
                    this.getKey() == null ? NULL_STR : this.getKey(),
                    this.getValue() == null ? NULL_STR : this.getValue());
        }
    }

}