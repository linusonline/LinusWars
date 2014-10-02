package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.pathfinding.*;

import java.util.*;

/**
 * Created by Linus on 2014-09-19.
 */
public class LogicalWarGame implements WarGame {

   public interface Listener {
      void unitWasDestroyed(LogicalUnit logicalUnit);
      void transportedUnitWasDestroyed(LogicalUnit logicalUnit);
   }

   public Faction getCurrentlyActiveFaction() {
      return _currentlyActiveFaction;
   }

   class UnitCollisionException extends RuntimeException {}
   class UnitNotFoundException extends RuntimeException {}
   class FactionAlreadySetException extends RuntimeException {}
   class FactionNotInGameException extends RuntimeException {}
   class HqNotSetException extends RuntimeException {}

   private final LogicalWarMap _logicalWarMap;
   private final MovementLogic _movementLogic;
   private final FuelLogic _fuelLogic;
   private final AttackLogic _attackLogic;
   private final TransportLogic _transportLogic;

   private final Map<LogicalUnit, Position> _positionsOfUnits;
   private final Map<Position, LogicalUnit> _unitsAtPositions;
   private final Map<LogicalUnit, Faction> _factionsOfUnits;
   private final Map<Faction, Collection<LogicalUnit>> _unitsInFaction;
   private final Map<LogicalUnit, List<LogicalUnit>> _transportedUnits;
   private final Map<Faction, Collection<LogicalUnit>> _transportedUnitsInFaction;

   private final Map<Position, Faction> _factionOwningProperty;

   private Faction _currentlyActiveFaction;
   private final List<Faction> _factionsInTurnOrder;
   private final Map<Faction, Position> _hqsOfFactions;
   private final Map<Position, Faction> _positionsOfHqs;

   private boolean _gameStarted;
   private Collection<LogicalUnit> _unitsLeftToMoveThisTurn;

   private final Collection<Listener> _listeners;

   private Map<Position, PathWithCost> _cachedOptimalPathsForTravellingUnit;
   private LogicalUnit _unitForWhichOptimalPathsAreCached;

   private Faction getNextFactionInTurn() {
      for (int i = 0; i < _factionsInTurnOrder.size(); i++) {
         if (_factionsInTurnOrder.get(i).equals(_currentlyActiveFaction)) {
            int indexOfNextFaction = i + 1;
            if (indexOfNextFaction == _factionsInTurnOrder.size()) {
               indexOfNextFaction = 0;
            }
            return _factionsInTurnOrder.get(indexOfNextFaction);
         }
      }
      throw new RuntimeException("Not gonna happen");
   }

   public LogicalWarGame(LogicalWarMap logicalWarMap, List<Faction> factionsInTurnOrder) {
      _logicalWarMap = logicalWarMap;
      _movementLogic = new MovementLogic();
      _fuelLogic = new FuelLogic();
      _attackLogic = new AttackLogic();
      _transportLogic = new TransportLogic();
      _positionsOfUnits = new HashMap<LogicalUnit, Position>();
      _unitsAtPositions = new HashMap<Position, LogicalUnit>();
      _transportedUnits = new HashMap<LogicalUnit, List<LogicalUnit>>(0);
      _factionsInTurnOrder = new ArrayList<Faction>(factionsInTurnOrder);
      _factionsOfUnits = new HashMap<LogicalUnit, Faction>();
      _unitsInFaction = new HashMap<Faction, Collection<LogicalUnit>>(_factionsInTurnOrder.size());
      _transportedUnitsInFaction = new HashMap<Faction, Collection<LogicalUnit>>(0);
      for (Faction faction : _factionsInTurnOrder) {
         _unitsInFaction.put(faction, new HashSet<LogicalUnit>());
         _transportedUnitsInFaction.put(faction, new HashSet<LogicalUnit>(0));
      }
      _currentlyActiveFaction = _factionsInTurnOrder.get(0);
      System.out.println("Current Faction is " + _currentlyActiveFaction.toString());
      _factionOwningProperty = new HashMap<Position, Faction>();
      _hqsOfFactions = new HashMap<Faction, Position>(_factionsInTurnOrder.size());
      _positionsOfHqs = new HashMap<Position, Faction>(_factionsInTurnOrder.size());
      _gameStarted = false;
      _unitsLeftToMoveThisTurn = new HashSet<LogicalUnit>();
      findHqs();
      _listeners = new HashSet<Listener>(0);
   }

