package se.lolektivet.linus.linuswars;

import org.newdawn.slick.*;
import se.lolektivet.linus.linuswars.graphicalgame.*;
import se.lolektivet.linus.linuswars.graphics.*;
import se.lolektivet.linus.linuswars.logic.*;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.maps.GameSetup1;
import se.lolektivet.linus.linuswars.maps.Map2;

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
      if (hqs.size() != factions.size()) {
         throw new RuntimeException("This map needs " + hqs.size() + " factions, but you supplied " + factions.size());
      }
      int factionIndex = 0;
      for (Position hqPosition : hqs) {
         logicalWarGame.setFactionForProperty(hqPosition, factions.get(factionIndex));
         factionIndex++;
      }
      return logicalWarGame;
   }

   @Override
   public void init(GameContainer gc) throws SlickException {
      // System.out.println("Container: [ " + gc.getWidth() + "," + gc.getHeight() + "], Screen: [" + gc.getScreenWidth() + "," + gc.getScreenHeight() + "]");

      _allSprites = Sprites.initializeSprites();
      _mainFont = _allSprites.getMainFont();

      LogicalWarMap logicalWarMap = new LogicalWarMap();
      GraphicalWarMap graphicalWarMap = new GraphicalWarMap(logicalWarMap);
      MapMaker mapMaker = new GraphicalAndLogicalMapMaker(_allSprites, logicalWarMap, graphicalWarMap);
      Map2 map = new Map2(new RowMapMaker(mapMaker));
      map.create();
      mapMaker.validate();

      List<Faction> factions = new ArrayList<>(2);
      factions.add(Faction.ORANGE_STAR);
      factions.add(Faction.BLUE_MOON);
      LogicalWarGame logicalWarGame = createGameFromMapAndFactions(logicalWarMap, factions);
      WarGameQueries warGameQueries = new WarGameQueriesImpl(logicalWarGame);
      logicalWarGame.setQueries(warGameQueries);

      ScrollingTileViewImpl scrollingTileViewImpl = new ScrollingTileViewImpl();
      scrollingTileViewImpl.setVisibleRectSize(15, 10);
      GraphicalWarGame graphicalWarGame = new GraphicalWarGame(warGameQueries);
      graphicalWarGame.init(_allSprites);
      graphicalWarGame.setMap(graphicalWarMap);
      _interactiveWarGame = new InteractiveWarGame(graphicalWarGame, warGameQueries, scrollingTileViewImpl);
      _interactiveWarGame.init(_allSprites);

      // Deploy logical units
      new GameSetup1().preDeploy(new LogicalGamePredeployer(logicalWarGame, new LogicalUnitFactory()));

      // Deploy graphical units
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
