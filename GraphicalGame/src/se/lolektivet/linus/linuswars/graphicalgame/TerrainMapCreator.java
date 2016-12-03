package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.game.LogicalWarMap;

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
      _defaults.put(TerrainType.WOODS, TerrainTile.WOODS_SINGLE);
      _defaults.put(TerrainType.MOUNTAINS, TerrainTile.MOUNTAIN_SMALL);
      _defaults.put(TerrainType.REEF, TerrainTile.REEF);

      // Not expected to be used
      _defaults.put(TerrainType.ROAD, TerrainTile.ROAD_HORIZONTAL);
      _defaults.put(TerrainType.RIVER, TerrainTile.RIVER_HORIZONTAL);
      _defaults.put(TerrainType.BRIDGE, TerrainTile.BRIDGE_HORIZONTAL);
      _defaults.put(TerrainType.SEA, TerrainTile.SEA);
      _defaults.put(TerrainType.SHOAL, TerrainTile.SHOAL_END_S);

      // There are no terrain tiles for buildings, since they look different according to faction.
      // Expected to be replaced by actual buildings later.
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
      if (type.equals(TerrainType.ROAD)) {
         doRoadTile();
      } else if (type.equals(TerrainType.RIVER)) {
         doRiverTile();
      } else if (type.equals(TerrainType.BRIDGE)) {
         doBridgeTile();
      } else if (type.equals(TerrainType.SHOAL)) {
         doShoalTile();
      } else if (type.equals(TerrainType.SEA)) {
         doSeaTile();
      } else {
         setDefaultTile();
      }
   }

   private void doRoadTile() {
      boolean roadNorth = isRoadLike(getTypeNorth());
      boolean roadSouth = isRoadLike(getTypeSouth());
      boolean roadWest = isRoadLike(getTypeWest());
      boolean roadEast = isRoadLike(getTypeEast());

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

   private void doRiverTile() {
      boolean riverNorth = isSea(getTypeNorth());
      boolean riverSouth = isSea(getTypeSouth());
      boolean riverWest = isSea(getTypeWest());
      boolean riverEast = isSea(getTypeEast());

      if (riverWest &&
            riverEast &&
            riverNorth &&
            riverSouth) {
         setTile(TerrainTile.RIVER_X);

      } else if (riverWest &&
            riverEast &&
            riverSouth) {
         setTile(TerrainTile.RIVER_T_SOUTH);
      } else if (riverWest &&
            riverEast &&
            riverNorth) {
         setTile(TerrainTile.RIVER_T_NORTH);
      } else if (riverWest &&
            riverSouth &&
            riverNorth) {
         setTile(TerrainTile.RIVER_T_WEST);
      } else if (riverEast &&
            riverSouth &&
            riverNorth) {
         setTile(TerrainTile.RIVER_T_EAST);

      } else if (riverNorth && riverEast) {
         setTile(TerrainTile.RIVER_BEND_NE);
      } else if (riverNorth && riverWest) {
         setTile(TerrainTile.RIVER_BEND_NW);
      } else if (riverSouth && riverWest) {
         setTile(TerrainTile.RIVER_BEND_SW);
      } else if (riverSouth && riverEast) {
         setTile(TerrainTile.RIVER_BEND_SE);

      } else if (riverNorth || riverSouth) {
         setTile(TerrainTile.RIVER_VERTICAL);

      } else {
         setDefaultTile();
      }
   }

   private void doBridgeTile() {
      boolean bridgeNorth = getTypeNorth().equals(TerrainType.BRIDGE);
      boolean bridgeSouth = getTypeSouth().equals(TerrainType.BRIDGE);
      boolean bridgeWest = getTypeWest().equals(TerrainType.BRIDGE);
      boolean bridgeEast = getTypeEast().equals(TerrainType.BRIDGE);

//      boolean roadNorth = getTypeNorth().equals(TerrainType.ROAD);
//      boolean roadSouth = getTypeSouth().equals(TerrainType.ROAD);
//      boolean roadWest = getTypeWest().equals(TerrainType.ROAD);
//      boolean roadEast = getTypeEast().equals(TerrainType.ROAD);

      if (bridgeNorth || bridgeSouth) {
         setTile(TerrainTile.BRIDGE_VERTICAL);
      } else if (bridgeEast || bridgeWest) {
         setTile(TerrainTile.BRIDGE_HORIZONTAL);

      } else if (isLandNotRiver(getTypeNorth()) && isLandNotRiver(getTypeSouth())) {
         setTile(TerrainTile.BRIDGE_VERTICAL);
      } else if (isLandNotRiver(getTypeWest()) && isLandNotRiver(getTypeEast())) {
         setTile(TerrainTile.BRIDGE_HORIZONTAL);

      } else if (isLandNotRiver(getTypeNorth()) || isLandNotRiver(getTypeSouth())) {
         setTile(TerrainTile.BRIDGE_VERTICAL);
      } else if (isLandNotRiver(getTypeWest()) || isLandNotRiver(getTypeEast())) {
         setTile(TerrainTile.BRIDGE_HORIZONTAL);

      } else {
         setDefaultTile();
      }
   }

   private void doShoalTile() {
      TerrainType terrainNorth = getTypeNorth();
      TerrainType terrainSouth = getTypeSouth();
      TerrainType terrainWest = getTypeWest();
      TerrainType terrainEast = getTypeEast();

      if (isLand(terrainNorth) &&
            isLand(terrainEast) &&
            isLand(terrainWest)) {
         setTile(TerrainTile.SHOAL_END_N);
      } else if (isLand(terrainSouth) &&
            isLand(terrainEast) &&
            isLand(terrainWest)) {
         setTile(TerrainTile.SHOAL_END_S);
      } else if (isLand(terrainWest) &&
            isLand(terrainSouth) &&
            isLand(terrainNorth)) {
         setTile(TerrainTile.SHOAL_END_W);
      } else if (isLand(terrainEast) &&
            isLand(terrainSouth) &&
            isLand(terrainNorth)) {
         setTile(TerrainTile.SHOAL_END_E);

      } else if (isLand(terrainNorth) &&
            isLand(terrainWest)) {
         if (isShoal(terrainSouth) || isShoal(terrainEast)) {
            setTile(TerrainTile.SHOAL_EDGE_NW);
         } else {
            setTile(TerrainTile.SHOAL_CORNER_NW);
         }
      } else if (isLand(terrainNorth) &&
            isLand(terrainEast)) {
         if (isShoal(terrainSouth) || isShoal(terrainWest)) {
            setTile(TerrainTile.SHOAL_EDGE_NE);
         } else {
            setTile(TerrainTile.SHOAL_CORNER_NE);
         }
      } else if (isLand(terrainSouth) &&
            isLand(terrainWest)) {
         if (isShoal(terrainNorth) || isShoal(terrainEast)) {
            setTile(TerrainTile.SHOAL_EDGE_SW);
         } else {
            setTile(TerrainTile.SHOAL_CORNER_SW);
         }
      } else if (isLand(terrainSouth) &&
            isLand(terrainEast)) {
         if (isShoal(terrainNorth) || isShoal(terrainWest)) {
            setTile(TerrainTile.SHOAL_EDGE_SE);
         } else {
            setTile(TerrainTile.SHOAL_CORNER_SE);
         }

      } else if (isLand(terrainNorth)) {
         if (isShoal(terrainWest)) {
            if (isShoal(terrainEast)) {
               setTile(TerrainTile.SHOAL_EDGE_N);
            } else {
               setTile(TerrainTile.SHOAL_EDGE_N_END_E);
            }
         } else {
            if (isShoal(terrainEast)) {
               setTile(TerrainTile.SHOAL_EDGE_N_END_W);
            } else {
               setTile(TerrainTile.SHOAL_EDGE_N_END_WE);
            }
         }

      } else if (isLand(terrainSouth)) {
         if (isShoal(terrainWest)) {
            if (isShoal(terrainEast)) {
               setTile(TerrainTile.SHOAL_EDGE_S);
            } else {
               setTile(TerrainTile.SHOAL_EDGE_S_END_E);
            }
         } else {
            if (isShoal(terrainEast)) {
               setTile(TerrainTile.SHOAL_EDGE_S_END_W);
            } else {
               setTile(TerrainTile.SHOAL_EDGE_S_END_WE);
            }
         }

      } else if (isLand(terrainWest)) {
         if (isShoal(terrainNorth)) {
            if (isShoal(terrainSouth)) {
               setTile(TerrainTile.SHOAL_EDGE_W);
            } else {
               setTile(TerrainTile.SHOAL_EDGE_W_END_S);
            }
         } else {
            if (isShoal(terrainSouth)) {
               setTile(TerrainTile.SHOAL_EDGE_W_END_N);
            } else {
               setTile(TerrainTile.SHOAL_EDGE_W_END_NS);
            }
         }

      } else if (isLand(terrainEast)) {
         if (isShoal(terrainNorth)) {
            if (isShoal(terrainSouth)) {
               setTile(TerrainTile.SHOAL_EDGE_E);
            } else {
               setTile(TerrainTile.SHOAL_EDGE_E_END_S);
            }
         } else {
            if (isShoal(terrainSouth)) {
               setTile(TerrainTile.SHOAL_EDGE_E_END_N);
            } else {
               setTile(TerrainTile.SHOAL_EDGE_E_END_NS);
            }
         }

      } else {
         setDefaultTile();
      }
   }

   private void doSeaTile() {
      TerrainType terrainNorth = getTypeNorth();
      TerrainType terrainSouth = getTypeSouth();
      TerrainType terrainWest = getTypeWest();
      TerrainType terrainEast = getTypeEast();

      TerrainType terrainNorthWest = getTypeNorthWest();
      TerrainType terrainNorthEast = getTypeNorthEast();
      TerrainType terrainSouthWest = getTypeSouthWest();
      TerrainType terrainSouthEast = getTypeSouthEast();

      if (isSeaOrShoal(terrainNorth) &&
            isSeaOrShoal(terrainSouth) &&
            isSeaOrShoal(terrainWest) &&
            isSeaOrShoal(terrainEast)) {
         if (isLand(terrainNorthEast) &&
               isLand(terrainNorthWest) &&
               isLand(terrainSouthEast) &&
               isLand(terrainSouthWest)) {
            setTile(TerrainTile.SEA_X);
         } else if (isLand(terrainNorthEast)) {
            setTile(TerrainTile.SEA_CORNER_NE);
         } else if (isLand(terrainNorthWest)) {
            setTile(TerrainTile.SEA_CORNER_NW);
         } else if (isLand(terrainSouthEast)) {
            setTile(TerrainTile.SEA_CORNER_SE);
         } else if (isLand(terrainSouthWest)) {
            setTile(TerrainTile.SEA_CORNER_SW);
         } else {
            setTile(TerrainTile.SEA);
         }

      } else if (isLand(terrainNorth) &&
            isLand(terrainSouth) &&
            isLand(terrainWest) &&
            isLand(terrainEast)) {
         setTile(TerrainTile.SEA_SINGLE);
      } else if (isLand(terrainNorth) &&
            isLand(terrainWest) &&
            isLand(terrainEast)) {
         setTile(TerrainTile.SEA_END_N);
      } else if (isLand(terrainSouth) &&
            isLand(terrainWest) &&
            isLand(terrainEast)) {
         setTile(TerrainTile.SEA_END_S);
      } else if (isLand(terrainWest) &&
            isLand(terrainNorth) &&
            isLand(terrainSouth)) {
         setTile(TerrainTile.SEA_END_W);
      } else if (isLand(terrainEast) &&
            isLand(terrainNorth) &&
            isLand(terrainSouth)) {
         setTile(TerrainTile.SEA_END_E);

      } else if (isLand(terrainNorth) &&
            isLand(terrainSouth)) {
         setTile(TerrainTile.SEA_HORIZONTAL);
      } else if (isLand(terrainWest) &&
            isLand(terrainEast)) {
         setTile(TerrainTile.SEA_VERTICAL);

      } else if (isLand(terrainNorth) &&
            isLand(terrainWest)) {
         if (isLand(terrainSouthEast)) {
            setTile(TerrainTile.SEA_BEND_SE);
         } else {
            setTile(TerrainTile.SEA_EDGE_NW);
         }
      } else if (isLand(terrainNorth) &&
            isLand(terrainEast)) {
         if (isLand(terrainSouthWest)) {
            setTile(TerrainTile.SEA_BEND_SW);
         } else {
            setTile(TerrainTile.SEA_EDGE_NE);
         }
      } else if (isLand(terrainSouth) &&
            isLand(terrainWest)) {
         if (isLand(terrainNorthEast)) {
            setTile(TerrainTile.SEA_BEND_NE);
         } else {
            setTile(TerrainTile.SEA_EDGE_SW);
         }
      } else if (isLand(terrainSouth) &&
            isLand(terrainEast)) {
         if (isLand(terrainNorthWest)) {
            setTile(TerrainTile.SEA_BEND_NW);
         } else {
            setTile(TerrainTile.SEA_EDGE_SE);
         }

      } else if (isLand(terrainNorth)) {
         if (isLand(terrainSouthWest) &&
               isLand(terrainSouthEast)) {
            setTile(TerrainTile.SEA_T_S);
         } else {
            setTile(TerrainTile.SEA_EDGE_N);
         }
      } else if (isLand(terrainSouth)) {
         if (isLand(terrainNorthWest) &&
               isLand(terrainNorthEast)) {
            setTile(TerrainTile.SEA_T_N);
         } else {
            setTile(TerrainTile.SEA_EDGE_S);
         }
      } else if (isLand(terrainWest)) {
         if (isLand(terrainNorthEast) &&
               isLand(terrainSouthEast)) {
            setTile(TerrainTile.SEA_T_E);
         } else {
            setTile(TerrainTile.SEA_EDGE_W);
         }
      } else if (isLand(terrainEast)) {
         if (isLand(terrainNorthWest) &&
               isLand(terrainSouthWest)) {
            setTile(TerrainTile.SEA_T_W);
         } else {
            setTile(TerrainTile.SEA_EDGE_E);
         }

      } else {
         setDefaultTile();
      }
   }

   private boolean isLand(TerrainType terrainType) {
      return terrainType.equals(TerrainType.PLAINS) ||
            terrainType.equals(TerrainType.WOODS) ||
            terrainType.equals(TerrainType.MOUNTAINS) ||
            terrainType.equals(TerrainType.ROAD) ||
            (terrainType.isBuilding() && !terrainType.equals(TerrainType.PORT));
   }

   private boolean isSea(TerrainType terrainType) {
      return terrainType.equals(TerrainType.SEA) ||
            terrainType.equals(TerrainType.REEF) ||
            terrainType.equals(TerrainType.PORT) ||
            terrainType.equals(TerrainType.RIVER) ||
            terrainType.equals(TerrainType.BRIDGE);
   }

   private boolean isShoal(TerrainType terrainType) {
      // NOTE: This equals (!isLand(t) && !isSea(t))
      return terrainType.equals(TerrainType.SHOAL);
   }

   private boolean isLandNotRiver(TerrainType terrainType) {
      return isLand(terrainType) && !terrainType.equals(TerrainType.RIVER);
   }

   private boolean isSeaOrShoal(TerrainType terrainType) {
      return isSea(terrainType) || isShoal(terrainType);
   }

   private boolean isRoadLike(TerrainType terrainType) {
      return terrainType.equals(TerrainType.ROAD) ||
            terrainType.equals(TerrainType.BRIDGE) ||
            terrainType.equals(TerrainType.BASE) ||
            terrainType.equals(TerrainType.PORT) ||
            terrainType.equals(TerrainType.AIRPORT) ||
            terrainType.equals(TerrainType.HQ);
   }

   private TerrainType getTypeNorth() {
      return getTypeOrMock(new Position(_currentX, _currentY - 1));
   }

   private TerrainType getTypeSouth() {
      return getTypeOrMock(new Position(_currentX, _currentY + 1));
   }

   private TerrainType getTypeWest() {
      return getTypeOrMock(new Position(_currentX - 1, _currentY));
   }

   private TerrainType getTypeEast() {
      return getTypeOrMock(new Position(_currentX + 1, _currentY));
   }

   private TerrainType getTypeNorthWest() {
      return getTypeOrMock(new Position(_currentX - 1, _currentY - 1));
   }

   private TerrainType getTypeNorthEast() {
      return getTypeOrMock(new Position(_currentX + 1, _currentY - 1));
   }

   private TerrainType getTypeSouthWest() {
      return getTypeOrMock(new Position(_currentX - 1, _currentY + 1));
   }

   private TerrainType getTypeSouthEast() {
      return getTypeOrMock(new Position(_currentX + 1, _currentY + 1));
   }

   private TerrainType getTypeOrMock(Position position) {
      if (_logicalWarMap.isPositionInsideMap(position)) {
         return getType(position);
      } else {
         return TerrainType.SEA;
      }
   }

   private TerrainType getType() {
      return getType(_currentX, _currentY);
   }

   private TerrainType getType(int x, int y) {
      return getType(new Position(x, y));
   }

   private TerrainType getType(Position position) {
      return _logicalWarMap.getTerrainForTile(position);
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
