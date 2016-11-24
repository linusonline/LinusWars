package se.lolektivet.linus.linuswars.logic.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.LogicalGameFactory;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.game.maps.TestMap2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Linus on 2016-11-24.
 */
public class TestResupply {
   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 0, 100, 0);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 1, 100, 0);
         predeployer.addNewUnit(UnitType.LANDER, Faction.BLUE_MOON, 0, 2);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 3);
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
      LogicalUnit infantry = _gameQueries.getUnitAtPosition(new Position(0, 1));
      assertNotEquals(_gameQueries.getFactionForUnit(infantry), _gameQueries.getCurrentlyActiveFaction());
      int fuelBefore = infantry.getFuel();
      assertNotEquals(infantry.getMaxFuel(), fuelBefore);
      _gameMoves.endTurn();
      assertEquals(_gameQueries.getFactionForUnit(infantry), _gameQueries.getCurrentlyActiveFaction());
      assertEquals(fuelBefore, infantry.getFuel());
   }
}
