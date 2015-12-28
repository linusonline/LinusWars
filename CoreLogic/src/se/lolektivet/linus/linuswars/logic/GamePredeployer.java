package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2015-12-27.
 */
public interface GamePredeployer {

   void addNewUnit(UnitType type, Faction faction, int x, int y);

   void addNewUnit(UnitType type, Faction faction, int x, int y, int hpPercent);
}
