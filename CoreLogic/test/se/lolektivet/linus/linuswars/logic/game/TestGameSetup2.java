package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2014-09-22.
 */
public class TestGameSetup2 {

   public void preDeploy(GamePredeployer predeployer) {
      predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 0, 0);

      predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 3);
      predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 1, 3);
      predeployer.addNewUnit(UnitType.MD_TANK, Faction.BLUE_MOON, 2, 2);
      predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 3, 1);
   }
}
