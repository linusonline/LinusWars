package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.maps.GameSetupAdapter;

import java.util.List;

/**
 * Created by Linus on 2014-09-22.
 */
public class TestGameSetup3 extends GameSetupAdapter {

   @Override
   public void preDeploy(GamePredeployer predeployer, List<Faction> factions) {
      Faction factionOne = factions.get(1);
      predeployer.addNewUnit(UnitType.INFANTRY, factionOne, 0, 0, 70);
      predeployer.addNewUnit(UnitType.INFANTRY, factionOne, 0, 1);
      predeployer.addNewUnit(UnitType.INFANTRY, factionOne, 0, 2, 70);
      predeployer.addNewUnit(UnitType.INFANTRY, factionOne, 0, 3, 70);
   }

   @Override
   public int getNrOfFactions() {
      return 2;
   }
}
