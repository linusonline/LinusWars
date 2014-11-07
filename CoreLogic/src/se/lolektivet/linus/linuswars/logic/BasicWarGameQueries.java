package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.pathfinding.Path;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Linus on 2014-11-07.
 */
public interface BasicWarGameQueries {
   boolean unitBelongsToCurrentlyActiveFaction(LogicalUnit unit);
   boolean unitCanStillMoveThisTurn(LogicalUnit logicalUnit);

   boolean hasUnitAtPosition(Position position);
   LogicalUnit getUnitAtPosition(Position position);
   Position getPositionOfUnit(LogicalUnit logicalUnit);

   Faction getCurrentlyActiveFaction();
   Faction getFactionForUnit(LogicalUnit logicalUnit);

   List<LogicalUnit> getTransportedUnits(LogicalUnit transporter);

      // Transport extended
   boolean canLoadOnto(LogicalUnit loadingUnit, LogicalUnit transporter);

   // Map and attack logic
   int calculateDamageInPercent(LogicalUnit attackingUnit, LogicalUnit defendingUnit);

   // Extended
   Collection<Position> getAdjacentPositions(Position position);
   Set<LogicalUnit> getSuppliableUnitsAfterMove(LogicalUnit supplier, Path path);
   Set<LogicalUnit> getAttackableUnitsAfterMove(LogicalUnit attackingUnit, Path path);
   Set<Position> getAdjacentVacantPositionsAfterMove(LogicalUnit movingUnit, Path path);

}
