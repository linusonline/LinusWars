package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.pathfinding.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Linus on 2014-10-02.
 */
public class WarGameQueriesImpl implements WarGameQueries {
   private final LogicalWarGame _logicalWarGame;
   private final BasicWarGameQueries _basicWarGameQueries;

   private Map<Position, PathWithCost> _cachedOptimalPathsForTravellingUnit;
   private LogicalUnit _unitForWhichOptimalPathsAreCached;

   public WarGameQueriesImpl(LogicalWarGame logicalWarGame) {
      _logicalWarGame = logicalWarGame;
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
      PathFinder pathFinder = new PathFinder(travellingUnit, _logicalWarGame, createCostCalculatorForUnit(travellingUnit));
      return pathFinder.getOptimalPathsToAllReachablePoints(getCostLimitForUnit(travellingUnit));
   }

   private CostCalculator createCostCalculatorForUnit(final LogicalUnit logicalUnit) {
      return new CostCalculator() {
         @Override
         public Cost getCostForPosition(Position position) {
            PotentiallyInfiniteInteger movementCost = _logicalWarGame.getTravelCostForUnitOnTile(logicalUnit, position);
            PotentiallyInfiniteInteger fuelCost = _logicalWarGame.getFuelCostForUnitOnTile(logicalUnit, position);
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
   public Faction getCurrentlyActiveFaction() {
      return _basicWarGameQueries.getCurrentlyActiveFaction();
   }

   @Override
   public Faction getFactionForUnit(LogicalUnit logicalUnit) {
      return _basicWarGameQueries.getFactionForUnit(logicalUnit);
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
   public int calculateDamageInPercent(LogicalUnit attackingUnit, LogicalUnit defendingUnit) {
      return _basicWarGameQueries.calculateDamageInPercent(attackingUnit, defendingUnit);
   }

   // TODO: Pull up implementation of extended queries from LogicalWarGame

   @Override
   public Collection<Position> getAdjacentPositions(Position position) {
      return _basicWarGameQueries.getAdjacentPositions(position);
   }

   @Override
   public Set<LogicalUnit> getSuppliableUnitsAfterMove(LogicalUnit supplier, Path path) {
      return _basicWarGameQueries.getSuppliableUnitsAfterMove(supplier, path);
   }

   @Override
   public Set<LogicalUnit> getAttackableUnitsAfterMove(LogicalUnit attackingUnit, Path path) {
      return _basicWarGameQueries.getAttackableUnitsAfterMove(attackingUnit, path);
   }

   @Override
   public Set<Position> getAdjacentVacantPositionsAfterMove(LogicalUnit movingUnit, Path path) {
      return _basicWarGameQueries.getAdjacentVacantPositionsAfterMove(movingUnit, path);
   }
}
