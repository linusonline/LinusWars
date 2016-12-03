package se.lolektivet.linus.linuswars.core.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.GamePredeployer;
import se.lolektivet.linus.linuswars.core.LinusWarsTest;
import se.lolektivet.linus.linuswars.core.LogicalGameFactory;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.maps.TestMap3;
import se.lolektivet.linus.linuswars.core.map.GameSetupAdapterForTest;

import static org.junit.Assert.*;

/**
 * Created by Linus on 2016-11-24.
 */
public class TestResupplyOrDie extends LinusWarsTest {
   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         // On airports
         predeployer.addNewUnit(UnitType.T_COPTER, Faction.BLUE_MOON, 5, 0, 100, 1);
         predeployer.addNewUnit(UnitType.B_COPTER, Faction.BLUE_MOON, 6, 0, 100, 1);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 4, 1, 100, 1);
         predeployer.addNewUnit(UnitType.BOMBER, Faction.BLUE_MOON, 5, 1, 100, 1);

         // On ports
         predeployer.addNewUnit(UnitType.LANDER, Faction.BLUE_MOON, 0, 1, 100, 1);
         predeployer.addNewUnit(UnitType.CRUISER, Faction.BLUE_MOON, 1, 1, 100, 1);
         predeployer.addNewUnit(UnitType.SUB, Faction.BLUE_MOON, 2, 1, 100, 1);
         predeployer.addNewUnit(UnitType.B_SHIP, Faction.BLUE_MOON, 3, 1, 100, 1);

         // Not on building
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 6, 1, 100, 0);

         // Not on building
         predeployer.addNewUnit(UnitType.LANDER, Faction.BLUE_MOON, 0, 3, 100, 1);
         predeployer.addNewUnit(UnitType.CRUISER, Faction.BLUE_MOON, 1, 3, 100, 1);
         predeployer.addNewUnit(UnitType.SUB, Faction.BLUE_MOON, 2, 3, 100, 1);
         predeployer.addNewUnit(UnitType.B_SHIP, Faction.BLUE_MOON, 3, 3, 100, 1);
         predeployer.addNewUnit(UnitType.T_COPTER, Faction.BLUE_MOON, 4, 3, 100, 1);
         predeployer.addNewUnit(UnitType.B_COPTER, Faction.BLUE_MOON, 5, 3, 100, 1);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 6, 3, 100, 1);
         predeployer.addNewUnit(UnitType.BOMBER, Faction.BLUE_MOON, 7, 3, 100, 1);
      }
   }

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      _theGame = new LogicalGameFactory().createLogicalWarGame(new TestMap3(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

      _gameMoves = _theGame;
      _gameQueries = _theGame;

      _theGame.callGameStart();
   }

   @Test
   public void testTCopterIsResuppliedOnAirport() {
      testUnitIsResuppliedOnTurnStart(new Position(5, 0));
   }

   @Test
   public void testBCopterIsResuppliedOnAirport() {
      testUnitIsResuppliedOnTurnStart(new Position(6, 0));
   }

   @Test
   public void testFighterIsResuppliedOnAirport() {
      testUnitIsResuppliedOnTurnStart(new Position(4, 1));
   }

   @Test
   public void testBomberIsResuppliedOnAirport() {
      testUnitIsResuppliedOnTurnStart(new Position(5, 1));
   }

   @Test
   public void testLanderIsResuppliedOnPort() {
      testUnitIsResuppliedOnTurnStart(new Position(0, 1));
   }

   @Test
   public void testCruiserIsResuppliedOnPort() {
      testUnitIsResuppliedOnTurnStart(new Position(1, 1));
   }

   @Test
   public void testSubIsResuppliedOnPort() {
      testUnitIsResuppliedOnTurnStart(new Position(2, 1));
   }

   @Test
   public void testBShipIsResuppliedOnPort() {
      testUnitIsResuppliedOnTurnStart(new Position(3, 1));
   }

   @Test
   public void testLanderIsDestroyedWhenOutOfFuel() {
      testUnitIsDestroyedOnTurnStart(new Position(0, 3));
   }

   @Test
   public void testCruiserIsDestroyedWhenOutOfFuel() {
      testUnitIsDestroyedOnTurnStart(new Position(1, 3));
   }

   @Test
   public void testSubIsDestroyedWhenOutOfFuel() {
      testUnitIsDestroyedOnTurnStart(new Position(2, 3));
   }

   @Test
   public void testBShipIsDestroyedWhenOutOfFuel() {
      testUnitIsDestroyedOnTurnStart(new Position(3, 3));
   }

   @Test
   public void testTCopterIsDestroyedWhenOutOfFuel() {
      testUnitIsDestroyedOnTurnStart(new Position(4, 3));
   }

   @Test
   public void testBCopterIsDestroyedWhenOutOfFuel() {
      testUnitIsDestroyedOnTurnStart(new Position(5, 3));
   }

   @Test
   public void testFighterIsDestroyedWhenOutOfFuel() {
      testUnitIsDestroyedOnTurnStart(new Position(6, 3));
   }

   @Test
   public void testBomberIsDestroyedWhenOutOfFuel() {
      testUnitIsDestroyedOnTurnStart(new Position(7, 3));
   }

   @Test
   public void testInfantryIsNotDestroyedWhenOutOfFuel() {
      Position unitPosition = new Position(6, 1);
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(unitPosition);
      _gameMoves.endTurn();
      assertUnitIsInActiveFaction(unitPosition);
      assertEquals(0, infantry.getFuel());
      assertFalse(infantry.isUnitDestroyed());
   }

   private void testUnitIsResuppliedOnTurnStart(Position unitPosition) {
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(unitPosition);
      _gameMoves.endTurn();
      assertUnitIsInActiveFaction(unitPosition);
      assertEquals(infantry.getMaxFuel(), infantry.getFuel());
   }

   private void testUnitIsDestroyedOnTurnStart(Position unitPosition) {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(unitPosition);
      _gameMoves.endTurn();
      assertTrue(unit.isUnitDestroyed());
   }

   private void assertUnitIsInActiveFaction(Position position) {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(position);
      assertEquals(_gameQueries.getFactionForUnit(unit), _gameQueries.getCurrentlyActiveFaction());
   }
}
