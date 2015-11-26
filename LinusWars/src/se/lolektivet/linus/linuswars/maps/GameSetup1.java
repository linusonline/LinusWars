package se.lolektivet.linus.linuswars.maps;

import se.lolektivet.linus.linuswars.logic.LogicalGamePredeployer;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2014-09-22.
 */
public class GameSetup1 {

   public void preDeploy(LogicalGamePredeployer predeployer) {
      predeployer.addNewUnit(UnitType.MD_TANK, new Position(3, 1), Faction.ORANGE_STAR);
      predeployer.addNewUnit(UnitType.APC, new Position(4, 2), Faction.ORANGE_STAR);
      predeployer.addNewUnit(UnitType.ARTILLERY, new Position(5, 3), Faction.ORANGE_STAR);
      predeployer.addNewUnit(UnitType.INFANTRY, new Position(6, 4), Faction.ORANGE_STAR);
      predeployer.addNewUnit(UnitType.MECH, new Position(7, 3), Faction.ORANGE_STAR, 50);
      predeployer.addNewUnit(UnitType.MECH, new Position(7, 4), Faction.ORANGE_STAR, 30);

      predeployer.addNewUnit(UnitType.MD_TANK, new Position(8, 1), Faction.BLUE_MOON);
      predeployer.addNewUnit(UnitType.APC, new Position(9, 2), Faction.BLUE_MOON);
      predeployer.addNewUnit(UnitType.ARTILLERY, new Position(10, 3), Faction.BLUE_MOON);
      predeployer.addNewUnit(UnitType.INFANTRY, new Position(11, 4), Faction.BLUE_MOON);
      predeployer.addNewUnit(UnitType.MECH, new Position(12, 3), Faction.BLUE_MOON);
   }
}
