package se.lolektivet.linus.linuswars.pathfinding;

import org.junit.Before;
import org.junit.Test;
import se.lolektivet.linus.linuswars.core.LinusWarsTest;
import se.lolektivet.linus.linuswars.core.LogicalMapMaker;
import se.lolektivet.linus.linuswars.core.MapMaker;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.game.LogicalWarGame;
import se.lolektivet.linus.linuswars.core.game.LogicalWarMapImpl;
import se.lolektivet.linus.linuswars.core.game.ModuleBuildings;
import se.lolektivet.linus.linuswars.core.maps.Map1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Linus on 2014-09-24.
 */
public class TestPathFinder extends LinusWarsTest {

   private LogicalWarGame _logicalWarGame;

   @Before
   public void setUp() {
      LogicalWarMapImpl logicalWarMap = new LogicalWarMapImpl(new ModuleBuildings());
      MapMaker mapMaker = new LogicalMapMaker(logicalWarMap);
      Map1 map = new Map1();
      map.create(mapMaker);
      List<Faction> factions = new ArrayList<Faction>(2);
      factions.add(Faction.ORANGE_STAR);
      factions.add(Faction.BLUE_MOON);
      _logicalWarGame = new LogicalWarGame(logicalWarMap, factions);
   }

   @Test
   public void testGetAdjacentTiles() {
      Collection<Position> adjacent = _logicalWarGame.getAdjacentPositions(new Position(0, 0));
      assertTrue(adjacent.contains(new Position(0, 1)));
      assertTrue(adjacent.contains(new Position(1, 0)));
      assertTrue(adjacent.size() == 2);
   }

   @Test
   public void testGetAdjacentTiles2() {
      int x = _logicalWarGame.getLogicalWarMap().getWidth() - 1;
      Collection<Position> adjacent = _logicalWarGame.getAdjacentPositions(new Position(x, 0));
      assertTrue(adjacent.contains(new Position(x - 1, 0)));
      assertTrue(adjacent.contains(new Position(x, 1)));
      assertTrue(adjacent.size() == 2);
   }

   @Test
   public void testGetAdjacentTiles3() {
      int y = _logicalWarGame.getLogicalWarMap().getHeight() - 1;
      Collection<Position> adjacent = _logicalWarGame.getAdjacentPositions(new Position(0, y));
      assertTrue(adjacent.contains(new Position(0, y - 1)));
      assertTrue(adjacent.contains(new Position(1, y)));
      assertTrue(adjacent.size() == 2);
   }

   @Test
   public void testGetAdjacentTiles4() {
      int y = _logicalWarGame.getLogicalWarMap().getHeight() - 1;
      int x = _logicalWarGame.getLogicalWarMap().getWidth() - 1;
      Collection<Position> adjacent = _logicalWarGame.getAdjacentPositions(new Position(x, y));
      assertTrue(adjacent.contains(new Position(x, y - 1)));
      assertTrue(adjacent.contains(new Position(x - 1, y)));
      assertTrue(adjacent.size() == 2);
   }

   @Test
   public void testGetAdjacentTiles5() {
      Collection<Position> adjacent = _logicalWarGame.getAdjacentPositions(new Position(1, 1));
      assertTrue(adjacent.contains(new Position(1, 0)));
      assertTrue(adjacent.contains(new Position(0, 1)));
      assertTrue(adjacent.contains(new Position(1, 2)));
      assertTrue(adjacent.contains(new Position(2, 1)));
      assertTrue(adjacent.size() == 4);
   }
}
