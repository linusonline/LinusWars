package se.lolektivet.linus.linuswars.logic.game;

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

   private final BasesSetup _basesModule;

   public LogicalWarMapImpl(BasesSetup basesSetup) {
      _terrainTiles = new HashMap<>();
      _mapSizeX = 0;
      _mapSizeY = 0;
      _basesModule = basesSetup;
   }

   @Override
   public boolean hasTerrainForTile(Position tile) {
      return _terrainTiles.get(tile) != null;
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
      _basesModule.addBase(new Position(x, y), terrainType, faction);
      internalSetTerrainType(x, y, terrainType);
   }

   public void setTerrain(int x, int y, TerrainType terrainType) {
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

   BasesSetup getBasesModule() {
      return _basesModule;
   }

   public Collection<Base> getAllBases() {
      return _basesModule.getAllBases();
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

   static class MapUninitializedException extends RuntimeException {
      public MapUninitializedException(String message) {
         super(message);
      }
   }
}
