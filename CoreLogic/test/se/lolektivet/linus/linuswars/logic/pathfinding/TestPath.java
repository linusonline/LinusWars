package se.lolektivet.linus.linuswars.logic.pathfinding;

import org.junit.Assert;
import org.junit.Test;
import se.lolektivet.linus.linuswars.logic.Position;

/**
 * Created by Linus on 2015-12-04.
 */
public class TestPath {

   @Test
   public void testAddPointMakesCopy() {
      Path path = new Path(new Position(0, 0));
      Position position = new Position(0, 1);
      path.addPoint(position);
      position.setX(1);
      Assert.assertFalse(path.getFinalPosition().equals(position));
   }
}
