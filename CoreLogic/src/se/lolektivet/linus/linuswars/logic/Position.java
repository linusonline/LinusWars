package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Direction;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Linus on 2014-09-19.
 */
public class Position {
   private int _x;
   private int _y;

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

   public void setX(int x) {
      _x = x;
   }

   public int getY() {
      return _y;
   }

   public void setY(int y) {
      _y = y;
   }

   Collection<Position> getAdjacentPositions() {
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
            obj instanceof Position &&
                  ((Position) obj).getX() == getX() &&
                  ((Position) obj).getY() == getY();
   }

   @Override
   public int hashCode() {
      return _x*3 + _y*31;
   }
}
