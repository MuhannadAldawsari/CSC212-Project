
public class TermBST {// for BST
    String word;

    BST <Integer, Integer> docIDS_ranked;

    public TermBST() {
        word = "";
        docIDS_ranked = new BST <Integer, Integer> (); // < for docID, for number of appearance >
    }

    
    public void add_docID ( int docID)
    {
        if (docIDS_ranked.empty())
            docIDS_ranked.insert(docID, 1);
        else
        {
            if (docIDS_ranked.find(docID)) // if docID in BST
            {
                int ranked = docIDS_ranked.retrieve();
                ranked++;
                docIDS_ranked.update(ranked);
            }   
            else // NOT FOUND add new leaf
                docIDS_ranked.insert(docID, 1);
        }
    }

    public void setWord(String word)
    {
        this. word = word; 
    }
    
    public String getWord()
    {
         return word;
    }
    
    public LinkedList<Integer> getDocs ()
    {
        return docIDS_ranked.getKeys();
    } // return all keys ( from BST )

    public LinkedList<Integer> getRanked()
    {
        return this.docIDS_ranked.getData();
    } // return all data ( from BST )
    
   @Override
    public String toString() {
        String docs = "";
        LinkedList<Integer> IDs = getDocs (); // get keys ( in linked list )
        IDs.findFirst();
        for (int i = 0, j = 0 ; i < IDs.size(); i++)
        {
                if ( i == 0)
                    docs += " " + String.valueOf(i) ;
                else
                    docs += ", " + String.valueOf(i) ;
                IDs.findNext();
        }
        return word + "[" + docs + ']';
    }
    
}
