
public class Inverted_Index_BST {// BST that every node contains str key and inner BST that contains (word, docID, rank)
        class frequency
        {
            int docID = 0;
            int f = 0;
            String msg = "Document ";
        }
 
        BST <String, TermBST> invertedindexBST;
        frequency [] freqs;

        //*************************************************************
        public Inverted_Index_BST() {
            invertedindexBST = new BST <String, TermBST>();
            freqs = new frequency[50];
        }

        //*************************************************************
        public int size()
        {
           return invertedindexBST.size();
        }

        //*************************************************************
        public boolean addNew (int docID, String word)
        {
           if (invertedindexBST.empty())
           {
               TermBST t = new TermBST();
               t.setWord(word);
               t.add_docID(docID);
               invertedindexBST.insert(word, t);
               return true;
           }
           else
           {
                if (invertedindexBST.find(word))
                {
                    TermBST t = invertedindexBST.retrieve();
                    t.add_docID(docID);
                    invertedindexBST.update(t);
                    return false;

                }

               TermBST t = new TermBST();
               t.setWord(word);
               t.add_docID(docID);
               invertedindexBST.insert(word, t);
                return true;
            }
        }

        //*************************************************************
        public boolean found(String word)
        {
               return invertedindexBST.find(word);
        }
        //*************************************************************
        public LinkedList<Integer> AND_Function (String str)
        {
            String [] ANDs = str.split(" AND ");

            LinkedList<Integer> result = new LinkedList<Integer>();
            if (this.found (ANDs[0].toLowerCase().trim()))
                result = invertedindexBST.retrieve().docIDS_ranked.getKeys();


            for ( int i = 0 ; i< ANDs.length ; i++)
            {

                LinkedList<Integer> b1 = result;
                result = new LinkedList<Integer> ();

                if (this.found (ANDs[i].toLowerCase().trim()))
                {
                    LinkedList<Integer> docs = invertedindexBST.retrieve().docIDS_ranked.getKeys();

                    docs.findFirst();
                    for ( int j = 0 ; j < docs.size ; j++)
                    {
                        b1.findFirst();
                        boolean found =  false;
                        while ( ! b1.last())
                        {
                            if ( b1.retrieve()==docs.retrieve())
                            {
                                found = true;
                                break;
                            }
                            b1.findNext();
                        }
                        if ( b1.retrieve()== docs.retrieve())
                            found = true;

                        if (found)
                            result.insert(docs.retrieve());

                        docs.findNext();
                    }
                }
            }
            return result;
        }
    //*************************************************************
    public LinkedList<Integer> OR_Function (String str)
    {
        String [] ORs = str.split(" OR ");

        LinkedList<Integer> result =  new LinkedList<Integer> ();
        if (this.found (ORs[0].toLowerCase().trim()))
            result = invertedindexBST.retrieve().docIDS_ranked.getKeys();

        for ( int i = 1 ; i< ORs.length ; i++)
        {
            if (this.found (ORs[i].toLowerCase().trim()))
            {
                LinkedList<Integer> docs = invertedindexBST.retrieve().docIDS_ranked.getKeys();
                docs.findFirst();
                for ( int j = 0 ; j < docs.size ; j++)
                {
                    result.findFirst();
                    boolean found =  false;
                    while (! result.last())
                    {
                        if ( result.retrieve()== docs.retrieve())
                        {
                            found = true;
                            break;
                        }
                        result.findNext();
                    }
                    if ( result.retrieve() == docs.retrieve())
                        found = true;

                    if (! found)
                        result.insert(j);

                    docs.findNext();
                }
            }
        }
        return result;
    }

