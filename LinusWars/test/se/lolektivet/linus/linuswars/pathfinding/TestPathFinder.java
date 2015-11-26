package se.lolektivet.linus.linuswars.pathfinding;

import junit.framework.TestCase;
import se.lolektivet.linus.linuswars.logic.*;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.game.LogicalWarGame;
import se.lolektivet.linus.linuswars.logic.game.LogicalWarMap;
import se.lolektivet.linus.linuswars.maps.Map1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Linus on 2014-09-24.
 */
public class TestPathFinder extends TestCase {

   LogicalWarGame _logicalWarGame;

   public void setUp() {
      LogicalWarMap logicalWarMap = new LogicalWarMap();
      MapMaker mapMaker = new LogicalMapMaker(logicalWarMap);
      Map1 map = new Map1(new RowMapMaker(mapMaker));
      map.create();
      List<Faction> factions = new ArrayList<Faction>(2);
      factions.add(Faction.ORANGE_STAR);
      factions.add(Faction.BLUE_MOON);
      _logicalWarGame = new LogicalWarGame(logicalWarMap, factions);
   }

   public void testGetAdjacentTiles() {
      Collection<Position> adjacent = _logicalWarGame.getAdjacentPositions(new Position(0, 0));
      assertTrue(adjacent.contains(new Position(0, 1)));
      assertTrue(adjacent.contains(new Position(1, 0)));
      assertTrue(adjacent.size() == 2);
   }

   public void testGetAdjacentTiles2() {
      int x = _logicalWarGame.getLogicalWarMap().getWidth() - 1;
      Collection<Position> adjacent = _logicalWarGame.getAdjacentPositions(new Position(x, 0));
      assertTrue(adjacent.contains(new Position(x - 1, 0)));
      assertTrue(adjacent.contains(new Position(x, 1)));
      assertTrue(adjacent.size() == 2);
   }

   public void testGetAdjacentTiles3() {
      int y = _logicalWarGame.getLogicalWarMap().getHeight() - 1;
      Collection<Position> adjacent = _logicalWarGame.getAdjacentPositions(new Position(0, y));
      assertTrue(adjacent.contains(new Position(0, y - 1)));
      assertTrue(adjacent.contains(new Position(1, y)));
      assertTrue(adjacent.size() == 2);
   }

   public void testGetAdjacentTiles4() {
      int y = _logicalWarGame.getLogicalWarMap().getHeight() - 1;
      int x = _logicalWarGame.getLogicalWarMap().getWidth() - 1;
      Collection<Position> adjacent = _logicalWarGame.getAdjacentPositions(new Position(x, y));
      assertTrue(adjacent.contains(new Position(x, y - 1)));
      assertTrue(adjacent.contains(new Position(x - 1, y)));
      assertTrue(adjacent.size() == 2);
   }

   public void testGetAdjacentTiles5() {
      Collection<Position> adjacent = _logicalWarGame.getAdjacentPositions(new Position(1, 1));
      assertTrue(adjacent.contains(new Position(1, 0)));
      assertTrue(adjacent.contains(new Position(0, 1)));
      assertTrue(adjacent.contains(new Position(1, 2)));
      assertTrue(adjacent.contains(new Position(2, 1)));
      assertTrue(adjacent.size() == 4);
   }

//   public static void main(String[] args) {
//      TestPathFinder testPathFinder = new TestPathFinder();
//      testPathFinder.setUp();
//      testPathFinder.testGetAdjacentTiles();
//   }
}