   public void addListener(Listener listener) {
      _listeners.add(listener);
   }

   private void fireTransportedUnitDestroyed(LogicalUnit logicalUnit) {
      for (Listener listener : _listeners) {
         listener.transportedUnitWasDestroyed(logicalUnit);
      }
   }

   private void fireUnitDestroyed(LogicalUnit logicalUnit) {
      for (Listener listener : _listeners) {
         listener.unitWasDestroyed(logicalUnit);
      }
   }

   private void findHqs() {
      Collection<Position> positionsOfHqs = _logicalWarMap.findHqs();
      for (Position hqPosition : positionsOfHqs) {
         _positionsOfHqs.put(hqPosition, Faction.NEUTRAL);
      }
   }

   public void setFactionForProperty(Position positionOfProperty, Faction faction) {
      if (_gameStarted) {
         throw new IllegalStateException("setFactionForProperty not allowed after game start!");
      }
      TerrainType terrain = _logicalWarMap.getTerrainForTile(positionOfProperty);
      if (_factionOwningProperty.get(positionOfProperty) != null) {
         throw new FactionAlreadySetException();
      }
      if (terrain.equals(TerrainType.HQ)) {
         _positionsOfHqs.put(positionOfProperty, faction);
         _hqsOfFactions.put(faction, positionOfProperty);
      }
      _factionOwningProperty.put(positionOfProperty, faction);
   }

   public void addUnit(LogicalUnit unit, Position position, Faction faction) {
      if (_unitsAtPositions.containsKey(position)) {
         throw new UnitCollisionException();
      }
      if (!_factionsInTurnOrder.contains(faction)) {
         throw new FactionNotInGameException();
      }
      if (_gameStarted) {
         throw new IllegalStateException("addUnit not allowed after game start!");
      }
      _unitsAtPositions.put(position, unit);
      _positionsOfUnits.put(unit, position);
      _unitsInFaction.get(faction).add(unit);
      _factionsOfUnits.put(unit, faction);
   }

   public void callGameStart() {
      if (!allFactionsHaveSetTheirHq()) {
         throw new HqNotSetException();
      }
      _gameStarted = true;
      activateAllNonTransportedUnitsInCurrentFaction();
   }

   private void activateAllNonTransportedUnitsInCurrentFaction() {
      Collection<LogicalUnit> nonTransportedUnitsInFaction = new HashSet<LogicalUnit>(_unitsInFaction.get(_currentlyActiveFaction));
      nonTransportedUnitsInFaction.removeAll(_transportedUnitsInFaction.get(_currentlyActiveFaction));
      _unitsLeftToMoveThisTurn.addAll(nonTransportedUnitsInFaction);
   }

   private boolean allFactionsHaveSetTheirHq() {
      return _hqsOfFactions.size() == _factionsInTurnOrder.size();
   }

   public Position getHqPosition(Faction faction) {
      return _hqsOfFactions.get(faction);
   }

   public List<Faction> getFactionsInGame() {
      return new ArrayList<Faction>(_factionsInTurnOrder);
   }

   public Collection<LogicalUnit> getAllUnits() {
      return new HashSet<LogicalUnit>(_factionsOfUnits.keySet());
   }

   private void removeUnit(LogicalUnit unit) {
      Position position = _positionsOfUnits.get(unit);
      Faction faction = _factionsOfUnits.get(unit);
      _factionsOfUnits.remove(unit);
      _positionsOfUnits.remove(unit);
      _unitsAtPositions.remove(position);
      _unitsInFaction.get(faction).remove(unit);
   }

