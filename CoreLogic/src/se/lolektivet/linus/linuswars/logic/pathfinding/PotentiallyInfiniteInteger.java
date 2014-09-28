package se.lolektivet.linus.linuswars.logic.pathfinding;

/**
 * Created by Linus on 2014-09-19.
 */
public class PotentiallyInfiniteInteger {
   private final int _specificInteger;
   private final boolean _isInfinite;

   public static PotentiallyInfiniteInteger infinite() {
      return new PotentiallyInfiniteInteger();
   }

   public static PotentiallyInfiniteInteger create(int cost) {
      return new PotentiallyInfiniteInteger(cost);
   }

   private PotentiallyInfiniteInteger(int specificCost) {
      _isInfinite = false;
      _specificInteger = specificCost;
   }

   private PotentiallyInfiniteInteger() {
      _specificInteger = 0;
      _isInfinite = true;
   }

   public boolean isInfinite() {
      return _isInfinite;
   }

   public int getInteger() {
      return _specificInteger;
   }

   static PotentiallyInfiniteInteger add(PotentiallyInfiniteInteger one, PotentiallyInfiniteInteger other) {
      if (one.isInfinite() || other.isInfinite()) {
         return PotentiallyInfiniteInteger.infinite();
      } else {
         return PotentiallyInfiniteInteger.create(one.getInteger() + other.getInteger());
      }
   }

   boolean greaterThan(PotentiallyInfiniteInteger other) {
      if (other.isInfinite()) {
         return false;
      } else {
         return isInfinite() || getInteger() > other.getInteger();
      }
   }
}
