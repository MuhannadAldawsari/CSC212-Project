
public class LinkedList<T>{
    
            // class Node
            class Node<T> {
                public T data;
                public Node<T> next;
                public Node () {
                    data = null;
                    next = null;
                }
                public Node (T val) {
                    data = val;
                    next = null;
                }
                // Setters/Getters...

                public T getData() {
                    return data;
                }

                public void setData(T data) {
                    this.data = data;
                }

                public Node<T> getNext() {
                    return next;
                }

                public void setNext(Node<T> next) {
                    this.next = next;
                }

            }

    // class Linked List
    private Node<T> head;
    private Node<T> current;
    int size;
    
    public LinkedList () {
        head = current = null;
        size = 0 ;
    }
    public boolean empty () {
        return head == null;
    }
    public int size ()
    {
        return size;
    }
    
    public boolean last () {
        return current.next == null;
    }
    public boolean full () {
            return false;
    }
    public void findFirst () {
            current = head;
    }
    public void findNext () {
            current = current.next;
    }
    public T retrieve () {
            return current.data;
    }
    public void update (T val) {
            current.data = val;
    }
    
    public void insert (T val) {
            Node<T> tmp;
            if (empty()) {
                    current = head = new Node<T> (val);
            }
            else {
                    tmp = current.next;
                    current.next = new Node<T> (val);
                    current = current.next;
                    current.next = tmp;
            }
            size++ ;
    }

    public void print()
    {
        if ( head == null)
            System.out.println("Empty data");
        else
        {
            Node<T> tmp = head;
            while ( tmp != null)
            {
                System.out.print(tmp.data + "  ");
                tmp = tmp.next;
            }
            
        }
        System.out.println("");
    }
    
}

