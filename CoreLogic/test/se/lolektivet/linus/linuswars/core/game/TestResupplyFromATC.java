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
import se.lolektivet.linus.linuswars.core.game.maps.TestMapResupplyFromAtc;

import static org.junit.Assert.*;

/**
 * Created by Linus on 2016-11-24.
 */
public class TestResupplyFromATC extends LinusWarsTest {
   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         // Top row, land units
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 1, 0, 100, 1);
         predeployer.addNewUnit(UnitType.MECH, Faction.BLUE_MOON, 2, 0, 100, 1);
         predeployer.addNewUnit(UnitType.ANTI_AIR, Faction.BLUE_MOON, 3, 0, 100, 1);
         predeployer.addNewUnit(UnitType.ARTILLERY, Faction.BLUE_MOON, 4, 0, 100, 1);
         predeployer.addNewUnit(UnitType.MD_TANK, Faction.BLUE_MOON, 5, 0, 100, 1);
         predeployer.addNewUnit(UnitType.MISSILES, Faction.BLUE_MOON, 6, 0, 100, 1);
         predeployer.addNewUnit(UnitType.TANK, Faction.BLUE_MOON, 7, 0, 100, 1);
         predeployer.addNewUnit(UnitType.RECON, Faction.BLUE_MOON, 8, 0, 100, 1);
         predeployer.addNewUnit(UnitType.ROCKETS, Faction.BLUE_MOON, 9, 0, 100, 1);

         // Mid row, APCs
         predeployer.addNewUnit(UnitType.APC, Faction.BLUE_MOON, 1, 1, 100, 1);
         predeployer.addNewUnit(UnitType.APC, Faction.BLUE_MOON, 2, 1, 100, 1);
         predeployer.addNewUnit(UnitType.APC, Faction.BLUE_MOON, 3, 1, 100, 1);
         predeployer.addNewUnit(UnitType.APC, Faction.BLUE_MOON, 4, 1, 100, 1);
         predeployer.addNewUnit(UnitType.APC, Faction.BLUE_MOON, 5, 1, 100, 1);
         predeployer.addNewUnit(UnitType.APC, Faction.BLUE_MOON, 6, 1, 100, 1);
         predeployer.addNewUnit(UnitType.APC, Faction.BLUE_MOON, 7, 1, 100, 1);
         predeployer.addNewUnit(UnitType.APC, Faction.BLUE_MOON, 8, 1, 100, 1);
         predeployer.addNewUnit(UnitType.APC, Faction.BLUE_MOON, 9, 1, 100, 1);

         // Bottom row, air units
         predeployer.addNewUnit(UnitType.T_COPTER, Faction.BLUE_MOON, 1, 2, 100, 1);
         predeployer.addNewUnit(UnitType.B_COPTER, Faction.BLUE_MOON, 2, 2, 100, 1);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 3, 2, 100, 1);
         predeployer.addNewUnit(UnitType.BOMBER, Faction.BLUE_MOON, 4, 2, 100, 1);

         // Bottom row, naval units
         predeployer.addNewUnit(UnitType.LANDER, Faction.BLUE_MOON, 5, 2, 100, 1);
         predeployer.addNewUnit(UnitType.CRUISER, Faction.BLUE_MOON, 6, 2, 100, 1);
         predeployer.addNewUnit(UnitType.SUB, Faction.BLUE_MOON, 7, 2, 100, 1);
         predeployer.addNewUnit(UnitType.B_SHIP, Faction.BLUE_MOON, 8, 2, 100, 1);
      }
   }

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      _theGame = new LogicalGameFactory().createLogicalWarGame(new TestMapResupplyFromAtc(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

      _gameMoves = _theGame;
      _gameQueries = _theGame;

      _theGame.callGameStart();
   }

   @Test
   public void testInfantryIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(1, 0));
   }

   @Test
   public void testMeckIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(2, 0));
   }

   @Test
   public void testAntiAirIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(3, 0));
   }

   @Test
   public void testArtilleryIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(4, 0));
   }

   @Test
   public void testMdTankIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(5, 0));
   }

   @Test
   public void testMissilesIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(6, 0));
   }

   @Test
   public void testTankIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(7, 0));
   }

   @Test
   public void testReconIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(8, 0));
   }

   @Test
   public void testRocketsIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(9, 0));
   }

   @Test
   public void testApcIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(1, 1));
   }

   @Test
   public void testTCopterIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(1, 2));
   }

   @Test
   public void testBCopterIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(2, 2));
   }

   @Test
   public void testFighterIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(3, 2));
   }

   @Test
   public void testBomberIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(4, 2));
   }

   @Test
   public void testLanderIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(5, 2));
   }

   @Test
   public void testCruiserIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(6, 2));
   }

   @Test
   public void testSubIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(7, 2));
   }

   @Test
   public void testBShipIsResupplied() {
      testUnitIsResuppliedOnTurnStart(new Position(8, 2));
   }

   private void testUnitIsResuppliedOnTurnStart(Position unitPosition) {
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(unitPosition);
      _gameMoves.endTurn();
      assertUnitIsInActiveFaction(unitPosition);
      assertEquals(infantry.getMaxFuel(), infantry.getFuel());
   }

   private void assertUnitIsInActiveFaction(Position position) {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(position);
      assertEquals(_gameQueries.getFactionForUnit(unit), _gameQueries.getCurrentlyActiveFaction());
   }
}
