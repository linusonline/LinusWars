package se.lolektivet.linus.linuswars.core.map;

import se.lolektivet.linus.linuswars.core.GamePredeployer;
import se.lolektivet.linus.linuswars.core.enums.Faction;

import java.util.List;

/**
 * Created by Linus on 2016-11-21.
 */
public class EmptyGameSetup implements GameSetup {
   @Override
   public void preDeploy(GamePredeployer predeployer) {

   }

   @Override
   public void preDeploy(GamePredeployer predeployer, List<Faction> factions) {

   }

   @Override
   public int getNrOfFactions() {
      return 0;
   }
}
