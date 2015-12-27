package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.MapMaker;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

import java.util.List;

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
      _logicalWarGame.addBase(new Position(x, y), buildingType, faction);
   }

   @Override
   public void validate() {
      // Ignored
   }
}
