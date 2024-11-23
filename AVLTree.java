
public class AVLTree<K extends Comparable<K>, T>{

        class AVLNode<K extends Comparable<K>, T> {
                public K key;
                public T data;
                AVLNode<K,T> parent; // pointer to the parent
                AVLNode<K,T> left; // pointer to left child
                AVLNode<K,T> right; // pointer to right child
                int bf; // balance factor of the node


                    public AVLNode(K key, T data) {
                            this.key = key  ;  
                            this.data = data;
                            this.parent = this.left = this.right = null;
                            this.bf = 0;
                    }

                    public AVLNode(K key, T data, AVLNode<K,T> p, AVLNode<K,T> l, AVLNode<K,T> r){
                            this.key = key  ;  
                            this.data = data;
                            left = l;
                            right = r;
                            parent = p;
                            bf =0;
                    }

                    public AVLNode<K,T> getLeft()
                    {
                        return left;
                    }

                    public AVLNode<K,T> getRight()
                    {
                        return right;
                    }

                    public T getData()
                    {
                        return data;
                    }
                    
                    @Override
                    public String toString() {
                        return "AVL Node{" + "key=" + key + ", data =" + data + '}';
                    }
            }

        //*************************************************************
        private AVLNode<K,T> root;
        private AVLNode<K,T>  curr;
        private int count;
        
        public AVLTree() {
                root = curr = null;
                count = 0;
        }

        public boolean empty() {
            return root == null;
        }

        public int size() {
            return count;
        }


        // Return the key and data of the current element
        public T retrieve()
        {
            T data =null;
            if (curr != null)
                data = curr.data;
            return data;
        }

        // Update the data of current element.
        public void update(T e)
        {
            if (curr != null)
                curr.data = e;
        }

        //searches for the key in the AVL, returns the data or null (if not found).
        private T searchTreeHelper(AVLNode<K,T> node, K key) {
                   // Place your code here\\
                    if (node == null)
                        return null;
                    else if (node.key.compareTo(key) ==0) 
                    {
                        curr = node;
                        return node.data;
                    } 
                    else if (node.key.compareTo(key) >0)
                         return searchTreeHelper(node.left, key);
                    else
                         return searchTreeHelper(node.right, key);
        }
        
        // update the balance factor the node
        private void updateBalance(AVLNode<K,T> node) {
                if (node.bf < -1 || node.bf > 1) {
                        rebalance(node);
                        return;
                }

                if (node.parent != null) {
                        if (node == node.parent.left) {
                                node.parent.bf -= 1;
                        } 

                        if (node == node.parent.right) {
                                node.parent.bf += 1;
                        }

                        if (node.parent.bf != 0) {
                                updateBalance(node.parent);
                        }
                }
        }

        void rebalance(AVLNode<K,T> node) {
                if (node.bf > 0) {
                        if (node.right.bf < 0) {
                                rightRotate(node.right);
                                leftRotate(node);
                        } else {
                                leftRotate(node);
                        }
                } else if (node.bf < 0) {
                        if (node.left.bf > 0) {
                                leftRotate(node.left);
                                rightRotate(node);
                        } else {
                                rightRotate(node);
                        }
                }
        }

        public boolean find(K key) {
                T data = searchTreeHelper(this.root, key);
                if ( data != null)
                    return true;
                return false;
        }

        // rotate left at node x
        void leftRotate(AVLNode<K,T> x) {
            AVLNode<K,T> y = x.right;
            x.right = y.left;
            if (y.left != null) {
                    y.left.parent = x;
            }
            
            y.parent = x.parent;
            if (x.parent == null) {
                    this.root = y;
            } else if (x == x.parent.left) {
                    x.parent.left = y;
            } else {
                    x.parent.right = y;
            }
            y.left = x;
            x.parent = y;

            // update the balance factor
            x.bf = x.bf - 1 - Math.max(0, y.bf);
            y.bf = y.bf - 1 + Math.min(0, x.bf);
        }

        // rotate right at node x
        void rightRotate(AVLNode<K,T> x) {
                AVLNode<K,T> y = x.left;
                x.left = y.right;
                if (y.right != null) {
                        y.right.parent = x;
                }
                y.parent = x.parent;
                if (x.parent == null) {
                        this.root = y;
                } else if (x == x.parent.right) {
                        x.parent.right = y;
                } else {
                        x.parent.left = y;
                }
                y.right = x;
                x.parent = y;

                // update the balance factor
                x.bf = x.bf + 1 - Math.min(0, y.bf);
                y.bf = y.bf + 1 + Math.max(0, x.bf);
        }

        
        
        public boolean insert(K key, T data) {
            AVLNode<K,T> node = new AVLNode<K,T>(key, data);

            AVLNode<K,T> p = null;
            AVLNode<K,T> current = this.root;

            while (current != null) {
                    p = current;
                    if (node.key.compareTo(current.key) ==0) {
                            return false;
                    }else if (node.key.compareTo(current.key) <0 ) {
                            current = current.left;
                    } else {
                            current = current.right;
                    }
            }
            // p  is parent of current
            node.parent = p;
            if (p == null) {
                    root = node;
                    curr = node;
            } else if (node.key.compareTo(p.key) < 0 ) {
                    p.left = node;
            } else {
                    p.right = node;
            }
            count ++;

            //  re-balance the node if necessary
            updateBalance(node);
            return true;        
        }

    //*************************************************************

        public LinkedList <K> getKeys()
        {
            LinkedList <K> keys = new LinkedList <K>();
            if (root != null)
                getKeysT(root, keys);
            return keys;
        }
        private void getKeysT (AVLNode<K,T> node , LinkedList <K> keys )
        {
            if (node == null)
                return;
            getKeysT( node.left ,keys );
            keys.insert(node.key);
            getKeysT( node.right, keys);
        }

    //*************************************************************
        public LinkedList <T> getData()
        {
            LinkedList <T> data = new LinkedList <T>();
            if (root != null)
                getDataT(root, data);
            return data;
        }
        private void getDataT (AVLNode<K,T> node , LinkedList <T> data )
        {
            if (node == null)
                return;
            getDataT( node.left ,data );
            data.insert(node.data);
            getDataT( node.right, data);
        }



}