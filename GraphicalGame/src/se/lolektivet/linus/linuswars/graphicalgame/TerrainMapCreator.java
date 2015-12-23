package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.game.LogicalWarMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2015-12-18.
 */
public class TerrainMapCreator {
   private Map<TerrainType, TerrainTile> _defaults;

   private TerrainMap _terrainMap;
   private LogicalWarMap _logicalWarMap;
   private int _currentX;
   private int _currentY;

   public TerrainMapCreator() {
      _defaults = new HashMap<>();
      _defaults.put(TerrainType.PLAINS, TerrainTile.PLAIN);
      _defaults.put(TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      _defaults.put(TerrainType.WOODS, TerrainTile.WOODS_SINGLE);
      _defaults.put(TerrainType.BRIDGE, TerrainTile.BRIDGE_HORIZONTAL);
      _defaults.put(TerrainType.MOUNTAINS, TerrainTile.MOUNTAIN_SMALL);

      // There are no terrain tiles for bases, since they look different according to faction.
      // Expected to be replaced by actual bases later.
      _defaults.put(TerrainType.CITY, TerrainTile.PLAIN);
      _defaults.put(TerrainType.BASE, TerrainTile.PLAIN);
      _defaults.put(TerrainType.AIRPORT, TerrainTile.PLAIN);
      _defaults.put(TerrainType.PORT, TerrainTile.PLAIN);
      _defaults.put(TerrainType.HQ, TerrainTile.PLAIN);
   }

   public TerrainMap create(LogicalWarMap logicalWarMap) {
      _terrainMap = new TerrainMap();
      _logicalWarMap = logicalWarMap;

      for (int y = 0; y < _logicalWarMap.getHeight(); y++) {
         for (int x = 0; x < _logicalWarMap.getWidth(); x++) {
            _currentX = x;
            _currentY = y;
            doTile();
         }
      }

      return _terrainMap;
   }

   private void doTile() {
      TerrainType type = getType();
      if (type == TerrainType.ROAD) {
         doRoadTile();
      } else {
         setDefaultTile();
      }
   }

   private void doRoadTile() {
      boolean roadNorth = (getTypeNorth() == TerrainType.ROAD);
      boolean roadSouth = (getTypeSouth() == TerrainType.ROAD);
      boolean roadWest = (getTypeWest() == TerrainType.ROAD);
      boolean roadEast = (getTypeEast() == TerrainType.ROAD);

      if (roadWest &&
            roadEast &&
            roadNorth &&
            roadSouth) {
         setTile(TerrainTile.ROAD_X);

      } else if (roadWest &&
            roadEast &&
            roadSouth) {
         setTile(TerrainTile.ROAD_T_SOUTH);
      } else if (roadWest &&
            roadEast &&
            roadNorth) {
         setTile(TerrainTile.ROAD_T_NORTH);
      } else if (roadWest &&
            roadSouth &&
            roadNorth) {
         setTile(TerrainTile.ROAD_T_WEST);
      } else if (roadEast &&
            roadSouth &&
            roadNorth) {
         setTile(TerrainTile.ROAD_T_EAST);

      } else if (roadNorth && roadEast) {
         setTile(TerrainTile.ROAD_BEND_NE);
      } else if (roadNorth && roadWest) {
         setTile(TerrainTile.ROAD_BEND_NW);
      } else if (roadSouth && roadWest) {
         setTile(TerrainTile.ROAD_BEND_SW);
      } else if (roadSouth && roadEast) {
         setTile(TerrainTile.ROAD_BEND_SE);

      } else if (roadNorth || roadSouth) {
         setTile(TerrainTile.ROAD_VERTICAL);

      } else {
         setDefaultTile();
      }
   }

   private TerrainType getTypeEast() {
      if (_logicalWarMap.isPositionInsideMap(new Position(_currentX + 1, _currentY))) {
         return getType(_currentX + 1, _currentY);
      } else {
         return TerrainType.PLAINS;
      }
   }

   private TerrainType getTypeWest() {
      if (_logicalWarMap.isPositionInsideMap(new Position(_currentX - 1, _currentY))) {
         return getType(_currentX - 1, _currentY);
      } else {
         return TerrainType.PLAINS;
      }
   }

   private TerrainType getTypeNorth() {
      if (_logicalWarMap.isPositionInsideMap(new Position(_currentX, _currentY - 1))) {
         return getType(_currentX, _currentY - 1);
      } else {
         return TerrainType.PLAINS;
      }
   }

   private TerrainType getTypeSouth() {
      if (_logicalWarMap.isPositionInsideMap(new Position(_currentX, _currentY + 1))) {
         return getType(_currentX, _currentY + 1);
      } else {
         return TerrainType.PLAINS;
      }
   }

   private TerrainType getType() {
      return getType(_currentX, _currentY);
   }

   private TerrainType getType(int x, int y) {
      return _logicalWarMap.getTerrainForTile(new Position(x, y));
   }

   private void setDefaultTile() {
      setTile(getDefaultTileForType(getType()));
   }

   private void setTile(TerrainTile terrainTile) {
      _terrainMap.put(new Position(_currentX, _currentY), terrainTile);
   }

   private TerrainTile getDefaultTileForType(TerrainType type) {
      return _defaults.get(type);
   }
}
