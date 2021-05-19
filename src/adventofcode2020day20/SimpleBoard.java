/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventofcode2020day20;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author s138616
 */
public class SimpleBoard {
    
    private List<SimpleTile> tiles; //holds all tiles
    private List<ArrayList<SimpleTile>> board; //holds all tiles in correct board arangement
    
    public SimpleBoard(String tileStr){
        //split tiles into...tiles
        String[] tileStrings = tileStr.split("\\n\\n");
        
        //turn tileStrings into tiles
        this.tiles = new ArrayList<>(); //create new list of tiles of correct size
        for(int i = 0; i < tileStrings.length; i++){ //loop and fill
            tiles.add(new SimpleTile(tileStrings[i])); //fill tiles with...tiles
        }
        
        board = new ArrayList<>(); //create empty board to fill
        board.add(new ArrayList<SimpleTile>()); //make board 2D
        
        SimpleTile t = tiles.get(0); //get a tile
        t.setGrounded(true); //ground tile
        board.get(0).add(t); //add tile to board
        
        for(int i = 1; i < tiles.size(); i++){ //loop through tiles to find a match in each direction
            
        }
    }
    
    public void getMatchingTiles(SimpleTile tile, int x, int y){
        for(int dir = 0; dir < 4; dir++){ //loop through each direction
            if()
        }
        for(SimpleTile checkTile : tiles){ //check each tile
            if(checkTile.isGrounded()){ //if grounded, skip checking and move on
                continue;
            }
            
            
        }
    }
}
