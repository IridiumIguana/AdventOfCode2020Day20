/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventofcode2020day20;
import java.lang.Integer;

/**
 *
 * @author s138616
 */
public class Tile {
    
    //setup direction constants
    final int N = 0;
    final int E = 1;
    final int S = 2;
    final int W = 3;
    
    private boolean isCorner; //true if tile is a corner tile
    private boolean isEdge; //true if tile is an edge tile
    private boolean isCore; //true if tile is neither a corner nor an edge tile
    
    private int tileID; //holds the ID number of the tile
    
    private char[][] tileChars; //2D Char array holding the chars of the tile in its current orientation
    private char[][] bodyChars; //2D Char array holding the body chars of the tile (tile without edges)
    private char[][] edgeChars; //2D Char array holding the edge chars of the tile in its current orientation
    
    private String tileText; //string containing entire base text of tile
    
    /**
     * Constructor for Tiles from a formatted Tile String
     * 
     * @param tileStr  the Tile String to create a Tile based off of
     */
    public Tile(String tileStr){
        tileText = tileStr; //save base string
        
        String[] tileLines = tileStr.split("\n"); //split string into lines
        
        //get and isolate tile ID #
        String idStr = tileLines[0]; //get string containing tile ID #
        int colPos = idStr.indexOf(":"); //get index of colon (end of ID #)
        idStr = idStr.substring(6,colPos); //get only ID #
        tileID = Integer.parseInt(idStr); //parse idStr for ID # and save value
        
        //split tile into chars
        tileChars = new char[tileLines.length - 1][]; //create empty char array array (top level of 2D char array) to hold each line of chars
        for(int row = 1; row < tileLines.length; row++){ //fill tileChars
            tileChars[row - 1] = tileLines[row].toCharArray(); //convert line to chars and add to tileChars
        }
        
        //get edgeChars
        edgeChars = new char[4][]; //create empty array to fill with char edges
        //NORTH edge
        edgeChars[N] = tileChars[0]; //NORTH edge is just top of tileChars
        //SOUTH edge
        edgeChars[S] = tileChars[tileChars.length - 1]; //SOUTH edge is just end of tileChars
        //EAST edge
        String edgeTmp = ""; //holds edge as String to convert to char array
        for(int row = 0; row < tileChars.length; row++){
            edgeTmp = edgeTmp + tileChars[row][tileChars[row].length - 1]; //append EAST value to string
        }
        edgeChars[E] = edgeTmp.toCharArray(); //convert value to char array and save
        //WEST edge
        edgeTmp = ""; //holds edge as String to convert to char array
        for(int row = 0; row < tileChars.length; row++){
            edgeTmp = edgeTmp + tileChars[row][0]; //append WEST value to string
        }
        edgeChars[W] = edgeTmp.toCharArray(); //convert value to char array and save
        
    }
}
