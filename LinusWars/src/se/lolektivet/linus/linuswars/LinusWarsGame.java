package se.lolektivet.linus.linuswars;

import org.newdawn.slick.*;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalAndLogicalMapMaker;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalGamePreDeployer;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalWarGame;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalWarMap;
import se.lolektivet.linus.linuswars.graphics.Buildings;
import se.lolektivet.linus.linuswars.graphics.HpNumbers;
import se.lolektivet.linus.linuswars.graphics.ResourceLoader;
import se.lolektivet.linus.linuswars.graphics.Terrain;
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
   private final ResourceLoader _resourceLoader;

   public LinusWarsGame(String gamename) {
      super(gamename);
      _resourceLoader = new ResourceLoader();
      _gameStateLock = new Object();
   }

   private Font _mainFont;

   @Override
   public void init(GameContainer gc) throws SlickException {
      LogicalWarMap logicalWarMap = new LogicalWarMap();
      GraphicalWarMap graphicalWarMap = new GraphicalWarMap(logicalWarMap);
      Terrain terrain = new Terrain();
      Buildings buildings = new Buildings();
      terrain.init(_resourceLoader);
      buildings.init(_resourceLoader);
      MapMaker mapMaker = new GraphicalAndLogicalMapMaker(logicalWarMap, graphicalWarMap, terrain, buildings);
      Map1 map1 = new Map1(mapMaker);
      map1.create();

      List<Faction> factions = new ArrayList<Faction>(2);
      factions.add(Faction.ORANGE_STAR);
      factions.add(Faction.BLUE_MOON);
      LogicalWarGame logicalWarGame = new LogicalWarGame(logicalWarMap, factions);
      List<Position> hqs = logicalWarMap.findHqs();
      logicalWarGame.setFactionForProperty(hqs.get(0), Faction.ORANGE_STAR);
      logicalWarGame.setFactionForProperty(hqs.get(1), Faction.BLUE_MOON);

      _mainFont = new SpriteSheetFont(new SpriteSheet(_resourceLoader.getFontSpriteSheet(), 7, 14), ' ');
      HpNumbers hpNumbers = new HpNumbers();
      hpNumbers.init(_resourceLoader);

      GraphicalWarGame graphicalWarGame = new GraphicalWarGame(hpNumbers, logicalWarGame);
      graphicalWarGame.setMap(graphicalWarMap);
      _interactiveWarGame = new InteractiveWarGame(graphicalWarGame, logicalWarGame);
      _interactiveWarGame.init(_resourceLoader);

      LogicalGame1 logicalGamePredeployer = new LogicalGame1(logicalWarGame);
      logicalGamePredeployer.preDeploy();
      GraphicalGamePreDeployer graphicalGamePreDeployer = new GraphicalGamePreDeployer();
      graphicalGamePreDeployer.init(_resourceLoader);
      graphicalGamePreDeployer.preDeploy(logicalWarGame, graphicalWarGame);

      logicalWarGame.callGameStart();
      graphicalWarGame.callGameStart();

      _gameState = new StartingState(_interactiveWarGame, logicalWarGame);
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
         _gameState.setResourceLoader(_resourceLoader);
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
      _gameState.draw(graphics, _mainFont, 0, 0);
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
