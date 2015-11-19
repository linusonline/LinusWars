package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.pathfinding.*;

import java.util.*;

/**
 * Created by Linus on 2014-10-02.
 */
public class WarGameQueriesImpl implements WarGameQueries {

   private final BasicWarGameQueries _basicWarGameQueries;

   private Map<Position, PathWithCost> _cachedOptimalPathsForTravellingUnit;
   private LogicalUnit _unitForWhichOptimalPathsAreCached;

   public WarGameQueriesImpl(BasicWarGameQueries logicalWarGame) {
      _basicWarGameQueries = logicalWarGame;
   }

   @Override
   public Path getOptimalPathForUnitToDestination(LogicalUnit travellingUnit, Position destination) {
      Map<Position, PathWithCost> optimalPaths = getAndCacheOptimalPathsToAllReachablePoints(travellingUnit);
      return optimalPaths.get(destination).getPath();
   }

   @Override
   public Set<Position> getAllReachablePoints(LogicalUnit travellingUnit) {
      return getAndCacheOptimalPathsToAllReachablePoints(travellingUnit).keySet();
   }

   @Override
   public boolean isPathAllowedForUnit(Path path, LogicalUnit movingUnit) {
      Cost totalCost = getCostForUnitAndPath(path, movingUnit);
      return totalCost.isEqualOrBetterThan(getCostLimitForUnit(movingUnit));
   }

   @Override
   public Cost getCostForUnitAndPath(Path path, LogicalUnit logicalUnit) {
      CostCalculator costCalculator = createCostCalculatorForUnit(logicalUnit);
      Cost totalCost = new Cost();
      for (Position step : path.getPositionList()) {
         totalCost = Cost.add(totalCost, costCalculator.getCostForPosition(step));
      }
      return totalCost;
   }

   @Override
   public void invalidateOptimalPathsCache() {
      _cachedOptimalPathsForTravellingUnit = null;
      _unitForWhichOptimalPathsAreCached = null;
   }

   private Map<Position, PathWithCost> getAndCacheOptimalPathsToAllReachablePoints(LogicalUnit travellingUnit) {
      if (!travellingUnit.equals(_unitForWhichOptimalPathsAreCached)) {
         _cachedOptimalPathsForTravellingUnit = getOptimalPathsToAllReachablePoints(travellingUnit);
         _unitForWhichOptimalPathsAreCached = travellingUnit;
      }
      return _cachedOptimalPathsForTravellingUnit;
   }

   private Map<Position, PathWithCost> getOptimalPathsToAllReachablePoints(LogicalUnit travellingUnit) {
      PathFinder pathFinder = new PathFinder(travellingUnit, this, createCostCalculatorForUnit(travellingUnit));
      return pathFinder.getOptimalPathsToAllReachablePoints(getCostLimitForUnit(travellingUnit));
   }

   private CostCalculator createCostCalculatorForUnit(final LogicalUnit logicalUnit) {
      return new CostCalculator() {
         @Override
         public Cost getCostForPosition(Position position) {
            PotentiallyInfiniteInteger movementCost = _basicWarGameQueries.getTravelCostForUnitOnTile(logicalUnit, position);
            PotentiallyInfiniteInteger fuelCost = _basicWarGameQueries.getFuelCostForUnitOnTile(logicalUnit, position);
            return new Cost(movementCost, fuelCost);
         }
      };
   }

   private Cost getCostLimitForUnit(LogicalUnit logicalUnit) {
      PotentiallyInfiniteInteger movementLimit = PotentiallyInfiniteInteger.create(logicalUnit.getBaseMovementRange());
      PotentiallyInfiniteInteger fuelLimit = PotentiallyInfiniteInteger.create(logicalUnit.getFuel());
      return new Cost(movementLimit, fuelLimit);
   }

   @Override
   public boolean hasActiveUnitAtPosition(Position position) {
      return _basicWarGameQueries.hasUnitAtPosition(position) && !unitHasMovedThisTurn(_basicWarGameQueries.getUnitAtPosition(position));
   }

   private boolean unitHasMovedThisTurn(LogicalUnit unit) {
      return _basicWarGameQueries.unitBelongsToCurrentlyActiveFaction(unit) && !_basicWarGameQueries.unitCanStillMoveThisTurn(unit);
   }

