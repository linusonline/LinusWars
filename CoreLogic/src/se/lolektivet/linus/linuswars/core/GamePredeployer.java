package se.lolektivet.linus.linuswars.core;

import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;

/**
 * Created by Linus on 2015-12-27.
 */
public interface GamePredeployer {

   void addNewUnit(UnitType type, Faction faction, int x, int y);

   void addNewUnit(UnitType type, Faction faction, int x, int y, int hpPercent);

   void addNewUnit(UnitType type, Faction faction, int x, int y, int hpPercent, int fuel);

   void addNewSubmergedSub(Faction faction, int x, int y, int hpPercent, int fuel);
}
