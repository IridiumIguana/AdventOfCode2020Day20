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
    
    public char[] getEdge(int dir){
        return this.edgeChars[dir];
    }
    
    public int getID(){
        return this.tileID;
    }
    
    public boolean isMatchFound(int dir){
        return matchFound[dir];
    }
    
    /**
     * Checks if the Tile's specified edge matches with any edge of the checkTile, and returns TileData based on the answer
     * 
     * @param edgeDir  the direction (0-3) of the edge on the main tile to check for matches to
     * @param checkTile  the Tile to check for matches against
     * @return  TileData containing data about if the tile has matches
     */
    public TileData checkMatchingEdge(int edgeDir, Tile checkTile){
        char[] edge = this.getEdge(edgeDir); //get the edge to check
        String edgeStr = new String(edge); //create string represenation of edge
        String mirrorEdgeStr = reverseString(edgeStr); //create reversed version of edge
        
        boolean mirrorSW = false; //set to true if SW checks should be mirrored
        
        if((edgeDir == 0) || (edgeDir == 1)){ //if main edge is N or E, mirror when checking S & W (due to storage system, SW will be flipped when the tile is rotated, but not in storage)
            mirrorSW = true;
        }
        else{ //(if 2 or 3) if main edge is S or W, mirror when checking N & E (due to storage system, NE will be flipped when the tile is rotated, but not in storage)
            mirrorSW = false;
        }
        
        //check against each edge of checkTile
        for(int dir = 0; dir <= 3; dir++){
            //if a match has already been found, don't bother checking dir
            if(checkTile.isMatchFound(dir)){
                continue;
            }
            
            char[] checkEdge = checkTile.getEdge(dir); //get checkEdge
            String checkEdgeStr = new String(checkEdge); //get checkEdge string
            
            //if SW should be mirrored and checking S or W
            //or if NE should be mirrored and checking N or E
            if((mirrorSW && ((dir == 2) || (dir == 3))) ||
               (!mirrorSW && ((dir == 0) || (dir == 1)))){
                
                if(mirrorEdgeStr.equals(checkEdgeStr)){ //check for mirrored match
                    return new TileData(checkTile, this, edgeDir, dir, false); //mir false due to the fact that edge should be mirrored by default
                }
                else if(edgeStr.equals(checkEdgeStr)){ //check for normal match
                    return new TileData(checkTile, this, edgeDir, dir, true); //mir true due to the fact that edge should be mirrored by default
                }
            }
            else{ //if using unmirrored values
                if(mirrorEdgeStr.equals(checkEdgeStr)){ //check for mirrored match
                    return new TileData(checkTile, this, edgeDir, dir, true);
                }
                else if(edgeStr.equals(checkEdgeStr)){ //check for normal match
                    return new TileData(checkTile, this, edgeDir, dir, false);
                }
            }
        }
        
        return new TileData(); //return false (default) TileData if no matches found
    }
    
    public void checkMatchingEdges(Tile checkTile){
        //check each direction
        for(int dir = 0; dir <= 3; dir++){
            if(this.isMatchFound(dir)){ //don't check dir if already checked
                continue;
            }
            TileData data = this.checkMatchingEdge(dir, checkTile);
            
            if(data.hasMatch()){ //if a match was found, save match data to both tiles
                this.addTileData(dir, data); //add base match data
                this.incMatchCount(); //increment match count
                
                //create inverted match data
                TileData invData = new TileData(data);
                
                //get inv match dir
                int matchDir = data.getMatchEdgeDir();
                
                //save inv data to checkTile
                checkTile.addTileData(matchDir, invData);
                checkTile.incMatchCount(); //increment match count
                
                //if a match is found, stop checking (break)
                break;
            }
        }
    }
    
//    public void addTileData(int dir, TileData data){
//        this.tileMatches[dir] = data;
//    }
    
//    public void incMatchCount(){
//        matchCount = matchCount + 1;
//    }
//    
//    public int getMatchCount(){
//        return matchCount;
//    }
    
    public static String reverseString(String str){
        String reverse = "";
        
        while(str.length() > 0){ //loop while str still has chars to remove
            reverse = reverse + str.substring(str.length() - 1); //add last char to reverse
            str = str.substring(0,str.length() - 1); //remove last char
        }
        
        return reverse; //return reversed string
    }
    
//    public void setTileType(){
//        int numMatches = this.getMatchCount(); //get number of matches
//        
//        //set each value to false to begin
//        isCorner = false;
//        isEdge = false;
//        isCore = false;
//        
//        switch(numMatches){
//            case 2: //if corner
//                isCorner = true;
//                if(!this.isMatchFound(0)){ //check if 0 is edge
//                    if(!this.isMatchFound(1)){ //check if 1 is edge
//                        edgeFaceDir = 1;
//                    }
//                    else{ //3 must be edge
//                        edgeFaceDir = 0;
//                    }
//                }
//                else{ //2 must be edge
//                    if(!this.isMatchFound(1)){ //check if 1 is edge
//                        edgeFaceDir = 2;
//                    }
//                    else{ //3 must be edge
//                        edgeFaceDir = 3;
//                    }
//                }
//                break;
//            case 3: //if edge
//                isEdge = true;
//                for(int dir = 0; dir < 4; dir++){ //check all directions
//                    if(!this.isMatchFound(dir)){
//                        edgeFaceDir = dir; //if edge is found, save dir and break
//                        break;
//                    }
//                }
//                break;
//            case 4: //if core
//                isCore = true;
//                edgeFaceDir = -1; //set edge dir to -1 (no edges)
//                break;
//        }
//        
//    }
//    
//    //changes rotation to account for tile rotations and mirrors
//    public int addRotation(int dir){
//        dir += rotation;
//        dir = dir % 4; //mod 4
//        return dir;
//    }
//    
//    public int removeRotation(int dir){
//        dir -= rotation;
//        if(dir < 0){ //if removing rotation puts out of bounds, add 4
//            dir += 4;
//        }
//        dir = dir % 4; //mod 4
//        return dir;
//    }
//    
//    public void rotateCCW(){
//        rotation = (rotation + 3) % 4;
//    }
//    
//    public void rotateCW(){
//        rotation = (rotation + 1) % 4;
//    }
//    
//    public TileData getEdgeTileData(int dir){
//        return tileMatches[dir];
//    }
//    
//    public int getEdgeFaceDir(){
//        return edgeFaceDir;
//    }
//    
//    public boolean getCorner(){
//        return isCorner;
//    }
//    
//    public boolean getEdge(){
//        return isEdge;
//    }
//    
//    public boolean getCore(){
//        return isCore;
//    }
//    
//    public boolean isXMirror(){
//        return xMirror;
//    }
//    
//    public boolean isYMirror(){
//        return yMirror;
//    }
}
