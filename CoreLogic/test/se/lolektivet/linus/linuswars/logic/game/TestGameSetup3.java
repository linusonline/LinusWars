package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2014-09-22.
 */
public class TestGameSetup3 {

   public void preDeploy(GamePredeployer predeployer) {
      predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 0, 70);
      predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 1);
      predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 2, 70);
      predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 3, 70);
   }
}
