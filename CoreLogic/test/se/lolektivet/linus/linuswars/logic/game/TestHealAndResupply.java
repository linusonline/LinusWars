package se.lolektivet.linus.linuswars.logic.game;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.*;
import se.lolektivet.linus.linuswars.logic.enums.Faction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 2016-02-07.
 */
public class TestHealAndResupply {

   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      LogicalWarGame theGame = new LogicalGameFactory().createLogicalWarGame(new TestMap1(), new TestGameSetup3(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

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
