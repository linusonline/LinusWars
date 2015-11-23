package se.lolektivet.linus.linuswars;

import org.newdawn.slick.*;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalAndLogicalMapMaker;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalGamePreDeployer;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalWarGame;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalWarMap;
import se.lolektivet.linus.linuswars.graphics.*;
import se.lolektivet.linus.linuswars.logic.*;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.enums.Faction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Linus on 2014-09-18.
 */
public class LinusWarsGame extends BasicGame {
   private InteractiveWarGame _interactiveWarGame;
   private InteractiveGameState _gameState;
   private final Object _gameStateLock;
   private Sprites _allSprites;

   public LinusWarsGame(String gamename) {
      super(gamename);
      _gameStateLock = new Object();
   }

   private Font _mainFont;

   private LogicalWarGame createGameFromMapAndFactions(LogicalWarMap logicalWarMap, List<Faction> factions) {
      LogicalWarGame logicalWarGame = new LogicalWarGame(logicalWarMap, factions);
      List<Position> hqs = logicalWarMap.findHqs();
      int factionIndex = 0;
      for (Position hqPosition : hqs) {
         if (factionIndex >= factions.size()) {
            throw new RuntimeException("This map needs " + hqs.size() + " factions, but you only supplied " + factions.size());
         }
         logicalWarGame.setFactionForProperty(hqPosition, factions.get(factionIndex));
         factionIndex++;
      }
      if (factionIndex < factions.size()) {
         throw new RuntimeException("This map only accepts " + hqs.size() + " factions, but you supplied " + factions.size());
      }
      return logicalWarGame;
   }

   @Override
   public void init(GameContainer gc) throws SlickException {
      _allSprites = Sprites.initializeSprites();
      _mainFont = _allSprites.getMainFont();

      LogicalWarMap logicalWarMap = new LogicalWarMap();
      GraphicalWarMap graphicalWarMap = new GraphicalWarMap(logicalWarMap);
      MapMaker mapMaker = new GraphicalAndLogicalMapMaker(_allSprites, logicalWarMap, graphicalWarMap);
      Map1 map1 = new Map1(mapMaker);
      map1.create();

      List<Faction> factions = new ArrayList<>(2);
      factions.add(Faction.ORANGE_STAR);
      factions.add(Faction.BLUE_MOON);
      LogicalWarGame logicalWarGame = createGameFromMapAndFactions(logicalWarMap, factions);
      WarGameQueries warGameQueries = new WarGameQueriesImpl(logicalWarGame);
      logicalWarGame.setQueries(warGameQueries);

      GraphicalWarGame graphicalWarGame = new GraphicalWarGame(warGameQueries);
      graphicalWarGame.init(_allSprites);
      graphicalWarGame.setMap(graphicalWarMap);
      _interactiveWarGame = new InteractiveWarGame(graphicalWarGame, warGameQueries);
      _interactiveWarGame.init(_allSprites);

      LogicalGame1 logicalGamePredeployer = new LogicalGame1(logicalWarGame);
      logicalGamePredeployer.preDeploy();
      GraphicalGamePreDeployer graphicalGamePreDeployer = new GraphicalGamePreDeployer();
      graphicalGamePreDeployer.init(_allSprites);
      graphicalGamePreDeployer.preDeploy(logicalWarGame, graphicalWarGame);

      logicalWarGame.callGameStart();
      graphicalWarGame.callGameStart();

      _gameState = new StartingState(_interactiveWarGame, warGameQueries, logicalWarGame);
   }

   @Override
   public void keyPressed(int key, char c) {
      InteractiveGameState newGameState;
      synchronized (_gameStateLock) {
         switch (key) {
            case Input.KEY_W:
               newGameState = _gameState.handleDirection(Direction.UP);
               break;
            case Input.KEY_A:
               newGameState = _gameState.handleDirection(Direction.LEFT);
               break;
            case Input.KEY_S:
               newGameState = _gameState.handleDirection(Direction.DOWN);
               break;
            case Input.KEY_D:
               newGameState = _gameState.handleDirection(Direction.RIGHT);
               break;
            case Input.KEY_E:
               newGameState = _gameState.handleExecuteDown();
               break;
            case Input.KEY_Q:
               newGameState = _gameState.handleCancel();
               break;
            default:
               newGameState = _gameState;
         }
         _gameState = newGameState;
         _gameState.setSprites(_allSprites);
      }
      System.out.println(_gameState);
   }

   @Override
   public void keyReleased(int key, char c) {
      InteractiveGameState newGameState;
      synchronized (_gameStateLock) {
         switch (key) {
            case Input.KEY_E:
               newGameState = _gameState.handleExecuteUp();
               break;
            default:
               newGameState = _gameState;
         }
         _gameState = newGameState;
      }
   }

   @Override
   public void update(GameContainer gc, int i) throws SlickException {}

   @Override
   public void render(GameContainer gc, Graphics graphics) throws SlickException {
      // g.drawString("Howdy!", 10, 10);
      // _img.draw();
      graphics.scale(2.0f, 2.0f);
      _gameState.draw(gc, graphics, _mainFont, 0, 0);
   }

   public static void main(String[] args) {
      try {
         AppGameContainer appgc;
         appgc = new AppGameContainer(new LinusWarsGame("Linus Wars"));
         appgc.setDisplayMode(640, 480, false);
         appgc.setShowFPS(false);
         appgc.start();
      }
      catch (SlickException ex) {
         Logger.getLogger(LinusWarsGame.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
}
