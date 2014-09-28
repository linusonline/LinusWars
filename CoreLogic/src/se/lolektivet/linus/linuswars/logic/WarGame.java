package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.pathfinding.Path;

import java.util.Set;

/**
 * Created by Linus on 2014-09-18.
 */
public interface WarGame {
   // Move piece X along path Y
   // Move piece X along path Y and then attack piece W
   // Move piece X along path Y and enter transport W
   // Move piece X along path Y and then unload carried unit(s) W
   // Move sub X along path Y and then submerge/surface
   // Move infantry X along path Y and then capture property W
   void executeMove(LogicalUnit logicalUnit, Path path);
   void executeAttackMove(LogicalUnit movingUnit, Path path, LogicalUnit attackedUnit);

   // For re-routing travel arrow when user is selecting path.
   Path getOptimalPathForUnitToDestination(LogicalUnit travellingUnit, Position destination);

   // For showing all reachable points for a unit.
   Set<Position> getAllReachablePoints(LogicalUnit travellingUnit);

   boolean hasUnitAtPosition(Position position);
}
