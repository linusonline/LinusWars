package se.lolektivet.linus.linuswars.core.pathfinding;

import org.junit.Test;
import se.lolektivet.linus.linuswars.core.LinusWarsTest;
import se.lolektivet.linus.linuswars.core.Position;

import static org.junit.Assert.assertEquals;

/**
 * Created by Linus on 2016-11-30.
 */
public class TestPathWithCost extends LinusWarsTest {

   @Test
   public void testTrivial() {
      PathWithCost pathWithCost = new PathWithCost(new Position(0, 0), false);
      assertCostIs(pathWithCost, 0, 0);
   }

   @Test
   public void testOneStep() {
      PathWithCost pathWithCost = new PathWithCost(new Position(0, 0), false);
      pathWithCost.addPoint(new Position(1, 0), new Cost(1, 1));
      assertCostIs(pathWithCost, 1, 1);
   }

   @Test
   public void testDifferentCosts() {
      PathWithCost pathWithCost = new PathWithCost(new Position(0, 0), false);
      pathWithCost.addPoint(new Position(1, 0), new Cost(2, 3));
      assertCostIs(pathWithCost, 2, 3);
   }

   @Test
   public void testWithoutAutoBackTrack() {
      PathWithCost pathWithCost = new PathWithCost(new Position(0, 0), false);
      pathWithCost.addPoint(new Position(1, 0), new Cost(1, 1));
      pathWithCost.addPoint(new Position(0, 0), new Cost(1, 1));
      assertCostIs(pathWithCost, 2, 2);
   }

   @Test
   public void testWithAutoBackTrack() {
      PathWithCost pathWithCost = new PathWithCost(new Position(0, 0), true);
      pathWithCost.addPoint(new Position(1, 0), new Cost(1, 1));
      pathWithCost.addPoint(new Position(0, 0), new Cost(1, 1));
      assertCostIs(pathWithCost, 0, 0);
   }

   private void assertCostIs(PathWithCost pathWithCost, int movementCost, int fuelCost) {
      assertEquals(movementCost, pathWithCost.getTotalCost().getMovementCost().getInteger());
      assertEquals(fuelCost, pathWithCost.getTotalCost().getFuelCost().getInteger());
   }
}
