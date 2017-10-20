/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keylistener;

/**
 * FileHistory --- this class contains the search history from inside of the file.
 * It used a double linked list to check the previous and next search items in the history.
 * @author Van Do
 */
public class FileHistory 
{
    // Previous search history
    /**
     * The previous node from this search item.
     */
    private FileHistory previous;
    // Next search history
    /**
     * The next node from this search item.
     */
    private FileHistory next;
    // Name of the file to search from
    /**
     * The name of the file being searched.
     */
    private String fileName;
    // User input for comparing lines if does contain search keywords
    /**
     * The keyword that the user wants to search for.
     */
    private String search;
    // List of comparing results found
    /**
     * An array of search results that compared between the keyword and lines from file.
     */
    private String[] results;
    // Date of being searched
    /**
     * The date that the search has occurred.
     */
    private String searchDate;
    // Null constructor to access methods
    /**
     * Null class constructor to access methods.
     */
    public FileHistory()
    {
        
    }
    // Parameterised constructor
    /**
     * Parameterized constructor to store object's file history data
     * @param file - the name of the file. See {@link #fileName}.
     * @param keyword - the user input for searching the line. See {@link #search}.
     * @param lstResult - the results that contain compared lines. See {@link #results}.
     * @param date - the search date that have been found. See {@link #searchDate}.
     */
    public FileHistory(String file, String keyword, String[] lstResult, String date)
    {
        previous = null;
        next = null;
        fileName = file;
        search = keyword;
        results = lstResult;
        searchDate = date;
    }
    // Getters
    /**
     * Return file's name. See {@link #fileName}.
     * @return file's name.
     */
    public String getFileName()
    {
        // Return file's name
        return fileName;
    }
    /**
     * Return user's input search. See {@link #search}.
     * @return keyword for this file.
     */
    public String getInputSearch()
    {
        // Return keyword
        return search;
    }
    /**
     * Return list of search results. See {@link #results}.
     * @return results of comparison for this file and keyword.
     */
    public String[] getResults()
    {
        // Return list of found result
        return results;
    }
    /**
     * Return the previous history before this search history. See {@link #previous}.
     * @return the previous node of this search item.
     */
    public FileHistory getPrevious()
    {
        // Return previous node
        return previous;
    }
    /**
     * Return the next history after this search history. See {@link #next}.
     * @return the next node of this search item.
     */
    public FileHistory getNext()
    {
        // Return next node
        return next;
    }
    /**
     * Return the search date of this search item. See {@link #searchDate}.
     * @return the date of this search item.
     */
    public String getDate()
    {
        // Return search date
        return searchDate;
    }
    // Setters
    /**
     * Set the name of the file. See {@link #fileName}.
     * @param file - the name of the file the user wants to search on.
     */
    public void setFileName(String file)
    {
        // Set file's name
        fileName = file;
    }
    /**
     * Set the keyword. See {@link #search}.
     * @param input - the keyword to search for in this file.
     */
    public void setInputSearch(String input)
    {
        // Set keyword
        search = input;
    }
    /**
     * Set the list of found compared results. See {@link #results}.
     * @param sResult - the list of results that compared to keyword.
     */
    public void setResults(String[] sResult)
    {
        // Set list of found result
        results = sResult;
    }
    /**
     * Set the previous node for this search item. See {@link #previous}.
     * @param before - the node that will be this search item's previous node.
     */
    public void setPrevious(FileHistory before)
    {
        // Set previous node
        previous = before;
    }
    /**
     * Set the next node for this search item. See {@link #next}.
     * @param after - the node that will be this search item's next node.
     */
    public void setNext(FileHistory after)
    {
        // Set next node
        next = after;
    }
    /**
     * Set the date for this search item. See {@link #searchDate}.
     * @param date - the search date for this search item.
     */
    public void setDate(String date)
    {
        // Set search date
        searchDate = date;
    }
}