   @Override
   public void addListener(WarGameListener listener) {
      _basicWarGameQueries.addListener(listener);
   }

   @Override
   public boolean unitBelongsToCurrentlyActiveFaction(LogicalUnit unit) {
      return _basicWarGameQueries.unitBelongsToCurrentlyActiveFaction(unit);
   }

   @Override
   public boolean unitCanStillMoveThisTurn(LogicalUnit logicalUnit) {
      return _basicWarGameQueries.unitCanStillMoveThisTurn(logicalUnit);
   }

   @Override
   public boolean hasUnitAtPosition(Position position) {
      return _basicWarGameQueries.hasUnitAtPosition(position);
   }

   @Override
   public LogicalUnit getUnitAtPosition(Position position) {
      return _basicWarGameQueries.getUnitAtPosition(position);
   }

   @Override
   public Position getPositionOfUnit(LogicalUnit logicalUnit) {
      return _basicWarGameQueries.getPositionOfUnit(logicalUnit);
   }

   @Override
   public List<Faction> getFactionsInGame() {
      return _basicWarGameQueries.getFactionsInGame();
   }

   @Override
   public Faction getCurrentlyActiveFaction() {
      return _basicWarGameQueries.getCurrentlyActiveFaction();
   }

   @Override
   public Faction getFactionForUnit(LogicalUnit logicalUnit) {
      return _basicWarGameQueries.getFactionForUnit(logicalUnit);
   }

   @Override
   public Position getHqPosition(Faction faction) {
      return _basicWarGameQueries.getHqPosition(faction);
   }

   @Override
   public List<LogicalUnit> getTransportedUnits(LogicalUnit transporter) {
      return _basicWarGameQueries.getTransportedUnits(transporter);
   }

   @Override
   public boolean canLoadOnto(LogicalUnit loadingUnit, LogicalUnit transporter) {
      return _basicWarGameQueries.canLoadOnto(loadingUnit, transporter);
   }

   @Override
   public boolean canJoinWith(LogicalUnit joiningUnit, LogicalUnit joinedUnit) {
      return _basicWarGameQueries.canJoinWith(joiningUnit, joinedUnit);
   }

   @Override
   public int calculateDamageInPercent(LogicalUnit attackingUnit, LogicalUnit defendingUnit) {
      return _basicWarGameQueries.calculateDamageInPercent(attackingUnit, defendingUnit);
   }

   @Override
   public boolean isPositionInsideMap(Position position) {
      return _basicWarGameQueries.isPositionInsideMap(position);
   }

   @Override
   public Set<LogicalUnit> getAdjacentUnits(Position position) {
      return _basicWarGameQueries.getAdjacentUnits(position);
   }

   // TODO: Pull up implementation of extended queries from LogicalWarGame

   @Override
   public Collection<Position> getAdjacentPositions(Position position) {
      return _basicWarGameQueries.getAdjacentPositions(position);
   }

   @Override
   public Set<LogicalUnit> getUnitsSuppliableFromPosition(LogicalUnit supplier, Position supplyingPosition) {
      return _basicWarGameQueries.getUnitsSuppliableFromPosition(supplier, supplyingPosition);
   }

   @Override
   public Set<LogicalUnit> getSuppliableUnitsAfterMove(LogicalUnit supplier, Path path) {
      Position destination = path.getFinalPosition();
      Set<LogicalUnit> suppliableUnits;
      suppliableUnits = getUnitsSuppliableFromPosition(supplier, destination);
      return suppliableUnits;
   }

   @Override
   public Set<LogicalUnit> getAttackableUnitsAfterMove(LogicalUnit attackingUnit, Path path) {
      Position destination = path.getFinalPosition();
      Set<LogicalUnit> attackableUnits;
      if (attackingUnit.isRanged() && !path.isEmpty()) {
         attackableUnits = new HashSet<LogicalUnit>(0);
      } else {
         attackableUnits = getUnitsAttackableFromPosition(attackingUnit, destination);
      }
      return attackableUnits;
   }

   @Override
   public boolean unitsAreEnemies(LogicalUnit oneUnit, LogicalUnit otherUnit) {
      return !getFactionForUnit(oneUnit).equals(getFactionForUnit(otherUnit));
   }

   @Override
   public Set<LogicalUnit> getUnitsAttackableByUnit(Set<LogicalUnit> targetUnits, LogicalUnit attacker) {
      return _basicWarGameQueries.getUnitsAttackableByUnit(targetUnits, attacker);
   }

