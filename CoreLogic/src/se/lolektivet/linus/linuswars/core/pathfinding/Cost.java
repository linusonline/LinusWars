package se.lolektivet.linus.linuswars.core.pathfinding;

/**
 * Created by Linus on 2014-09-23.
 */
public class Cost {
   private final InfiniteInteger _movementCost;
   private final InfiniteInteger _fuelCost;

   public Cost() {
      this(0, 0);
   }

   public Cost(Cost otherCost) {
      this(InfiniteInteger.copy(otherCost._movementCost), InfiniteInteger.copy(otherCost._fuelCost));
   }

   public Cost(int movementCost, int fuelCost) {
      this(InfiniteInteger.create(movementCost), InfiniteInteger.create(fuelCost));
   }

   public Cost(InfiniteInteger movementCost, InfiniteInteger fuelCost) {
      _movementCost = movementCost;
      _fuelCost = fuelCost;
   }

   public boolean isEqualOrBetterThan(Cost other) {
      return !other.hasLowerMovementCostThan(this) &&
            !other.costsLessFuelThan(this);
   }

   public boolean isEqualOrBetterInATieThan(Cost other) {
      // TODO: Throw exception if not tied?
      return !other.costsLessFuelThan(this);
   }

   private boolean hasLowerMovementCostThan(Cost other) {
      return other._movementCost.greaterThan(_movementCost);
   }

   private boolean costsLessFuelThan(Cost other) {
      return other._fuelCost.greaterThan(_fuelCost);
   }

   public InfiniteInteger getMovementCost() {
      return _movementCost;
   }

   public InfiniteInteger getFuelCost() {
      return _fuelCost;
   }

   public static Cost add(Cost one, Cost other) {
      return new Cost(InfiniteInteger.add(one.getMovementCost(), other.getMovementCost()),
            InfiniteInteger.add(one.getFuelCost(), other.getFuelCost()));
   }
}
