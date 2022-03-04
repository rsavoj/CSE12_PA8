import java.util.ArrayList;

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
            if(parent == null){
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
        MyBST.MyBSTNode<K, V> curr = this.root;
        MyBST.MyBSTNode<K, V> item = searchHelper(curr, key);
        if( item== null){
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
       
        if(removing == null){
            return null;
        }
      return removeHelper(removing);
    }
    public V removeHelper( MyBSTNode<K, V> removing){
   
        
        if(removing == null){
            return null;
        }
       //case where the node has no children
        if(hasNoChildren(removing)){
           //calls a helper method to check if the node we are removing 
           // is the left child
         removeLeaf(removing);
         return removing.getValue();
        }
       
        //case where the node has 2 children 
         if(hasTwoChildren(removing)){
            MyBSTNode<K, V>  successor = removing.successor();
            swap(removing, successor);
            removeLeaf(removing);
            return removing.getValue();
        }
        else{
        

            if(removing.getLeft() != null){
                System.out.println("removing rn " + removing.getLeft());
                swap(removing, removing.getLeft());
                System.out.println(removing);
                System.out.println(removing.getLeft());
            }
           
            else{
                System.out.println("removing rn " + removing.getRight());
                swap(removing, removing.getRight());  
                System.out.println(removing);
                System.out.println(removing.getRight());
               
            
        }
            removeLeaf(removing);
        }
        return removing.getValue();
      
    }
    private void removeLeaf(MyBSTNode<K, V> removing){
   
        if(isLeftChild(removing)){     
            //removes left node
             removing.getParent().setLeft(null);     
        }
        else{
         //removes right node 
         removing.getParent().setRight(null);
        }
    }

   
    
    private void swap(MyBST.MyBSTNode<K, V>  nodeOne
    , MyBST.MyBSTNode<K, V> nodeTwo){
      
        MyBST.MyBSTNode<K, V> parentTemp = nodeOne.getParent();
        MyBST.MyBSTNode<K, V> rightTemp = nodeOne.getRight();
        MyBST.MyBSTNode<K, V> leftTemp = nodeOne.getLeft();
      
        MyBST.MyBSTNode<K, V> parentTemp2 = nodeTwo.getParent();
        MyBST.MyBSTNode<K, V> rightTemp2 = nodeTwo.getRight();
        MyBST.MyBSTNode<K, V> leftTemp2 = nodeTwo.getLeft();
      
      
      
       
       //node twos temporary varibales 
     
      
        boolean leftbool = isLeftChild(nodeTwo);
        
       //if it was the left child resets parent pointers
        if(isLeftChild(nodeOne)){
            (nodeOne.getParent()).setLeft(nodeTwo);
        }
        //if node one was the root resets parent pointers 
        else if(nodeOne.getParent() == null){
            root = nodeTwo;
        }
        //if node one was the right child resents parent pointers 
        else{
            (nodeOne.getParent()).setRight(nodeTwo);
        }
        //resets the pointers of node ones children 
        if(rightTemp != null){
            nodeOne.getRight().setParent(nodeTwo);
        }
        if(leftTemp != null){
            nodeOne.getLeft().setParent(nodeTwo);
        }
        if(nodeOne == parentTemp2){
            nodeOne.setParent(nodeTwo);;
        }
        else{
            nodeOne.setParent(parentTemp2);
        }
      
        
        nodeOne.setRight(rightTemp2);
        if(nodeOne == rightTemp2){
            nodeOne.setRight(nodeTwo);
        }
        nodeOne.setLeft(leftTemp2);
        if(nodeOne == leftTemp2){
            nodeOne.setLeft(nodeTwo);
        }
        if(nodeTwo == rightTemp){
            nodeTwo.setRight(nodeOne);
        }
        


        if(leftbool){
            (parentTemp2).setLeft(nodeOne);
        }
        else if(parentTemp2 == null){
            root = nodeOne;
        }
        else{
            (parentTemp2).setRight(nodeOne);
        }
        nodeTwo.setParent(parentTemp);
        nodeTwo.setRight(rightTemp);
        nodeTwo.setLeft(leftTemp);    
       //node ones temporary variables 

      
       
    }
    private boolean hasNoChildren(MyBST.MyBSTNode<K, V>  curr){
        if(curr.getRight() == null && curr.getLeft() == null){
            return true;
        }
        return false;
    }
    private boolean hasTwoChildren(MyBST.MyBSTNode<K, V>  curr){
        if(curr.getRight() != null && curr.getLeft() != null){
            return true;
        }
        return false;
    }
    private boolean isLeftChild(MyBST.MyBSTNode<K, V>  nodeOne){
        if(nodeOne.getParent() == null){
            return false;
        }
        MyBST.MyBSTNode<K, V> parentsLeft = nodeOne.getParent().getLeft();
      
       
        if(parentsLeft == null || (parentsLeft  != nodeOne)){
            return false;
        }
       
        return true;
    }


    public ArrayList<MyBSTNode<K, V>> inorder() {
        ArrayList<MyBSTNode<K, V>> array = new ArrayList<>();
        inorderHelper(this.root, array);
        return array;
    }
    private void inorderHelper(MyBSTNode<K, V> curr,  ArrayList<MyBSTNode<K, V>> array){
        if(curr == null){
            return; 
        }
        inorderHelper(curr.left,array);
        array.add(curr);
        inorderHelper(curr.right,array);
        
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
         * TODO: add inline comments for this method to demonstrate your
         * understanding of this method. The predecessor can be implemented
         * in a similar way.
         *
         * This method returns the in order successor of current node object.
         * It can be served as a helper method when implementing inorder().
         * 
         * @return the successor of current node object
         */
        public MyBSTNode<K, V> successor() {
            if (this.getRight() != null) {

                MyBSTNode<K, V> curr = this.getRight();
                // checks the right side of the tree for the leftmost node
                // this node will be the smallest node larger than the node
                // we are looking for. The leftmost node on the right side is
                // the smallest node on the right side. Every node on the right
                // side is larger than our element
                while (curr.getLeft() != null) {
                    curr = curr.getLeft();
                }
                return curr;
            } else {
                // if there is no right child then check the parents right
                // child
                // Checks for when our element is the right chil
                // if we reach the root null is returned
                // element
                MyBSTNode<K, V> parent = this.getParent();
                MyBSTNode<K, V> curr = this;
                while (parent != null && curr == parent.getRight()) {
                    curr = parent;
                    parent = parent.getParent();
                }
                return parent;
            }
        }

        public MyBSTNode<K, V> predecessor() {
            if (this.getLeft() != null) {
                MyBSTNode<K, V> curr = this.getLeft();
                while (curr.getRight() != null) {
                    curr = curr.getRight();
                }
                return curr;
            }

            else {

                // element
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

            return ((this.getKey() == null ? comp.getKey() == null : this.getKey().equals(comp.getKey()))
                    && (this.getValue() == null ? comp.getValue() == null : this.getValue().equals(comp.getValue())));
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