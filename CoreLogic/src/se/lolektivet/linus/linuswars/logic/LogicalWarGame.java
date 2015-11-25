package se.lolektivet.linus.linuswars.logic;

import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;
import se.lolektivet.linus.linuswars.logic.pathfinding.*;

import java.util.*;

/**
 * Created by Linus on 2014-09-19.
 */
public class LogicalWarGame implements WarGameMoves, WarGameSetup, BasicWarGameQueries {

   private static final int INCOME_PER_PROPERTY = 1000;

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
   private final Map<Faction, Integer> _moneyForFactions;

   private boolean _gameStarted;
   private Collection<LogicalUnit> _unitsLeftToMoveThisTurn;

   private final Collection<WarGameListener> _listeners;

   private WarGameQueries _queries;

   public LogicalWarGame(LogicalWarMap logicalWarMap, List<Faction> factionsInTurnOrder) {
      _logicalWarMap = logicalWarMap;
      _movementLogic = new MovementLogic();
      _fuelLogic = new FuelLogic();
      _attackLogic = new AttackLogic();
      _transportLogic = new TransportLogic();
      _positionsOfUnits = new HashMap<>();
      _unitsAtPositions = new HashMap<>();
      _transportedUnits = new HashMap<>(0);
      _factionsInTurnOrder = new ArrayList<>(factionsInTurnOrder);
      _factionsOfUnits = new HashMap<>();
      _unitsInFaction = new HashMap<>(_factionsInTurnOrder.size());
      _transportedUnitsInFaction = new HashMap<>(0);
      for (Faction faction : _factionsInTurnOrder) {
         _unitsInFaction.put(faction, new HashSet<>());
         _transportedUnitsInFaction.put(faction, new HashSet<>(0));
      }
      _currentlyActiveFaction = _factionsInTurnOrder.get(0);
      System.out.println("Current Faction is " + _currentlyActiveFaction.toString());
      _factionOwningProperty = new HashMap<>();
      _hqsOfFactions = new HashMap<>(_factionsInTurnOrder.size());
      _positionsOfHqs = new HashMap<>(_factionsInTurnOrder.size());
      _moneyForFactions = new HashMap<>(_factionsInTurnOrder.size());
      for (Faction faction : _factionsInTurnOrder) {
         _moneyForFactions.put(faction, 0);
      }
      _gameStarted = false;
      _unitsLeftToMoveThisTurn = new HashSet<>();
      _listeners = new HashSet<>(0);
      findHqs();
   }

   public void setQueries(WarGameQueries warGameQueries) {
      _queries = warGameQueries;
   }

   private void findHqs() {
      Collection<Position> positionsOfHqs = _logicalWarMap.findHqs();
      for (Position hqPosition : positionsOfHqs) {
         _positionsOfHqs.put(hqPosition, Faction.NEUTRAL);
      }
   }

   // Listening
   @Override
   public void addListener(WarGameListener listener) {
      _listeners.add(listener);
   }

   @Override
   public int getMapWidth() {
      return _logicalWarMap.getWidth();
   }

   @Override
   public int getMapHeight() {
      return _logicalWarMap.getHeight();
   }

   private void fireTransportedUnitDestroyed(LogicalUnit logicalUnit) {
      for (WarGameListener listener : _listeners) {
         listener.transportedUnitWasDestroyed(logicalUnit);
      }
   }

   private void fireUnitDestroyed(LogicalUnit logicalUnit) {
      for (WarGameListener listener : _listeners) {
         listener.unitWasDestroyed(logicalUnit);
      }
   }

   private void fireUnitJoined(LogicalUnit logicalUnit) {
      for (WarGameListener listener : _listeners) {
         listener.unitJoined(logicalUnit);
      }
   }

   // Setup

   @Override
   public void setFactionForProperty(Position positionOfProperty, Faction faction) {
      if (_gameStarted) {
         throw new IllegalStateException("setFactionForProperty not allowed after game start!");
      }
      if (_factionOwningProperty.get(positionOfProperty) != null) {
         throw new FactionAlreadySetException();
      }

      TerrainType terrain = _logicalWarMap.getTerrainForTile(positionOfProperty);
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
      // TODO: Throw on infinite movement cost for unit at position
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
      doBeginningOfTurn();
   }

   private void activateAllNonTransportedUnitsInCurrentFaction() {
      Collection<LogicalUnit> nonTransportedUnitsInFaction = new HashSet<>(_unitsInFaction.get(_currentlyActiveFaction));
      nonTransportedUnitsInFaction.removeAll(_transportedUnitsInFaction.get(_currentlyActiveFaction));
      _unitsLeftToMoveThisTurn.addAll(nonTransportedUnitsInFaction);
   }

   private boolean allFactionsHaveSetTheirHq() {
      return _hqsOfFactions.size() == _factionsInTurnOrder.size();
   }

