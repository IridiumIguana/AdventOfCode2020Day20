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
public class TileBoard {
    
    private List<Tile> tiles; //holds all tiles
    private List<Tile> cornerTiles; //holds all corner tiles (2 matches)
    private List<Tile> edgeTiles; //holds all edge tiles (3 matches)
    private List<Tile> bodyTiles; //holds all body tiles (4 matches)
    
    private Tile[][] board; //holds all tiles in correct board arangement
    
    public TileBoard(String tileStr){
        
        //split tiles into...tiles
        String[] tileStrings = tileStr.split("\\n\\n");
        
        //turn tileStrings into tiles
        this.tiles = new ArrayList<Tile>(tileStrings.length); //create new list of tiles of correct size
        for(int i = 0; i < tileStrings.length; i++){ //loop and fill
            tiles.set(i, new Tile(tileStrings[i])); //fill tiles with...tiles
        }
        
        //check each tile for matches
        for(int i = 0; i < tiles.size(); i++){
            
        }
    }
}
