package map;

import core.Position;
import core.Size;
import game.Game;
import gfx.SpriteLibrary;

import java.util.Arrays;

public class GameMap {

    private Tile[][] tiles;

    public GameMap(Size size, SpriteLibrary spriteLibrary) {
        tiles = new Tile[size.getWidth()][size.getHeight()];
        initializeTiles(spriteLibrary);
    }

    private void initializeTiles(SpriteLibrary spriteLibrary) {
        for(Tile[] row: tiles){
            Arrays.fill(row, new Tile(spriteLibrary));
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getWidth(){
        return tiles.length * Game.SPRITE_SIZE_TILE;
    }

    public int getHeight(){
        return tiles[0].length * Game.SPRITE_SIZE_TILE;
    }

    public Position getRandomPosition() {
        //top left quarter of the map
        double x = Math.random() * tiles.length * Game.SPRITE_SIZE_TILE / 2 + 500;
        double y = Math.random() * tiles[0].length * Game.SPRITE_SIZE_TILE / 2 + 500;

        return new Position(x, y);
    }
}