   // Fuel and map query
   @Override
   public PotentiallyInfiniteInteger getFuelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile) {
      return _fuelLogic.getFuelCostForMovementTypeOnTerrainType(travellingUnit.getMovementType(), _logicalWarMap.getTerrainForTile(tile));
   }

   // Movement & map query
   @Override
   public PotentiallyInfiniteInteger getTravelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile) {
      if (hasUnitAtPosition(tile) && _queries.unitsAreEnemies(travellingUnit, getUnitAtPosition(tile))) {
         return PotentiallyInfiniteInteger.infinite();
      }
      return _movementLogic.getTravelCostForMovementTypeOnTerrainType(travellingUnit.getMovementType(), _logicalWarMap.getTerrainForTile(tile));
   }

   // Extended query
   @Override
   public Collection<Position> getAdjacentPositions(Position position) {
      Collection<Position> adjacentPositions = position.getAdjacentPositions();
      Collection<Position> adjacentPositionsInsideMap = new HashSet<>(2);
      for (Position adjacentPosition : adjacentPositions) {
         if (isPositionInsideMap(adjacentPosition)) {
            adjacentPositionsInsideMap.add(adjacentPosition);
         }
      }
      return adjacentPositionsInsideMap;
   }

   // Extended query
   @Override
   public Set<LogicalUnit> getAdjacentUnits(Position position) {
      Collection<Position> adjacentPositions = getAdjacentPositions(position);
      Set<LogicalUnit> adjacentUnits = new HashSet<>(4);
      for (Position adjacentPosition : adjacentPositions) {
         addUnitFromPositionToList(adjacentUnits, adjacentPosition);
      }
      return adjacentUnits;
   }

   // Extended query
   @Override
   public Set<LogicalUnit> getUnitsSuppliableFromPosition(LogicalUnit supplier, Position supplyingPosition) {
      Set<LogicalUnit> adjacentUnits = getAdjacentUnits(supplyingPosition);
      return getUnitsSuppliableByUnit(adjacentUnits, supplier);
   }

   // Fuel query (plus todo)
   private Set<LogicalUnit> getUnitsSuppliableByUnit(Set<LogicalUnit> targetUnits, LogicalUnit supplier) {
      Set<LogicalUnit> suppliableUnits = new HashSet<>(0);
      for (LogicalUnit targetUnit : targetUnits) {
         if (!supplier.equals(targetUnit) && !_queries.unitsAreEnemies(supplier, targetUnit)) {
            if (_fuelLogic.canResupplyUnit(supplier.getType(), targetUnit.getType())) {
               // TODO: If attacker is out of ammo AND cannot attack with secondary weapon!?
               suppliableUnits.add(targetUnit);
            }
         }
      }
      return suppliableUnits;
   }

   // Attack query (plus todo)
   @Override
   public Set<LogicalUnit> getUnitsAttackableByUnit(Set<LogicalUnit> targetUnits, LogicalUnit attacker) {
      Set<LogicalUnit> attackableUnits = new HashSet<>(0);
      for (LogicalUnit targetUnit : targetUnits) {
         if (_queries.unitsAreEnemies(attacker, targetUnit)) {
            if (_attackLogic.canAttack(attacker.getType(), targetUnit.getType())) {
               // TODO: If attacker is out of ammo AND cannot attack with secondary weapon!?
               attackableUnits.add(targetUnit);
            }
         }
      }
      return attackableUnits;
   }

   // Extended operation
   private void addUnitFromPositionToList(Collection<LogicalUnit> unitList, Position position) {
      if (hasUnitAtPosition(position)) {
         unitList.add(getUnitAtPosition(position));
      }
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

   @Override
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
      Set<LogicalUnit> apcsInActiveFaction = getAllUnitsFromFactionOfType(faction, UnitType.APC);
      for (LogicalUnit apc : apcsInActiveFaction) {
         try {
            internalExecuteSupply(apc);
         } catch (NoSuppliableUnitsInRangeException ignore) {}
      }
   }

   private Set<LogicalUnit> getAllUnitsFromFactionOfType(Faction faction, UnitType type) {
      Set<LogicalUnit> unitsFromFactionOfType = new HashSet<>();
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

   @Override
   public boolean canLoadOnto(LogicalUnit loadingUnit, LogicalUnit transporter) {
      return !_queries.unitsAreEnemies(loadingUnit, transporter) &&
            _transportLogic.canTransport(transporter.getType(), loadingUnit.getType()) &&
            !transportIsFull(transporter);
   }

   @Override
   public boolean canJoinWith(LogicalUnit joiningUnit, LogicalUnit joinedUnit) {
      return !_queries.unitsAreEnemies(joiningUnit, joinedUnit) &&
            joiningUnit.getType().equals(joinedUnit.getType()) &&
            joiningUnit.isDamaged() && joinedUnit.isDamaged();
   }

   private boolean transportIsFull(LogicalUnit transporter) {
      return getTransportedUnits(transporter).size() >= _transportLogic.getTransportLimit(transporter.getType());
   }

   @Override
   public List<LogicalUnit> getTransportedUnits(LogicalUnit transporter) {
      List<LogicalUnit> unitsOnTransport = _transportedUnits.get(transporter);
      if (unitsOnTransport == null) {
         return new ArrayList<>(0);
      } else {
         return new ArrayList<>(unitsOnTransport);
      }
   }

   private void loadUnitOntoTransport(LogicalUnit movingUnit, LogicalUnit transport) {
      List<LogicalUnit> unitsOnThisTransport = _transportedUnits.get(transport);
      if (unitsOnThisTransport == null) {
         unitsOnThisTransport = new ArrayList<>(1);
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
   public void executeJoinMove(LogicalUnit movingUnit, Path movementPath) {
      LogicalUnit joinedUnit = getUnitAtPosition(movementPath.getFinalPosition());
      // TODO: Check if path is allowed.
      if (!canJoin(movingUnit, joinedUnit)) {
         throw new LogicException("Units cannot join!");
      }
      internalExecuteJoinMove(movingUnit, joinedUnit);
      _unitsLeftToMoveThisTurn.remove(joinedUnit);
      _unitsLeftToMoveThisTurn.remove(movingUnit);
   }

   private boolean canJoin(LogicalUnit joiningUnit, LogicalUnit joinedUnit) {
      return unitsBelongToSameFaction(joiningUnit, joinedUnit) &&
            unitsAreSameType(joiningUnit, joinedUnit) &&
            joiningUnit.getHp1To10() < 10 &&
            joinedUnit.getHp1To10() < 10;
   }

   // Extended query
   private boolean unitsBelongToSameFaction(LogicalUnit unitOne, LogicalUnit unitTwo) {
      return getFactionForUnit(unitOne).equals(getFactionForUnit(unitTwo));
   }

   private boolean unitsAreSameType(LogicalUnit unitOne, LogicalUnit unitTwo) {
      return unitOne.getType().equals(unitTwo.getType());
   }

   private void internalExecuteJoinMove(LogicalUnit movingUnit, LogicalUnit joinedUnit) {
      joinedUnit.setHp1To100(Math.min(movingUnit.getHp1To100() + joinedUnit.getHp1To100(), 100));
      joinedUnit.addFuel(movingUnit.getFuel());
      fireUnitJoined(movingUnit);
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
      System.out.println("Current Faction is " + _currentlyActiveFaction.toString());
      activateAllNonTransportedUnitsInCurrentFaction();
      _queries.invalidateOptimalPathsCache();
      resupplyFromAllAtcs(_currentlyActiveFaction);
      addIncomeFromProperties(_currentlyActiveFaction);
      // Repair units on friendly bases
      // Subtract per-day fuel consumptions
      // Resupply all units on appropriate bases or adjacent to resupply units
      // Check for crashing aircraft or ships

      // Question: Should resupply or healing be prioritized when not enough funds for both?
   }

   private void addIncomeFromProperties(Faction faction) {
      for (Map.Entry<Position, Faction> propertyForFaction :  _factionOwningProperty.entrySet()) {
         if (faction == propertyForFaction.getValue()) {
            addMoneyForFaction(faction, INCOME_PER_PROPERTY);
         }
      }
   }

   private void addMoneyForFaction(Faction faction, int money) {
      _moneyForFactions.put(faction, _moneyForFactions.get(faction) + money);
   }

   public LogicalWarMap getLogicalWarMap() {
      return _logicalWarMap;
   }

   // Map query
   @Override
   public boolean isPositionInsideMap(Position position) {
      return _logicalWarMap.isPositionInsideMap(position);
   }

   // Basic query
   @Override
   public Position getHqPosition(Faction faction) {
      return _hqsOfFactions.get(faction);
   }

   @Override
   public int getMoneyForFaction(Faction faction) {
      return _moneyForFactions.get(faction);
   }

   // Basic query
   @Override
   public List<Faction> getFactionsInGame() {
      return new ArrayList<>(_factionsInTurnOrder);
   }

   // Basic query
   @Override
   public Faction getCurrentlyActiveFaction() {
      return _currentlyActiveFaction;
   }

   // Basic query
   public Collection<LogicalUnit> getAllUnits() {
      return new HashSet<>(_factionsOfUnits.keySet());
   }

   // Basic query
   @Override
   public LogicalUnit getUnitAtPosition(Position position) {
      if (!hasUnitAtPosition(position)) {
         throw new UnitNotFoundException();
      }
      return _unitsAtPositions.get(position);
   }

   // Basic query
   @Override
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
   @Override
   public Faction getFactionForUnit(LogicalUnit logicalUnit) {
      return _factionsOfUnits.get(logicalUnit);
   }

   // Basic query
   @Override
   public boolean unitCanStillMoveThisTurn(LogicalUnit logicalUnit) {
      return _unitsLeftToMoveThisTurn.contains(logicalUnit);
   }

   // Basic query
   @Override
   public boolean unitBelongsToCurrentlyActiveFaction(LogicalUnit unit) {
      return _factionsOfUnits.get(unit).equals(_currentlyActiveFaction);
   }

   // Basic query
   @Override
   public boolean hasUnitAtPosition(Position position) {
      return _unitsAtPositions.get(position) != null;
   }

   private class NoSuppliableUnitsInRangeException extends LogicException {
      public NoSuppliableUnitsInRangeException(String s) {
         super(s);
      }
   }
}
