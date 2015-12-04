package se.lolektivet.linus.linuswars.logic.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.LogicException;
import se.lolektivet.linus.linuswars.logic.LogicalGamePredeployer;
import se.lolektivet.linus.linuswars.logic.LogicalMapMaker;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.pathfinding.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 2015-12-04.
 */
public class TestLogicGame_executeMove {

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      LogicalWarMap theMap = new LogicalWarMap();
      LogicalMapMaker mapMaker = new LogicalMapMaker(theMap);
      TestMap1 map = new TestMap1(mapMaker);
      map.create();

      List<Faction> factions = new ArrayList<>(2);
      factions.add(Faction.ORANGE_STAR);
      factions.add(Faction.BLUE_MOON);
      LogicalWarGameCreator gameCreator = new LogicalWarGameCreator();
      _theGame = gameCreator.createGameFromMapAndFactions(theMap, factions);

      new TestGameSetup1().preDeploy(new LogicalGamePredeployer(_theGame, new LogicalUnitFactory()));

      _gameMoves = _theGame;
      _gameQueries = _theGame;

      _theGame.callGameStart();
   }

   @Test
   public void testFirstFactionGoesFirst() {
      Assert.assertTrue(_gameQueries.getCurrentlyActiveFaction() == Faction.ORANGE_STAR);
   }

   @Test
   public void testAllowEndTurn() {
      _gameMoves.endTurn();
      Assert.assertTrue(_gameQueries.getCurrentlyActiveFaction() == Faction.BLUE_MOON);
   }

   @Test
   public void testAllowWaitMove() {
      LogicalUnit unit = _gameQueries.getAllUnitsInActiveFaction().iterator().next();
      Path path = new Path(_gameQueries.getPositionOfUnit(unit));
      _theGame.executeMove(unit, path);
   }

   @Test
   public void testMoveOneSquare() {
      LogicalUnit unit = _gameQueries.getAllUnitsInActiveFaction().iterator().next();
      Position origin = _gameQueries.getPositionOfUnit(unit);
      Path path = new Path(origin);
      Position destination = origin.getPositionAfterStep(Direction.RIGHT);
      path.addPoint(destination);
      _theGame.executeMove(unit, path);

      Assert.assertTrue(_gameQueries.getPositionOfUnit(unit).equals(destination));
   }

   @Test
   public void testMoveMaximum() {
      LogicalUnit unit = _gameQueries.getAllUnitsInActiveFaction().iterator().next();
      Position movingPosition = _gameQueries.getPositionOfUnit(unit);

      Path path = new Path(movingPosition);

      movingPosition = movingPosition.getPositionAfterStep(Direction.RIGHT);
      path.addPoint(movingPosition);

      movingPosition = movingPosition.getPositionAfterStep(Direction.RIGHT);
      path.addPoint(movingPosition);

      movingPosition = movingPosition.getPositionAfterStep(Direction.RIGHT);
      path.addPoint(movingPosition);

      _theGame.executeMove(unit, path);

      Assert.assertTrue(_gameQueries.getPositionOfUnit(unit).equals(movingPosition));
   }

   @Test (expected = LogicException.class)
   public void testMoveBeyondMaximum() {
      LogicalUnit unit = _gameQueries.getAllUnitsInActiveFaction().iterator().next();
      Position movingPosition = _gameQueries.getPositionOfUnit(unit);

      Path path = new Path(movingPosition);

      movingPosition = movingPosition.getPositionAfterStep(Direction.RIGHT);
      path.addPoint(movingPosition);

      movingPosition = movingPosition.getPositionAfterStep(Direction.RIGHT);
      path.addPoint(movingPosition);

      movingPosition = movingPosition.getPositionAfterStep(Direction.RIGHT);
      path.addPoint(movingPosition);

      movingPosition = movingPosition.getPositionAfterStep(Direction.DOWN);
      path.addPoint(movingPosition);

      _theGame.executeMove(unit, path);

      Assert.assertTrue(_gameQueries.getPositionOfUnit(unit).equals(movingPosition));
   }

}
