/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventofcode2020day20;

/**
 *
 * @author s138616
 */
public class SimpleTile {
    //setup direction constants
    final int N = 0;
    final int E = 1;
    final int S = 2;
    final int W = 3;

    private int tileID; //holds the ID number of the tile
    
    private char[][] tileChars; //2D Char array holding the chars of the tile in its current orientation
    private char[][] bodyChars; //2D Char array holding the body chars of the tile (tile without edges)
    private char[][] edgeChars; //2D Char array holding the edge chars of the tile in its current orientation
    
    private String tileText; //string containing entire base text of tile
    
    private boolean[] matchFound; //holds if matches have been found
    
    private boolean grounded; //holds if the tile is fixed in place
    
    /**
     * Constructor for SimpleTiles from a formatted Tile String
     * 
     * @param tileStr  the SimpleTile String to create a SimpleTile based off of
     */
    public SimpleTile(String tileStr){
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
        this.recalculateEdges(); //recalculate edges
        
        this.grounded = false; //start as ungrounded (movable)
    }
    
    public char[] getEdge(int dir){
        return this.edgeChars[dir];
    }
    
    public int getID(){
        return this.tileID;
    }
    
    public boolean isMatchFound(int dir){
        return matchFound[dir];
    }
    
    public boolean isGrounded(){
        return this.grounded;
    }
    
    public void setGrounded(boolean groundValue){
        this.grounded = groundValue;
    }
    
    /**
     * Checks if the Tile's specified edge matches with the corresponding edge of the CheckTile (the edge that would face it)
     * 
     * @param edgeDir  the direction (0-3) of the edge on the main tile to check for matches to
     * @param checkTile  the Tile to check for matches against
     * @return  boolean true if a match was found, false if not
     */
    public boolean checkMatchingEdge(int edgeDir, Tile checkTile){
        char[] edge = this.getEdge(edgeDir); //get the edge to check
        String edgeStr = new String(edge); //create string represenation of edge
        
        //get edge opposite of edgeDir to check
        int dir = (edgeDir + 2) % 4;
        
        char[] checkEdge = checkTile.getEdge(dir); //get checkEdge
        String checkEdgeStr = new String(checkEdge); //get checkEdge string
        
        if(edgeStr.equals(checkEdgeStr)){ //if both strings are equal, return true, else, return false
            return true;
        }
        
        return false;
    }
    
    //"borrowed" from techiedelight
    public void rotateCW(){
        int len = tileChars.length;
 
        // Transpose the matrix
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < i; j++)
            {
                char temp = tileChars[i][j];
                tileChars[i][j] = tileChars[j][i];
                tileChars[j][i] = temp;
            }
        }
 
        // swap columns
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len / 2; j++)
            {
                char temp = tileChars[i][j];
                tileChars[i][j] = tileChars[i][len - j - 1];
                tileChars[i][len - j - 1] = temp;
            }
        }
        
        boolean temp = this.matchFound[3];
        this.matchFound[3] = this.matchFound[2];
        this.matchFound[2] = this.matchFound[1];
        this.matchFound[1] = this.matchFound[0];
        this.matchFound[0] = temp;
        
        this.recalculateEdges(); //fix edges
    }
    
    //"borrowed" from techiedelight
    public void rotateCCW(){
        int len = tileChars.length;
 
        // Transpose the matrix
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < i; j++)
            {
                // swap `tileChars[i][j]` with `tileChars[j][i]`
                char temp = tileChars[i][j];
                tileChars[i][j] = tileChars[j][i];
                tileChars[j][i] = temp;
            }
        }
 
        // swap rows
        for (int i = 0; i < len / 2; i++)
        {
            for (int j = 0; j < len; j++)
            {
                // swap `tileChars[i][j]` with `tileChars[len-i-1][j]`
                char temp = tileChars[i][j];
                tileChars[i][j] = tileChars[len-i-1][j];
                tileChars[len-i-1][j] = temp;
            }
        }
        
        boolean temp = this.matchFound[0];
        this.matchFound[0] = this.matchFound[1];
        this.matchFound[1] = this.matchFound[2];
        this.matchFound[2] = this.matchFound[3];
        this.matchFound[3] = temp;
        
        this.recalculateEdges(); //fix edges
    }
    
    public void flipOverX(){ //flips top with bottom
        int len = tileChars.length;
        
        // swap rows
        for (int i = 0; i < len / 2; i++)
        {
            for (int j = 0; j < len; j++)
            {
                // swap `tileChars[i][j]` with `tileChars[len-i-1][j]`
                char temp = tileChars[i][j];
                tileChars[i][j] = tileChars[len-i-1][j];
                tileChars[len-i-1][j] = temp;
            }
        }
        
        boolean temp = this.matchFound[0];
        this.matchFound[0] = this.matchFound[2];
        this.matchFound[2] = temp;
        
        this.recalculateEdges(); //fix edges
    }
    
    public void flipOverY(){ //flips left with right
        int len = tileChars.length;
        
        // swap columns
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len / 2; j++)
            {
                char temp = tileChars[i][j];
                tileChars[i][j] = tileChars[i][len - j - 1];
                tileChars[i][len - j - 1] = temp;
            }
        }
        
        boolean temp = this.matchFound[1];
        this.matchFound[1] = this.matchFound[3];
        this.matchFound[3] = temp;
        
        this.recalculateEdges(); //fix edges
    }
    
    private void recalculateEdges(){
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
    
    public static String reverseString(String str){
        String reverse = "";
        
        while(str.length() > 0){ //loop while str still has chars to remove
            reverse = reverse + str.substring(str.length() - 1); //add last char to reverse
            str = str.substring(0,str.length() - 1); //remove last char
        }
        
        return reverse; //return reversed string
    }
}