package se.lolektivet.linus.linuswars.maps;

import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.enums.Faction;

import java.util.List;

/**
 * Created by Linus on 2015-12-21.
 */
public interface GameSetup {
   void preDeploy(GamePredeployer predeployer);
   void preDeploy(GamePredeployer predeployer, List<Faction> factions);
   int getNrOfFactions();
}
