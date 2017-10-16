/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keylistener;

import java.util.*;

/**
 *
 * @author Van Do
 */
public class FileHistory 
{
    // Previous search history
    private FileHistory previous;
    // Next search history
    private FileHistory next;
    // Name of the file to search from
    private String fileName;
    // User input for comparing lines if does contain search keywords
    private String search;
    // List of comparing results found
    private List<String> results;
    // Date of being searched
    private String searchDate;
    // Null constructor to access methods
    public FileHistory()
    {
        
    }
    // Parameterised constructor
    public FileHistory(String file, String keyword, List<String> lstResult, String date)
    {
        previous = null;
        next = null;
        fileName = file;
        search = keyword;
        results = lstResult;
        searchDate = date;
    }
    // Getters
    public String getFileName()
    {
        return fileName;
    }
    
    public String getInputSearch()
    {
        return search;
    }
    
    public List<String> getResults()
    {
        return results;
    }
    
    public FileHistory getPrevious()
    {
        return previous;
    }
    
    public FileHistory getNext()
    {
        return next;
    }
    
    public String getDate()
    {
        return searchDate;
    }
    // Setters
    public void setFileName(String file)
    {
        fileName = file;
    }
    
    public void setInputSearch(String input)
    {
        search = input;
    }
    
    public void setResults(List<String> sResult)
    {
        results = sResult;
    }
    
    public void setPrevious(FileHistory before)
    {
        previous = before;
    }
    
    public void setNext(FileHistory after)
    {
        next = after;
    }
    
    public void setDate(String date)
    {
        searchDate = date;
    }
}
