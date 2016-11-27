package se.lolektivet.linus.linuswars.pathfinding;

import junit.framework.TestCase;
import se.lolektivet.linus.linuswars.core.pathfinding.Cost;
import se.lolektivet.linus.linuswars.core.pathfinding.InfiniteInteger;

/**
 * Created by Linus on 2014-09-24.
 */
public class TestCost extends TestCase {
   public void testCost() {
      final InfiniteInteger infinity = InfiniteInteger.infinite();
      final InfiniteInteger finite = InfiniteInteger.create(3);
      Cost cost1 = new Cost(finite, finite);
      Cost cost2 = new Cost(finite, finite);
      assertTrue(cost1.isEqualOrBetterThan(cost2));
      assertTrue(cost2.isEqualOrBetterThan(cost1));
      assertTrue(cost2.isEqualOrBetterInATieThan(cost1));
      assertTrue(cost1.isEqualOrBetterInATieThan(cost2));

      cost1 = new Cost(finite, infinity);
      assertTrue(!cost1.isEqualOrBetterThan(cost2));
      assertTrue(cost2.isEqualOrBetterThan(cost1));
      assertTrue(!cost1.isEqualOrBetterInATieThan(cost2));
      assertTrue(cost2.isEqualOrBetterInATieThan(cost1));

      cost1 = new Cost(infinity, finite);
      assertTrue(!cost1.isEqualOrBetterThan(cost2));
      assertTrue(cost2.isEqualOrBetterThan(cost1));
      assertTrue(cost1.isEqualOrBetterInATieThan(cost2));
      assertTrue(cost2.isEqualOrBetterInATieThan(cost1));

      cost1 = new Cost(finite, finite);
      Cost total = Cost.add(cost1, cost2);
      assertTrue(!total.getMovementCost().isInfinite());
      assertTrue(total.getMovementCost().getInteger() == 6);
      assertTrue(!total.getFuelCost().isInfinite());
      assertTrue(total.getFuelCost().getInteger() == 6);

      cost1 = new Cost(infinity, finite);
      total = Cost.add(cost1, cost2);
      assertTrue(total.getMovementCost().isInfinite());
      assertTrue(!total.getFuelCost().isInfinite());
      assertTrue(total.getFuelCost().getInteger() == 6);

      cost1 = new Cost(finite, infinity);
      total = Cost.add(cost1, cost2);
      assertTrue(!total.getMovementCost().isInfinite());
      assertTrue(total.getMovementCost().getInteger() == 6);
      assertTrue(total.getFuelCost().isInfinite());

      cost1 = new Cost(infinity, infinity);
      total = Cost.add(cost1, cost2);
      assertTrue(total.getMovementCost().isInfinite());
      assertTrue(total.getFuelCost().isInfinite());

   }
}
