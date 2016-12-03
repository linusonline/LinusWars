package se.lolektivet.linus.linuswars.core.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.*;
import se.lolektivet.linus.linuswars.core.enums.Direction;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.map.GameSetupAdapterForTest;
import se.lolektivet.linus.linuswars.core.map.StringMap;
import se.lolektivet.linus.linuswars.core.pathfinding.Path;
import se.lolektivet.linus.linuswars.core.pathfinding.PathFactory;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by Linus on 2015-12-04.
 */
public class TestLogicGame_executeAttackMove extends LinusWarsTest {

   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.ORANGE_STAR, 0, 0);
         predeployer.addNewUnit(UnitType.MD_TANK, Faction.ORANGE_STAR, 1, 0);

         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 2);
         predeployer.addNewUnit(UnitType.ARTILLERY, Faction.BLUE_MOON, 1, 3, 10);
      }
   }

   private static final String _theMapString = "" +
         "QbLLL\n" +
         "L LLL\n" +
         "L LLL\n" +
         "QoLLL\n";

   private static final StringMap theMap = new StringMap(2, _theMapString);

   private LogicalWarGame _theGame;
   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      _theGame = new LogicalGameFactory().createLogicalWarGame(theMap, new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

      _gameMoves = _theGame;
      _gameQueries = _theGame;

      _theGame.callGameStart();
   }

   @Test
   public void testUnitStartsWithFullHealth() {
      LogicalUnit unit = _gameQueries.getUnitAtPosition(new Position(0, 0));
      assertTrue(unit.getHp1To10() == 10);
   }

   @Test
   public void testInfantryCanAttackInfantry() {
      Path path = PathFactory.create(new Position(0, 0), Direction.DOWN);
      LogicalUnit target = _gameQueries.getUnitAtPosition(new Position(0, 2));
      doAttack(path, target);
      assertTrue(target.getHp1To10() < 10);
   }

   @Test
   public void testInfantryIsCounterAttackedByInfantry() {
      Path path = PathFactory.create(new Position(0, 0), Direction.DOWN);
      LogicalUnit attackedUnit = _gameQueries.getUnitAtPosition(new Position(0, 2));
      LogicalUnit attacker = doAttack(path, attackedUnit);
      assertTrue(attacker.getHp1To10() < 10);
   }

   @Test
   public void testInfantryDamagesInfantryFor5to6HpOnPlains() {
      Path path = PathFactory.create(new Position(0, 0), Direction.DOWN);
      LogicalUnit target = _gameQueries.getUnitAtPosition(new Position(0, 2));
      LogicalUnit attacker = doAttack(path, target);

      assertEquals(attacker.getType(), UnitType.INFANTRY);
      assertEquals(target.getType(), UnitType.INFANTRY);
      assertThat(target.getHp1To10(), either(equalTo(5)).or(equalTo(6)));
   }

   @Test(expected = LogicException.class)
   public void testInfantryCannotAttackWhenOutOfReach() {
      Path path = PathFactory.create(new Position(0, 0));
      LogicalUnit target = _gameQueries.getUnitAtPosition(new Position(0, 2));
      doAttack(path, target);
   }

   @Test
   public void testMdTankDestroysDamagedArtilleryOnPlains() {
      Path path = PathFactory.create(new Position(1, 0), Direction.DOWN, Direction.DOWN);
      LogicalUnit target = _gameQueries.getUnitAtPosition(new Position(1, 3));
      doAttack(path, target);
      assertTrue(target.isUnitDestroyed());
   }

   private LogicalUnit doAttack(Path path, LogicalUnit target) {
      Position origin = path.getOrigin();
      LogicalUnit attacker = _gameQueries.getUnitAtPosition(origin);
      _theGame.executeAttackMove(attacker, path, target);
      return attacker;
   }
}
