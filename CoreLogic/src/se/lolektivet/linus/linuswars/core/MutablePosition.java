package se.lolektivet.linus.linuswars.core;

/**
 * Created by Linus on 2016-11-30.
 */
public class MutablePosition {
   private int _x;
   private int _y;

   public MutablePosition(MutablePosition position) {
      this(position.getX(), position.getY());
   }

   public MutablePosition(Position position) {
      this(position.getX(), position.getY());
   }

   public MutablePosition(int x, int y) {
      _x = x;
      _y = y;
   }

   public Position asPosition() {
      return new Position(_x, _y);
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
