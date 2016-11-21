package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.graphicalgame.*;
import se.lolektivet.linus.linuswars.graphics.Sprites;
import se.lolektivet.linus.linuswars.logic.*;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.game.*;
import se.lolektivet.linus.linuswars.logic.maps.GameSetup;

import java.util.List;

/**
 * Created by Linus on 2015-12-27.
 */
public class GameFactory extends LogicalGameFactory {
   private Sprites _sprites;

   public GameFactory(Sprites sprites) {
      _sprites = sprites;
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

   public void deployToGraphicalGame(GraphicalWarGame graphicalWarGame, GameSetup gameSetup, List<Faction> factions) {
      GraphicalGamePreDeployer graphicalGamePreDeployer = new GraphicalGamePreDeployer(graphicalWarGame);
      graphicalGamePreDeployer.init(_sprites);
      gameSetup.preDeploy(graphicalGamePreDeployer, factions);
   }
}
