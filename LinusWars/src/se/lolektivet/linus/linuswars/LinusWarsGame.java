package se.lolektivet.linus.linuswars;

import org.newdawn.slick.*;
import se.lolektivet.linus.linuswars.graphicalgame.*;
import se.lolektivet.linus.linuswars.graphics.*;
import se.lolektivet.linus.linuswars.logic.*;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.game.*;
import se.lolektivet.linus.linuswars.maps.GameSetup;
import se.lolektivet.linus.linuswars.maps.GameSetup1;
import se.lolektivet.linus.linuswars.maps.Map3;

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

   @Override
   public void init(GameContainer gc) throws SlickException {
      // System.out.println("Container: [ " + gc.getWidth() + "," + gc.getHeight() + "], Screen: [" + gc.getScreenWidth() + "," + gc.getScreenHeight() + "]");

      _allSprites = Sprites.createSprites();
      _mainFont = _allSprites.getMainFont();

      List<Faction> factions = new ArrayList<>(2);
      factions.add(Faction.BLUE_MOON);
      factions.add(Faction.ORANGE_STAR);

      Map3 map = new Map3();
      GameSetup gameSetup = new GameSetup1();

      startGame(map, gameSetup, factions);
   }

   private void startGame(WarMap warMap, GameSetup gameSetup, List<Faction> factions) {
      if (warMap.getNrOfFactions() != factions.size()) {
         throw new RuntimeException("This map needs " + warMap.getNrOfFactions() + " factions, but you supplied " + factions.size());
      }

      if (gameSetup.getNrOfFactions() != factions.size()) {
         throw new RuntimeException("This setup needs " + gameSetup.getNrOfFactions() + " factions, but you supplied " + factions.size());
      }

      LogicalWarMapImpl logicalWarMap = new LogicalWarMapImpl();
      MapMaker mapMaker = new LogicalMapMaker(logicalWarMap);
      warMap.create(mapMaker, factions);

      GraphicalWarMap graphicalWarMap = GraphicalWarMap.createFromLogicalWarMap(_allSprites, logicalWarMap);

      LogicalWarGameCreator gameCreator = new LogicalWarGameCreator();
      LogicalWarGame logicalWarGame = gameCreator.createGameFromMapAndFactions(logicalWarMap, factions);

      GraphicalWarGame graphicalWarGame = new GraphicalWarGame(logicalWarGame);
      graphicalWarGame.init(_allSprites);
      graphicalWarGame.setMap(graphicalWarMap);

      ScrollingTileViewImpl scrollingTileView = new ScrollingTileViewImpl();
      scrollingTileView.setVisibleRectSize(15, 10);
      _interactiveWarGame = new InteractiveWarGame(graphicalWarGame, logicalWarGame, scrollingTileView);
      _interactiveWarGame.init(_allSprites);

      // Deploy logical units
      gameSetup.preDeploy(new LogicalGamePredeployer(logicalWarGame, new LogicalUnitFactory()), factions);

      // Deploy graphical units
      GraphicalGamePreDeployer graphicalGamePreDeployer = new GraphicalGamePreDeployer();
      graphicalGamePreDeployer.init(_allSprites);
      graphicalGamePreDeployer.preDeploy(logicalWarGame, graphicalWarGame);

      logicalWarGame.callGameStart();
      graphicalWarGame.callGameStart();

      _gameState = new StateStarting(_interactiveWarGame, logicalWarGame, logicalWarGame);
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
      graphics.scale(HORIZONTAL_SCALE, VERTICAL_SCALE);
      _gameState.draw(gc, _mainFont, 0, 0);
   }

   private static final float VERTICAL_SCALE = 2.0f;
   private static final float HORIZONTAL_SCALE = 2.0f;
   private static final int BASE_HORIZONTAL_RESOLUTION = 240;
   private static final int BASE_VERTICAL_RESOLUTION = 160;

   public static void main(String[] args) {
      try {
         AppGameContainer appgc;
         appgc = new AppGameContainer(new LinusWarsGame("Linus Wars"));
         appgc.setDisplayMode((int)(BASE_HORIZONTAL_RESOLUTION*HORIZONTAL_SCALE), (int)(BASE_VERTICAL_RESOLUTION*VERTICAL_SCALE), false);
         appgc.setShowFPS(false);
         appgc.start();
      }
      catch (SlickException ex) {
         Logger.getLogger(LinusWarsGame.class.getName()).log(Level.SEVERE, null, ex);
      }
   }
}
