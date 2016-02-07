package se.lolektivet.linus.linuswars.logic.game;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.LogicalGamePredeployer;
import se.lolektivet.linus.linuswars.logic.LogicalMapMaker;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.WarMap;
import se.lolektivet.linus.linuswars.logic.enums.Faction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 2016-02-07.
 */
public class TestHealAndResupply {

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      LogicalWarMapImpl theMap = new LogicalWarMapImpl(new ModuleBuildings());
      LogicalMapMaker mapMaker = new LogicalMapMaker(theMap);
      WarMap map = new TestMap1();
      map.create(mapMaker);

      List<Faction> factions = new ArrayList<>(2);
      factions.add(Faction.ORANGE_STAR);
      factions.add(Faction.BLUE_MOON);

      _theGame = new LogicalWarGame(theMap, factions);
      LogicalWarGameCreator gameCreator = new LogicalWarGameCreator(_theGame);
      map.create(gameCreator, factions);

      new TestGameSetup3().preDeploy(new LogicalGamePredeployer(_theGame, new LogicalUnitFactory()));

      _gameMoves = _theGame;
      _gameQueries = _theGame;

      _theGame.callGameStart();
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
