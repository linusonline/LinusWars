package se.lolektivet.linus.linuswars.core.map;

import se.lolektivet.linus.linuswars.core.GamePredeployer;

/**
 * Created by Linus on 2015-12-21.
 */
public abstract class GameSetupAdapter implements GameSetup {

   @Override
   public void preDeploy(GamePredeployer predeployer) {
      preDeploy(predeployer, new DefaultFactions().getDefaultFactions());
   }
}
