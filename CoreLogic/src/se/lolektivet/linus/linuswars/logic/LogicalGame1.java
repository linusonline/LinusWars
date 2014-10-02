package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2014-09-22.
 */
public class LogicalGame1 extends LogicalGamePredeployer {
   public LogicalGame1(LogicalWarGame logicalWarGame) {
      super(logicalWarGame);
   }

   public void preDeploy() {
      addNewUnit(UnitType.MD_TANK, new Position(1, 1), Faction.ORANGE_STAR);
      addNewUnit(UnitType.APC, new Position(2, 2), Faction.ORANGE_STAR);
      addNewUnit(UnitType.ARTILLERY, new Position(3, 3), Faction.ORANGE_STAR);
      addNewUnit(UnitType.INFANTRY, new Position(4, 4), Faction.ORANGE_STAR);
      addNewUnit(UnitType.MECH, new Position(4, 3), Faction.ORANGE_STAR);

      addNewUnit(UnitType.MD_TANK, new Position(10, 1), Faction.BLUE_MOON);
      addNewUnit(UnitType.APC, new Position(11, 2), Faction.BLUE_MOON);
      addNewUnit(UnitType.ARTILLERY, new Position(12, 3), Faction.BLUE_MOON);
      addNewUnit(UnitType.INFANTRY, new Position(13, 4), Faction.BLUE_MOON);
      addNewUnit(UnitType.MECH, new Position(13, 3), Faction.BLUE_MOON);
   }
}
