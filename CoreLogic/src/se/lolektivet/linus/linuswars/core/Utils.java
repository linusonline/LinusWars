package se.lolektivet.linus.linuswars.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Linus on 2016-12-03.
 */
public class Utils {
   public static Set<Position> generateAllPositionsInSquare(Position topLeft, Position bottomRight) {
      int topLeftX = topLeft.getX();
      int topLeftY = topLeft.getY();
      int bottomRightX = bottomRight.getX();
      int bottomRightY = bottomRight.getY();
      Set<Position> allPositions = new HashSet<>((bottomRightX - topLeftX + 1) * (bottomRightY - topLeftY + 1));
      for (int y = topLeftY; y <= bottomRightY; y++) {
         for (int x = topLeftX; x <= bottomRightX; x++) {
            allPositions.add(new Position(x, y));
         }
      }
      return allPositions;
   }
}
