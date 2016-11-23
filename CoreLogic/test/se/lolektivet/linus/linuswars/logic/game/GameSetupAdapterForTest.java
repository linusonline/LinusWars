package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.DefaultFactions;
import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.maps.GameSetup;

import java.util.List;

/**
 * Created by Linus on 2016-11-22.
 */
public class GameSetupAdapterForTest implements GameSetup {
   @Override
   public void preDeploy(GamePredeployer predeployer) {
      preDeploy(predeployer, new DefaultFactions().getDefaultFactions());
   }

   @Override
   public void preDeploy(GamePredeployer predeployer, List<Faction> factions) {
      preDeploy(predeployer);
   }

   @Override
   public int getNrOfFactions() {
      return 0;
   }
}
