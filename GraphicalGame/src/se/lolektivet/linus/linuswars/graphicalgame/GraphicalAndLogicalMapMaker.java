package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.MapMaker;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.game.LogicalWarMapImpl;

/**
 * Created by Linus on 2014-09-22.
 */
public class GraphicalAndLogicalMapMaker implements MapMaker {
   private final LogicalWarMapImpl _logicalWarMap;
   private final GraphicalWarMap _graphicalWarMap;
   private final Sprites _sprites;

   public GraphicalAndLogicalMapMaker(Sprites sprites, LogicalWarMapImpl logicalWarMap, GraphicalWarMap graphicalWarMap) {
      _logicalWarMap = logicalWarMap;
      _graphicalWarMap = graphicalWarMap;
      _sprites = sprites;
   }

   @Override
   public void addTerrain(TerrainTile terrainTile, int x, int y) {
      _logicalWarMap.setTerrain(x, y, terrainTile.getTerrainType());
      _graphicalWarMap.addTerrain(_sprites.getTerrainSprite(terrainTile), x, y);
   }

   @Override
   public void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      _logicalWarMap.setBuilding(x, y, buildingType, faction);
      _graphicalWarMap.addBuilding(_sprites.getBuildingSprite(buildingType, faction), x, y);
   }

   @Override
   public void validate() {
      _logicalWarMap.validate();
   }
}
