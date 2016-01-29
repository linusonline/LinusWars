package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;

/**
 * Created by Linus on 2014-10-03.
 */
public interface WarGameSetup {
   void addBuilding(Position position, TerrainType terrainType, Faction faction);
   void addUnit(LogicalUnit unit, Position position, Faction faction);
   void callGameStart();
}
