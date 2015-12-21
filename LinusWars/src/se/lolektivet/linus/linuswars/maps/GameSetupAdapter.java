package se.lolektivet.linus.linuswars.maps;

import se.lolektivet.linus.linuswars.logic.DefaultFactions;
import se.lolektivet.linus.linuswars.logic.LogicalGamePredeployer;

/**
 * Created by Linus on 2015-12-21.
 */
public abstract class GameSetupAdapter implements GameSetup {

   @Override
   public void preDeploy(LogicalGamePredeployer predeployer) {
      preDeploy(predeployer, new DefaultFactions().getDefaultFactions());
   }
}
