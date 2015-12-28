package se.lolektivet.linus.linuswars.logic.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.LogicException;
import se.lolektivet.linus.linuswars.logic.LogicalGamePredeployer;
import se.lolektivet.linus.linuswars.logic.LogicalMapMaker;
import se.lolektivet.linus.linuswars.logic.WarMap;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.MovementType;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.pathfinding.InfiniteInteger;
import se.lolektivet.linus.linuswars.logic.pathfinding.Path;
import se.lolektivet.linus.linuswars.logic.pathfinding.PathFactory;

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
      LogicalWarMapImpl theMap = new LogicalWarMapImpl(new ModuleBases());
      LogicalMapMaker mapMaker = new LogicalMapMaker(theMap);
      WarMap map = new TestMap1();
      map.create(mapMaker);

      List<Faction> factions = new ArrayList<>(2);
      factions.add(Faction.ORANGE_STAR);
      factions.add(Faction.BLUE_MOON);

      _theGame = new LogicalWarGame(theMap, factions);
      LogicalWarGameCreator gameCreator = new LogicalWarGameCreator(_theGame);
      map.create(gameCreator, factions);

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
