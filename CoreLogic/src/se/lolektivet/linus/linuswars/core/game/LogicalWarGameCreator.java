package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.map.MapMaker;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainTile;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

/**
 * Created by Linus on 2015-12-02.
 */
public class LogicalWarGameCreator implements MapMaker {
   private LogicalWarGame _logicalWarGame;

   public LogicalWarGameCreator(LogicalWarGame logicalWarGame) {
      _logicalWarGame = logicalWarGame;
   }

   @Override
   public void addTerrain(TerrainTile terrainTile, int x, int y) {
      // Ignored
   }

   @Override
   public void addTerrain(TerrainType terrainType, int x, int y) {
      // Ignored
   }

   @Override
   public void addBuilding(TerrainType buildingType, Faction faction, int x, int y) {
      _logicalWarGame.addBuilding(new Position(x, y), buildingType, faction);
   }

   @Override
   public void finish() {
      // Ignored
   }
}
