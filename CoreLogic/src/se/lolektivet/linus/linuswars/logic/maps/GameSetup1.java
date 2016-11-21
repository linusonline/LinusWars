package se.lolektivet.linus.linuswars.logic.maps;

import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

import java.util.List;

/**
 * Created by Linus on 2014-09-22.
 */
public class GameSetup1 extends GameSetupAdapter {

   @Override
   public int getNrOfFactions() {
      return 2;
   }

   @Override
   public void preDeploy(GamePredeployer predeployer, List<Faction> factions) {
      Faction factionOne = factions.get(0);

      predeployer.addNewUnit(UnitType.MD_TANK, factionOne, 3, 1);
      predeployer.addNewUnit(UnitType.APC, factionOne, 4, 2);
      predeployer.addNewUnit(UnitType.ARTILLERY, factionOne, 5, 3);
      predeployer.addNewUnit(UnitType.INFANTRY, factionOne, 6, 4);
      predeployer.addNewUnit(UnitType.MECH, factionOne, 7, 3, 50);
      predeployer.addNewUnit(UnitType.MECH, factionOne, 7, 4, 30);

      Faction factionTwo = factions.get(1);

      predeployer.addNewUnit(UnitType.MD_TANK, factionTwo, 8, 1);
      predeployer.addNewUnit(UnitType.APC, factionTwo, 9, 2);
      predeployer.addNewUnit(UnitType.ARTILLERY, factionTwo, 10, 3);
      predeployer.addNewUnit(UnitType.INFANTRY, factionTwo, 11, 4);
      predeployer.addNewUnit(UnitType.MECH, factionTwo, 12, 3);
   }
}
