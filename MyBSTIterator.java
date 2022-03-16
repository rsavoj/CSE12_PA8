
/**
 * Name: Roya Savoj
 * Email: rsavoj@ucsd.edu
 * Sources used: Zyebooks, lecture slides 
 * 
 * This is a class that can create a MyBTSIterator object. This object can 
 * be used to iterate through a MyBST object.
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class creates a iterorator object. The iterator object has two
 * instance vaibales next and lastVisited which contain BST nodes . The
 * lastVisited holds the value of what was stored in next after next
 * is called(). When next is called the next variable moves to the successor
 */
public class MyBSTIterator<K extends Comparable<K>, V> extends MyBST<K, V> {
    abstract class MyBSTNodeIterator<T> implements Iterator<T> {
        MyBSTNode<K, V> next;
        MyBSTNode<K, V> lastVisited;

        /**
         * Constructor that initializes the node iterator
         *
         * @param first The initial node that next points
         */
        MyBSTNodeIterator(MyBSTNode<K, V> first) {
            next = first;
            lastVisited = null;
        }

        /**
         * This method is used for determining if the next pointer in the
         * iterator points to null.
         *
         * @return If next is null based on the current position of iterator
         */
        public boolean hasNext() {
            return next != null;
        }

        /**
         * This method iterates through the tree to the next successor of the
         * current node in next.
         * 
         * @return the value of the previous next value
         */
        MyBSTNode<K, V> nextNode() {
            MyBSTNode<K, V> temp = next;
            if(next == null){
                throw new NoSuchElementException();
            }
            next = next.successor();
            lastVisited = temp;

            return temp;
        }

        /**
         * This method removes the last visited node from the tree.
         */
        public void remove() {
            // cannot call the remove method if last visited is not defined
            // as a node , already empty
            if (lastVisited == null) {
                throw new IllegalStateException();
            }
            // if lastVisited is not a leaf node next is set to last visited
            if (lastVisited.getRight() != null &&
                    lastVisited.getLeft() != null) {
                next = lastVisited;
            }
            // calls the remove method removing last visited
            MyBSTIterator.this.remove(lastVisited.getKey());
            // after the node is removed last visited is set to null
            // another node cannot be removed until next is called
            lastVisited = null;
        }
    }

    /**
     * BST Key iterator class that extends the node iterator.
     */
    class MyBSTKeyIterator extends MyBSTNodeIterator<K> {

        MyBSTKeyIterator(MyBSTNode<K, V> first) {
            super(first);
        }

        /**
         * This method advance the iterator and returns a node key
         *
         * @return K the next key
         */
        public K next() {
            return super.nextNode().getKey();
        }
    }

    /**
     * BST value iterator class that extends the node iterator.
     */
    class MyBSTValueIterator extends MyBSTNodeIterator<V> {

        /**
         * Call the constructor method from node iterator
         *
         * @param first The initial value that next points
         */
        MyBSTValueIterator(MyBSTNode<K, V> first) {
            super(first);
        }

        /**
         * This method advance the iterator and returns a node value
         *
         * @return V the next value
         */
        public V next() {
            return super.nextNode().getValue();
        }
    }

    /**
     * This method is used to obtain an iterator that iterates through the
     * value of BST.
     *
     * @return The value iterator of BST.
     */
    public MyBSTKeyIterator getKeyIterator() {
        MyBSTNode<K, V> curr = root;
        if (curr != null) {
            while (curr.getLeft() != null) {
                curr = curr.getLeft();
            }
        }
        return new MyBSTKeyIterator(curr);
    }

    /**
     * This method is used to obtain an iterator that iterates through the
     * value of BST.
     *
     * @return The value iterator of BST.
     */
    public MyBSTValueIterator getValueIterator() {
        MyBSTNode<K, V> curr = root;
        if (curr != null) {
            while (curr.getLeft() != null) {
                curr = curr.getLeft();
            }
        }
        return new MyBSTValueIterator(curr);
    }
}