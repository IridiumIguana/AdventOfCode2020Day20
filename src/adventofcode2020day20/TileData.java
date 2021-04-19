/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventofcode2020day20;

/**
 * Class to hold extra data on matching tiles for easier data use/transportation
 * @author s138616
 */
public class TileData {
    
    private boolean isMatching; //holds if a match exists (false only for edges/corners)
    
    private int matchID; //the ID of the tile the edge matches with
    private Tile matchTile; //the tile the edge matches with
    private Tile baseTile; //the base tile of the match
    private int baseEdge; //the edge of the base tile the matchEdge matches with
    private int matchEdge; //the edge of the tile the edge matches with
    private boolean isMirror; //if the match requires mirroring
    
    /**
     * Default constructor for TileData. Creates false match for edges/corners.
     */
    public TileData(){
        isMatching = false; //set matching to false, leave rest blank
    }
    
    public TileData(int tileID, int tileEdge, boolean isMirror){
        isMatching = true; //set matching to true (must be true if an ID is given)
        
        matchID = tileID; //store tileID as matchID
        matchEdge = tileEdge; //store edge direction
        this.isMirror = isMirror; //store if mirrored
    }
    
    public TileData(Tile matchTile, Tile baseTile, int baseEdge, int tileEdge, boolean isMirror){
        isMatching = true; //set matching to true (must be true if an ID is given)
        
        this.matchTile = matchTile; //store matchTile
        matchID = matchTile.getID(); //store tileID as matchID
        this.baseTile = baseTile; //store baseTile
        this.baseEdge = baseEdge; //save base edge direction
        matchEdge = tileEdge; //store edge direction
        this.isMirror = isMirror; //store if mirrored
    }
    
    /**
     * Constructor for making TileData for other side of match from premade TileData
     * @param data  TileData containing data for other side of match
     */
    public TileData(TileData data){
        if(!data.hasMatch()){ //if no match, just leave blank with false match
            isMatching = false;
        }
        else{ //if matching, fill
            isMatching = true;
            
            //get matchTile values (baseTile of data)
            matchTile = data.getBaseTile();
            matchID = matchTile.getID();
            matchEdge = data.getBaseEdgeDir();
            
            //get baseTile values (matchTile of data)
            baseTile = data.getMatchTile();
            baseEdge = data.getMatchEdgeDir();
            
            //keep mirror value as false
            //(if data's is true, flipping again will undo the mirror, and if false, flipping will make mirrored when it shouldn't be)
            isMirror = false;
        }
    }
    
    public boolean hasMatch(){
        return this.isMatching;
    }
    
    public Tile getMatchTile(){
        return matchTile;
    }
    
    public Tile getBaseTile(){
        return baseTile;
    }
    
    public int getBaseEdgeDir(){
        return baseEdge;
    }
    
    public int getMatchEdgeDir(){
        return matchEdge;
    }
    
    public boolean getMirror(){
        return isMirror;
    }
}
