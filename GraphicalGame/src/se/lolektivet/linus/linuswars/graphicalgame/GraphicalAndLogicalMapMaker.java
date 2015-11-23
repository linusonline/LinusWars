package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.LogicalWarMap;
import se.lolektivet.linus.linuswars.logic.MapMaker;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2014-09-22.
 */
public class GraphicalAndLogicalMapMaker implements MapMaker {
   private final LogicalWarMap _logicalWarMap;
   private final GraphicalWarMap _graphicalWarMap;
   private final Sprites _sprites;

   public GraphicalAndLogicalMapMaker(Sprites sprites, LogicalWarMap logicalWarMap, GraphicalWarMap graphicalWarMap) {
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
      _logicalWarMap.setTerrain(x, y, buildingType);
      _graphicalWarMap.addBuilding(_sprites.getBuildingSprite(buildingType, faction), x, y);
   }
}
