package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.MovementType;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.pathfinding.PotentiallyInfiniteInteger;

/**
 * Created by Linus on 2014-09-20.
 */
public class FuelLogic {
   int getFuelCostPerTurn(LogicalUnit unit) {
      return 0;
   }

   int getFuelCostPerTileMoved(LogicalUnit unit) {
      return 1;
   }

   PotentiallyInfiniteInteger getFuelCostForMovementTypeOnTerrainType(MovementType movement, TerrainType terrain) {
      return PotentiallyInfiniteInteger.create(1);
   }

   void resupplyUnit(LogicalUnit logicalUnit) {
      logicalUnit.resupply();
   }

   public boolean canResupply(UnitType supplier) {
      return supplier.equals(UnitType.APC);
   }

   boolean canResupplyUnit(UnitType supplier, UnitType supplied) {
      return supplier.equals(UnitType.APC);
   }
}
