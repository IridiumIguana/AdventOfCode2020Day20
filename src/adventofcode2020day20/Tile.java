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
public class Tile {
    
    private boolean isCorner; //true if tile is a corner tile
    private boolean isEdge; //true if tile is an edge tile
    private boolean isCore; //true if tile is neither a corner nor an edge tile
    
    private int tileID; //holds the ID number of the tile
    
    private char[][] tileChars; //2D Char array holding the chars of the tile in its current orientation
}
