package se.lolektivet.linus.linuswars.maps;

import se.lolektivet.linus.linuswars.logic.DefaultFactions;
import se.lolektivet.linus.linuswars.logic.GamePredeployer;

/**
 * Created by Linus on 2015-12-21.
 */
public abstract class GameSetupAdapter implements GameSetup {

   @Override
   public void preDeploy(GamePredeployer predeployer) {
      preDeploy(predeployer, new DefaultFactions().getDefaultFactions());
   }
}
