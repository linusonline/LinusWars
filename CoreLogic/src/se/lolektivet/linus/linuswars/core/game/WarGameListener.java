package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.Position;

/**
* Created by Linus on 2014-11-08.
*/
public interface WarGameListener {
   void unitWasDestroyed(LogicalUnit logicalUnit);
   void unitJoined(LogicalUnit logicalUnit);
   void transportedUnitWasDestroyed(LogicalUnit logicalUnit);
   void buildingWasCaptured(Building building);
   void unitDeployed(LogicalUnit logicalUnit, Position position);
}
