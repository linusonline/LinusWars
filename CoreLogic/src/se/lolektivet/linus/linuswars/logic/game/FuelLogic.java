package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.enums.MovementType;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.pathfinding.InfiniteInteger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-20.
 */
class FuelLogic {

   int getFuelCostPerTurn(LogicalUnit unit) {
      return 0;
   }

   int getFuelCostPerTileMoved(LogicalUnit unit) {
      return 1;
   }

   InfiniteInteger getFuelCostForMovementTypeOnTerrainType(MovementType movement, TerrainType terrain) {
      return InfiniteInteger.create(1);
   }

   void resupplyUnit(LogicalUnit logicalUnit) {
      logicalUnit.resupply();
   }

   boolean canResupply(UnitType supplier) {
      return supplier == UnitType.APC;
   }

   boolean canResupplyUnit(UnitType supplier, UnitType supplied) {
      return supplier == UnitType.APC;
   }
}
