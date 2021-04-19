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
        this.tiles = new ArrayList<>(); //create new list of tiles of correct size
        for(int i = 0; i < tileStrings.length; i++){ //loop and fill
            tiles.add(new Tile(tileStrings[i])); //fill tiles with...tiles
        }
        
        //check each tile for matches
        for(int i = 0; i < tiles.size(); i++){ //base tile
            Tile baseTile = tiles.get(i); //get base tile
            for(int j = i + 1; j < tiles.size(); j++){ //check tile
                Tile checkTile = tiles.get(j); //get check tile
                
                //check for matches
                baseTile.checkMatchingEdges(checkTile);
            }
        }
        
        //setup tile type stores
        this.cornerTiles = new ArrayList<>();
        this.edgeTiles = new ArrayList<>();
        this.bodyTiles = new ArrayList<>();
        
        for(int i = 0; i < tiles.size(); i++){ //loop through and save each tile into its correct location
            Tile t = tiles.get(i); //get tile
            int matches = t.getMatchCount(); //get matchCount
            
            //set tile type based on matchCount
            t.setTileType(matches);
            
            if(t.getCorner()){ //if corner, add to corners
                cornerTiles.add(t);
            }
            else if(t.getEdge()){ //if edge, add to edges
                edgeTiles.add(t);
            }
            else if(t.getCore()){ //if body, add to bodies
                bodyTiles.add(t);
            }
        }
        
        //set board to correct size
        int size = (int)Math.sqrt(tiles.size()); //get sqrt of size (correct size for width and height of board)
        board = new Tile[size][size]; //create board of correct size
    }
    
    public void sort(){
        //get first corner
        Tile corner = cornerTiles.get(0);
        
    }
}
