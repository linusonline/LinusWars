package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;

/**
 * Created by Linus on 2014-10-03.
 */
public interface WarGameSetup {
   void setFactionForProperty(Position positionOfProperty, Faction faction);
   void addUnit(LogicalUnit unit, Position position, Faction faction);
   void callGameStart();
}
