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
public class GameFactory {
   private Sprites _sprites;

   public GameFactory(Sprites sprites) {
      _sprites = sprites;
   }

   public LogicalWarMap createLogicalMap(WarMap warMap, GameSetup gameSetup, List<Faction> factions) {
      if (warMap.getNrOfFactions() != factions.size()) {
         throw new RuntimeException("This map needs " + warMap.getNrOfFactions() + " factions, but you supplied " + factions.size());
      }

      if (gameSetup.getNrOfFactions() != factions.size()) {
         throw new RuntimeException("This setup needs " + gameSetup.getNrOfFactions() + " factions, but you supplied " + factions.size());
      }

      LogicalWarMapImpl logicalWarMap = new LogicalWarMapImpl(new ModuleBuildings());
      MapMaker mapMaker = new LogicalMapMaker(logicalWarMap);
      warMap.create(mapMaker, factions);
      return logicalWarMap;
   }

   public LogicalWarGame createLogicalWarGame(WarMap warMap, LogicalWarMap logicalWarMap, List<Faction> factions) {
      LogicalWarGame logicalWarGame = new LogicalWarGame(logicalWarMap, factions);
      MapMaker warGameCreator = new LogicalWarGameCreator(logicalWarGame);
      warMap.create(warGameCreator, factions);
      return logicalWarGame;
   }

   public GraphicalWarMap createGraphicalWarMap(WarMap warMap, LogicalWarMap logicalWarMap, List<Faction> factions) {
      GraphicalWarMap newWarMap = new GraphicalWarMap(logicalWarMap);
      GraphicalMapMaker mapMaker = new GraphicalMapMaker(_sprites, newWarMap);
      warMap.create(mapMaker, factions);
      return newWarMap;
   }

   public GraphicalWarGame createGraphicalWarGame(LogicalWarGame logicalWarGame, GraphicalWarMap graphicalWarMap) {
      GraphicalWarGame graphicalWarGame = new GraphicalWarGame(logicalWarGame, new GraphicalUnitFactory());
      graphicalWarGame.init(_sprites);
      graphicalWarGame.setMap(graphicalWarMap);
      return graphicalWarGame;
   }

   public InteractiveWarGame createInteractiveWarGame(LogicalWarGame logicalWarGame, GraphicalWarGame graphicalWarGame) {
      ScrollingTileViewImpl scrollingTileView = new ScrollingTileViewImpl();
      scrollingTileView.setVisibleRectSize(15, 10);
      InteractiveWarGame interactiveWarGame = new InteractiveWarGame(graphicalWarGame, logicalWarGame, scrollingTileView);
      interactiveWarGame.init(_sprites);
      return interactiveWarGame;
   }

   public void deployToLogicalGame(LogicalWarGame logicalWarGame, GameSetup gameSetup, List<Faction> factions) {
      gameSetup.preDeploy(new LogicalGamePredeployer(logicalWarGame, new LogicalUnitFactory()), factions);
   }

   public void deployToGraphicalGame(GraphicalWarGame graphicalWarGame, GameSetup gameSetup, List<Faction> factions) {
      GraphicalGamePreDeployer graphicalGamePreDeployer = new GraphicalGamePreDeployer(graphicalWarGame);
      graphicalGamePreDeployer.init(_sprites);
      gameSetup.preDeploy(graphicalGamePreDeployer, factions);
   }
}
