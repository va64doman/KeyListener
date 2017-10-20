/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keylistener;

import java.util.*;

/**
 * Search --- this class will use a linear search method to compare each line that contains the keyword.
 * @author Van Do
 */
public class Search 
{
    /**
     * Search through the line from the file and return the list of results that contain keyword.
     * @param keyword - the input that the user wants to search for each line.
     * @param list - all of the lines from the file.
     * @return the list of compared line that contains the keyword.
     */
    public List<String> searchList(String keyword, String[] list)
    {
        // Initialise the list of string
        List<String> result = new ArrayList<>();
        // Check through every string in list parameter
        for (String line : list) 
        {
            // Check if keyword does contain from the line, then add line to result
            if (line.toLowerCase().contains(keyword.toLowerCase())) 
            {
                result.add(line);
            }
        }
        // Return list of found results
        return result;
    }
}
