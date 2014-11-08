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

   // Map logic
   public boolean isPositionInsideMap(Position position);

   // Attack logic
   Set<LogicalUnit> getUnitsAttackableByUnit(Set<LogicalUnit> targetUnits, LogicalUnit attacker);

   // Extended
   Set<LogicalUnit> getAdjacentUnits(Position position);
   Collection<Position> getAdjacentPositions(Position position);
   Set<LogicalUnit> getUnitsSuppliableFromPosition(LogicalUnit supplier, Position supplyingPosition);
}
