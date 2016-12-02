package se.lolektivet.linus.linuswars.core.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.*;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.maps.TestMap_executeDeployMove;

import static org.junit.Assert.assertEquals;

/**
 * Created by Linus on 2016-11-28.
 */
public class TestLogicGame_executeDeployMove extends LinusWarsTest {
   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 4, 2);
      }
   }

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      _theGame = new LogicalGameFactory().createLogicalWarGame(new TestMap_executeDeployMove(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

      _gameMoves = _theGame;
      _gameQueries = _theGame;

      _theGame.callGameStart();
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployInfantryOnHq() {
      _gameMoves.executeDeployMove(new Position(0, 0), UnitType.INFANTRY);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployInfantryOnCity() {
      _gameMoves.executeDeployMove(new Position(1, 0), UnitType.INFANTRY);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployTCopterOnCity() {
      _gameMoves.executeDeployMove(new Position(2, 0), UnitType.T_COPTER);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployLanderOnCity() {
      _gameMoves.executeDeployMove(new Position(3, 0), UnitType.LANDER);
   }

   @Test
   public void testCanDeployInfantryOnBase() {
      assertUnitCanBeDeployed(new Position(4, 0), UnitType.INFANTRY);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployTCopterOnBase() {
      _gameMoves.executeDeployMove(new Position(5, 0), UnitType.T_COPTER);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployLanderOnBase() {
      _gameMoves.executeDeployMove(new Position(6, 0), UnitType.LANDER);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployInfantryOnAirport() {
      _gameMoves.executeDeployMove(new Position(7, 0), UnitType.INFANTRY);
   }

   @Test
   public void testCanDeployTCopterOnAirport() {
      assertUnitCanBeDeployed(new Position(8, 0), UnitType.T_COPTER);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployLanderOnAirport() {
      _gameMoves.executeDeployMove(new Position(9, 0), UnitType.LANDER);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployInfantryOnPort() {
      _gameMoves.executeDeployMove(new Position(1, 2), UnitType.INFANTRY);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployTCopterOnPort() {
      _gameMoves.executeDeployMove(new Position(2, 2), UnitType.T_COPTER);
   }

   @Test
   public void testCanDeployLanderOnPort() {
      assertUnitCanBeDeployed(new Position(3, 2), UnitType.LANDER);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployInfantryOnOccupiedBase() {
      _gameMoves.executeDeployMove(new Position(4, 2), UnitType.INFANTRY);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployInfantryOnUnfriendlyBase() {
      _gameMoves.executeDeployMove(new Position(5, 2), UnitType.INFANTRY);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployTooExpensiveUnit() {
      _gameMoves.executeDeployMove(new Position(6, 2), UnitType.MD_TANK);
   }

   @Test(expected = LogicException.class)
   public void testCannotDeployOnPlains() {
      _gameMoves.executeDeployMove(new Position(7, 2), UnitType.INFANTRY);
   }

   private void assertUnitCanBeDeployed(Position deployPosition, UnitType unitType) {
      _gameMoves.executeDeployMove(deployPosition, unitType);
      assertMoneySubtractedForUnit(unitType);
      LogicalUnit logicalUnit = _gameQueries.getUnitAtPosition(deployPosition);
      assertEquals(unitType, logicalUnit.getType());
   }

   private void assertMoneySubtractedForUnit(UnitType unitType) {
      assertEquals(TestMap_executeDeployMove.ORANGE_STAR_STARTING_FUNDS - unitCost(unitType),
            _gameQueries.getMoneyForFaction(Faction.ORANGE_STAR));
   }

   private int unitCost(UnitType unitType) {
      return new LogicalUnitFactory(new FuelLogic()).getUnitCost(unitType);
   }
}
