package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.pathfinding.*;

import java.util.*;

/**
 * Created by Linus on 2014-09-19.
 */
public class LogicalWarGame implements WarGameMoves, WarGameSetup {

   public interface Listener {
      void unitWasDestroyed(LogicalUnit logicalUnit);
      void transportedUnitWasDestroyed(LogicalUnit logicalUnit);
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
   private final Map<Faction, Position> _hqsOfFactions;
   private final Map<Position, Faction> _positionsOfHqs;

   private Faction _currentlyActiveFaction;
   private final List<Faction> _factionsInTurnOrder;

   private boolean _gameStarted;
   private Collection<LogicalUnit> _unitsLeftToMoveThisTurn;

   private final Collection<Listener> _listeners;

   private final WarGameQueries _queries;

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
      _listeners = new HashSet<Listener>(0);
      _queries = new WarGameQueriesImpl(this);
      findHqs();
   }

   private void findHqs() {
      Collection<Position> positionsOfHqs = _logicalWarMap.findHqs();
      for (Position hqPosition : positionsOfHqs) {
         _positionsOfHqs.put(hqPosition, Faction.NEUTRAL);
      }
   }

   // Listening

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

   // Setup

   @Override
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

   @Override
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

   @Override
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

   // Fuel and map query
   PotentiallyInfiniteInteger getFuelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile) {
      return _fuelLogic.getFuelCostForMovementTypeOnTerrainType(travellingUnit.getMovementType(), _logicalWarMap.getTerrainForTile(tile));
   }

   // Movement & map query
   PotentiallyInfiniteInteger getTravelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile) {
      if (hasUnitAtPosition(tile) && unitsAreEnemies(travellingUnit, getUnitAtPosition(tile))) {
         return PotentiallyInfiniteInteger.infinite();
      }
      return _movementLogic.getTravelCostForMovementTypeOnTerrainType(travellingUnit.getMovementType(), _logicalWarMap.getTerrainForTile(tile));
   }

   // Extended query
   public Collection<Position> getAdjacentPositions(Position position) {
      Collection<Position> adjacentPositions = position.getAdjacentPositions();
      Collection<Position> adjacentPositionsInsideMap = new HashSet<Position>(2);
      for (Position adjacentPosition : adjacentPositions) {
         if (isPositionInsideMap(adjacentPosition)) {
            adjacentPositionsInsideMap.add(adjacentPosition);
         }
      }
      return adjacentPositionsInsideMap;
   }

   // Extended query
   private Set<LogicalUnit> getAdjacentUnits(Position position) {
      Collection<Position> adjacentPositions = getAdjacentPositions(position);
      Set<LogicalUnit> adjacentUnits = new HashSet<LogicalUnit>(4);
      for (Position adjacentPosition : adjacentPositions) {
         addUnitFromPositionToList(adjacentUnits, adjacentPosition);
      }
      return adjacentUnits;
   }

   // Extended query
   public Set<LogicalUnit> getSuppliableUnitsAfterMove(LogicalUnit supplier, Path path) {
      Position destination = path.getFinalPosition();
      Set<LogicalUnit> suppliableUnits;
      suppliableUnits = getUnitsSuppliableFromPosition(supplier, destination);
      return suppliableUnits;
   }

   // Extended query
   private Set<LogicalUnit> getUnitsSuppliableFromPosition(LogicalUnit supplier, Position supplyingPosition) {
      Set<LogicalUnit> adjacentUnits = getAdjacentUnits(supplyingPosition);
      return getUnitsSuppliableByUnit(adjacentUnits, supplier);
   }

   // Fuel query (plus todo)
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

   // Extended query
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

   // Extended query
   private Set<LogicalUnit> getUnitsAttackableFromPosition(LogicalUnit attacker, Position attackingPosition) {
      if (attacker.isRanged()) {
         return getUnitsRemotelyAttackableFromPosition(attacker, attackingPosition);
      } else if (attacker.isCombat()) {
         return getUnitsDirectlyAttackableFromPosition(attacker, attackingPosition);
      } else {
         return new HashSet<LogicalUnit>(0);
      }
   }

   // Extended query
   private Set<LogicalUnit> getUnitsRemotelyAttackableFromPosition(LogicalUnit attacker, Position attackingPosition) {
      Set<Position> targetPositions = getPositionsRemotelyAttackableFromPosition(attackingPosition, attacker.getBaseMinAttackRange(), attacker.getBaseMaxAttackRange());
      Set<LogicalUnit> targetUnits = getUnitsOnPositions(targetPositions);
      Set<LogicalUnit> attackableTargetUnits = getUnitsAttackableByUnit(targetUnits, attacker);
      return attackableTargetUnits;
   }

   // Basic or Extended query
   private Set<LogicalUnit> getUnitsOnPositions(Collection<Position> positions) {
      Set<LogicalUnit> units = new HashSet<LogicalUnit>();
      for (Position position : positions) {
         if (hasUnitAtPosition(position)) {
            units.add(getUnitAtPosition(position));
         }
      }
      return units;
   }

   // Extended query
   private Set<Position> getPositionsRemotelyAttackableFromPosition(Position attackingPosition, int minRange, int maxRange) {
      Set<Position> attackablePositions = new HashSet<Position>(0);
      for (int range = minRange; range <= maxRange; range++) {
         attackablePositions.addAll(getPositionsXStepsAwayFrom(attackingPosition, range));
      }
      return attackablePositions;
   }

   // Extended query
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

   // Extended query
   private void addPositionIfInsideMap(int x, int y, Collection<Position> positions) {
      Position position = new Position(x, y);
      if (isPositionInsideMap(position)) {
         positions.add(position);
      }
   }

   // Extended query
   private Set<LogicalUnit> getUnitsDirectlyAttackableFromPosition(LogicalUnit attacker, Position attackingPosition) {
      Set<LogicalUnit> adjacentUnits = getAdjacentUnits(attackingPosition);
      return getUnitsAttackableByUnit(adjacentUnits, attacker);
   }

   // Attack query (plus todo)
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

   // Extended query
   private boolean unitsAreEnemies(LogicalUnit oneUnit, LogicalUnit otherUnit) {
      return !getFactionForUnit(oneUnit).equals(getFactionForUnit(otherUnit));
   }

   // Extended operation
   private void addUnitFromPositionToList(Collection<LogicalUnit> unitList, Position position) {
      if (hasUnitAtPosition(position)) {
         unitList.add(getUnitAtPosition(position));
      }
   }

   // Extended query
   private boolean unitsBelongToSameFaction(LogicalUnit unitOne, LogicalUnit unitTwo) {
      return getFactionForUnit(unitOne).equals(getFactionForUnit(unitTwo));
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
      _queries.invalidateOptimalPathsCache();
   }

   private void subtractFuelForUnitAndPath(Path path, LogicalUnit logicalUnit) {
      Cost cost = _queries.getCostForUnitAndPath(path, logicalUnit);
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
      _queries.invalidateOptimalPathsCache();
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

   @Override
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
      _queries.invalidateOptimalPathsCache();
   }

   @Override
   public void executeUnloadMove(LogicalUnit transport, LogicalUnit unloadingUnit, Path movementPath, Position unloadPosition) {
      internalExecuteMove(transport, movementPath);
      unloadUnitFromTransport(transport, unloadingUnit, unloadPosition);
      _unitsLeftToMoveThisTurn.remove(transport);
   }

   @Override
   public void executeLoadMove(LogicalUnit movingUnit, Path path) {
      LogicalUnit transport = getUnitAtPosition(path.getFinalPosition());
      if (!canLoadOnto(movingUnit, transport)) {
         throw new LogicException("Cannot load onto that unit!");
      }
      internalExecuteLoadMove(movingUnit, path, transport);
      _unitsLeftToMoveThisTurn.remove(movingUnit);
   }

   private void internalExecuteLoadMove(LogicalUnit movingUnit, Path movementPath, LogicalUnit transport) {
      subtractFuelForUnitAndPath(movementPath, movingUnit);
      loadUnitOntoTransport(movingUnit, transport);
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
      _queries.invalidateOptimalPathsCache();
   }

   @Override
   public void executeJoinMove(LogicalUnit movingUnit, Path movementPath, LogicalUnit joinedUnit) {
      // TODO: Check if path is allowed.
      if (!canJoin(movingUnit, joinedUnit)) {
         throw new LogicException("Units cannot join!");
      }
      internalExecuteJoinMove(movingUnit, joinedUnit);
      _unitsLeftToMoveThisTurn.remove(movingUnit);
   }

   private boolean canJoin(LogicalUnit joiningUnit, LogicalUnit joinedUnit) {
      return unitsBelongToSameFaction(joiningUnit, joinedUnit) &&
            unitsAreSameType(joiningUnit, joinedUnit) &&
            joiningUnit.getHp1To10() < 10 &&
            joinedUnit.getHp1To10() < 10;
   }

   private boolean unitsAreSameType(LogicalUnit unitOne, LogicalUnit unitTwo) {
      return unitOne.getType().equals(unitTwo.getType());
   }

   private void internalExecuteJoinMove(LogicalUnit movingUnit, LogicalUnit joinedUnit) {
      joinedUnit.setHp1To100(movingUnit.getHp1To100());
      joinedUnit.addFuel(movingUnit.getFuel());
      removeUnit(movingUnit);
   }

   private void removeUnit(LogicalUnit unit) {
      Position position = _positionsOfUnits.get(unit);
      Faction faction = _factionsOfUnits.get(unit);
      _factionsOfUnits.remove(unit);
      _positionsOfUnits.remove(unit);
      _unitsAtPositions.remove(position);
      _unitsInFaction.get(faction).remove(unit);
   }

   @Override
   public void endTurn() {
      _unitsLeftToMoveThisTurn.clear();
      _currentlyActiveFaction = getNextFactionInTurn();
      System.out.println("Current Faction is " + _currentlyActiveFaction.toString());
      activateAllNonTransportedUnitsInCurrentFaction();
      _queries.invalidateOptimalPathsCache();
      doBeginningOfTurn();
   }

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

   // Extended query
   public boolean hasActiveUnitAtPosition(Position position) {
      return hasUnitAtPosition(position) && !unitHasMovedThisTurn(getUnitAtPosition(position));
   }

   // Extended query
   private boolean unitHasMovedThisTurn(LogicalUnit unit) {
       return unitBelongsToCurrentlyActiveFaction(unit) && !unitCanStillMoveThisTurn(unit);
   }

   @Deprecated
   public Set<Position> getAllReachablePoints(LogicalUnit travellingUnit) {
      return _queries.getAllReachablePoints(travellingUnit);
   }

   @Deprecated
   public Path getOptimalPathForUnitToDestination(LogicalUnit travellingUnit, Position destination) {
      return _queries.getOptimalPathForUnitToDestination(travellingUnit, destination);
   }

   @Deprecated
   public boolean isPathAllowedForUnit(Path path, LogicalUnit movingUnit) {
      return _queries.isPathAllowedForUnit(path, movingUnit);
   }

   public LogicalWarMap getLogicalWarMap() {
      return _logicalWarMap;
   }

   // Map query
   public boolean isPositionInsideMap(Position position) {
      return _logicalWarMap.isPositionInsideMap(position);
   }

   // Basic query
   public Position getHqPosition(Faction faction) {
      return _hqsOfFactions.get(faction);
   }

   // Basic query
   public List<Faction> getFactionsInGame() {
      return new ArrayList<Faction>(_factionsInTurnOrder);
   }

   // Basic query
   public Faction getCurrentlyActiveFaction() {
      return _currentlyActiveFaction;
   }

   // Basic query
   public Collection<LogicalUnit> getAllUnits() {
      return new HashSet<LogicalUnit>(_factionsOfUnits.keySet());
   }

   // Basic query
   public LogicalUnit getUnitAtPosition(Position position) {
      if (!hasUnitAtPosition(position)) {
         throw new UnitNotFoundException();
      }
      return _unitsAtPositions.get(position);
   }

   // Basic query
   public Position getPositionOfUnit(LogicalUnit logicalUnit) {
      if (!hasUnit(logicalUnit)) {
         throw new UnitNotFoundException();
      }
      return _positionsOfUnits.get(logicalUnit);
   }

   // Basic query
   private boolean hasUnit(LogicalUnit logicalUnit) {
      return _positionsOfUnits.containsKey(logicalUnit);
   }

   // Basic query
   public Faction getFactionForUnit(LogicalUnit logicalUnit) {
      return _factionsOfUnits.get(logicalUnit);
   }

   // Basic query
   private boolean unitCanStillMoveThisTurn(LogicalUnit logicalUnit) {
      return _unitsLeftToMoveThisTurn.contains(logicalUnit);
   }

   // Basic query
   private boolean unitBelongsToCurrentlyActiveFaction(LogicalUnit unit) {
      return _factionsOfUnits.get(unit).equals(_currentlyActiveFaction);
   }

   // Basic query
   public boolean hasUnitAtPosition(Position position) {
      return _unitsAtPositions.get(position) != null;
   }

   private class NoSuppliableUnitsInRangeException extends LogicException {
      public NoSuppliableUnitsInRangeException(String s) {
         super(s);
      }
   }
}
