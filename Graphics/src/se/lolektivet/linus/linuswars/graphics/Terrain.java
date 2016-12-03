package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;

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
      setSpriteCoordinatesForTile(TerrainTile.RIVER_HORIZONTAL, 0, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_VERTICAL, 1, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_BEND_SE, 2, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_BEND_SW, 3, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_BEND_NE, 4, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_BEND_NW, 5, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_T_SOUTH, 6, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_T_NORTH, 7, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_T_EAST, 8, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_T_WEST, 9, 0);
      setSpriteCoordinatesForTile(TerrainTile.RIVER_X, 10, 0);
      setSpriteCoordinatesForTile(TerrainTile.WOODS_SINGLE, 11, 0);
      setSpriteCoordinatesForTile(TerrainTile.WOODS_4X4_NW, 12, 0);
      setSpriteCoordinatesForTile(TerrainTile.WOODS_4X4_NE, 13, 0);
      setSpriteCoordinatesForTile(TerrainTile.WOODS_4X4_SW, 14, 0);
      setSpriteCoordinatesForTile(TerrainTile.WOODS_4X4_SE, 15, 0);

      setSpriteCoordinatesForTile(TerrainTile.ROAD_HORIZONTAL, 0, 1);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_VERTICAL, 1, 1);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_BEND_SE, 2, 1);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_BEND_SW, 3, 1);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_BEND_NE, 4, 1);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_BEND_NW, 5, 1);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_T_SOUTH, 6, 1);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_T_NORTH, 7, 1);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_T_EAST, 8, 1);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_T_WEST, 9, 1);
      setSpriteCoordinatesForTile(TerrainTile.ROAD_X, 10, 1);
      setSpriteCoordinatesForTile(TerrainTile.BRIDGE_HORIZONTAL, 11, 1);
      setSpriteCoordinatesForTile(TerrainTile.BRIDGE_VERTICAL, 12, 1);

      setSpriteCoordinatesForTile(TerrainTile.REEF, 0, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA, 1, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_EDGE_N, 2, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_EDGE_S, 3, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_EDGE_W, 4, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_EDGE_E, 5, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_EDGE_NW, 6, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_EDGE_NE, 7, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_EDGE_SW, 8, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_EDGE_SE, 9, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_CORNER_NW, 10, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_CORNER_NE, 11, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_CORNER_SW, 12, 2);
      setSpriteCoordinatesForTile(TerrainTile.SEA_CORNER_SE, 13, 2);
      setSpriteCoordinatesForTile(TerrainTile.PLAIN, 14, 2);
      setSpriteCoordinatesForTile(TerrainTile.MOUNTAIN_SMALL, 15, 2);

      setSpriteCoordinatesForTile(TerrainTile.SEA_BEND_NW, 0, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_BEND_NE, 1, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_BEND_SW, 2, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_BEND_SE, 3, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_T_S, 4, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_T_N, 5, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_T_E, 6, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_T_W, 7, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_X, 8, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_HORIZONTAL, 9, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_VERTICAL, 10, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_END_N, 11, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_END_S, 12, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_END_W, 13, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_END_E, 14, 3);
      setSpriteCoordinatesForTile(TerrainTile.SEA_SINGLE, 15, 3);

      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_N, 0, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_N_END_W, 1, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_N_END_E, 2, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_N_END_WE, 3, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_S, 4, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_S_END_W, 5, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_S_END_E, 6, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_S_END_WE, 7, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_W, 8, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_W_END_N, 9, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_W_END_S, 10, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_W_END_NS, 11, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_E, 12, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_E_END_N, 13, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_E_END_S, 14, 4);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_E_END_NS, 15, 4);

      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_NW, 0, 5);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_NE, 1, 5);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_SW, 2, 5);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_EDGE_SE, 3, 5);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_END_N, 4, 5);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_END_S, 5, 5);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_END_W, 6, 5);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_END_E, 7, 5);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_CORNER_NW, 8, 5);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_CORNER_NE, 9, 5);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_CORNER_SW, 10, 5);
      setSpriteCoordinatesForTile(TerrainTile.SHOAL_CORNER_SE, 11, 5);

   }

   private void setSpriteCoordinatesForTile(TerrainTile tile, int x, int y) {
      _spritePositionForTile.put(tile, new Position(x, y));
   }

   Renderable getTerrainSprite(TerrainTile terrainTile) {
      Position spritePosition = _spritePositionForTile.get(terrainTile);
      if (spritePosition == null) {
         throw new RuntimeException("Sprite not found!");
      }
      return _spriteSheet.getSubImage(spritePosition.getX(), spritePosition.getY());
   }
}
