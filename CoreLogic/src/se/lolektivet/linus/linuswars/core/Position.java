package se.lolektivet.linus.linuswars.core;

import se.lolektivet.linus.linuswars.core.enums.Direction;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Linus on 2014-09-19.
 */
public class Position {
   private final int _x;
   private final int _y;

   public Position(Position position) {
      this(position.getX(), position.getY());
   }

   public Position(int x, int y) {
      _x = x;
      _y = y;
   }

   public int getX() {
      return _x;
   }

   public int getY() {
      return _y;
   }

   public Collection<Position> getAdjacentPositions() {
      Collection<Position> adjacentPositions = new HashSet<Position>(4);
      adjacentPositions.add(new Position(getX(), getY() + 1));
      adjacentPositions.add(new Position(getX(), getY() - 1));
      adjacentPositions.add(new Position(getX() + 1, getY()));
      adjacentPositions.add(new Position(getX() - 1, getY()));
      return adjacentPositions;
   }

   public Position getPositionAfterStep(Direction direction) {
      int x = _x;
      int y = _y;
      switch (direction) {
         case LEFT:
            x--;
            break;
         case RIGHT:
            x++;
            break;
         case UP:
            y--;
            break;
         case DOWN:
         default:
            y++;
      }
      return new Position(x, y);
   }

   public boolean isAdjacentTo(Position otherPosition) {
      return getAdjacentPositions().contains(otherPosition);
   }

   @Override
   public boolean equals(Object obj) {
      return
            (obj instanceof Position &&
                  ((Position) obj).getX() == getX() &&
                  ((Position) obj).getY() == getY()) ||
                  (obj instanceof MutablePosition &&
                        ((MutablePosition) obj).getX() == getX() &&
                        ((MutablePosition) obj).getY() == getY());
   }

   @Override
   public int hashCode() {
      return _x*3 + _y*31;
   }

   @Override
   public String toString() {
      return "(" + _x + "," + _y + ")";
   }
}
