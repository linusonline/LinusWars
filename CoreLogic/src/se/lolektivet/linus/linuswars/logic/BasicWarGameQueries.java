package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.pathfinding.PotentiallyInfiniteInteger;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Linus on 2014-11-07.
 */
public interface BasicWarGameQueries {

   void addListener(WarGameListener listener);

   int getMapWidth();
   int getMapHeight();

   boolean unitBelongsToCurrentlyActiveFaction(LogicalUnit unit);
   boolean unitCanStillMoveThisTurn(LogicalUnit logicalUnit);

   boolean hasUnitAtPosition(Position position);
   LogicalUnit getUnitAtPosition(Position position);
   Position getPositionOfUnit(LogicalUnit logicalUnit);

   List<Faction> getFactionsInGame();
   Faction getCurrentlyActiveFaction();
   Faction getFactionForUnit(LogicalUnit logicalUnit);

   Position getHqPosition(Faction faction);

   List<LogicalUnit> getTransportedUnits(LogicalUnit transporter);

      // Transport extended
   boolean canLoadOnto(LogicalUnit loadingUnit, LogicalUnit transporter);

   boolean canJoinWith(LogicalUnit joiningUnit, LogicalUnit joinedUnit);

   // Map and attack logic
   int calculateDamageInPercent(LogicalUnit attackingUnit, LogicalUnit defendingUnit);

   // Map logic
   boolean isPositionInsideMap(Position position);

   // Attack logic
   Set<LogicalUnit> getUnitsAttackableByUnit(Set<LogicalUnit> targetUnits, LogicalUnit attacker);

   // Fuel and map
   PotentiallyInfiniteInteger getFuelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile);

   // Movement and map
   PotentiallyInfiniteInteger getTravelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile);

      // Extended
   Set<LogicalUnit> getAdjacentUnits(Position position);
   Collection<Position> getAdjacentPositions(Position position);
   Set<LogicalUnit> getUnitsSuppliableFromPosition(LogicalUnit supplier, Position supplyingPosition);
}
