package se.lolektivet.linus.linuswars.logic.pathfinding;

/**
 * Created by Linus on 2014-09-23.
 */
public class Cost {
   private final PotentiallyInfiniteInteger _movementCost;
   private final PotentiallyInfiniteInteger _fuelCost;

   public Cost() {
      this(PotentiallyInfiniteInteger.create(0), PotentiallyInfiniteInteger.create(0));
   }

   public Cost(PotentiallyInfiniteInteger movementCost, PotentiallyInfiniteInteger fuelCost) {
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

   public PotentiallyInfiniteInteger getMovementCost() {
      return _movementCost;
   }

   public PotentiallyInfiniteInteger getFuelCost() {
      return _fuelCost;
   }

   public static Cost add(Cost one, Cost other) {
      return new Cost(PotentiallyInfiniteInteger.add(one.getMovementCost(), other.getMovementCost()),
            PotentiallyInfiniteInteger.add(one.getFuelCost(), other.getFuelCost()));
   }
}
