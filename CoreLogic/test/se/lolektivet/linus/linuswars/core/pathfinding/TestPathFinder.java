package se.lolektivet.linus.linuswars.core.pathfinding;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.*;
import se.lolektivet.linus.linuswars.core.enums.Direction;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.*;
import se.lolektivet.linus.linuswars.core.game.maps.TestMap3;
import se.lolektivet.linus.linuswars.core.game.maps.TestMapPathfinder;
import se.lolektivet.linus.linuswars.core.maps.Map1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Linus on 2014-09-24.
 */
public class TestPathFinder extends LinusWarsTest {

   // TODO: Test PathFinder class better!

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.MECH, Faction.ORANGE_STAR, 0, 0);
         predeployer.addNewUnit(UnitType.RECON, Faction.ORANGE_STAR, 0, 6);
      }
   }

   @Before
   public void setUp() {
      _theGame = new LogicalGameFactory().createLogicalWarGame(new TestMapPathfinder(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

      _gameMoves = _theGame;
      _gameQueries = _theGame;

      _theGame.callGameStart();
   }

   @Test (expected = LogicException.class)
   public void testExceptionOnMoveByDisallowedPathToReachablePosition() {
      LogicalUnit recon = _gameQueries.getUnitAtPosition(new Position(0, 6));
      Map<Position, PathWithCost> paths = getAllPathsForUnit(recon);
      assertTrue(paths.containsKey(new Position(4, 6)));
      Path forbiddenPath = PathFactory.create(new Position(0, 6), Direction.RIGHT, Direction.RIGHT, Direction.RIGHT, Direction.RIGHT);
      _gameMoves.executeMove(recon, forbiddenPath);
   }

   @Test
   public void testPathFinder() {
      LogicalUnit mech = _gameQueries.getUnitAtPosition(new Position(0, 0));
      Map<Position, PathWithCost> paths = getAllPathsForUnit(mech);
      assertPathExistsWithCorrectCost(paths, new Position(1, 0), 1, 1);
      assertPathExistsWithCorrectCost(paths, new Position(2, 0), 2, 2);
      assertPathExistsWithCorrectCost(paths, new Position(0, 1), 1, 1);
      assertPathExistsWithCorrectCost(paths, new Position(0, 2), 2, 2);
      assertPathExistsWithCorrectCost(paths, new Position(1, 1), 2, 2);
   }

   private Map<Position, PathWithCost> getAllPathsForUnit(LogicalUnit unit) {
      PathFinder pathFinder = new PathFinder(unit, _gameQueries, CostCalculator.createForUnitAndGame(unit, _gameQueries));
      return pathFinder.getOptimalPathsToAllReachablePoints(getCostLimitForUnit(unit));
   }

   private void assertPathExistsWithCorrectCost(Map<Position, PathWithCost> paths, Position finalPosition, int movementCost, int fuelCost) {
      PathWithCost pathWithCost = paths.get(finalPosition);
      assertNotNull(pathWithCost);
      assertEquals(finalPosition, pathWithCost.getFinalPosition());
      assertEquals(movementCost, pathWithCost.getTotalCost().getMovementCost().getInteger());
      assertEquals(fuelCost, pathWithCost.getTotalCost().getFuelCost().getInteger());
   }

   private Cost getCostLimitForUnit(LogicalUnit logicalUnit) {
      InfiniteInteger movementLimit = InfiniteInteger.create(logicalUnit.getBaseMovementRange());
      InfiniteInteger fuelLimit = InfiniteInteger.create(logicalUnit.getFuel());
      return new Cost(movementLimit, fuelLimit);
   }

   @Test
   public void testGetAdjacentTiles() {
      Collection<Position> adjacent = _gameQueries.getAdjacentPositions(new Position(0, 0));
      assertTrue(adjacent.contains(new Position(0, 1)));
      assertTrue(adjacent.contains(new Position(1, 0)));
      assertTrue(adjacent.size() == 2);
   }

   @Test
   public void testGetAdjacentTiles2() {
      int x = _gameQueries.getMapWidth() - 1;
      Collection<Position> adjacent = _gameQueries.getAdjacentPositions(new Position(x, 0));
      assertTrue(adjacent.contains(new Position(x - 1, 0)));
      assertTrue(adjacent.contains(new Position(x, 1)));
      assertTrue(adjacent.size() == 2);
   }

   @Test
   public void testGetAdjacentTiles3() {
      int y = _gameQueries.getMapHeight() - 1;
      Collection<Position> adjacent = _gameQueries.getAdjacentPositions(new Position(0, y));
      assertTrue(adjacent.contains(new Position(0, y - 1)));
      assertTrue(adjacent.contains(new Position(1, y)));
      assertTrue(adjacent.size() == 2);
   }

   @Test
   public void testGetAdjacentTiles4() {
      int y = _gameQueries.getMapHeight() - 1;
      int x = _gameQueries.getMapWidth() - 1;
      Collection<Position> adjacent = _gameQueries.getAdjacentPositions(new Position(x, y));
      assertTrue(adjacent.contains(new Position(x, y - 1)));
      assertTrue(adjacent.contains(new Position(x - 1, y)));
      assertTrue(adjacent.size() == 2);
   }

   @Test
   public void testGetAdjacentTiles5() {
      Collection<Position> adjacent = _gameQueries.getAdjacentPositions(new Position(1, 1));
      assertTrue(adjacent.contains(new Position(1, 0)));
      assertTrue(adjacent.contains(new Position(0, 1)));
      assertTrue(adjacent.contains(new Position(1, 2)));
      assertTrue(adjacent.contains(new Position(2, 1)));
      assertTrue(adjacent.size() == 4);
   }
}
