package se.lolektivet.linus.linuswars.core.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.GamePredeployer;
import se.lolektivet.linus.linuswars.core.LogicalGameFactory;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.maps.TestMap3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Linus on 2016-11-24.
 */
public class TestResupply {
   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 0, 100, 10);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 3, 100, 10);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 1, 0, 100, 10);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 2, 0, 100, 10);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 3, 0, 100, 10);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 4, 0, 100, 10);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 5, 0, 100, 10);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 6, 0, 100, 10);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 1, 100, 10);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 1, 1, 100, 10);
         predeployer.addNewUnit(UnitType.LANDER, Faction.BLUE_MOON, 2, 1, 100, 10);
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
   public void testInfantryIsResuppliedOnOwnHQ() {
      testUnitIsResuppliedOnTurnStart(new Position(0, 0));
   }

   @Test
   public void testInfantryIsNotResuppliedOnOtherHQ() {
      testUnitIsNotResuppliedOnTurnStart(new Position(0, 3));
   }

   @Test
   public void testInfantryIsResuppliedOnOwnCity() {
      testUnitIsResuppliedOnTurnStart(new Position(1, 0));
   }

   @Test
   public void testFighterIsNotResuppliedOnOwnCity() {
      testUnitIsNotResuppliedOnTurnStart(new Position(2, 0));
   }

   @Test
   public void testInfantryIsResuppliedOnOwnBase() {
      testUnitIsResuppliedOnTurnStart(new Position(3, 0));
   }

   @Test
   public void testFighterIsNotResuppliedOnOwnBase() {
      testUnitIsNotResuppliedOnTurnStart(new Position(4, 0));
   }

   @Test
   public void testInfantryIsNotResuppliedOnOwnAirport() {
      testUnitIsNotResuppliedOnTurnStart(new Position(5, 0));
   }

   @Test
   public void testFighterIsResuppliedOnOwnAirport() {
      testUnitIsResuppliedOnTurnStart(new Position(6, 0));
   }

   @Test
   public void testInfantryIsNotResuppliedOnOwnPort() {
      testUnitIsNotResuppliedOnTurnStart(new Position(0, 1));
   }

   @Test
   public void testFighterIsNotResuppliedOnOwnPort() {
      testUnitIsNotResuppliedOnTurnStart(new Position(1, 1));
   }

   @Test
   public void testLanderIsResuppliedOnOwnPort() {
      testUnitIsResuppliedOnTurnStart(new Position(2, 1));
   }

   private void testUnitIsResuppliedOnTurnStart(Position unitPosition) {
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(unitPosition);
      assertNotEquals(infantry.getMaxFuel(), infantry.getFuel());
      _gameMoves.endTurn();
      assertUnitIsInActiveFaction(unitPosition);
      assertEquals(infantry.getMaxFuel(), infantry.getFuel());
   }

   private void testUnitIsNotResuppliedOnTurnStart(Position unitPosition) {
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(unitPosition);
      assertNotEquals(infantry.getMaxFuel(), infantry.getFuel());
      _gameMoves.endTurn();
      assertUnitIsInActiveFaction(unitPosition);
      assertNotEquals(infantry.getMaxFuel(), infantry.getFuel());
   }

   private void assertUnitIsInActiveFaction(Position position) {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(position);
      assertEquals(_gameQueries.getFactionForUnit(unit), _gameQueries.getCurrentlyActiveFaction());
   }
}
