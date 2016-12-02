package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.BasicWarGameQueries;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.pathfinding.Cost;
import se.lolektivet.linus.linuswars.core.pathfinding.Path;

import java.util.Set;

/**
 * Created by Linus on 2014-10-02.
 */
public interface WarGameQueries extends BasicWarGameQueries {

   Set<LogicalUnit> getAllUnitsInActiveFaction();
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
   boolean areEnemies(Faction oneFaction, Faction anotherFaction);
   boolean areEnemies(LogicalUnit oneUnit, LogicalUnit anotherUnit);
   boolean areEnemies(LogicalUnit unit, Faction faction);
   Set<LogicalUnit> getSuppliableUnitsAfterMove(LogicalUnit supplier, Path path);
   boolean hasEnemyBuildingAtPosition(LogicalUnit movingUnit, Position position);
   boolean hasFriendlyUnoccupiedBaseAtPosition(Faction faction, Position position);
}
