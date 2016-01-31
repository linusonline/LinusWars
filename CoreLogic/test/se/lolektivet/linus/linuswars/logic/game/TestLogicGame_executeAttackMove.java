package se.lolektivet.linus.linuswars.logic.game;

import static org.junit.Assert.*;
import org.junit.matchers.JUnitMatchers.*;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.core.CombinableMatcher;
import se.lolektivet.linus.linuswars.logic.LogicalGamePredeployer;
import se.lolektivet.linus.linuswars.logic.LogicalMapMaker;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.WarMap;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.pathfinding.Path;
import se.lolektivet.linus.linuswars.logic.pathfinding.PathFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 2015-12-04.
 */
public class TestLogicGame_executeAttackMove {

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

      new TestGameSetup2().preDeploy(new LogicalGamePredeployer(_theGame, new LogicalUnitFactory()));

      _gameMoves = _theGame;
      _gameQueries = _theGame;

      _theGame.callGameStart();
   }

   @Test
   public void testUnitStartsWithFullHealth() {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(new Position(0, 3));
      assertTrue(unit.getHp1To10() == 10);
   }

   @Test
   public void testInfantryCanAttackInfantry() {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(new Position(0, 0));
      Path path = PathFactory.create(new Position(0, 0), Direction.DOWN, Direction.DOWN);
      LogicalUnit attackedUnit = _gameQueries.getUnitAtPosition(new Position(0, 3));
      _theGame.executeAttackMove(unit, path, attackedUnit);
      assertTrue(attackedUnit.getHp1To10() < 10);
   }

   @Test
   public void testInfantryIsCounterAttackedByInfantry() {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(new Position(0, 0));
      Path path = PathFactory.create(new Position(0, 0), Direction.DOWN, Direction.DOWN);
      LogicalUnit attackedUnit = _gameQueries.getUnitAtPosition(new Position(0, 3));
      _theGame.executeAttackMove(unit, path, attackedUnit);
      assertTrue(unit.getHp1To10() < 10);
   }

   @Test
   public void testInfantryDamagesInfantryFor5to6HpOnPlains() {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(new Position(0, 0));
      Path path = PathFactory.create(new Position(0, 0), Direction.DOWN, Direction.DOWN, Direction.RIGHT);
      LogicalUnit attackedUnit = _gameQueries.getUnitAtPosition(new Position(1, 3));
      _theGame.executeAttackMove(unit, path, attackedUnit);
      assertEquals(unit.getType(), UnitType.INFANTRY);
      assertEquals(attackedUnit.getType(), UnitType.INFANTRY);
      assertThat(attackedUnit.getHp1To10(), either(equalTo(5)).or(equalTo(6)));
   }
}
