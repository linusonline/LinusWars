package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.maps.GameSetup;
import se.lolektivet.linus.linuswars.logic.maps.GameSetupAdapter;

import java.util.List;

/**
 * Created by Linus on 2014-09-22.
 */
public class TestGameSetup2 extends GameSetupAdapter {

   @Override
   public void preDeploy(GamePredeployer predeployer, List<Faction> factions) {
      Faction factionOne = factions.get(0);
      predeployer.addNewUnit(UnitType.INFANTRY, factionOne, 0, 0);

      Faction factionTwo = factions.get(1);
      predeployer.addNewUnit(UnitType.INFANTRY, factionTwo, 0, 3);
      predeployer.addNewUnit(UnitType.INFANTRY, factionTwo, 1, 3);
      predeployer.addNewUnit(UnitType.MD_TANK, factionTwo, 2, 2);
      predeployer.addNewUnit(UnitType.FIGHTER, factionTwo, 3, 1);

   }

   @Override
   public int getNrOfFactions() {
      return 2;
   }
}
