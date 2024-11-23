
import java.io.File;
import java.util.Scanner;

public class Search_Engine {
    int tokens = 0;
    int vocab = 0;
    
    Index index;
    Inverted_Index invertedindex;
    Inverted_Index_BST invertedindexBST;
    Inverted_Index_AVL invertedindexAVL;
    
    Inverted_Index_BST test;

    //*************************************************************
    public Search_Engine()
    {
        index = new Index();
        invertedindex = new Inverted_Index();
        invertedindexBST = new Inverted_Index_BST();
        invertedindexAVL = new Inverted_Index_AVL();
        
        test = new Inverted_Index_BST();
    }

    //*************************************************************
    public void LoadData (String stopFile, String fileName) //stopFile(not wanted words), fileName(docID, all words)
    {
            try{
                File stopfile = new File (stopFile);
                Scanner reader = new Scanner (stopfile).useDelimiter("\\Z");
                String stops = reader.next();// read the all file once

                stops = stops.replaceAll("\n", " "); // make it one line

                File docsfile = new File(fileName);
                Scanner reader1 = new Scanner (docsfile);
                String line = reader1.nextLine(); // no need for header in file (remove header)
                
                for ( int lineID = 0 ; lineID <50 ; lineID ++ )  //reading all 50 lines
                {
                    line = reader1.nextLine().toLowerCase();
                    
                    int pos = line.indexOf(','); // when (line) reads the file, it split docID and words by ((,))
                    // save the pos of ((,))
                    int docID = Integer.parseInt( line .substring(0, pos));// take docID by using substring

                    String data = line.substring(pos+1, line.length() - pos).trim(); //take the data with remove double cot from 1st line
                    data = data.substring(0, data.length() -1);// remove double cot from last line

                    data = data.toLowerCase();
                    data = data.replaceAll("[\']", "");// remove all dashes
                    data = data.replaceAll("e-sports", "esports");//remove(-)
                    data = data.replaceAll("[^a-zA-Z0-9]", " ").trim() ; // remove anything except this

                    String [] words = data.split(" "); // --1-- split all words into an array

                    for (int i = 0; i < words.length ; i++)
                    {
                        String word = words[i].trim(); //--2--
                
                        if ( word.compareToIgnoreCase("") != 0)// if it's an empty space, don't do token++
                            tokens ++;
                        
                        this.test.addNew(docID, word); // add the doc and word to test BST (test is a BST with stop words)
                                
                        if ( ! stops.contains(word + " ")) //--3-- if the word not in stopFile
                         { // add in all the data structures
                            this.index.addDocument(docID, word);
                            this.invertedindex.addNew(docID, word);
                            this.invertedindexBST.addNew(docID, word);
                            this.invertedindexAVL.addNew(docID, word);
                        }
                    }

                }

                vocab = test.size();

                /*System.out.println("tokens " + tokens);
                System.out.println("vocabs " + vocab);*/

                reader.close();
                reader1.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }
    
    public LinkedList<Integer> Boolean_Retrieval(String str , int DSType)
    { // the method that calls AND & OR depends on data structure selected
        LinkedList<Integer> docs = new LinkedList<Integer> ();
        switch (DSType)
        {
            case 1 :
                docs = index.AND_OR_Function(str);
                break;
            case 2 :
                docs = invertedindex.AND_OR_Function(str);
                break;
            case 3:
                docs = invertedindexBST.AND_OR_Function(str);
                break;
            case 4:
                docs = invertedindexAVL.AND_OR_Function(str);
                break;
            default :
                System.out.println("wrong input");
                
        }
        return docs;
    }
        
    public void Ranked_Retrieval(String str , int DSType)
    {// the method that calls TF(rank method) depends on data structure selected
        switch (DSType)
        {
            case 1 :
                index.Rank_Function(str);
                break;
            case 2 :
                invertedindex.Rank_Function(str);
                break;
            case 3:
                invertedindexBST.Rank_Function(str);
                break;
            case 4:
                invertedindexAVL.Rank_Function(str);
                break;
            default :
                System.out.println("wrong input");
        }
    }
    
    public LinkedList<Integer> Term_Retrieval(String str , int DSType)
    { // search for the word in selected DS
        LinkedList<Integer> docs = new LinkedList<Integer> ();
        switch (DSType)
        {
            case 1 :
            {
                boolean [] docs1 = index.getDocs(str);//get boolean array (T if in doc)
                for ( int i = 0 ; i < 50 ; i++)
                    if ( docs1[i] == true)
                        docs.insert(i);// if T insert in docs (LL)
            }
            break;
            case 2 :
                if (invertedindex.found(str))// search in inverted
                {
                    boolean [] docs1 = invertedindex.invertedindex.retrieve().getDocs();
                    for ( int i = 0 ; i < 50 ; i++)
                        if ( docs1[i] == true)
                            docs.insert(i);
                }
                break;
            case 3:
                if (invertedindexBST.found(str))
                    docs = invertedindexBST.invertedindexBST.retrieve().getDocs();
                break;
            case 4:
                if (invertedindexAVL.found(str))
                    docs = invertedindexAVL.invertedindexAVL.retrieve().getDocs();
                break;
            default :
                System.out.println("wrong input");
                
        }
        return docs;
    }
    
    public void Indexed_Documents()
    {
        System.out.println("All Documents with the number of words in them ");
        for ( int i = 0 ; i < 50 ; i++ )
        {
            int size = index.indexes[i].index.size();
            System.out.println("Document# " + i +"  with size(" +  size + ")"  );
        }
        
    }
    
    public void Indexed_Tokens()
    {
        System.out.println("All tokens with the documents appear in it ");
        invertedindexBST.invertedindexBST.PrintData();
    }
    
}