   @Override
   public PotentiallyInfiniteInteger getFuelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile) {
      return _basicWarGameQueries.getFuelCostForUnitOnTile(travellingUnit, tile);
   }

   @Override
   public PotentiallyInfiniteInteger getTravelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile) {
      return _basicWarGameQueries.getTravelCostForUnitOnTile(travellingUnit, tile);
   }

   private Set<LogicalUnit> getUnitsAttackableFromPosition(LogicalUnit attacker, Position attackingPosition) {
      if (attacker.isRanged()) {
         return getUnitsRemotelyAttackableFromPosition(attacker, attackingPosition);
      } else if (attacker.isCombat()) {
         return getUnitsDirectlyAttackableFromPosition(attacker, attackingPosition);
      } else {
         return new HashSet<LogicalUnit>(0);
      }
   }

   private Set<LogicalUnit> getUnitsRemotelyAttackableFromPosition(LogicalUnit attacker, Position attackingPosition) {
      Set<Position> targetPositions = getPositionsRemotelyAttackableFromPosition(attackingPosition, attacker.getBaseMinAttackRange(), attacker.getBaseMaxAttackRange());
      Set<LogicalUnit> targetUnits = getUnitsOnPositions(targetPositions);
      Set<LogicalUnit> attackableTargetUnits = getUnitsAttackableByUnit(targetUnits, attacker);
      return attackableTargetUnits;
   }

   private Set<Position> getPositionsRemotelyAttackableFromPosition(Position attackingPosition, int minRange, int maxRange) {
      Set<Position> attackablePositions = new HashSet<Position>(0);
      for (int range = minRange; range <= maxRange; range++) {
         attackablePositions.addAll(getPositionsXStepsAwayFrom(attackingPosition, range));
      }
      return attackablePositions;
   }

   private Collection<Position> getPositionsXStepsAwayFrom(Position origin, int nrOfSteps) {
      Collection<Position> positions = new HashSet<Position>(0);
      int x = origin.getX();
      int y = origin.getY() - nrOfSteps;
      for (int stepCount = 0; stepCount < nrOfSteps; stepCount++) {
         x++;
         y++;
         addPositionIfInsideMap(x, y, positions);
      }
      for (int stepCount = 0; stepCount < nrOfSteps; stepCount++) {
         x--;
         y++;
         addPositionIfInsideMap(x, y, positions);
      }
      for (int stepCount = 0; stepCount < nrOfSteps; stepCount++) {
         x--;
         y--;
         addPositionIfInsideMap(x, y, positions);
      }
      for (int stepCount = 0; stepCount < nrOfSteps; stepCount++) {
         x++;
         y--;
         addPositionIfInsideMap(x, y, positions);
      }
      return positions;
   }

   private void addPositionIfInsideMap(int x, int y, Collection<Position> positions) {
      Position position = new Position(x, y);
      if (isPositionInsideMap(position)) {
         positions.add(position);
      }
   }

   private Set<LogicalUnit> getUnitsOnPositions(Collection<Position> positions) {
      Set<LogicalUnit> units = new HashSet<LogicalUnit>();
      for (Position position : positions) {
         if (hasUnitAtPosition(position)) {
            units.add(getUnitAtPosition(position));
         }
      }
      return units;
   }

   private Set<LogicalUnit> getUnitsDirectlyAttackableFromPosition(LogicalUnit attacker, Position attackingPosition) {
      Set<LogicalUnit> adjacentUnits = getAdjacentUnits(attackingPosition);
      return getUnitsAttackableByUnit(adjacentUnits, attacker);
   }

   @Override
   public Set<Position> getAdjacentVacantPositionsAfterMove(LogicalUnit movingUnit, Path path) {
      Position destination = path.getFinalPosition();
      Collection<Position> adjacentPositions = getAdjacentPositions(destination);
      Set<Position> adjacentVacantPositions = new HashSet<Position>();
      for (Position adjacentPosition : adjacentPositions) {
         if (hasUnitAtPosition(adjacentPosition)) {
            if (!getUnitAtPosition(adjacentPosition).equals(movingUnit)) {
               continue;
            }
         }
         adjacentVacantPositions.add(adjacentPosition);
      }
      return adjacentVacantPositions;
   }

}
