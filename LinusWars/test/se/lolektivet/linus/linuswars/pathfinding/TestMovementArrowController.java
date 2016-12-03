package se.lolektivet.linus.linuswars.pathfinding;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.InteractiveWarGame;
import se.lolektivet.linus.linuswars.MovementArrow;
import se.lolektivet.linus.linuswars.MovementArrowController;
import se.lolektivet.linus.linuswars.MovementArrowControllerImpl;
import se.lolektivet.linus.linuswars.core.*;
import se.lolektivet.linus.linuswars.core.enums.Direction;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.*;
import se.lolektivet.linus.linuswars.core.game.maps.TestMapPathfinder;
import se.lolektivet.linus.linuswars.core.map.GameSetupAdapterForTest;
import se.lolektivet.linus.linuswars.core.pathfinding.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by Linus on 2016-11-30.
 */
public class TestMovementArrowController extends LinusWarsTest {

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.RECON, Faction.BLUE_MOON, 0, 6);
      }
   }

   @Before
   public void setUp() {
      _theGame = new LogicalGameFactory().createLogicalWarGame(new TestMapPathfinder(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

      _gameMoves = _theGame;
      _gameQueries = _theGame;

      _theGame.callGameStart();
   }

   @Test
   public void testMacDisallowsIllegalPathToReachablePosition() {
      Path forbiddenPath = PathFactory.create(new Position(0, 6),
            Direction.RIGHT, Direction.RIGHT, Direction.RIGHT);
      assertMacDisallowsPath(forbiddenPath);
   }

   @Test
   public void testMacAllowsMaximalPath() {
      Path legalPath = PathFactory.create(new Position(0, 6),
            Direction.UP, Direction.UP, Direction.UP, Direction.UP);
      assertMacAllowsPathButDisallowsExtensionPath(legalPath, Direction.UP);
   }

   @Test
   public void testMacAllowsRecon7ButNot9() {
      Path legalPath = PathFactory.create(new Position(0, 6),
            Direction.DOWN, Direction.RIGHT, Direction.RIGHT, Direction.RIGHT, Direction.RIGHT, Direction.RIGHT);
      assertMacAllowsPathButDisallowsExtensionPath(legalPath, Direction.RIGHT);
   }

   @Test
   public void testReconCannotCrossMountain() {
      Path legalPath = PathFactory.create(new Position(0, 6),
            Direction.UP);
      assertMacAllowsPathButDisallowsExtensionPath(legalPath, Direction.RIGHT);
   }

   @Test
   public void testReconPositive() {
      assertMacAllowsAllPathsThePathFinderFinds(new Position(0, 6));
   }

   @Test
   public void testReconNegative() {
      assertMacDisallowsAllExtensionsToAllPathsThePathFinderFinds(new Position(0, 6));
   }

   private Set<Position> generateAllPositionsInSquare(Position topLeft, Position bottomRight) {
      int topLeftX = topLeft.getX();
      int topLeftY = topLeft.getY();
      int bottomRightX = bottomRight.getX();
      int bottomRightY = bottomRight.getY();
      Set<Position> allPositions = new HashSet<>((bottomRightX - topLeftX + 1) * (bottomRightY - topLeftY + 1));
      for (int y = topLeftY; y <= bottomRightY; y++) {
         for (int x = topLeftX; x <= bottomRightX; x++) {
            allPositions.add(new Position(x, y));
         }
      }
      return allPositions;
   }

   private void assertMacDisallowsAllExtensionsToAllPathsThePathFinderFinds(Position origin) {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(origin);
      Map<Position, PathWithCost> allowedPaths = getAllPathsForUnit(unit);
      Set<Position> unreachablePositions = generateAllPositionsInSquare(new Position(0, 0), new Position(7, 7));
      unreachablePositions.removeAll(allowedPaths.keySet());

      for (PathWithCost path : allowedPaths.values()) {
         assertMacDisallowsAllUnreachableExtensionsToPath(path.getPath(), unreachablePositions);
      }
   }

   private void assertMacAllowsAllPathsThePathFinderFinds(Position origin) {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(origin);
      Map<Position, PathWithCost> allowedPaths = getAllPathsForUnit(unit);
      for (PathWithCost path : allowedPaths.values()) {
         assertMacAllowsPath(path.getPath());
      }
   }

   private void assertMacDisallowsAllUnreachableExtensionsToPath(Path legalPath, Set<Position> unreachablePositions) {
      for (Position unreachablePosition : unreachablePositions) {
         Path illegalPath = new Path(legalPath);
         try {
            illegalPath.addPoint(unreachablePosition);
            assertMacAllowsPathButDisallowsExtensionPath(legalPath, illegalPath);
         } catch (Path.IllegalPathException ignored) {
         }
      }
   }

   private void assertMacDisallowsPath(Path illegalPath) {
      Path legalPath = getPathMinusLastStep(illegalPath);
      assertMacAllowsPathButDisallowsExtensionPath(legalPath, illegalPath);
   }

   private void assertMacAllowsPathButDisallowsExtensionPath(Path legalPath, Direction extension) {
      assertMacAllowsPathButDisallowsExtensionPath(legalPath, legalPath.getFinalPosition().getPositionAfterStep(extension));
   }
   private void assertMacAllowsPathButDisallowsExtensionPath(Path legalPath, Position extensionPosition) {
      Path illegalPath = new Path(legalPath);
      illegalPath.addPoint(extensionPosition);
      assertMacAllowsPathButDisallowsExtensionPath(legalPath, illegalPath);
   }

   private void assertMacAllowsPathButDisallowsExtensionPath(Path legalPath, Path illegalExtension) {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(illegalExtension.getOrigin());
      assertFalse(_gameQueries.isPathAllowedForUnit(illegalExtension, unit));

      assertTrue(_gameQueries.isPathAllowedForUnit(legalPath, unit));
      assertMacAllowsPath(legalPath);

      MovementArrow movementArrow = new MovementArrow(legalPath);
      MovementArrowController arrowController = new MovementArrowControllerImpl(movementArrow, unit, _gameQueries, mock(InteractiveWarGame.class));
      assertFalse(arrowController.canExtendMovementArrowToPosition(illegalExtension.getFinalPosition()));
   }

   private void assertMacAllowsPath(Path legalPath) {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(legalPath.getOrigin());
      MovementArrow movementArrow = new MovementArrow(legalPath.getOrigin());
      MovementArrowController arrowController = new MovementArrowControllerImpl(movementArrow, unit, _gameQueries, mock(InteractiveWarGame.class));
      for (Position nextPosition : legalPath.getPositionList()) {
         assertTrue(arrowController.canExtendMovementArrowToPosition(nextPosition));
         movementArrow.addPoint(nextPosition);
      }
   }

   private Path getPathMinusLastStep(Path path) {
      Path newPath = new Path(path, true);
      newPath.addPoint(path.getBacktrackPosition());
      return newPath;
   }

   private Map<Position, PathWithCost> getAllPathsForUnit(LogicalUnit unit) {
      PathFinder pathFinder = new PathFinder(unit, _gameQueries, CostCalculator.createForUnitAndGame(unit, _gameQueries));
      return pathFinder.getOptimalPathsToAllReachablePoints(getCostLimitForUnit(unit));
   }

   private Cost getCostLimitForUnit(LogicalUnit logicalUnit) {
      InfiniteInteger movementLimit = InfiniteInteger.create(logicalUnit.getBaseMovementRange());
      InfiniteInteger fuelLimit = InfiniteInteger.create(logicalUnit.getFuel());
      return new Cost(movementLimit, fuelLimit);
   }
}
