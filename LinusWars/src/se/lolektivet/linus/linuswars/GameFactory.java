package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.graphicalgame.*;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.LogicalGamePredeployer;
import se.lolektivet.linus.linuswars.logic.LogicalMapMaker;
import se.lolektivet.linus.linuswars.logic.MapMaker;
import se.lolektivet.linus.linuswars.logic.WarMap;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.game.*;
import se.lolektivet.linus.linuswars.maps.GameSetup;

import java.util.List;

/**
 * Created by Linus on 2015-12-27.
 */
class GameFactory {
   private Sprites _sprites;

   GameFactory(Sprites sprites) {
      _sprites = sprites;
   }

   LogicalWarMap createLogicalMap(WarMap warMap, GameSetup gameSetup, List<Faction> factions) {
      if (warMap.getNrOfFactions() != factions.size()) {
         throw new RuntimeException("This map needs " + warMap.getNrOfFactions() + " factions, but you supplied " + factions.size());
      }

      if (gameSetup.getNrOfFactions() != factions.size()) {
         throw new RuntimeException("This setup needs " + gameSetup.getNrOfFactions() + " factions, but you supplied " + factions.size());
      }

      LogicalWarMapImpl logicalWarMap = new LogicalWarMapImpl(new ModuleBases());
      MapMaker mapMaker = new LogicalMapMaker(logicalWarMap);
      warMap.create(mapMaker, factions);
      return logicalWarMap;
   }

   LogicalWarGame createLogicalWarGame(WarMap warMap, LogicalWarMap logicalWarMap, List<Faction> factions) {
      LogicalWarGame logicalWarGame = new LogicalWarGame(logicalWarMap, factions);
      MapMaker warGameCreator = new LogicalWarGameCreator(logicalWarGame);
      warMap.create(warGameCreator, factions);
      return logicalWarGame;
   }

   GraphicalWarMap createGraphicalWarMap(WarMap warMap, LogicalWarMap logicalWarMap, List<Faction> factions) {
      GraphicalWarMap newWarMap = new GraphicalWarMap(logicalWarMap);
      GraphicalMapMaker mapMaker = new GraphicalMapMaker(_sprites, newWarMap);
      warMap.create(mapMaker, factions);
      return newWarMap;
   }

   GraphicalWarGame createGraphicalWarGame(LogicalWarGame logicalWarGame, GraphicalWarMap graphicalWarMap) {
      GraphicalWarGame graphicalWarGame = new GraphicalWarGame(logicalWarGame);
      graphicalWarGame.init(_sprites);
      graphicalWarGame.setMap(graphicalWarMap);
      return graphicalWarGame;
   }

   InteractiveWarGame createInteractiveWarGame(LogicalWarGame logicalWarGame, GraphicalWarGame graphicalWarGame) {
      ScrollingTileViewImpl scrollingTileView = new ScrollingTileViewImpl();
      scrollingTileView.setVisibleRectSize(15, 10);
      InteractiveWarGame interactiveWarGame = new InteractiveWarGame(graphicalWarGame, logicalWarGame, scrollingTileView);
      interactiveWarGame.init(_sprites);
      return interactiveWarGame;
   }

   void deployToLogicalGame(LogicalWarGame logicalWarGame, GameSetup gameSetup, List<Faction> factions) {
      gameSetup.preDeploy(new LogicalGamePredeployer(logicalWarGame, new LogicalUnitFactory()), factions);
   }

   void deployToGraphicalGame(GraphicalWarGame graphicalWarGame, GameSetup gameSetup, List<Faction> factions) {
      GraphicalGamePreDeployer graphicalGamePreDeployer = new GraphicalGamePreDeployer(graphicalWarGame);
      graphicalGamePreDeployer.init(_sprites);
      gameSetup.preDeploy(graphicalGamePreDeployer, factions);
   }
}
