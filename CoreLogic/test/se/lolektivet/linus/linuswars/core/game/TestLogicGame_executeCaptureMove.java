package se.lolektivet.linus.linuswars.core.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.*;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.maps.TestMap_executeCaptureMove;
import se.lolektivet.linus.linuswars.core.pathfinding.PathFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by Linus on 2016-11-29.
 */
public class TestLogicGame_executeCaptureMove extends LinusWarsTest {

   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 1, 0);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 2, 0, 80);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 3, 0);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 4, 0);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 5, 0);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 6, 0);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 7, 0);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 10, 1);
      }
   }

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      _theGame = new LogicalGameFactory().createLogicalWarGame(new TestMap_executeCaptureMove(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

      _gameMoves = _theGame;
      _gameQueries = _theGame;

      _theGame.callGameStart();
   }

   @Test
   public void testInfantryCanCaptureEnemyCity() {
      Position position = new Position(1, 0);
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(position);
      _gameMoves.executeCaptureMove(infantry, PathFactory.create(position));
      Building city = _gameQueries.getBuildingAtPosition(position);
      assertEquals(10, city.getCaptureStatus());
   }

   @Test
   public void testInjuredInfantryCapturesForLess() {
      Position position = new Position(2, 0);
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(position);
      _gameMoves.executeCaptureMove(infantry, PathFactory.create(position));
      Building city = _gameQueries.getBuildingAtPosition(position);
      assertEquals(12, city.getCaptureStatus());
   }

   @Test(expected = LogicException.class)
   public void testCannotCaptureOwnBuilding() {
      Position position = new Position(3, 0);
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(position);
      _gameMoves.executeCaptureMove(infantry, PathFactory.create(position));
   }

   @Test
   public void testInfantryCanCaptureCompletely() {
      Position position = new Position(4, 0);
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(position);
      _gameMoves.executeCaptureMove(infantry, PathFactory.create(position));
      _gameMoves.endTurn();
      _gameMoves.endTurn();
      _gameMoves.executeCaptureMove(infantry, PathFactory.create(position));
      Building city = _gameQueries.getBuildingAtPosition(position);
      assertEquals(20, city.getCaptureStatus());
      assertEquals(Faction.ORANGE_STAR, city.getFaction());
   }

   @Test
   public void testInfantryCanCaptureEnemyBase() {
      Position position = new Position(5, 0);
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(position);
      _gameMoves.executeCaptureMove(infantry, PathFactory.create(position));
      Building base = _gameQueries.getBuildingAtPosition(position);
      assertEquals(10, base.getCaptureStatus());
   }

   @Test
   public void testInfantryCanCaptureEnemyAirport() {
      Position position = new Position(6, 0);
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(position);
      _gameMoves.executeCaptureMove(infantry, PathFactory.create(position));
      Building airport = _gameQueries.getBuildingAtPosition(position);
      assertEquals(10, airport.getCaptureStatus());
   }

   @Test
   public void testInfantryCanCaptureEnemyPort() {
      Position position = new Position(7, 0);
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(position);
      _gameMoves.executeCaptureMove(infantry, PathFactory.create(position));
      Building airport = _gameQueries.getBuildingAtPosition(position);
      assertEquals(10, airport.getCaptureStatus());
   }

   @Test
   public void testInfantryCanCaptureEnemyHq() {
      Position position = new Position(10, 1);
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(position);
      _gameMoves.executeCaptureMove(infantry, PathFactory.create(position));
      Building hq = _gameQueries.getBuildingAtPosition(position);
      assertEquals(10, hq.getCaptureStatus());
   }
}
