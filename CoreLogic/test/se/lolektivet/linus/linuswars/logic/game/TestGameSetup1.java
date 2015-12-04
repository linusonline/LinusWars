package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.LogicalGamePredeployer;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2014-09-22.
 */
public class TestGameSetup1 {

   public void preDeploy(LogicalGamePredeployer predeployer) {
      predeployer.addNewUnit(UnitType.INFANTRY, new Position(0, 0), Faction.ORANGE_STAR);

      predeployer.addNewUnit(UnitType.INFANTRY, new Position(3, 3), Faction.BLUE_MOON);
   }
}
