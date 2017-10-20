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
 * ReadFile --- this class allows to check the existence of the file and read file.
 * @author Van Do
 */
public class ReadFile 
{
    // Use buffered reader to read file
    /**
     * Read lines from any file
     */
    private BufferedReader reader;
    /**
     * Check if file does exist.
     * @param fileName - the name of the file.
     * @return if the file is exists or not.
     */
    public Boolean isFile(String fileName)
    {
        // Initialise file for the file's name
        File file = new File(fileName);
        // Check if file does exist, return true, else return false
        if(file.exists())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * Read all lines from the file and store into list.
     * @param fileName - the name of the file.
     * @return the list of lines from the file.
     * @exception FileNotFoundException - catch errors if file does not exists.
     * @exception IOException - catch errors if lines have incorrect variable declaration.
     */
    public List<String> readFile(String fileName)
    {
        // Initialise list of string as lines
        List<String> lines = new ArrayList<>();
        
        try 
        {
            // Initialise currentLine as string to read line at this position
            String currentLine;
            // Initialise reader as new buffered reader using file reader for file's name
            reader = new BufferedReader(new FileReader(fileName));
            try 
            {
                // Set currentLine as the first line of the file
                currentLine = reader.readLine();
                // Iterate loop while read file until last line
                while(currentLine != null)
                {
                    // Add currentLine to list
                    lines.add(currentLine);
                    // Read the next line of the file
                    currentLine = reader.readLine();
                }
                // Close file
                reader.close();
            } 
            catch (IOException ex) 
            {
                // Catch if input/output operation failed, display message
                System.out.println("Error message is " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Something wrong with the data in file. Please check.", "Problem With File", JOptionPane.INFORMATION_MESSAGE);
            }
        } 
        catch (FileNotFoundException ex) 
        {
            // Catch if file does not exist, display message
            System.out.println("Error message is " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "File does not existed.", "Unknown file", JOptionPane.INFORMATION_MESSAGE);
        }
        // Return list of lines from file
        return lines;
    }
}
