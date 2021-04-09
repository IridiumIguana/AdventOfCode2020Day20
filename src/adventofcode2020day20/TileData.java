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
    private int matchEdge; //the edge of the tile the edge matches with
    private boolean isMirror; //if the match requires mirroring
    
    /**
     * Default constructor for TileData. Creates false match for edges/corners.
     */
    public TileData(){
        isMatching = false; //set matching to false, leave rest blank
    }
    
    public boolean hasMatch(){
        return this.isMatching;
    }
}
