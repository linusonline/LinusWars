package se.lolektivet.linus.linuswars.core.game;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.GamePredeployer;
import se.lolektivet.linus.linuswars.core.LogicalGameFactory;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.maps.TestMap4x4PlainsString;

import static org.junit.Assert.assertEquals;

/**
 * Created by Linus on 2016-11-28.
 */
public class TestAutoHealCostsMoney {
   private static class TestGameSetup extends GameSetupAdapterForTest {
      @Override
      public void preDeploy(GamePredeployer predeployer) {
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 0, 70);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 1);
         predeployer.addNewUnit(UnitType.INFANTRY, Faction.BLUE_MOON, 0, 2, 70);
      }
   }

   private WarGameMoves _gameMoves;
   private WarGameQueries _gameQueries;

   @Before
   public void setup() {
      LogicalWarGame theGame = new LogicalGameFactory().createLogicalWarGame(TestMap4x4PlainsString.create(), new TestGameSetup(), Faction.ORANGE_STAR, Faction.BLUE_MOON);

      _gameMoves = theGame;
      _gameQueries = theGame;

      theGame.callGameStart();
   }

   @Test
   public void testHealingCosts200G() {
      // This tests that:
      // - The injured unit on the HQ is healed
      // - The uninjured unit on the base is NOT healed (does not cost money)
      // - The injured unit on the plains is NOT healed
      int moneyBefore = _gameQueries.getMoneyForFaction(Faction.BLUE_MOON);
      _gameMoves.endTurn();
      int moneyAfter = _gameQueries.getMoneyForFaction(Faction.BLUE_MOON);
      assertEquals(moneyBefore + TestMap4x4PlainsString.BLUE_MOON_STARTING_FUNDS - 200, moneyAfter);
   }
}
