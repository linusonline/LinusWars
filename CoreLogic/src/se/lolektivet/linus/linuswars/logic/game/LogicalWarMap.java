package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.InitializationException;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

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

   // TODO: Need way of specifying which properties belong to which faction. Problem is, maps should be playable with any factions!
   // Still, some properties might be owned by the same faction as one specific HQ.

   public LogicalWarMap() {
      _terrainTiles = new HashMap<>();
      _mapSizeX = 0;
      _mapSizeY = 0;
   }

   TerrainType getTerrainForTile(Position tile) {
      return _terrainTiles.get(tile);
   }

   public void setBuilding(int x, int y, TerrainType terrainType) {
      if (!terrainType.isBuilding()) {
         throw new InitializationException();
      }
      internalSetTerrainType(x, y, terrainType);
   }

   public void setTerrain(int x, int y, TerrainType terrainType) {
      if (terrainType.isBuilding()) {
         throw new InitializationException();
      }
      Position tilePosition = new Position(x, y);
      if (_terrainTiles.get(tilePosition) != null) {
         throw new TileAlreadySetException("Tile in position " + tilePosition + " was set twice!");
      }
      internalSetTerrainType(x, y, terrainType);
   }

   private void internalSetTerrainType(int x, int y, TerrainType terrainType) {
      _terrainTiles.put(new Position(x, y), terrainType);
      if (x >= _mapSizeX) {
         _mapSizeX = x + 1;
      }
      if (y >= _mapSizeY) {
         _mapSizeY = y + 1;
      }
   }

   public void validate() {
      if (_mapSizeX * _mapSizeY != _terrainTiles.size()) {
         throw new MapUninitializedException("Some tiles of the map were not set!");
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

   static class TileAlreadySetException extends RuntimeException {
      public TileAlreadySetException(String message) {
         super(message);
      }
   }

   static class MapUninitializedException extends RuntimeException {
      public MapUninitializedException(String message) {
         super(message);
      }
   }
}
