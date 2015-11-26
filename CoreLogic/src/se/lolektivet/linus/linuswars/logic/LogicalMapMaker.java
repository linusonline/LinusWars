package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2014-09-24.
 */
public class LogicalMapMaker implements MapMaker {
   private final LogicalWarMap _logicalWarMap;

   public LogicalMapMaker(LogicalWarMap logicalWarMap) {
      _logicalWarMap = logicalWarMap;
   }

   @Override
   public void addTerrain(TerrainTile terrainTile, int x, int y) {
      _logicalWarMap.setTerrain(x, y, terrainTile.getTerrainType());
   }

   @Override
   public void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      _logicalWarMap.setBuilding(x, y, buildingType);
   }

   @Override
   public void validate() {
      _logicalWarMap.validate();
   }
}
