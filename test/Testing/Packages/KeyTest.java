/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing.Packages;

// Access project
import keylistener.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Van Do
 */
public class KeyTest 
{     
    // Access ReadFile class methods
    ReadFile file = new ReadFile();
    // Access Search class methods
    Search search = new Search();
    // Use for testing file
    String fileName = "SortedList.txt";
    // Default list as empty list
    List<String> lineList = new ArrayList<>();
    
    
    @Before
    public void setUp() 
    {
        // Check if file does exists and then transfer lines to list
        checkFileExistence();
        checkFileTransfersLineToList();
    }
    
    @Test
    public void checkFileExistence()
    {
        // Check if file does exists
        assertTrue("This file does exists.", file.isFile(fileName));
    }
    
    @Ignore
    public void checkFileNonExistence()
    {
        // Check if file does not exist
        assertFalse("This file does not exist.", file.isFile("fakefile.txt"));
    }
    
    @Test
    public void checkFileTransfersLineToList()
    {
        // Check if every lines in this file added to list and check if list is not empty
        lineList = file.readFile(fileName);
        for(String line : lineList)
        {
            System.out.println(line);
        }
        assertTrue("List does contains lines from file.", !lineList.isEmpty());
    }
    
    @Test
    public void searchLineByKeywordAndOneOrMore()
    {
        // Check if using keyword allowing to display one or more results found by comparing
        // Testing keyword
        String keyword = "Owari";
        // Convert string list to string array
        String[] array = lineList.toArray(new String[0]);
        // Collect keyword comparison from line to list
        List<String> searchList = search.searchList(keyword, array);
        // Check if list is not empty when found lines
        assertTrue("Search results are not empty.", !searchList.isEmpty());
        // Check if list's size is greater than 0
        assertTrue("Search results contains one or more found results.", searchList.size() > 0);
        System.out.println("Number of found results: " + searchList.size());
    }
    
    @Test
    public void searchLineByKeywordAndReturnNone()
    {
        // Check if using keyword and trying to search but no results
        // Testing keyword, similar to searchLineByKeywordAndOneOrMore
        String keyword = "abcd";
        String[] array = lineList.toArray(new String[0]);
        List<String> searchList = search.searchList(keyword, array);
        assertTrue("Search results are empty.", searchList.isEmpty());
        System.out.println("Number of found results: " + searchList.size() + " for " + keyword);
    }
    
    @After
    public void displayLinkedListResult() 
    {
        // Check if FileHistory objects are linked in a form of a linked list
        // Test those keywords
        String[] keywords = new String[]{"Owari", "mono", "abcd"};
        // Set start to null for adding FileHistory objects after start
        FileHistory start = null;
        // Use for display all of the nodes in the linked list
        FileHistory temp;
        // Iterate through all of the keywords
        for (String key : keywords) 
        {
            // Set today date and current time
            Date date = new Date();
            // Convert line list to array
            String[] array = lineList.toArray(new String[0]);
            // Search through all lines from keyword
            List<String> searchList = search.searchList(key, array);
            // Collect all search results
            String[] foundResults = searchList.toArray(new String[0]);
            // Initialise object from the data above
            FileHistory history = new FileHistory(fileName, key, foundResults, date.toString());
            // If start is null, set this history as starting node of the linked list
            if(start == null)
            {
                start = history;
            }
            // If start has already set, check for the next empty node
            else
            {
                // Set entry as start
                FileHistory entry = start;
                // Continue while entry's next node is empty
                while(entry.getNext() != null)
                {
                    entry = entry.getNext();
                }
                // Set entry's next node as history
                entry.setNext(history);
                // Set history's previous node as entry
                history.setPrevious(entry);
            }
        }
        // Set temp as start
        temp = start;
        System.out.println("Displaying history and connection.");
        System.out.println();
        // Iterate loop until all nodes have been read
        while(temp != null)
        {
            // Display this node details
            System.out.print(temp.getFileName() + " ");
            System.out.print(temp.getInputSearch() + " ");
            System.out.print(Arrays.toString(temp.getResults()) + " ");
            System.out.print(temp.getDate() + "\n");
            // Check if this node previous node is not empty, display relationship
            if(temp.getPrevious() != null)
            {
                FileHistory previous = temp.getPrevious();
                System.out.println("Previous history contains " + previous.getFileName() + " and " + previous.getInputSearch());
            }
            // Check if this node next node is not empty, display relationship
            if(temp.getNext() != null)
            {
                FileHistory next = temp.getNext();
                System.out.println("Previous history contains " + next.getFileName() + " and " + next.getInputSearch());
            }
            // Get next node after temp
            System.out.println("\n");
            temp = temp.getNext();
        }
        
    }
}
