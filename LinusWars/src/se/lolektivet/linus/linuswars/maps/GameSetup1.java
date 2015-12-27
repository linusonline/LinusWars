package se.lolektivet.linus.linuswars.maps;

import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.Position;
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

      predeployer.addNewUnit(UnitType.MD_TANK, new Position(3, 1), factionOne);
      predeployer.addNewUnit(UnitType.APC, new Position(4, 2), factionOne);
      predeployer.addNewUnit(UnitType.ARTILLERY, new Position(5, 3), factionOne);
      predeployer.addNewUnit(UnitType.INFANTRY, new Position(6, 4), factionOne);
      predeployer.addNewUnit(UnitType.MECH, new Position(7, 3), factionOne, 50);
      predeployer.addNewUnit(UnitType.MECH, new Position(7, 4), factionOne, 30);

      Faction factionTwo = factions.get(1);

      predeployer.addNewUnit(UnitType.MD_TANK, new Position(8, 1), factionTwo);
      predeployer.addNewUnit(UnitType.APC, new Position(9, 2), factionTwo);
      predeployer.addNewUnit(UnitType.ARTILLERY, new Position(10, 3), factionTwo);
      predeployer.addNewUnit(UnitType.INFANTRY, new Position(11, 4), factionTwo);
      predeployer.addNewUnit(UnitType.MECH, new Position(12, 3), factionTwo);
   }
}
