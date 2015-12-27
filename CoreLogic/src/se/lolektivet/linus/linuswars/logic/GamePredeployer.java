package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2015-12-27.
 */
public interface GamePredeployer {
   void addNewUnit(UnitType type, Position position, Faction faction, int hpPercent);

   void addNewUnit(UnitType type, Position position, Faction faction);
}
