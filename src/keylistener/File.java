/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keylistener;

import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Van Do
 */
public class File 
{
    private BufferedReader reader;
    
    public List<String> readFile(String fileName)
    {
        List<String> lines = new ArrayList<>();
        
        try 
        {
            String currentLine;
            reader = new BufferedReader(new FileReader(fileName));
            try 
            {
                currentLine = reader.readLine();
                while(currentLine != null)
                {
                    lines.add(currentLine);
                    currentLine = reader.readLine();
                }
            } 
            catch (IOException ex) 
            {
                System.out.println("Error message is " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Something wrong with the data in file. Please check.", "Problem With File", JOptionPane.INFORMATION_MESSAGE);
            }
        } 
        catch (FileNotFoundException ex) 
        {
            System.out.println("Error message is " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "File does not existed.", "Unknown file", JOptionPane.INFORMATION_MESSAGE);
        }
        
        return lines;
    }
}
