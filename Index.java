
public class Index {
    class frequency
    { // number of frequency words
        int docID = 0;
        int f = 0;
        String msg = "Document ";
    }

    class Document {
            int docID;  // array of 50 element, every element contains docID and linked list index
            LinkedList <String> index; 

            public Document() {
                docID = 0;
                index = new LinkedList <String>();
            }

            public void addNew (String word) // add a word to the linked list
            {
                index.insert(word);
            }

           public boolean found(String word)
           {
               if (index.empty())
                   return false;

               index.findFirst();
               while ( ! index.last())
               {
                   if ( index.retrieve().compareToIgnoreCase(word) == 0)
                       return true;
                  index.findNext();
               }
                if ( index.retrieve().compareToIgnoreCase(word) == 0)
                    return true;
               return false;
           }
    }
    //*************************************************************
    Document [] indexes;
    frequency [] freqs;

    
    public Index() {
        freqs = new frequency [50];
        indexes = new Document [50];
        for ( int i = 0 ; i < indexes.length ; i++)
        {
            indexes [i] = new Document();
            indexes [i].docID = i;
        }
    }
        
    public void addDocument ( int docID, String data) // selects doc from the array then add word to L.L
    {
            indexes[docID].addNew(data);
    }

    //*************************************************************
public  boolean [] getDocs (String str) // search for the word in all the L.Ls (T if the word mentioned) then return boolean array
{
    boolean [] result = new boolean [50];
    for (int i = 0 ; i < 50 ; i++)
        result[i] = false;
    
    for (int i = 0 ; i < 50 ; i++)
        if (indexes[i].found(str))
            result[i] = true;

    return result;
}

    //*************************************************************
    public LinkedList<Integer> AND_Function (String str)
    {
        String [] ANDs = str.split(" AND "); //split the msg( green AND white ) become [green ,white ]

        LinkedList<Integer> result = new LinkedList<Integer>();
        boolean [] docs = getDocs(ANDs[0].toLowerCase().trim());// take first element to search then return [50] T,F
        for ( int i = 0 ; i < docs.length ; i++)
            if (docs[i])
                result.insert(i); // add num of docs to LL result

        for ( int i = 1 ; i< ANDs.length ; i++)
        {

            LinkedList<Integer> b1 = result; // save the prev result
            result = new LinkedList<Integer> (); //clear the LL

            docs = getDocs(ANDs[i].toLowerCase().trim()); //search
            for ( int j = 0 ; j < docs.length ; j++)
            {
                if (docs[j] )  {
                    b1.findFirst(); //if the word true in doc 2 search in b1(prev LL)
                    boolean found =  false;
                    while ( ! b1.last())
                    {
                        if ( b1.retrieve()==j) // if it's the same num in prev LL (like 2) =true
                        {
                            found = true;
                            break;
                        }
                        b1.findNext();
                    }
                    if ( b1.retrieve()== j)
                        found = true;
                    if (found)
                        result.insert(j);
                }
            }
        }
        return result;
    }
    //*************************************************************
    public LinkedList<Integer> OR_Function (String str)
    {
        String [] ORs = str.split(" OR ");// split OR

        LinkedList<Integer> result = new LinkedList<Integer> ();
        boolean [] docs = getDocs(ORs[0].toLowerCase().trim()); //search for 1st element
        for ( int i = 0 ; i < docs.length ; i++)
            if (docs[i])
                result.insert(i);

        for ( int i = 1 ; i< ORs.length ; i++)
        {
            docs = getDocs(ORs[i].toLowerCase().trim()); // search for remaining words
            for ( int j = 0 ; j < 50 ; j++)
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
                    if ( result.retrieve() == j) //then add to result the non mentioned
                        found = true;

                    if (! found)
                        result.insert(j);
                }
            }
        }
        return result;
    }

    //*************************************************************
        public LinkedList<Integer> AND_OR_Function (String str )
        {
            if (! str.contains(" OR ") && ! str.contains(" AND ")) // IF no AND & OR
            {
                str = str.toLowerCase().trim();
                LinkedList<Integer> result = new LinkedList<Integer>();
                boolean [] docs = getDocs(str); //search for str in docs
                for ( int i = 0 ; i < docs.length ; i++)
                    if (docs[i]) 
                        result.insert(i);
                return result;
            }
            
            else if (str.contains(" OR ") && str.contains(" AND ")) // AND & OR (priority for and)
            {
                String [] AND_ORs = str.split(" OR "); // we need AND only (--AND--OR--)->[--AND--, --]
                LinkedList<Integer> result = AND_Function (AND_ORs[0]);// call AND fun in result
               
                for ( int i = 1 ; i < AND_ORs.length ; i++  )
                {   
                    LinkedList<Integer> r2 =AND_Function (AND_ORs[i]); // call AND for remaining (return all mentioned in docs if not AND)

                    r2.findFirst();
                    for ( int j = 0 ; j < r2.size() ; j++)
                    {
                        boolean found = false;
                        result.findFirst();
                        while (! result.last()) // do OR for the LLs result and r2 ...
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
        public void Rank_Function(String str) //rank method
        {
            str = str.toLowerCase().trim();
            String [] words = str.split(" ");
            freqs = new frequency[50];
            for ( int i = 0 ; i < 50 ; i++ )
            {
                freqs[i] = new frequency(); //new array
                freqs[i].docID = i; // docID
                freqs[i].f = 0; // num of frequency's in doc
                freqs[i].msg = "Document " + i + " : ";
            }
            
            for ( int docs = 0 ; docs <50 ; docs++)
            {
                for ( int i = 0 ; i < words.length ; i++)
                {
                    indexes[docs].index.findFirst(); // search in all LLs for the word
                    int wordcount = 0; // num of frequency's in current doc
                    for ( int x = 0 ; x < indexes[docs].index.size() ; x++ )
                    {
                        if (indexes[docs].index.retrieve().compareTo(words[i])==0)
                            wordcount ++;
                        indexes[docs].index.findNext();
                    }
                    freqs[docs].f += wordcount;
                    freqs[docs].msg +=" ( " + words[i] + ", " + wordcount + " ) +"; // + not here
                }
            }
            
            for ( int x = 0 ; x < freqs.length ; x ++)
            {
                freqs[x].msg = freqs[x].msg.substring(0, freqs[x].msg.length()-1); //-1 to delete (+)
                freqs[x].msg += " = " + freqs[x].f;// add = and num of Freq
            }
            
            mergesort(freqs, 0, freqs.length-1 ); // sort from down to up(merge sort)
            
            System.out.println("Results: ");
            
            for ( int x = 0 ;  freqs[x].f != 0 ; x++)// print all msg's for non-zero freq
                System.out.println(freqs[x].msg);
            
            System.out.println("\nDocIDt\tScore"); // print docID with the score( freq )
            for ( int x = 0 ;  freqs[x].f != 0 ; x++)
                System.out.println(freqs[x].docID + "\t\t" + freqs[x].f);
        }

    //*************************************************************
    public static void mergesort ( frequency [] A , int l , int r ) // in slides
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
