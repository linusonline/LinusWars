package se.lolektivet.linus.linuswars.core.pathfinding;

/**
 * Created by Linus on 2014-09-19.
 */
public class InfiniteInteger {
   private final int _specificInteger;
   private final boolean _isInfinite;

   public static InfiniteInteger infinite() {
      return new InfiniteInteger();
   }

   public static InfiniteInteger create(int cost) {
      return new InfiniteInteger(cost);
   }

   private InfiniteInteger(int specificCost) {
      _isInfinite = false;
      _specificInteger = specificCost;
   }

   private InfiniteInteger() {
      _specificInteger = 0;
      _isInfinite = true;
   }

   public boolean isInfinite() {
      return _isInfinite;
   }

   public int getInteger() {
      return _specificInteger;
   }

   static InfiniteInteger add(InfiniteInteger one, InfiniteInteger other) {
      if (one.isInfinite() || other.isInfinite()) {
         return InfiniteInteger.infinite();
      } else {
         return InfiniteInteger.create(one.getInteger() + other.getInteger());
      }
   }

   boolean greaterThan(InfiniteInteger other) {
      if (other.isInfinite()) {
         return false;
      } else {
         return isInfinite() || getInteger() > other.getInteger();
      }
   }
}
