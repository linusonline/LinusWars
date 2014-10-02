package se.lolektivet.linus.linuswars.graphicalgame;

import se.lolektivet.linus.linuswars.graphics.Buildings;
import se.lolektivet.linus.linuswars.graphics.Terrain;
import se.lolektivet.linus.linuswars.logic.*;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2014-09-22.
 */
public class GraphicalAndLogicalMapMaker implements MapMaker {
   private final LogicalWarMap _logicalWarMap;
   private final GraphicalWarMap _graphicalWarMap;
   private final Terrain _terrainSprites;
   private final Buildings _spriteSheet;

   public GraphicalAndLogicalMapMaker(LogicalWarMap logicalWarMap, GraphicalWarMap graphicalWarMap, Terrain terrainSprites, Buildings buildingSprites) {
      _logicalWarMap = logicalWarMap;
      _graphicalWarMap = graphicalWarMap;
      _terrainSprites = terrainSprites;
      _spriteSheet = buildingSprites;
   }

   @Override
   public void addTerrain(TerrainTile terrainTile, int x, int y) {
      _logicalWarMap.setTerrain(x, y, terrainTile.getTerrainType());
      _graphicalWarMap.addTerrain(_terrainSprites.getTerrainSprite(terrainTile), x, y);
   }

   @Override
   public void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      _logicalWarMap.setTerrain(x, y, buildingType);
      _graphicalWarMap.addBuilding(_spriteSheet.getBuildingSprite(buildingType, faction), x, y);
   }
}
