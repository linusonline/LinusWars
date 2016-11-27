package se.lolektivet.linus.linuswars.core.pathfinding;

import org.junit.Test;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Direction;

import static org.junit.Assert.assertEquals;

/**
 * Created by Linus on 2016-11-23.
 */
public class TestPathFactory {

   @Test
   public void testCreatePathWithOneDir() {
      Path thePath = PathFactory.create(new Position(0, 0), Direction.RIGHT);
      assertEquals(new Position(1, 0), thePath.getFinalPosition());
   }

   @Test
   public void testCreatePathWithTwoDirs() {
      Path thePath = PathFactory.create(new Position(0, 0), Direction.RIGHT, Direction.DOWN);
      assertEquals(new Position(1, 1), thePath.getFinalPosition());
   }

   @Test
   public void testCreatePathWithThreeDirs() {
      Path thePath = PathFactory.create(new Position(0, 0), Direction.RIGHT, Direction.DOWN, Direction.LEFT);
      assertEquals(new Position(0, 1), thePath.getFinalPosition());
   }
}
