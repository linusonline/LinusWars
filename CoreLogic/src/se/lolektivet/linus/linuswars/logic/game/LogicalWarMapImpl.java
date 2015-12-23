package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.InitializationException;
import se.lolektivet.linus.linuswars.logic.LogicException;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-19.
 */
public class LogicalWarMapImpl implements LogicalWarMap {
   private final Map<Position, TerrainType> _terrainTiles;
   private int _mapSizeX;
   private int _mapSizeY;

   private ModuleBases _basesModule;

   // TODO: Need way of specifying which properties belong to which faction. Problem is, maps should be playable with any factions!
   // Still, some properties might be owned by the same faction as one specific HQ.

   public LogicalWarMapImpl() {
      _terrainTiles = new HashMap<>();
      _mapSizeX = 0;
      _mapSizeY = 0;
      _basesModule = new ModuleBases();
   }

   @Override
   public TerrainType getTerrainForTile(Position tile) {
      TerrainType type = _terrainTiles.get(tile);
      if (type == null) {
         throw new LogicException("Position " + tile + " is outside map!");
      }
      return type;
   }

   public void setBuilding(int x, int y, TerrainType terrainType, Faction faction) {
      if (!terrainType.isBuilding()) {
         throw new InitializationException();
      }
      Position tilePosition = new Position(x, y);
      TerrainType previousType = _terrainTiles.get(tilePosition);
      if (previousType.isBuilding()) {
         throw new InitializationException("Tried to set two bases at same position!");
      }
      _basesModule.addBase(tilePosition, terrainType, faction);
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
      _basesModule.validateSetup();
   }

   ModuleBases takeOverBasesModule() {
      throwIfNoBasesModule();
      ModuleBases basesModule = _basesModule;
      _basesModule = null;
      return basesModule;
   }

   public Collection<Base> getAllBases() {
      throwIfNoBasesModule();
      return _basesModule.getAllBases();
   }

   private void throwIfNoBasesModule() {
      if (_basesModule == null) {
         throw new InitializationException("Bases Module already taken over from LogicalWarMap!");
      }
   }

   @Override
   public int getWidth() {
      return _mapSizeX;
   }

   @Override
   public int getHeight() {
      return _mapSizeY;
   }

   @Override
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
