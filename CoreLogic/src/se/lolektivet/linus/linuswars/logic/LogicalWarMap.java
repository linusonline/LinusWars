package se.lolektivet.linus.linuswars.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Linus on 2014-09-19.
 */
public class LogicalWarMap {
   private final Map<Position, TerrainType> _terrainTiles;
   private int _mapSizeX;
   private int _mapSizeY;

   public LogicalWarMap() {
      _terrainTiles = new HashMap<Position, TerrainType>();
      _mapSizeX = 0;
      _mapSizeY = 0;
   }

   TerrainType getTerrainForTile(Position tile) {
      return _terrainTiles.get(tile);
   }

   public void setTerrain(int x, int y, TerrainType terrainType) {
      _terrainTiles.put(new Position(x, y), terrainType);
      if (x >= _mapSizeX) {
         _mapSizeX = x + 1;
      }
      if (y >= _mapSizeY) {
         _mapSizeY = y + 1;
      }
   }

   public void mapReady() {
      if (_mapSizeX * _mapSizeY != _terrainTiles.size()) {
         throw new MapUninitializedException();
      }
   }

   public List<Position> findHqs() {
      List<Position> positionsOfHqs = new ArrayList<Position>();
      for (Map.Entry<Position, TerrainType> entry : _terrainTiles.entrySet()) {
         if (entry.getValue().equals(TerrainType.HQ)) {
            positionsOfHqs.add(entry.getKey());
         }
      }
      return positionsOfHqs;
   }

   public int getWidth() {
      return _mapSizeX;
   }

   public int getHeight() {
      return _mapSizeY;
   }

   public boolean isPositionInsideMap(Position position) {
      return position.getX() >= 0 &&
            position.getX() < getWidth() &&
            position.getY() >= 0 &&
            position.getY() < getHeight();
   }

   class TileAlreadySetException extends RuntimeException {
   }

   class MapUninitializedException extends RuntimeException {
   }
}
