
import java.util.Scanner;
public class Main {
       
     public static Scanner input = new Scanner (System.in);
     public static Search_Engine SE = new Search_Engine();

    
    public static int menu()
    {
        System.out.println("1. Retrieve term.     | 2. Boolean Retrieval. ");
        System.out.println("3. Ranked Retrieval.  | 4. Indexed Documents. ");
        System.out.println("5. Indexed Tokens.    | 6. Exist. ");
        System.out.print("enter choice: ");
        int choice = input.nextInt();
        return choice;
    }

    public static void Retrieval_Term()
    {
        int choice1 ;
        System.out.println("################### Retrieval Term ####################");
        
        System.out.println("1. index");
        System.out.println("2. inverted index");
        System.out.println("3. inverted index using BST");
        System.out.println("4. inverted index using AVL");
        System.out.print("enter your choice: ");
        choice1 = input.nextInt();

        System.out.println("Enter Term :");
        String str = "";
        str = input.next();
        
        System.out.print("Result doc IDs: ");
        SE.Term_Retrieval(str.trim().toLowerCase(), choice1 ).print(); //remove spaces on the edges with lowerCase
        // return L.L then print()
        System.out.println("\n");

    }
   
    public static void Boolean_Retrieval_menu()
    {
        int choice1 ;
        System.out.println("################### Boolean Retrieval ####################");
        
        System.out.println("1. index");
        System.out.println("2. inverted index");
        System.out.println("3. inverted index using BST");
        System.out.println("4. inverted index using AVL");
        System.out.println("enter your choice");
        choice1 = input.nextInt();

        System.out.println("Enter Question (using Only AND OR ):");
        System.out.println("");
        
        String str = input.nextLine();
        str = input.nextLine();
        
        System.out.println("Q#: " + str.trim());
        
        System.out.print("Result doc IDs: ");
        SE.Boolean_Retrieval(str.trim().toUpperCase(), choice1 ).print();// upperCase because we want AND&OR not lowerCase
        System.out.println("\n");
    }

    public static void Ranked_Retrieval_menu()
    {
        System.out.println("######## Ranked Retrieval ######## ");
        System.out.println("1. index");
        System.out.println("2. inverted index");
        System.out.println("3. inverted index using BST");
        System.out.println("4. inverted index using AVL");
        System.out.println("enter your choice");
        int choice2 = input.nextInt();

        System.out.println("Enter Question: ");
        String str = input.nextLine();
        str = input.nextLine();
        
        System.out.println("## Q: " + str);
        SE.Ranked_Retrieval(str.trim().toLowerCase(), choice2);
        System.out.println("\n");
    }
    
    public static void Indexed_Documents_menu()
    {
        System.out.println("######## Indexed Documents ######## ");
        System.out.println("Indexed Documents " );
        SE.Indexed_Documents();
        System.out.println("");
    }
    
    public static void Indexed_Tokens_menu()
    {
        System.out.println("######## Indexed Tokens ######## ");
        System.out.println("tokens " );
        SE.Indexed_Tokens();
        System.out.println("");
    }
    
    public static void main(String[] args) {

        SE.LoadData("C:\\Users\\muhan\\Downloads\\data\\stop.txt", "C:\\Users\\muhan\\Downloads\\data\\dataset.CSV");

        int choice;
        
        do {
                choice = menu();
                switch (choice)
                {
                    //retrieve Term 
                    case 1:
                            Retrieval_Term();// word and the docID that the word in
                            break;
                            
                    //Boolean Retrieval: to enter a Boolean query that will return a set of unranked documents  
                    case 2:
                            Boolean_Retrieval_menu();
                            break;
                            
                    //Ranked Retrieval: to enter a query that will return a ranked list of documents with their scores 
                    case 3:
                            Ranked_Retrieval_menu();//word and the docID that the word in with num of times
                            break;
                    
                    //Indexed Documents: to show number of documents in the index 
                    case 4:
                            Indexed_Documents_menu();
                        System.out.println("tokens " + SE.tokens);
                        System.out.println();
                            break;
                    
                    //Indexed Tokens: to show number of vocabulary and tokens in the index  
                    case 5:
                            Indexed_Tokens_menu();
                        System.out.println("vocabs " + SE.vocab);
                        System.out.println();
                        break;
                     
                    case 6:
                            break;
                            
                    default:       
                            System.out.println("wrong input, try again!");
                }
        } while (choice != 6);
    }
    
}
