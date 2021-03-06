package se.lolektivet.linus.linuswars.core.map;

import se.lolektivet.linus.linuswars.core.IllegalMapException;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.game.LogicalWarMapSetup;

/**
 * Created by Linus on 2014-09-24.
 */
public class LogicalMapMaker implements MapMaker {
   private final LogicalWarMapSetup _logicalWarMap;

   public LogicalMapMaker(LogicalWarMapSetup logicalWarMap) {
      _logicalWarMap = logicalWarMap;
   }

   @Override
   public void addTerrain(TerrainTile terrainTile, int x, int y) {
      addTerrain(terrainTile.getTerrainType(), x, y);
   }

   @Override
   public void addTerrain(TerrainType terrainTile, int x, int y) {
      if (terrainTile.isBuilding()) {
         throw new IllegalMapException();
      }
      Position tilePosition = new Position(x, y);
      if (_logicalWarMap.hasTerrainForTile(tilePosition)) {
         throw new IllegalMapException("Tile in position " + tilePosition + " was set twice!");
      }
      _logicalWarMap.setTerrain(x, y, terrainTile);
   }

   @Override
   public void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      if (!buildingType.isBuilding()) {
         throw new IllegalMapException();
      }
      Position tilePosition = new Position(x, y);
      if (_logicalWarMap.hasTerrainForTile(tilePosition)) {
         TerrainType previousType = _logicalWarMap.getTerrainForTile(tilePosition);
         if (previousType.isBuilding()) {
            throw new IllegalMapException("Tried to set two buildings at same position!");
         }
      }
      _logicalWarMap.setBuilding(x, y, buildingType, faction);
   }

   @Override
   public void finish() {
      _logicalWarMap.validate();
   }

}
