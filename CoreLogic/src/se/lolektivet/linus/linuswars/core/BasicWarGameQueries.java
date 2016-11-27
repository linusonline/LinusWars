package se.lolektivet.linus.linuswars.core;

import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.game.Building;
import se.lolektivet.linus.linuswars.core.game.LogicalUnit;
import se.lolektivet.linus.linuswars.core.game.WarGameListener;
import se.lolektivet.linus.linuswars.core.pathfinding.InfiniteInteger;

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

   int getDayNumber();

   boolean unitBelongsToCurrentlyActiveFaction(LogicalUnit unit);
   boolean unitCanStillMoveThisTurn(LogicalUnit logicalUnit);
   boolean unitHasMovedThisTurn(LogicalUnit logicalUnit);

   boolean hasUnitAtPosition(Position position);
   LogicalUnit getUnitAtPosition(Position position);
   Position getPositionOfUnit(LogicalUnit logicalUnit);

   List<Faction> getFactionsInGame();
   Faction getCurrentlyActiveFaction();
   Faction getFactionForUnit(LogicalUnit logicalUnit);
   Position getHqPosition(Faction faction);
   int getMoneyForFaction(Faction faction);

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
   InfiniteInteger getFuelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile);

   // Movement and map
   InfiniteInteger getTravelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile);

      // Extended
   Set<LogicalUnit> getAdjacentUnits(Position position);
   Collection<Position> getAdjacentPositions(Position position);
   Set<LogicalUnit> getUnitsSuppliableFromPosition(LogicalUnit supplier, Position supplyingPosition);

   boolean hasBuildingAtPosition(Position position);
   Building getBuildingAtPosition(Position position);
   List<UnitType> getTypesDeployableFromBuilding(TerrainType buildingType);
   int getCostForNewUnit(UnitType unitType);
}
