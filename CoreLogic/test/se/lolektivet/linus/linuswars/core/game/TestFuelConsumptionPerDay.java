package se.lolektivet.linus.linuswars.core.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.GamePredeployer;
import se.lolektivet.linus.linuswars.core.LinusWarsTest;
import se.lolektivet.linus.linuswars.core.LogicalGameFactory;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.maps.TestMap2;
import se.lolektivet.linus.linuswars.core.map.GameSetupAdapterForTest;

import static org.junit.Assert.assertEquals;

/**
 * Created by Linus on 2016-11-21.
 */
public class TestFuelConsumptionPerDay extends LinusWarsTest {

   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 1, 1);
         predeployer.addNewUnit(UnitType.MECH, Faction.BLUE_MOON, 2, 1);
         predeployer.addNewUnit(UnitType.RECON, Faction.BLUE_MOON, 3, 1);
         predeployer.addNewUnit(UnitType.TANK, Faction.BLUE_MOON, 4, 1);
         predeployer.addNewUnit(UnitType.MD_TANK, Faction.BLUE_MOON, 5, 1);
         predeployer.addNewUnit(UnitType.APC, Faction.BLUE_MOON, 6, 1);
         predeployer.addNewUnit(UnitType.ARTILLERY, Faction.BLUE_MOON, 7, 1);
         predeployer.addNewUnit(UnitType.ROCKETS, Faction.BLUE_MOON, 8, 1);
         predeployer.addNewUnit(UnitType.ANTI_AIR, Faction.BLUE_MOON, 9, 1);
         predeployer.addNewUnit(UnitType.MISSILES, Faction.BLUE_MOON, 10, 1);

         predeployer.addNewUnit(UnitType.LANDER, Faction.BLUE_MOON, 1, 2);
         predeployer.addNewUnit(UnitType.CRUISER, Faction.BLUE_MOON, 2, 2);
         predeployer.addNewUnit(UnitType.SUB, Faction.BLUE_MOON, 3, 2);
         predeployer.addNewUnit(UnitType.B_SHIP, Faction.BLUE_MOON, 4, 2);
         predeployer.addNewSubmergedSub(Faction.BLUE_MOON, 5, 2, 100, 60);

         predeployer.addNewUnit(UnitType.B_COPTER, Faction.BLUE_MOON, 1, 3);
         predeployer.addNewUnit(UnitType.T_COPTER, Faction.BLUE_MOON, 2, 3);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 3, 3);
         predeployer.addNewUnit(UnitType.BOMBER, Faction.BLUE_MOON, 4, 3);
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
   public void testInfantryConsumesNoFuelPerDay() {
      Position unitPosition = new Position(1, 1);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 0);
   }

   @Test
   public void testMechConsumesNoFuelPerDay() {
      Position unitPosition = new Position(2, 1);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 0);
   }

   @Test
   public void testReconConsumesNoFuelPerDay() {
      Position unitPosition = new Position(3, 1);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 0);
   }

   @Test
   public void testTankConsumesNoFuelPerDay() {
      Position unitPosition = new Position(4, 1);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 0);
   }

   @Test
   public void testMdTankConsumesNoFuelPerDay() {
      Position unitPosition = new Position(5, 1);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 0);
   }

   @Test
   public void testApcConsumesNoFuelPerDay() {
      Position unitPosition = new Position(6, 1);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 0);
   }

   @Test
   public void testArtilleryConsumesNoFuelPerDay() {
      Position unitPosition = new Position(7, 1);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 0);
   }

   @Test
   public void testRocketsConsumesNoFuelPerDay() {
      Position unitPosition = new Position(8, 1);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 0);
   }

   @Test
   public void testAntiAirConsumesNoFuelPerDay() {
      Position unitPosition = new Position(9, 1);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 0);
   }

   @Test
   public void testMissilesConsumesNoFuelPerDay() {
      Position unitPosition = new Position(10, 1);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 0);
   }


   @Test
   public void testLanderConsumesOneFuelPerDay() {
      Position unitPosition = (new Position(1, 2));
      testUnitConsumesXFuelOnTurnStart(unitPosition, 1);
   }

   @Test
   public void testCruiserConsumesOneFuelPerDay() {
      Position unitPosition = (new Position(2, 2));
      testUnitConsumesXFuelOnTurnStart(unitPosition, 1);
   }

   @Test
   public void testSubConsumesOneFuelPerDay() {
      Position unitPosition = (new Position(3, 2));
      testUnitConsumesXFuelOnTurnStart(unitPosition, 1);
   }

   @Test
   public void testBattleShipConsumesOneFuelPerDay() {
      Position unitPosition = (new Position(4, 2));
      testUnitConsumesXFuelOnTurnStart(unitPosition, 1);
   }

   @Test
   public void testSubmergedSubConsumesFiveFuelPerDay() {
      Position unitPosition = (new Position(5, 2));
      testUnitConsumesXFuelOnTurnStart(unitPosition, 5);
   }


   @Test
   public void testBattleCopterConsumesOneFuelPerDay() {
      Position unitPosition = new Position(1, 3);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 1);
   }

   @Test
   public void testTransportCopterConsumesTwoFuelPerDay() {
      Position unitPosition = new Position(2, 3);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 2);
   }

   @Test
   public void testFighterConsumesFiveFuelPerDay() {
      Position unitPosition = new Position(3, 3);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 5);
   }

   @Test
   public void testBomberConsumesFiveFuelPerDay() {
      Position unitPosition = new Position(4, 3);
      testUnitConsumesXFuelOnTurnStart(unitPosition, 5);
   }

   private void testUnitConsumesXFuelOnTurnStart(Position unitPosition, int fuelConsumed) {
      int fuelBefore = getFuelOfUnit(unitPosition);
      _gameMoves.endTurn();
      assertUnitIsInActiveFaction(unitPosition);
      assertEquals(fuelBefore - fuelConsumed, getFuelOfUnit(unitPosition));
   }

   private void assertUnitIsInActiveFaction(Position position) {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(position);
      assertEquals(_gameQueries.getFactionForUnit(unit), _gameQueries.getCurrentlyActiveFaction());
   }

   private int getFuelOfUnit(Position position) {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(position);
      return unit.getFuel();
   }
}
