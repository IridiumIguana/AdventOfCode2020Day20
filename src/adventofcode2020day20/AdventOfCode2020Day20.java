/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventofcode2020day20;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author s138616
 */
public class AdventOfCode2020Day20 {
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //get tiles
        String tileStr = fileToString("exampleTiles.txt");
        
        TileBoard board = new TileBoard(tileStr); //create TileBoard
    }
    
    private static String fileToString(String filePath) 
    {
        String content = "";
 
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
 
        return content;
    }
}
