package se.lolektivet.linus.linuswars.logic.game;

import se.lolektivet.linus.linuswars.logic.BasicWarGameQueries;
import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.pathfinding.Cost;
import se.lolektivet.linus.linuswars.logic.pathfinding.Path;

import java.util.Set;

/**
 * Created by Linus on 2014-10-02.
 */
public interface WarGameQueries extends BasicWarGameQueries {

   // For re-routing travel arrow when user is selecting path.
   Path getOptimalPathForUnitToDestination(LogicalUnit travellingUnit, Position destination);
   // For showing all reachable points for a unit.
   Set<Position> getAllReachablePoints(LogicalUnit travellingUnit);
   boolean isPathAllowedForUnit(Path path, LogicalUnit movingUnit);
   Cost getCostForUnitAndPath(Path path, LogicalUnit logicalUnit);
   void invalidateOptimalPathsCache();

   boolean hasActiveUnitAtPosition(Position position);
   Set<Position> getAdjacentVacantPositionsAfterMove(LogicalUnit movingUnit, Path path);
   Set<LogicalUnit> getAttackableUnitsAfterMove(LogicalUnit attackingUnit, Path path);
   boolean unitsAreEnemies(LogicalUnit oneUnit, LogicalUnit otherUnit);
   Set<LogicalUnit> getSuppliableUnitsAfterMove(LogicalUnit supplier, Path path);

}
