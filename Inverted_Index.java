
public class Inverted_Index { //LL, every node contains (word)(boolean docID[50])(rank[50])
        class frequency
        {
            int docID = 0;
            int f = 0;
            String msg = "Document ";
        }
 
        frequency [] freqs;
        LinkedList <Term> invertedindex; // using TERM class

    //*************************************************************
        public Inverted_Index() {
            invertedindex = new LinkedList <Term>();
            freqs = new frequency [50];    
        }

        public int size()
        {
            return invertedindex.size();
        }

    //*************************************************************
        public boolean addNew (int docID, String word)
        {
            if (invertedindex.empty())// if LL has no nodes
           {
               Term t = new Term ();
                t.setWord(word);
                t.add_docID(docID);// put true to docID (e.g 4) ,if it true before rank+1
                invertedindex.insert(t);
                return true;
           }
           else
           { // if not empty search for word
                invertedindex.findFirst();
               while ( ! invertedindex.last())
                {
                    if ( invertedindex.retrieve().word.compareTo(word) == 0)// finds the word in LL
                    {
                        Term t = invertedindex.retrieve();
                        t.add_docID(docID);// put true to docID (e.g 4) ,if it true before rank+1
                        invertedindex.update(t);
                        return false;    
                    }
                   invertedindex.findNext();
                }
                if ( invertedindex.retrieve().word.compareTo(word) == 0) // last node
                {
                    Term t = invertedindex.retrieve();
                    t.add_docID(docID);
                    invertedindex.update(t);
                    return false;    
                }
                Term t = new Term (); // not found, new node
                t.setWord(word);
                t.add_docID(docID);
                invertedindex.insert(t);
            }
            return true;
       }

    //*************************************************************
        public boolean found(String word) // search for the word in LL
        {
               if (invertedindex.empty())
                   return false;

               invertedindex.findFirst();
               for ( int i = 0 ; i < invertedindex.size ; i++)
               {
                   if ( invertedindex.retrieve().word.compareTo(word) == 0)
                       return true;
                  invertedindex.findNext();
               }
               return false;
        }
    //*************************************************************
    public LinkedList<Integer> AND_Function (String str)
    {
        String [] ANDs = str.split(" AND ");

        LinkedList<Integer> result = new LinkedList<Integer>();
        if (this.found (ANDs[0].toLowerCase().trim()))
        {
            boolean [] docs = invertedindex.retrieve().getDocs();
            for ( int i = 0 ; i < docs.length ; i++)
                if (docs[i])
                    result.insert(i);
        }

        for ( int i = 1 ; i< ANDs.length ; i++)
        {
            LinkedList<Integer> b1 = result;
            result = new LinkedList<Integer> ();

            if (this.found (ANDs[i].toLowerCase().trim()))
            {
                boolean [] docs = invertedindex.retrieve().getDocs();
                for ( int j = 0 ; j < docs.length ; j++)
                {
                    if (docs[j] )  {
                        b1.findFirst();
                        boolean found =  false;
                        while ( ! b1.last())
                        {
                            if ( b1.retrieve()==j)
                                found = true;
                            b1.findNext();
                        }

                        if ( b1.retrieve()== j)
                            found = true;

                        if (found)
                            result.insert(j);
                    }
                }
            }
        }
        return result;
    }
    //*************************************************************
    public LinkedList<Integer> OR_Function (String str)
    {
        String [] ORs = str.split(" OR ");

        LinkedList<Integer> result = new LinkedList<Integer> ();
        if (this.found (ORs[0].toLowerCase().trim()))
        {
            boolean [] docs = invertedindex.retrieve().getDocs();
            for ( int i = 0 ; i < docs.length ; i++)
                if (docs[i])
                    result.insert(i);
        }
        for ( int i = 1 ; i< ORs.length ; i++)
        {
            if (this.found (ORs[i].toLowerCase().trim()))
            {
                boolean [] docs = invertedindex.retrieve().getDocs();
                for ( int j = 0 ; j < docs.length ; j++)
                {
                    if (docs[j] )  {

                        result.findFirst();
                        boolean found =  false;

                        while (! result.last() )
                        {
                            if ( result.retrieve() == j)
                                found = true;
                            result.findNext();
                        }
                        if ( result.retrieve() == j)
                            found = true;

                        if (! found)
                            result.insert(j);
                    }
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
                {
                    boolean [] docs = invertedindex.retrieve().getDocs();
                    for ( int i = 0 ; i < docs.length ; i++)
                        if (docs[i]) 
                            result.insert(i);
                }
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
                        while (! result.last())
                        {
                            if (result.retrieve().compareTo(r2.retrieve()) == 0 )
                                found = true;
                            result.findNext();
                        }
                        if (result.retrieve().compareTo(r2.retrieve()) == 0 )
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

        //=================================================================
        public void Rank_Function(String str)
        {
            str = str.toLowerCase().trim();
            String [] words = str.split(" ");// array of words
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
                if (found (words[i]))
                {
                    boolean [] docs = invertedindex.retrieve().getDocs();
                    int [] rank = invertedindex.retrieve().getRanked();
                    
                    for ( int j = 0 ; j < docs.length ; j ++)
                    {
                        if (docs[j] == true)
                        {
                            int index = j;
                            freqs[index].docID = index;
                            freqs[index].f += rank[j];
                            freqs[index].msg +=" ( " + words[i] + ", " + rank[j] + " ) +"; 
                        }
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

         //=================================================================
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