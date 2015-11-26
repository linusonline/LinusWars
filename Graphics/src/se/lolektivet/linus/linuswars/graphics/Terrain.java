package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-21.
 */
class Terrain {
   private SpriteSheet _spriteSheet;
   private final Map<TerrainTile, Position> _spritePositionForTile;

   Terrain() {
      _spritePositionForTile = new HashMap<>();
   }

   void init(ResourceLoader resourceLoader) {
      _spriteSheet = new SpriteSheet(resourceLoader.getTerrainSpriteSheet(), 16, 16);
      setSpriteCoordinatesForTile(TerrainTile.PLAIN, 0, 0);
      setSpriteCoordinatesForTile(TerrainTile.WOODS_SINGLE, 1, 0);
      setSpriteCoordinatesForTile(TerrainTile.MOUNTAIN_SMALL, 2, 0);
      setSpriteCoordinatesForTile(TerrainTile.WOODS_4X4_NW, 11, 0);
      setSpriteCoordinatesForTile(TerrainTile.WOODS_4X4_NE, 12, 0);
      setSpriteCoordinatesForTile(TerrainTile.WOODS_4X4_SW, 13, 0);
      setSpriteCoordinatesForTile(TerrainTile.WOODS_4X4_SE, 14, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_HORIZONTAL, 3, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_VERTICAL, 4, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_BEND_NW, 5, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_BEND_NE, 6, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_BEND_SW, 7, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_BEND_SE, 8, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_T_SOUTH, 9, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_T_NORTH, 10, 0);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_HORIZONTAL, 15, 0);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_VERTICAL, 16, 0);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_BEND_NE, 17, 0);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_BEND_SW, 18, 0);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_BEND_NW, 19, 0);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_BEND_SE, 20, 0);
      setSpriteCoordinatesForTile(TerrainTile.BRIDGE_HORIZONTAL, 21, 0);
   }

   private void setSpriteCoordinatesForTile(TerrainTile tile, int x, int y) {
      _spritePositionForTile.put(tile, new Position(x, y));
   }

   Renderable getTerrainSprite(TerrainTile terrainTile) {
      Position spritePosition = _spritePositionForTile.get(terrainTile);
      return _spriteSheet.getSubImage(spritePosition.getX(), spritePosition.getY());
   }
}
