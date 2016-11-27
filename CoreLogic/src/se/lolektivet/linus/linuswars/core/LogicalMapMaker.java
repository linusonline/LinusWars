package se.lolektivet.linus.linuswars.core;

import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.game.LogicalWarMapImpl;

/**
 * Created by Linus on 2014-09-24.
 */
public class LogicalMapMaker implements MapMaker {
   private final LogicalWarMapImpl _logicalWarMap;

   public LogicalMapMaker(LogicalWarMapImpl logicalWarMap) {
      _logicalWarMap = logicalWarMap;
   }

   @Override
   public void addTerrain(TerrainTile terrainTile, int x, int y) {
      addTerrain(terrainTile.getTerrainType(), x, y);
   }

   @Override
   public void addTerrain(TerrainType terrainTile, int x, int y) {
      if (terrainTile.isBuilding()) {
         throw new InitializationException();
      }
      Position tilePosition = new Position(x, y);
      if (_logicalWarMap.hasTerrainForTile(tilePosition)) {
         throw new TileAlreadySetException("Tile in position " + tilePosition + " was set twice!");
      }
      _logicalWarMap.setTerrain(x, y, terrainTile);
   }

   @Override
   public void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      if (!buildingType.isBuilding()) {
         throw new InitializationException();
      }
      Position tilePosition = new Position(x, y);
      TerrainType previousType = _logicalWarMap.getTerrainForTile(tilePosition);
      if (previousType.isBuilding()) {
         throw new InitializationException("Tried to set two buildings at same position!");
      }
      _logicalWarMap.setBuilding(x, y, buildingType, faction);
   }

   @Override
   public void finish() {
      _logicalWarMap.validate();
   }

   static class TileAlreadySetException extends RuntimeException {
      public TileAlreadySetException(String message) {
         super(message);
      }
   }
}
