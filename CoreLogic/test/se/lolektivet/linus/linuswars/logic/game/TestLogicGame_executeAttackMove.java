package se.lolektivet.linus.linuswars.logic.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.GamePredeployer;
import se.lolektivet.linus.linuswars.logic.LogicalGameFactory;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.game.maps.TestMap4x4Plains;
import se.lolektivet.linus.linuswars.logic.pathfinding.Path;
import se.lolektivet.linus.linuswars.logic.pathfinding.PathFactory;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by Linus on 2015-12-04.
 */
public class TestLogicGame_executeAttackMove {

   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 0, 0);

         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 3);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 1, 3);
         predeployer.addNewUnit(UnitType.MD_TANK, Faction.BLUE_MOON, 2, 2);
         predeployer.addNewUnit(UnitType.FIGHTER, Faction.BLUE_MOON, 3, 1);
      }
   }

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      _theGame = new LogicalGameFactory().createLogicalWarGame(new TestMap4x4Plains(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

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
