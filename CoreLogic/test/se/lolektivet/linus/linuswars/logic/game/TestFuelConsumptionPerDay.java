package se.lolektivet.linus.linuswars.logic.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.LogicalGameFactory;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Linus on 2016-11-21.
 */
public class TestFuelConsumptionPerDay {

   private class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 0, 100, 0);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 1);
         predeployer.addNewUnit(UnitType.LANDER, Faction.BLUE_MOON, 0, 2);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 3, 100, 0);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 1, 3);
      }
   }

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      _theGame = new LogicalGameFactory().createLogicalWarGame(new TestMap2(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

      _gameMoves = _theGame;
      _gameQueries = _theGame;

      _theGame.callGameStart();
   }

   @Test
   public void testLandUnitConsumesNoFuelPerDay() {
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(new Position(0, 1));
      assertNotEquals(_gameQueries.getFactionForUnit(infantry), _gameQueries.getCurrentlyActiveFaction());
      int fuel = infantry.getFuel();
      _gameMoves.endTurn();
      assertEquals(_gameQueries.getFactionForUnit(infantry), _gameQueries.getCurrentlyActiveFaction());
      assertEquals(fuel, infantry.getFuel());
   }

   @Test
   public void testNavalUnitConsumesOneFuelPerDay() {
      LogicalUnit lander = _gameQueries.getUnitAtPosition(new Position(0, 2));
      assertNotEquals(_gameQueries.getFactionForUnit(lander), _gameQueries.getCurrentlyActiveFaction());
      int fuel = lander.getFuel();
      _gameMoves.endTurn();
      assertEquals(_gameQueries.getFactionForUnit(lander), _gameQueries.getCurrentlyActiveFaction());
      assertEquals(fuel - 1, lander.getFuel());
   }

   @Test
   public void testFighterConsumesFiveFuelPerDay() {
      LogicalUnit fighter = _gameQueries.getUnitAtPosition(new Position(1, 3));
      assertNotEquals(_gameQueries.getFactionForUnit(fighter), _gameQueries.getCurrentlyActiveFaction());
      int fuel = fighter.getFuel();
      _gameMoves.endTurn();
      assertEquals(_gameQueries.getFactionForUnit(fighter), _gameQueries.getCurrentlyActiveFaction());
      assertEquals(fuel - 5, fighter.getFuel());
   }

   @Test
   public void testInfantryIsResuppliedOnOwnHQ() {
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(new Position(0, 0));
      assertNotEquals(_gameQueries.getFactionForUnit(infantry), _gameQueries.getCurrentlyActiveFaction());
      assertNotEquals(infantry.getMaxFuel(), infantry.getFuel());
      _gameMoves.endTurn();
      assertEquals(_gameQueries.getFactionForUnit(infantry), _gameQueries.getCurrentlyActiveFaction());
      assertEquals(infantry.getMaxFuel(), infantry.getFuel());
   }

   @Test
   public void testInfantryIsNotResuppliedOnOtherHQ() {
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(new Position(0, 3));
      assertNotEquals(_gameQueries.getFactionForUnit(infantry), _gameQueries.getCurrentlyActiveFaction());
      int fuelBefore = infantry.getFuel();
      assertNotEquals(infantry.getMaxFuel(), fuelBefore);
      _gameMoves.endTurn();
      assertEquals(_gameQueries.getFactionForUnit(infantry), _gameQueries.getCurrentlyActiveFaction());
      assertEquals(fuelBefore, infantry.getFuel());
   }
}
