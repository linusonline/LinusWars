package se.lolektivet.linus.linuswars.logic.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.LogicalGameFactory;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.game.maps.TestMap4x4Plains;

import static org.junit.Assert.assertTrue;

/**
 * Created by Linus on 2016-02-07.
 */
public class TestAutoHeal {

   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 0, 70);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 1);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 2, 70);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 3, 70);
      }
   }

   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      LogicalWarGame theGame = new LogicalGameFactory().createLogicalWarGame(new TestMap4x4Plains(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

      _gameMoves = theGame;
      _gameQueries = theGame;

      theGame.callGameStart();
   }

   @Test
   public void testUnitIsHealedWhenOnFriendlyBuildingOnTurnStart() {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(new Position(0, 0));
      assertTrue(_gameQueries.getFactionForUnit(unit) != _gameQueries.getCurrentlyActiveFaction());
      assertTrue(unit.getHp1To10() == 7);
      _gameMoves.endTurn();
      assertTrue(unit.getHp1To10() == 9);
   }

   @Test
   public void testUnitIsNotHealedWhenNotOnBuildingOnTurnStart() {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(new Position(0, 2));
      assertTrue(_gameQueries.getFactionForUnit(unit) != _gameQueries.getCurrentlyActiveFaction());
      assertTrue(unit.getHp1To10() == 7);
      _gameMoves.endTurn();
      assertTrue(unit.getHp1To10() == 7);
   }

   @Test
   public void testUnitIsNotHealedWhenOnUnfriendlyBuildingOnTurnStart() {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(new Position(0, 3));
      assertTrue(_gameQueries.getFactionForUnit(unit) != _gameQueries.getCurrentlyActiveFaction());
      assertTrue(unit.getHp1To10() == 7);
      _gameMoves.endTurn();
      assertTrue(unit.getHp1To10() == 7);
   }

   @Test
   public void testFullyHealedUnitIsUnaffected() {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(new Position(0, 1));
      assertTrue(_gameQueries.getFactionForUnit(unit) != _gameQueries.getCurrentlyActiveFaction());
      assertTrue(unit.getHp1To10() == 10);
      _gameMoves.endTurn();
      assertTrue(unit.getHp1To10() == 10);
   }

}
