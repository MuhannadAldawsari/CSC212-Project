
public class Term {
    String word;
    boolean [] docIDS ;// true if the word in doc
    int [] ranked;// number the word repeated

    public Term() {
        word = "";
        docIDS = new boolean [50];
        ranked = new int [50];
        for (int i = 0 ; i < docIDS.length ; i++)
        {
            docIDS [i] = false;
            ranked[i] = 0;
        }
    }

    public void add_docID ( int docID)
    {
        docIDS[docID] = true;
        ranked[docID] ++;
    }

    public void setWord(String word)
    {
        this. word = word; 
    }
    
    public String getWord()
    {
         return word;
    }
    
    public boolean [] getDocs () //return the boolean IDs
    {
        boolean [] test = new boolean [ranked.length];
        
        for ( int i = 0 ; i < test.length ; i++)
            test[i] = docIDS[i];
        
        return test;
    }

    public int [] getRanked() //return the Ranked array
    {
        int[] test = new int [ranked.length];
        for ( int i = 0 ; i < test.length ; i++)
            test[i] = ranked[i];
        return test;
    }
    
   @Override
    public String toString() { //string of array word
        String docs = "";
        for (int i = 0, j = 0 ; i < docIDS.length; i++)
            if ( docIDS[i] != false)
            {
                if ( j == 0)
                {
                    docs += " " + String.valueOf(i) ;
                    j++;
                }
                else
                {
                    docs += ", " + String.valueOf(i) ;
                    j++;
                }
            }
        return word + "[" + docs + ']';
    }
    
}
