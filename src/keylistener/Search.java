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
public class Search 
{
    public List<String> searchList(String keyword, String[] list)
    {
        List<String> result = new ArrayList<>();
        for (String line : list) 
        {
            if (line.toLowerCase().contains(keyword.toLowerCase())) 
            {
                result.add(line);
            }
        }
        return result;
    }
}
