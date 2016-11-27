package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;

/**
 * Created by Linus on 2014-10-03.
 */
public interface WarGameSetup {
   void addBuilding(Position position, TerrainType terrainType, Faction faction);
   void addUnit(LogicalUnit unit, Position position, Faction faction);
   void callGameStart();
}
