package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.LogicException;
import se.lolektivet.linus.linuswars.core.enums.MovementType;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.pathfinding.InfiniteInteger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Linus on 2014-09-20.
 */
public class FuelLogic {
   private Map<UnitType, Integer> _maxFuelByUnitType = new HashMap<>(UnitType.values().length);

   public FuelLogic() {
      _maxFuelByUnitType.put(UnitType.INFANTRY, 99);
      _maxFuelByUnitType.put(UnitType.MECH, 70);
      _maxFuelByUnitType.put(UnitType.APC, 70);
      _maxFuelByUnitType.put(UnitType.TANK, 70);
      _maxFuelByUnitType.put(UnitType.MD_TANK, 50);
      _maxFuelByUnitType.put(UnitType.ANTI_AIR, 60);
      _maxFuelByUnitType.put(UnitType.ARTILLERY, 99);
      _maxFuelByUnitType.put(UnitType.RECON, 80);
      _maxFuelByUnitType.put(UnitType.ROCKETS, 50);
      _maxFuelByUnitType.put(UnitType.MISSILES, 50);
      _maxFuelByUnitType.put(UnitType.LANDER, 99);
      _maxFuelByUnitType.put(UnitType.CRUISER, 99);
      _maxFuelByUnitType.put(UnitType.B_SHIP, 99);
      _maxFuelByUnitType.put(UnitType.SUB, 60);
      _maxFuelByUnitType.put(UnitType.B_COPTER, 99);
      _maxFuelByUnitType.put(UnitType.T_COPTER, 99);
      _maxFuelByUnitType.put(UnitType.FIGHTER, 99);
      _maxFuelByUnitType.put(UnitType.BOMBER, 99);
   }

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
   // NOTE: So far, true for air units and infantry on plain and woods.
   InfiniteInteger getFuelCostForMovementTypeOnTerrainType(MovementType movement, TerrainType terrain) {
      return InfiniteInteger.create(1);
   }

   public int getMaxFuelForUnitType(UnitType type) {
      return _maxFuelByUnitType.get(type);
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
