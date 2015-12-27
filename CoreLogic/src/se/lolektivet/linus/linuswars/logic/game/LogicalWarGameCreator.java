package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.enums.Faction;

import java.util.List;

/**
 * Created by Linus on 2015-12-02.
 */
public class LogicalWarGameCreator {
   public LogicalWarGame createGameFromMapAndFactions(LogicalWarMapImpl logicalWarMap, List<Faction> factions) {

      // TODO: Separate init steps:
      // 1. create logical map
      // 2. replace factions in logical map
      // 3. build graphical map from logical map (may be difficult)
      // 4. build logical game from logical map
      // 5. build graphical game
      // 6. deploy units with SAME faction replacement as in step 2!

      ModuleBases basesModule = (ModuleBases)logicalWarMap.getBasesModule();

      LogicalWarGame logicalWarGame = new LogicalWarGame(logicalWarMap, factions);
      logicalWarGame.setBases(basesModule);
      return logicalWarGame;
   }
}
