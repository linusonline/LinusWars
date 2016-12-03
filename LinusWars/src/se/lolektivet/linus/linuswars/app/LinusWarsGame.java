package se.lolektivet.linus.linuswars.app;

import org.newdawn.slick.*;
import se.lolektivet.linus.linuswars.*;
import se.lolektivet.linus.linuswars.core.map.MapFile;
import se.lolektivet.linus.linuswars.core.map.WarMap;
import se.lolektivet.linus.linuswars.core.enums.Direction;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.game.LogicalWarGame;
import se.lolektivet.linus.linuswars.core.game.LogicalWarMap;
import se.lolektivet.linus.linuswars.core.map.EmptyGameSetup;
import se.lolektivet.linus.linuswars.core.map.GameSetup;
import se.lolektivet.linus.linuswars.examples.setups.GameSetup1;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalWarGame;
import se.lolektivet.linus.linuswars.graphicalgame.GraphicalWarMap;
import se.lolektivet.linus.linuswars.graphics.Sprites;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Linus on 2014-09-18.
 */
public class LinusWarsGame extends BasicGame {
   private static final Logger _logger = Logger.getLogger(LinusWarsGame.class.getName());

   private static final float VERTICAL_SCALE = 2.0f;
   private static final float HORIZONTAL_SCALE = 2.0f;
   private static final int BASE_HORIZONTAL_RESOLUTION = 240;
   private static final int BASE_VERTICAL_RESOLUTION = 160;

   private static AppGameContainer _gameContainer;

   private GameState _gameState;
   private final Object _gameStateLock;
   private Sprites _allSprites;
   private Font _mainFont;

   private LinusWarsGame(String gamename) {
      super(gamename);
      _gameStateLock = new Object();
   }

   public static void main(String[] args) {
      _logger.log(Level.INFO, "Starting Linus Wars");
      try {
         _gameContainer = new AppGameContainer(new LinusWarsGame("Linus Wars"));
         _gameContainer.setDisplayMode((int)(BASE_HORIZONTAL_RESOLUTION*HORIZONTAL_SCALE), (int)(BASE_VERTICAL_RESOLUTION*VERTICAL_SCALE), false);
         _gameContainer.setShowFPS(false);
         _gameContainer.start();
         _gameContainer.exit();
      }
      catch (SlickException ex) {
         _logger.log(Level.SEVERE, null, ex);
      }
   }

   @Override
   public void init(GameContainer gc) throws SlickException {
      try {
         initInternal(gc);
      } catch (RuntimeException e) {
         handleRuntimeException("Uncaught RuntimeException during init(" + gc + "):", e);
      }
   }

   private void initInternal(GameContainer gc) {
      _logger.config("Container: [ " + gc.getWidth() + "," + gc.getHeight() + "], Screen: [" + gc.getScreenWidth() + "," + gc.getScreenHeight() + "]");

      _allSprites = Sprites.createSprites();
      _mainFont = _allSprites.getMainFont();

      GameSetup gameSetup = new GameSetup1();

      try {
         startGameFromFile("maps/Point Stormy.lwmap", new EmptyGameSetup(), Arrays.asList(Faction.ORANGE_STAR, Faction.BLUE_MOON));
      } catch (IOException e) {
         throw new RuntimeException("Couldn't load map file!", e);
      }
   }

   private void startGameFromFile(String mapFileName, GameSetup gameSetup, List<Faction> factions) throws IOException {
      MapFile mapFile = new MapFile(mapFileName);
      mapFile.readFileFully();
      startGame(mapFile, gameSetup, factions);
   }

   private void startGame(WarMap warMap, GameSetup gameSetup, List<Faction> factions) {
      GameFactory gameFactory = new GameFactory(_allSprites);

      LogicalWarMap logicalWarMap = gameFactory.createLogicalMap(warMap, factions);

      LogicalWarGame logicalWarGame = gameFactory.createLogicalWarGame(warMap, logicalWarMap, factions);

      GraphicalWarMap graphicalWarMap = gameFactory.createGraphicalWarMap(warMap, logicalWarMap, factions);

      GraphicalWarGame graphicalWarGame = gameFactory.createGraphicalWarGame(logicalWarGame, graphicalWarMap);

      InteractiveWarGame interactiveWarGame = gameFactory.createInteractiveWarGame(logicalWarGame, graphicalWarGame);

      gameFactory.deployToLogicalGame(logicalWarGame, gameSetup, factions);

      gameFactory.deployToGraphicalGame(graphicalWarGame, gameSetup, factions);

      logicalWarGame.callGameStart();
      graphicalWarGame.callGameStart();

      GameStateContext context = new GameStateContext(interactiveWarGame, logicalWarGame, logicalWarGame);
      _gameState = new StateTurnTransition(context);
   }

   @Override
   public void keyPressed(int key, char c) {
      try {
         keyPressedInternal(key, c);
      } catch (RuntimeException e) {
         handleRuntimeException("Uncaught RuntimeException during keyPressed(" + key + ", " + c + "):", e);
      }
   }

   @Override
   public void keyReleased(int key, char c) {
      try {
         keyReleasedInternal(key, c);
      } catch (RuntimeException e) {
         handleRuntimeException("Uncaught RuntimeException during keyReleased(" + key + ", " + c + "):", e);
      }
   }

   @Override
   public void update(GameContainer gc, int i) throws SlickException {
      try {
         updateInternal(gc, i);
      } catch (RuntimeException e) {
         handleRuntimeException("Uncaught RuntimeException during update(" + gc + ", " + i + "):", e);
      }
   }

   @Override
   public void render(GameContainer gc, Graphics graphics) throws SlickException {
      try {
         renderInternal(gc, graphics);
      } catch (RuntimeException e) {
         handleRuntimeException("Uncaught RuntimeException during render(" + gc + ", " + graphics + "):", e);
      }
   }

   private void keyPressedInternal(int key, char c) {
      GameState newGameState;
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
            case Input.KEY_ESCAPE:
               newGameState = _gameState;
               exit();
               break;
            default:
               newGameState = _gameState;
         }
         _gameState = newGameState;
         _gameState.init(_allSprites);
      }
      _logger.fine(_gameState.toString());
   }

   private void keyReleasedInternal(int key, char c) {
      GameState newGameState;
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

   private void updateInternal(GameContainer gc, int i) throws SlickException {
      synchronized (_gameStateLock) {
         _gameState = _gameState.update();
      }
   }

   private void renderInternal(GameContainer gc, Graphics graphics) throws SlickException {
      graphics.scale(HORIZONTAL_SCALE, VERTICAL_SCALE);
      _gameState.draw(gc, _mainFont, 0, 0);
   }

   private void handleRuntimeException(String msg, RuntimeException e) {
      _logger.log(Level.SEVERE, msg, e);
      // TODO: Go to special game state, show blue screen or something.
      try {
         int read = System.in.read();
      } catch (IOException e1) {
         e1.printStackTrace();
      }
      exit();
   }

   private void exit() {
      _gameContainer.exit();
   }
}
