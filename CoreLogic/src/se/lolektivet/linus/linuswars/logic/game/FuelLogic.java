package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.LogicException;
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
      if (unit.isLand()) {
         return 0;
      } else if (unit.getType().equals(UnitType.SUB) && unit.subIsSubmerged()) {
         return 5;
      } else if (unit.isSea()) {
         return 1;
      } else {
         switch (unit.getType()) {
            case B_COPTER: return 1;
            case T_COPTER: return 2;
            case FIGHTER: return 5;
            case BOMBER: return 5;
         }
      }
      throw new LogicException("Unknown unit type!");
   }

   int getFuelCostPerTileMoved(LogicalUnit unit) {
      return 1;
   }

   // NOTE: Not sure this is actually needed. Need some experimenting.
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
