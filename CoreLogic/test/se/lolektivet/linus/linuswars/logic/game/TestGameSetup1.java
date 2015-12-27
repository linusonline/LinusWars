package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2014-09-22.
 */
public class TestGameSetup1 {

   public void preDeploy(GamePredeployer predeployer) {
      predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 0, 0);

      predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 3);
   }
}