   public PotentiallyInfiniteInteger getFuelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile) {
      return _fuelLogic.getFuelCostForMovementTypeOnTerrainType(travellingUnit.getMovementType(), _logicalWarMap.getTerrainForTile(tile));
   }

   public PotentiallyInfiniteInteger getTravelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile) {
      LogicalUnit destinationUnit = _unitsAtPositions.get(tile);
      if (destinationUnit != null && unitsAreEnemies(travellingUnit, destinationUnit)) {
         return PotentiallyInfiniteInteger.infinite();
      } else {
         return _movementLogic.getTravelCostForMovementTypeOnTerrainType(travellingUnit.getMovementType(), _logicalWarMap.getTerrainForTile(tile));
      }
   }

   public Collection<Position> getAdjacentPositions(Position position) {
      Collection<Position> adjacentPositions = position.getAdjacentPositions();
      Collection<Position> adjacentPositionsInsideMap = new HashSet<Position>(2);
      for (Position adjacentPosition : adjacentPositions) {
         if (_logicalWarMap.isPositionInsideMap(adjacentPosition)) {
            adjacentPositionsInsideMap.add(adjacentPosition);
         }
      }
      return adjacentPositionsInsideMap;
   }

   private Set<LogicalUnit> getAdjacentUnits(Position position) {
      Collection<Position> adjacentPositions = getAdjacentPositions(position);
      Set<LogicalUnit> adjacentUnits = new HashSet<LogicalUnit>(4);
      for (Position adjacentPosition : adjacentPositions) {
         addUnitFromPositionToList(adjacentUnits, adjacentPosition);
      }
      return adjacentUnits;
   }

   public LogicalUnit getUnitAtPosition(Position position) {
      if (!hasUnitAtPosition(position)) {
         throw new UnitNotFoundException();
      }
      return _unitsAtPositions.get(position);
   }

   public Position getPositionOfUnit(LogicalUnit logicalUnit) {
      if (!hasUnit(logicalUnit)) {
         throw new UnitNotFoundException();
      }
      return _positionsOfUnits.get(logicalUnit);
   }

   private boolean hasUnit(LogicalUnit logicalUnit) {
      return _positionsOfUnits.containsKey(logicalUnit);
   }

   public Set<LogicalUnit> getSuppliableUnitsAfterMove(LogicalUnit supplier, Path path) {
      Position destination = path.getFinalPosition();
      Set<LogicalUnit> suppliableUnits;
      suppliableUnits = getUnitsSuppliableFromPosition(supplier, destination);
      return suppliableUnits;
   }

   private Set<LogicalUnit> getUnitsSuppliableFromPosition(LogicalUnit supplier, Position supplyingPosition) {
      Set<LogicalUnit> adjacentUnits = getAdjacentUnits(supplyingPosition);
      return getUnitsSuppliableByUnit(adjacentUnits, supplier);
   }

   private Set<LogicalUnit> getUnitsSuppliableByUnit(Set<LogicalUnit> targetUnits, LogicalUnit supplier) {
      Set<LogicalUnit> suppliableUnits = new HashSet<LogicalUnit>(0);
      for (LogicalUnit targetUnit : targetUnits) {
         if (!supplier.equals(targetUnit) && !unitsAreEnemies(supplier, targetUnit)) {
            if (_fuelLogic.canResupplyUnit(supplier.getType(), targetUnit.getType())) {
               // TODO: If attacker is out of ammo AND cannot attack with secondary weapon!?
               suppliableUnits.add(targetUnit);
            }
         }
      }
      return suppliableUnits;
   }

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

   private Set<LogicalUnit> getUnitsOnPositions(Collection<Position> positions) {
      Set<LogicalUnit> units = new HashSet<LogicalUnit>();
      for (Position position : positions) {
         LogicalUnit logicalUnit = _unitsAtPositions.get(position);
         if (logicalUnit != null) {
            units.add(logicalUnit);
         }
      }
      return units;
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
         addPositionIfInsideMap(x, y,positions);
      }
      for (int stepCount = 0; stepCount < nrOfSteps; stepCount++) {
         x--;
         y++;
         addPositionIfInsideMap(x, y,positions);
      }
      for (int stepCount = 0; stepCount < nrOfSteps; stepCount++) {
         x--;
         y--;
         addPositionIfInsideMap(x, y,positions);
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
      if (_logicalWarMap.isPositionInsideMap(position)) {
         positions.add(position);
      }
   }

   private Set<LogicalUnit> getUnitsDirectlyAttackableFromPosition(LogicalUnit attacker, Position attackingPosition) {
      Set<LogicalUnit> adjacentUnits = getAdjacentUnits(attackingPosition);
      return getUnitsAttackableByUnit(adjacentUnits, attacker);
   }

   private Set<LogicalUnit> getUnitsAttackableByUnit(Set<LogicalUnit> targetUnits, LogicalUnit attacker) {
      Set<LogicalUnit> attackableUnits = new HashSet<LogicalUnit>(0);
      for (LogicalUnit targetUnit : targetUnits) {
         if (unitsAreEnemies(attacker, targetUnit)) {
            if (_attackLogic.canAttack(attacker.getType(), targetUnit.getType())) {
               // TODO: If attacker is out of ammo AND cannot attack with secondary weapon!?
               attackableUnits.add(targetUnit);
            }
         }
      }
      return attackableUnits;
   }

   private boolean unitsAreEnemies(LogicalUnit oneUnit, LogicalUnit otherUnit) {
      return !getFactionForUnit(oneUnit).equals(getFactionForUnit(otherUnit));
   }

   private void addUnitFromPositionToList(Collection<LogicalUnit> unitList, Position position) {
      LogicalUnit logicalUnit = _unitsAtPositions.get(position);
      if (logicalUnit != null) {
         unitList.add(logicalUnit);
      }
   }

   public Faction getFactionForUnit(LogicalUnit logicalUnit) {
      return _factionsOfUnits.get(logicalUnit);
   }

   @Override
   public void executeMove(LogicalUnit logicalUnit, Path path) {
      internalExecuteMove(logicalUnit, path);
      _unitsLeftToMoveThisTurn.remove(logicalUnit);
   }

   private void internalExecuteMove(LogicalUnit logicalUnit, Path path) {
      // TODO: Check if move is allowed
      Position oldPosition = _positionsOfUnits.get(logicalUnit);
      _unitsAtPositions.remove(oldPosition);
      _unitsAtPositions.put(path.getFinalPosition(), logicalUnit);
      _positionsOfUnits.put(logicalUnit, path.getFinalPosition());
      subtractFuelForUnitAndPath(path, logicalUnit);
      invalidateOptimalPathsCache();
   }

   private void subtractFuelForUnitAndPath(Path path, LogicalUnit logicalUnit) {
      Cost cost = getCostForUnitAndPath(path, logicalUnit);
      PotentiallyInfiniteInteger fuelCost = cost.getFuelCost();
      if (fuelCost.isInfinite()) {
         throw new LogicException();
      }
      logicalUnit.subtractFuel(fuelCost.getInteger());
   }

   @Override
   public void executeAttackMove(LogicalUnit movingUnit, Path path, LogicalUnit attackedUnit) {
      // TODO: Check if move + attack is allowed
      internalExecuteMove(movingUnit, path);
      internalExecuteAttack(movingUnit, attackedUnit);
      _unitsLeftToMoveThisTurn.remove(movingUnit);
   }

   private void internalExecuteAttack(LogicalUnit attackingUnit, LogicalUnit defendingUnit) {
      doDamage(attackingUnit, defendingUnit);
      if (canCounterAttack(attackingUnit, defendingUnit)) {
         doDamage(defendingUnit, attackingUnit);
      }
      invalidateOptimalPathsCache();
   }

   private void doDamage(LogicalUnit attackingUnit, LogicalUnit defendingUnit) {
      TerrainType defenderTerrain = _logicalWarMap.getTerrainForTile(getPositionOfUnit(defendingUnit));
      int percentDamageForDefender = _attackLogic.calculateDamageInPercent(attackingUnit, defendingUnit, defenderTerrain, 1.0f, 1.0f);
      subtractDamageForUnit(defendingUnit, percentDamageForDefender);
   }

   private boolean canCounterAttack(LogicalUnit attackingUnit, LogicalUnit defendingUnit) {
      return nonTransportedUnitExists(defendingUnit) &&
            !attackingUnit.isRanged() &&
            !defendingUnit.isRanged() &&
            _attackLogic.canAttack(defendingUnit.getType(), attackingUnit.getType());
   }

   public int calculateDamageInPercent(LogicalUnit attackingUnit, LogicalUnit defendingUnit) {
      if (!_attackLogic.canAttack(attackingUnit.getType(), defendingUnit.getType())) {
         throw new LogicException();
      }
      TerrainType defenderTerrain = _logicalWarMap.getTerrainForTile(getPositionOfUnit(defendingUnit));
      return _attackLogic.calculateDamageInPercent(attackingUnit, defendingUnit, defenderTerrain, 1.0f, 1.0f);
   }

   public void executeSupplyMove(LogicalUnit movingUnit, Path path) {
      internalExecuteMove(movingUnit, path);
      internalExecuteSupply(movingUnit);
      _unitsLeftToMoveThisTurn.remove(movingUnit);
   }

   private void resupplyFromAllAtcs(Faction faction) {
      Set<LogicalUnit> apcsInActiveFaction = getAllUnitFromFactionOfType(faction, UnitType.APC);
      for (LogicalUnit apc : apcsInActiveFaction) {
         try {
            internalExecuteSupply(apc);
         } catch (NoSuppliableUnitsInRangeException ignore) {}
      }
   }

   private Set<LogicalUnit> getAllUnitFromFactionOfType(Faction faction, UnitType type) {
      Set<LogicalUnit> unitsFromFactionOfType = new HashSet<LogicalUnit>();
      for (LogicalUnit logicalUnit : _unitsInFaction.get(faction)) {
         if (logicalUnit.getType().equals(type)) {
            unitsFromFactionOfType.add(logicalUnit);
         }
      }
      return unitsFromFactionOfType;
   }

   private void internalExecuteSupply(LogicalUnit supplier) {
      Position supplierPosition = getPositionOfUnit(supplier);
      Set<LogicalUnit> suppliedUnits = getUnitsSuppliableFromPosition(supplier, supplierPosition);
      if (suppliedUnits.isEmpty()) {
         throw new NoSuppliableUnitsInRangeException("Can't use supply move with no suppliable units in range!");
      }
      for (LogicalUnit suppliedUnit : suppliedUnits) {
         _fuelLogic.resupplyUnit(suppliedUnit);
      }
      invalidateOptimalPathsCache();
   }

   public void executeUnloadMove(LogicalUnit transport, LogicalUnit unloadingUnit, Path movementPath, Position unloadPosition) {
      internalExecuteMove(transport, movementPath);
      unloadUnitFromTransport(transport, unloadingUnit, unloadPosition);
      _unitsLeftToMoveThisTurn.remove(transport);
   }

   public void executeLoadMove(LogicalUnit movingUnit, Path path) {
      LogicalUnit transport = getUnitAtPosition(path.getFinalPosition());
      if (!canLoadOnto(movingUnit, transport)) {
         throw new LogicException("Cannot load onto that unit!");
      }
      internalExecuteLoadMove(movingUnit, path, transport);
   }

   private void internalExecuteLoadMove(LogicalUnit movingUnit, Path movementPath, LogicalUnit transport) {
      loadUnitOntoTransport(movingUnit, transport);
      subtractFuelForUnitAndPath(movementPath, movingUnit);
      _unitsLeftToMoveThisTurn.remove(movingUnit);
   }

   public boolean canLoadOnto(LogicalUnit loadingUnit, LogicalUnit transporter) {
      return !unitsAreEnemies(loadingUnit, transporter) &&
            _transportLogic.canTransport(transporter.getType(), loadingUnit.getType()) &&
            getTransportedUnits(transporter).size() < _transportLogic.getTransportLimit(transporter.getType());
   }

   public List<LogicalUnit> getTransportedUnits(LogicalUnit transporter) {
      List<LogicalUnit> unitsOnTransport = _transportedUnits.get(transporter);
      if (unitsOnTransport == null) {
         return new ArrayList<LogicalUnit>(0);
      } else {
         return new ArrayList<LogicalUnit>(unitsOnTransport);
      }
   }

   private void loadUnitOntoTransport(LogicalUnit movingUnit, LogicalUnit transport) {
      List<LogicalUnit> unitsOnThisTransport = _transportedUnits.get(transport);
      if (unitsOnThisTransport == null) {
         unitsOnThisTransport = new ArrayList<LogicalUnit>(1);
         _transportedUnits.put(transport, unitsOnThisTransport);
      }
      unitsOnThisTransport.add(movingUnit);
      _transportedUnitsInFaction.get(_factionsOfUnits.get(movingUnit)).add(movingUnit);

      Position oldPositionOfMovingUnit = _positionsOfUnits.get(movingUnit);
      _unitsAtPositions.remove(oldPositionOfMovingUnit);
      _positionsOfUnits.remove(movingUnit);
   }

   private void unloadUnitFromTransport(LogicalUnit transport, LogicalUnit unloadingUnit, Position unloadPosition) {
      removeUnitFromTransport(transport, unloadingUnit);
      _unitsAtPositions.put(unloadPosition, unloadingUnit);
      _positionsOfUnits.put(unloadingUnit, unloadPosition);
   }

   private void removeUnitFromTransport(LogicalUnit transport, LogicalUnit transportedUnit) {
      List<LogicalUnit> unitsOnThisTransport = _transportedUnits.get(transport);
      if (!unitsOnThisTransport.contains(transportedUnit)) {
         throw new LogicException();
      }
      unitsOnThisTransport.remove(transportedUnit);
      if (unitsOnThisTransport.isEmpty()) {
         _transportedUnits.remove(transport);
      }
      _transportedUnitsInFaction.get(_factionsOfUnits.get(transportedUnit)).remove(transportedUnit);
   }

   private boolean nonTransportedUnitExists(LogicalUnit logicalUnit) {
      return _positionsOfUnits.containsKey(logicalUnit);
   }

   private void subtractDamageForUnit(LogicalUnit damagedUnit, int damageInPercent) {
      int newHp = damagedUnit.getHp1To100() - damageInPercent;
      damagedUnit.setHp1To100(newHp);
      if (damagedUnit.getHp1To10() <= 0) {
         destroyUnit(damagedUnit);
      }
   }

   private void destroyUnit(LogicalUnit destroyedUnit) {
      fireUnitDestroyed(destroyedUnit);
      removeUnit(destroyedUnit);
      for (LogicalUnit transportedUnit : getTransportedUnits(destroyedUnit)) {
         fireTransportedUnitDestroyed(transportedUnit);
         removeUnitFromTransport(destroyedUnit, transportedUnit);
      }
      invalidateOptimalPathsCache();
   }

   public void endTurn() {
      _unitsLeftToMoveThisTurn.clear();
      _currentlyActiveFaction = getNextFactionInTurn();
      System.out.println("Current Faction is " + _currentlyActiveFaction.toString());
      activateAllNonTransportedUnitsInCurrentFaction();
      invalidateOptimalPathsCache();
      doBeginningOfTurn();
   }

   private void doBeginningOfTurn() {
      // TODO: Do beginning-of-turn stuff for next faction.
      resupplyFromAllAtcs(_currentlyActiveFaction);
      // Add income from properties
      // Repair units on friendly bases
      // Subtract per-day fuel consumptions
      // Resupply all units on appropriate bases or adjacent to resupply units
      // Check for crashing aircraft or ships

      // Question: Should resupply or healing be prioritized when not enough funds for both?
   }

   public boolean hasActiveUnitAtPosition(Position position) {
      LogicalUnit unit = _unitsAtPositions.get(position);
      return unit != null && !unitHasMovedThisTurn(unit);
   }

   private boolean unitHasMovedThisTurn(LogicalUnit unit) {
       return unitBelongsToCurrentlyActiveFaction(unit) && !_unitsLeftToMoveThisTurn.contains(unit);
   }

   private boolean unitBelongsToCurrentlyActiveFaction(LogicalUnit unit) {
      return _factionsOfUnits.get(unit).equals(_currentlyActiveFaction);
   }

   @Override
   public boolean hasUnitAtPosition(Position position) {
      return _unitsAtPositions.get(position) != null;
   }

   @Override
   public Set<Position> getAllReachablePoints(LogicalUnit travellingUnit) {
      return getAndCacheOptimalPathsToAllReachablePoints(travellingUnit).keySet();
   }

   private void invalidateOptimalPathsCache() {
      _cachedOptimalPathsForTravellingUnit = null;
      _unitForWhichOptimalPathsAreCached = null;
   }

   @Override
   public Path getOptimalPathForUnitToDestination(LogicalUnit travellingUnit, Position destination) {
      Map<Position, PathWithCost> optimalPaths = getAndCacheOptimalPathsToAllReachablePoints(travellingUnit);
      return optimalPaths.get(destination).getPath();
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

   private Cost getCostForUnitAndPath(Path path, LogicalUnit logicalUnit) {
      CostCalculator costCalculator = createCostCalculatorForUnit(logicalUnit);
      Cost totalCost = new Cost();
      for (Position step : path.getPositionList()) {
         totalCost = Cost.add(totalCost, costCalculator.getCostForPosition(step));
      }
      return totalCost;
   }

   public boolean isPathAllowedForUnit(Path path, LogicalUnit movingUnit) {
      Cost totalCost = getCostForUnitAndPath(path, movingUnit);
      return totalCost.isEqualOrBetterThan(getCostLimitForUnit(movingUnit));
   }

   private CostCalculator createCostCalculatorForUnit(final LogicalUnit logicalUnit) {
      return new CostCalculator() {
         @Override
         public Cost getCostForPosition(Position position) {
            PotentiallyInfiniteInteger movementCost = getTravelCostForUnitOnTile(logicalUnit, position);
            PotentiallyInfiniteInteger fuelCost = getFuelCostForUnitOnTile(logicalUnit, position);
            return new Cost(movementCost, fuelCost);
         }
      };
   }

   private Cost getCostLimitForUnit(LogicalUnit logicalUnit) {
      PotentiallyInfiniteInteger movementLimit = PotentiallyInfiniteInteger.create(logicalUnit.getBaseMovementRange());
      PotentiallyInfiniteInteger fuelLimit = PotentiallyInfiniteInteger.create(logicalUnit.getFuel());
      return new Cost(movementLimit, fuelLimit);
   }

   public LogicalWarMap getLogicalWarMap() {
      return _logicalWarMap;
   }

   public boolean isPositionInsideMap(Position position) {
      return _logicalWarMap.isPositionInsideMap(position);
   }

   private class NoSuppliableUnitsInRangeException extends LogicException {
      public NoSuppliableUnitsInRangeException(String s) {
         super(s);
      }
   }
}