    //*************************************************************
        public LinkedList<Integer> AND_OR_Function (String str )
        {
            if (! str.contains(" OR ") && ! str.contains(" AND "))
            {
                str = str.toLowerCase().trim();
                LinkedList<Integer> result = new LinkedList<Integer>();
                if (this.found (str))
                    result = invertedindexBST.retrieve().getDocs();
                return result;
            }
            
            else if (str.contains(" OR ") && str.contains(" AND "))
            {
         
                String [] AND_ORs = str.split(" OR ");
                LinkedList<Integer> result = AND_Function (AND_ORs[0]);
               
                for ( int i = 1 ; i < AND_ORs.length ; i++  )
                {   
                    LinkedList<Integer> r2 =AND_Function (AND_ORs[i]);

                    r2.findFirst();
                    for ( int j = 0 ; j < r2.size() ; j++)
                    {
                        boolean found = false;
                        result.findFirst();
                        while ( ! result.last())
                        {
                            if (result.retrieve()== r2.retrieve())
                            {
                                found = true;
                                break;
                            }
                            result.findNext();
                        }
                        if (result.retrieve() == r2.retrieve())
                            found = true;
                        
                        if (!found )
                            result.insert(r2.retrieve());
 
                        r2.findNext();
                    }
                }
                return result;
            }
            
            else  if (str.contains(" AND "))
                return AND_Function (str);
            
            return OR_Function (str);
        }

        //*************************************************************
        public void Rank_Function(String str)
        {
            str = str.toLowerCase().trim();
            String [] words = str.split(" ");
            freqs = new frequency[50];
            for ( int i = 0 ; i < 50 ; i++ )
            {
                freqs[i] = new frequency();
                freqs[i].docID = i;
                freqs[i].f = 0;
                freqs[i].msg = "Document " + i + " : ";
            }
            
            for ( int i = 0 ; i < words.length ; i++)
            {
                if (invertedindexBST.find(words[i]))
                {
                    LinkedList<Integer> docs = invertedindexBST.retrieve().getDocs();
                    LinkedList<Integer> rank = invertedindexBST.retrieve().getRanked();
                    
                    docs.findFirst();
                    rank.findFirst();
                    for ( int j = 0 ; j < docs.size() ; j ++)
                    {
                        int index = docs.retrieve();
                        freqs[index].docID = index;
                        freqs[index].f += rank.retrieve();
                        freqs[index].msg +=" ( " + words[i] + ", " + rank.retrieve() + " ) +"; 
                        docs.findNext();
                        rank.findNext();
                    }
                }
            }
            
            for ( int x = 0 ; x < freqs.length ; x ++)
            {
                freqs[x].msg = freqs[x].msg.substring(0, freqs[x].msg.length()-1);
                freqs[x].msg += " = " + freqs[x].f;
            }
            
            mergesort(freqs, 0, freqs.length-1 );
            
            System.out.println("Results: ");
            
            for ( int x = 0 ;  freqs[x].f != 0 ; x++)
                System.out.println(freqs[x].msg);
            
            System.out.println("\nDocIDt\tScore");
            for ( int x = 0 ;  freqs[x].f != 0 ; x++)
                System.out.println(freqs[x].docID + "\t\t" + freqs[x].f);
        }

        //*************************************************************
    public static void mergesort ( frequency [] A , int l , int r ) 
    {
        if ( l >= r )
            return;
        int m = ( l + r ) / 2;
        mergesort (A , l , m ) ;          // Sort first half
        mergesort (A , m + 1 , r ) ;    // Sort second half
        merge (A , l , m , r ) ;            // Merge
    }

    private static void merge ( frequency [] A , int l , int m , int r ) 
    {
        frequency [] B = new frequency [ r - l + 1];
        int i = l , j = m + 1 , k = 0;
    
        while ( i <= m && j <= r )
        {
            if ( A [ i ].f >= A [ j ].f)
                B [ k ++] = A [ i ++];
            else
                B [ k ++] = A [ j ++];
        }
        
        if ( i > m )
            while ( j <= r )
                B [ k ++] = A [ j ++];
        else
            while ( i <= m )
                B [ k ++] = A [ i ++];
        
        for ( k = 0; k < B . length ; k ++)
            A [ k + l ] = B [ k ];
    }
        
}
