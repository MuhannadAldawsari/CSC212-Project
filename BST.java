
public class BST<K extends Comparable<K>, T>{ // to make the object Comparable using compare to

   class BSTNode<K extends Comparable<K>, T> {
        public K key;
        public T data;
        public BSTNode<K,T> left, right;

        public BSTNode(K k, T val) {
                key = k;
                data = val;
                left = right = null;
        }

    }
    
        BSTNode<K, T> root; 
        BSTNode<K, T > curr;
        int count ;
                
        public BST()
        {
            root = curr = null;
            count = 0;
        }
        
        // Returns the number of elements
        public int size()
        {
            return count;
        }

        // Return true if the tree is empty
        public boolean empty()
        {
            return root == null;
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


        // This method must be O(log(n)) in average.
        public boolean find(K key)
        { // Search for element with key k and make it the current element if it exists.
            BSTNode<K,T> p = root;

            if(empty())
                    return false;

            while(p != null) {
                    if(p.key.compareTo(key) == 0) {
                            curr = p;
                            return true;
                    }
                    else if(key.compareTo(p.key) < 0)
                            p = p.left;
                    else
                            p = p.right;
            }
            return false;
        }


        // This method must be O(log(n)) in average.
        public boolean insert(K key, T data)
        { // Insert a new element if it does not exist and return true. The current points to the new element.

            if(empty())
            {
                curr = root = new BSTNode <K, T> ( key, data);
                count ++;
                return true;
            }
            BSTNode<K,T> par = null; // parent
            BSTNode<K,T> child  = root;
            
            while(child != null) {
                    if(child.key.compareTo(key) == 0) {
                            return false;
                    }
                    else if(key.compareTo(child.key) < 0)
                    {
                        par = child;
                        child = child.left;
                    }
                    else
                    {
                        par = child;
                        child = child.right;
                    }
            }
           
            if(key.compareTo(par.key) < 0)
            {
                par.left = new BSTNode <K, T> ( key, data);
                curr = par.left;
            }
            
            else
            {
                par.right = new BSTNode <K, T> ( key, data);
                curr = par.right;
            }
            count ++;
            return true;
        }
       //****************************************************************
        public LinkedList <T> getData()
        {
            LinkedList <T> data = new LinkedList <T>();
            if (root != null)
                getDataT(root, data);
            return data;
        }
        private void getDataT (BSTNode<K,T> node , LinkedList <T> data )
        {
            if (node == null)
                return;
            getDataT( node.left ,data );
            data.insert(node.data);
            getDataT( node.right, data);
        }
       
//**************************************************************************
        public LinkedList <K> getKeys()
        {
            LinkedList <K> keys = new LinkedList <K>();
            if (root != null)
                getKeysT(root, keys);
            return keys;
        }
        private void getKeysT (BSTNode<K,T> node , LinkedList <K> keys )
        {
            if (node == null)
                return;
            getKeysT( node.left ,keys );
            keys.insert(node.key);
            getKeysT( node.right, keys);
        }
        //*******************************************************************
       public void PrintData()
        {
            if (root != null)
                PrintData_(root);
        }
        
        private void PrintData_ (BSTNode<K,T> node)
        {
            if (node == null)
                return;
            PrintData_( node.left );
           
           System.out.print(node.key);
           
            if (node.data instanceof TermBST)
            {
                System.out.print("   docs: ");
                ((TermBST) node.data).getDocs().print();
            }
                
            
            PrintData_( node.right);
            
        }
}
