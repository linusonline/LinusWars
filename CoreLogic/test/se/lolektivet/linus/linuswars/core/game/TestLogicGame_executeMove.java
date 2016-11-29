package se.lolektivet.linus.linuswars.core.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.*;
import se.lolektivet.linus.linuswars.core.enums.*;
import se.lolektivet.linus.linuswars.core.game.maps.TestMap4x4Plains;
import se.lolektivet.linus.linuswars.core.pathfinding.InfiniteInteger;
import se.lolektivet.linus.linuswars.core.pathfinding.Path;
import se.lolektivet.linus.linuswars.core.pathfinding.PathFactory;

/**
 * Created by Linus on 2015-12-04.
 */
public class TestLogicGame_executeMove extends LinusWarsTest {

   // TODO: Test Load moves
   // TODO: Test Unload moves
   // TODO: Test Supply moves
   // TODO: Test Join moves

   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 0, 0);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 3);
      }
   }

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      _theGame = new LogicalGameFactory().createLogicalWarGame(new TestMap4x4Plains(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

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
      Path path = PathFactory.create(_gameQueries.getPositionOfUnit(unit));
      _theGame.executeMove(unit, path);
   }

   @Test
   public void testMoveOneSquare() {
      LogicalUnit unit = _gameQueries.getAllUnitsInActiveFaction().iterator().next();
      Path path = PathFactory.create(_gameQueries.getPositionOfUnit(unit), Direction.RIGHT);

      _theGame.executeMove(unit, path);

      Assert.assertTrue(_gameQueries.getPositionOfUnit(unit).equals(path.getFinalPosition()));
   }

   @Test
   public void testMoveMaximum() {
      LogicalUnit unit = _gameQueries.getAllUnitsInActiveFaction().iterator().next();
      Path path = PathFactory.create(_gameQueries.getPositionOfUnit(unit),
            Direction.RIGHT, Direction.RIGHT, Direction.RIGHT);

      _theGame.executeMove(unit, path);

      Assert.assertTrue(_gameQueries.getPositionOfUnit(unit).equals(path.getFinalPosition()));
   }

   @Test (expected = LogicException.class)
   public void testMoveBeyondMaximum() {
      LogicalUnit unit = _gameQueries.getAllUnitsInActiveFaction().iterator().next();
      Path path = PathFactory.create(_gameQueries.getPositionOfUnit(unit),
            Direction.RIGHT, Direction.RIGHT, Direction.RIGHT, Direction.DOWN);

      _theGame.executeMove(unit, path);
   }

   @Test (expected = LogicException.class)
   public void testCannotMoveTwice() {
      LogicalUnit unit = _gameQueries.getAllUnitsInActiveFaction().iterator().next();

      _theGame.executeMove(unit, PathFactory.create(_gameQueries.getPositionOfUnit(unit), Direction.RIGHT));
      _theGame.executeMove(unit, PathFactory.create(_gameQueries.getPositionOfUnit(unit), Direction.RIGHT));
   }

   @Test
   public void testMovingExpendsFuel() {
      LogicalUnit unit = _gameQueries.getAllUnitsInActiveFaction().iterator().next();
      int fuelBefore = unit.getFuel();
      InfiniteInteger fuelCost = new FuelLogic().getFuelCostForMovementTypeOnTerrainType(MovementType.FOOT, TerrainType.PLAINS);

      _theGame.executeMove(unit, PathFactory.create(_gameQueries.getPositionOfUnit(unit), Direction.RIGHT));

      int fuelAfter = unit.getFuel();
      Assert.assertTrue(fuelBefore - fuelAfter == fuelCost.getInteger());
   }

   @Test (expected = LogicException.class)
   public void testCannotMoveToOtherEnemyUnit() {
      LogicalUnit unit = _gameQueries.getAllUnitsInActiveFaction().iterator().next();
      Path path = PathFactory.create(_gameQueries.getPositionOfUnit(unit),
            Direction.DOWN, Direction.DOWN, Direction.DOWN);

      _theGame.executeMove(unit, path);
   }

   @Test (expected = LogicException.class)
   public void testCannotMoveOutsideMap() {
      LogicalUnit unit = _gameQueries.getAllUnitsInActiveFaction().iterator().next();
      Path path = PathFactory.create(_gameQueries.getPositionOfUnit(unit),
            Direction.LEFT);

      _theGame.executeMove(unit, path);
   }

}
