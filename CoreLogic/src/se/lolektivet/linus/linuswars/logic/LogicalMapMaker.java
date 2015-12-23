package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.game.LogicalWarMapImpl;

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
      _logicalWarMap.setTerrain(x, y, terrainTile.getTerrainType());
   }

   @Override
   public void addTerrain(TerrainType terrainTile, int x, int y) {
      _logicalWarMap.setTerrain(x, y, terrainTile);
   }

   @Override
   public void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      _logicalWarMap.setBuilding(x, y, buildingType, faction);
   }

   @Override
   public void validate() {
      _logicalWarMap.validate();
   }
}
