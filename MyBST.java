/**
 * Name: Roya Savoj
 * Email: rsavoj@ucsd.edu
 * Sources used: Zyebooks, lecture slides 
 * 
 * This is a class that can create a MyBTS object. This object has MyBSTNodes. 
 * Each node has a key and a value. The nodes are inputed in to the tree such
 * that every node's key to the left of the parents is less than the parents 
 * key and every node that is to the right of the parents key is greater 
 *  than the parents key
 */
import java.util.ArrayList;

/**
 * This class creates a MyBST object with size 0 and a null root. BSTNodes 
 * can be added using the insert method following the ordering patern. Keys 
 * can be found using the search algrithum. Nodes can be removed via the remove 
 * mehod 
 */
public class MyBST<K extends Comparable<K>, V> {
    MyBSTNode<K, V> root = null;
    int size = 0;

    public int size() {
        return size;
    }

    public V insert(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        return insertHelper(key, value, null, this.root, false);
    }

    private V insertHelper(K key, V value, MyBST.MyBSTNode<K, V> parent,
            MyBST.MyBSTNode<K, V> curr, boolean isLeft) {
        // base case leaf node found will create the BTS node and insert it
        if (curr == null) {
            curr = new MyBSTNode<K, V>(key, value, parent);
            if (parent == null) {
                root = curr;
                return null;
            }
            if (isLeft) {
                parent.setLeft(curr);
            } else {
                parent.setRight(curr);
            }
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
        if (key.compareTo(curr.getKey()) < 0) {

            return insertHelper(key, value, curr, curr.getLeft(), true);
        }
        // will search the right child

        return insertHelper(key, value, curr, curr.getRight(), false);
    }

    public V search(K key) {
        if (key == null) {
            return null;
        }
        MyBST.MyBSTNode<K, V> curr = this.root;
        MyBST.MyBSTNode<K, V> item = searchHelper(curr, key);
        if (item == null) {
            return null;
        }
        return item.getValue();
    }

    private MyBSTNode<K, V> searchHelper(MyBST.MyBSTNode<K, V> curr, K key) {
        if (curr == null) {
            return null;
        }
        K currKey = curr.getKey();
        if (curr.getKey() == key) {
            return curr;
        }
        if (key.compareTo(currKey) == -1) {
            return searchHelper(curr.getLeft(), key);
        }
        return searchHelper(curr.getRight(), key);

    }

    public V remove(K key) {
        MyBSTNode<K, V> removing = searchHelper(this.root, key);

        if (removing == null) {
            return null;
        }
        return removeHelper(removing);
    }

    public V removeHelper(MyBSTNode<K, V> removing) {
        if (removing == null) {
            return null;
        }
        // case where the node has no children
        if (hasNoChildren(removing)) {

            // calls a helper method to check if the node we are removing
            // is the left child
            removeLeaf(removing);
            return removing.getValue();
        }

        // case where the node has 2 children
        if (hasTwoChildren(removing)) {
            MyBSTNode<K, V> successor = removing.successor();
            swap(removing, successor);

            return removeHelper(removing);
        } else {

            if (removing.getLeft() != null) {
                swap(removing, removing.getLeft());
            }

            else {
                swap(removing, removing.getRight());
            }
            return removeHelper(removing);
        }

    }

    private void removeLeaf(MyBSTNode<K, V> removing) {

        if (isLeftChild(removing)) {
            // removes left node
            removing.getParent().setLeft(null);
        } else {
            // removes right node
            removing.getParent().setRight(null);
        }
    }

    private void resetParent(MyBST.MyBSTNode<K, V> nodeOneParent, MyBST.MyBSTNode<K, V> nodeTwo, boolean isLeft) {
        // if node one was the root resets parent pointers
        if (nodeOneParent == null) {
            root = nodeTwo;
        }
        // if it was the left child resets parent pointers
        else if (isLeft) {
            System.out.println("We are setting the left pointer of " + nodeOneParent);
            (nodeOneParent).setLeft(nodeTwo);
            System.out.println("To " + nodeOneParent.getLeft());
        }
        // if node one was the right child resents parent pointers
        else {
            (nodeOneParent).setRight(nodeTwo);
        }
    }

    private void resetChild(MyBST.MyBSTNode<K, V> nodeOneChild, MyBST.MyBSTNode<K, V> nodeTwo) {
        if (nodeOneChild != null) {
            nodeOneChild.setParent(nodeTwo);
        }
    }

    private void swap(MyBST.MyBSTNode<K, V> nodeOne, MyBST.MyBSTNode<K, V> nodeTwo) {

        MyBST.MyBSTNode<K, V> parentTemp = nodeOne.getParent();
        // checks if the first node is the parent of the second node
        // if this is the case it calls a helper method
        if (parentTemp == nodeTwo) {
            swapCaseTwo(nodeTwo, nodeOne);
            System.out.println("The two nodes we are trying to swap are next to" +
                    "each other 1");
            // swapNeigbors();
            return;
        }
        MyBST.MyBSTNode<K, V> rightTemp = nodeOne.getRight();
        MyBST.MyBSTNode<K, V> leftTemp = nodeOne.getLeft();
        MyBST.MyBSTNode<K, V> parentTemp2 = nodeTwo.getParent();
        // checks if the second node is the parent of the first node
        // if this is the case it calls a helper method
        if (parentTemp2 == nodeOne) {
            swapCaseTwo(nodeOne, nodeTwo);
            return;
        }
        MyBST.MyBSTNode<K, V> rightTemp2 = nodeTwo.getRight();
        MyBST.MyBSTNode<K, V> leftTemp2 = nodeTwo.getLeft();

        // Stores data on if the node in question is the right or left node
        boolean isLeftOne = isLeftChild(nodeOne);
        boolean isLeftTwo = isLeftChild(nodeTwo);
        if (isLeftTwo) {
            System.out.println(nodeTwo + "is the left child ");
        }
        System.out.println(parentTemp2);
        nodeOne.setParent(parentTemp2);
        nodeOne.setRight(rightTemp2);
        nodeOne.setLeft(leftTemp2);

        nodeTwo.setParent(parentTemp);
        nodeTwo.setRight(rightTemp);
        nodeTwo.setLeft(leftTemp);

        resetParent(parentTemp, nodeTwo, isLeftOne);
        resetParent(parentTemp2, nodeOne, isLeftTwo);

        // resets the pointers of node children
        resetChild(rightTemp, nodeTwo);
        resetChild(leftTemp, nodeTwo);
        resetChild(rightTemp2, nodeOne);
        resetChild(leftTemp2, nodeOne);

        // node ones temporary variables

    }

    private void swapCaseTwo(MyBST.MyBSTNode<K, V> nodeParent, MyBST.MyBSTNode<K, V> nodeChild) {
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

    private boolean hasNoChildren(MyBST.MyBSTNode<K, V> curr) {
        if (curr.getRight() == null && curr.getLeft() == null) {
            return true;
        }
        return false;
    }

    private boolean hasTwoChildren(MyBST.MyBSTNode<K, V> curr) {
        if (curr.getRight() != null && curr.getLeft() != null) {
            return true;
        }
        return false;
    }

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

    public ArrayList<MyBSTNode<K, V>> inorder() {
        ArrayList<MyBSTNode<K, V>> array = new ArrayList<>();
        inorderHelper(this.root, array);
        return array;
    }

    private void inorderHelper(MyBSTNode<K, V> curr, ArrayList<MyBSTNode<K, V>> array) {
        if (curr == null) {
            return;
        }
        inorderHelper(curr.left, array);
        array.add(curr);
        inorderHelper(curr.right, array);

    }

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
           //checks that the right node is not null
            if (this.getRight() != null) {
                //saves the right node 
                MyBSTNode<K, V> curr = this.getRight();
                
                //finds the minimum node of the right side of the parent and 
                //returns it
                while (curr.getLeft() != null) {
                    curr = curr.getLeft();
                }
                //returns the minimum element on the right side of the tree
                return curr;
            } else {
                //saves the parent and the current node 
                MyBSTNode<K, V> parent = this.getParent();
                MyBSTNode<K, V> curr = this;
                //if current node is the right node and not the root curr 
                // will be updated to parent and parent will be updated to 
                //parents parent 
                while (parent != null && curr == parent.getRight()) {
                    curr = parent;
                    parent = parent.getParent();
                }
                //returns the minimum parent on the left side of the our node
                return parent;
            }
        }
         /**
         * This method returns the in order predecessor of current node object.
         * 
         * @return the predecessor of current node object
         */
        public MyBSTNode<K, V> predecessor() {
            //finds the maximum node on the left side of the tree 
            if (this.getLeft() != null) {
                MyBSTNode<K, V> curr = this.getLeft();
                while (curr.getRight() != null) {
                    curr = curr.getRight();
                }
                return curr;
            }
            else {
               //finds the maximum parent node that the given node is a left 
               //child or on the left side of.
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

            return ((this.getKey() == null ? comp.getKey() == 
            null : this.getKey().equals(comp.getKey()))
                    && (this.getValue() == null ? comp.getValue() == 
                    null : this.getValue().equals(comp.getValue())));
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