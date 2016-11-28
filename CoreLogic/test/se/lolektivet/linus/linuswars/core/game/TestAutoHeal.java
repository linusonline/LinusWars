package se.lolektivet.linus.linuswars.core.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.GamePredeployer;
import se.lolektivet.linus.linuswars.core.LinusWarsTest;
import se.lolektivet.linus.linuswars.core.LogicalGameFactory;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.maps.TestMapAutoHeal;

import static org.junit.Assert.assertEquals;

/**
 * Created by Linus on 2016-02-07.
 */
public class TestAutoHeal extends LinusWarsTest {

   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 0, 70);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 1, 0, 70);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 2, 0, 70);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 3, 0, 70);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 4, 0, 70);

         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 0, 1, 70);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 1, 1, 70);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 2, 1, 70);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 3, 1, 70);
         predeployer.addNewUnit(UnitType.LANDER, Faction.BLUE_MOON, 4, 1, 70);

         predeployer.addNewUnit(UnitType.FIGHTER, Faction.ORANGE_STAR, 0, 3, 70);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 1, 3, 70);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 2, 3, 70);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 2);
      }
   }

   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      LogicalWarGame theGame = new LogicalGameFactory().createLogicalWarGame(new TestMapAutoHeal(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

      _gameMoves = theGame;
      _gameQueries = theGame;

      theGame.callGameStart();
   }

   @Test
   public void testInfantryIsHealedOnHQ() {
      assertUnitIsHealedOnNextTurn(new Position(0, 0));
   }

   @Test
   public void testInfantryIsHealedOnBase() {
      assertUnitIsHealedOnNextTurn(new Position(1, 0));
   }

   @Test
   public void testInfantryIsHealedOnCity() {
      assertUnitIsHealedOnNextTurn(new Position(2, 0));
   }

   @Test
   public void testInfantryIsNotHealedOnAirport() {
      assertUnitIsNotHealedOnNextTurn(new Position(3, 0));
   }

   @Test
   public void testInfantryIsNotHealedOnPort() {
      assertUnitIsNotHealedOnNextTurn(new Position(4, 0));
   }

   @Test
   public void testFighterIsNotHealedOnBase() {
      assertUnitIsNotHealedOnNextTurn(new Position(0, 1));
   }

   @Test
   public void testFighterIsNotHealedOnCity() {
      assertUnitIsNotHealedOnNextTurn(new Position(1, 1));
   }

   @Test
   public void testFighterIsHealedOnAirport() {
      assertUnitIsHealedOnNextTurn(new Position(2, 1));
   }

   @Test
   public void testFighterIsNotHealedOnPort() {
      assertUnitIsNotHealedOnNextTurn(new Position(3, 1));
   }

   @Test
   public void testLanderIsHealedOnPort() {
      assertUnitIsHealedOnNextTurn(new Position(4, 1));
   }

   @Test
   public void testUnitIsNotHealedOnUnfriendlyBuilding() {
      assertUnitIsNotHealedOnNextTurn(new Position(1, 3));
   }

   @Test
   public void testUnitIsNotHealedWhenNotOnBuilding() {
      assertUnitIsNotHealedOnNextTurn(new Position(2, 3));
   }

   @Test
   public void testFullyHealedUnitIsUnaffected() {
      assertUnitHasHpBeforeAndAfterEndOfTurn(new Position(0, 2), 10, 10);
   }

   @Test
   public void testFighterIsNotHealedOnHQ() {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(new Position(0, 3));
      assertEquals(_gameQueries.getCurrentlyActiveFaction(), _gameQueries.getFactionForUnit(unit));
      assertEquals(7, unit.getHp1To10());
   }

   private void assertUnitIsNotHealedOnNextTurn(Position unitPosition) {
      assertUnitHasHpAfterEndOfTurn(unitPosition, 7);
   }

   private void assertUnitIsHealedOnNextTurn(Position unitPosition) {
      assertUnitHasHpAfterEndOfTurn(unitPosition, 9);
   }

   private void assertUnitHasHpAfterEndOfTurn(Position unitPosition, int after) {
      assertUnitHasHpBeforeAndAfterEndOfTurn(unitPosition, 7, after);
   }

   private void assertUnitHasHpBeforeAndAfterEndOfTurn(Position unitPosition, int before, int after) {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(unitPosition);
      assertEquals(before, unit.getHp1To10());
      _gameMoves.endTurn();
      assertEquals(_gameQueries.getCurrentlyActiveFaction(), _gameQueries.getFactionForUnit(unit));
      assertEquals(after, unit.getHp1To10());
   }
}
