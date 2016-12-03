package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.IllegalMapOrSetupException;
import se.lolektivet.linus.linuswars.core.LogicException;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

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

   private final BuildingsSetup _buildingsModule;

   public LogicalWarMapImpl(BuildingsSetup buildingsSetup) {
      _terrainTiles = new HashMap<>();
      _mapSizeX = 0;
      _mapSizeY = 0;
      _buildingsModule = buildingsSetup;
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
      _buildingsModule.addBuilding(new Position(x, y), terrainType, faction);
      internalSetTerrainType(x, y, terrainType);
   }

   public void setTerrain(int x, int y, TerrainType terrainType) {
      internalSetTerrainType(x, y, terrainType);
   }

   private Position _rightmostTile = new Position(0, 0);
   private Position _bottomTile = new Position(0, 0);

   private void internalSetTerrainType(int x, int y, TerrainType terrainType) {
      Position newTilePosition = new Position(x, y);
      _terrainTiles.put(newTilePosition, terrainType);
      if (x >= _mapSizeX) {
         _mapSizeX = x + 1;
         _rightmostTile = newTilePosition;
      }
      if (y >= _mapSizeY) {
         _mapSizeY = y + 1;
         _bottomTile = newTilePosition;
      }
   }

   public void validate() {
      if (_mapSizeX * _mapSizeY != _terrainTiles.size()) {

         throw new IllegalMapOrSetupException("Some tiles of the map were not set! Map size is " + _mapSizeX + " by " +
               _mapSizeY + ", missing " + (_mapSizeX * _mapSizeY - _terrainTiles.size()) + " tiles. Rightmost tile is on " + _rightmostTile);
      }
      _buildingsModule.validateSetup();
   }

   BuildingsSetup getBuildingsModule() {
      return _buildingsModule;
   }

   public Collection<Building> getAllBuildings() {
      return _buildingsModule.getAllBuildings();
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
}
