package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.pathfinding.PotentiallyInfiniteInteger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
