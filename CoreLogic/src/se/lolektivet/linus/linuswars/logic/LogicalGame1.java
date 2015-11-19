package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2014-09-22.
 */
public class LogicalGame1 extends LogicalGamePredeployer {
   public LogicalGame1(WarGameSetup logicalWarGame) {
      super(logicalWarGame);
   }

   public void preDeploy() {
      addNewUnit(UnitType.MD_TANK, new Position(3, 1), Faction.ORANGE_STAR);
      addNewUnit(UnitType.APC, new Position(4, 2), Faction.ORANGE_STAR);
      addNewUnit(UnitType.ARTILLERY, new Position(5, 3), Faction.ORANGE_STAR);
      addNewUnit(UnitType.INFANTRY, new Position(6, 4), Faction.ORANGE_STAR);
      addNewUnit(UnitType.MECH, new Position(7, 3), Faction.ORANGE_STAR, 50);
      addNewUnit(UnitType.MECH, new Position(7, 4), Faction.ORANGE_STAR, 30);

      addNewUnit(UnitType.MD_TANK, new Position(8, 1), Faction.BLUE_MOON);
      addNewUnit(UnitType.APC, new Position(9, 2), Faction.BLUE_MOON);
      addNewUnit(UnitType.ARTILLERY, new Position(10, 3), Faction.BLUE_MOON);
      addNewUnit(UnitType.INFANTRY, new Position(11, 4), Faction.BLUE_MOON);
      addNewUnit(UnitType.MECH, new Position(12, 3), Faction.BLUE_MOON);
   }
}
