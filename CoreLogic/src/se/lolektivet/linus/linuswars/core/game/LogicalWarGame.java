package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.IllegalSetupException;
import se.lolektivet.linus.linuswars.core.LogicException;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.TerrainType;
import se.lolektivet.linus.linuswars.core.enums.UnitType;
import se.lolektivet.linus.linuswars.core.pathfinding.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Linus on 2014-09-19.
 */
public class LogicalWarGame implements WarGameMoves, WarGameSetup, WarGameQueries {

   private static final Logger _logger = Logger.getLogger(LogicalWarGame.class.getName());

   // TODO: Big one! Have GraphicalWarGame and InteractiveWarGame implement at least WarGameSetup. Maybe the other two as well!

   private static final int INCOME_PER_PROPERTY = 1000;

   static class UnitCollisionException extends RuntimeException {}
   static class UnitNotFoundException extends RuntimeException {}
   static class FactionAlreadySetException extends RuntimeException {}
   static class FactionNotInGameException extends RuntimeException {}

   private final LogicalWarMap _logicalWarMap;
   private final MovementLogic _movementLogic;
   private final FuelLogic _fuelLogic;
   private final AttackLogic _attackLogic;
   private final TransportLogic _transportLogic;

   private final ModuleMoney _moneyModule;
   private final ModuleTurnOrder _turnOrderModule;
   private final ModuleUnits _unitModule;
   private final ModuleBuildings _buildingsModule;
   private final DeployLogic _deployLogic;

   private boolean _gameStarted;

   private final Collection<WarGameListener> _listeners;

   private Map<Position, PathWithCost> _cachedOptimalPathsForTravellingUnit;
   private LogicalUnit _unitForWhichOptimalPathsAreCached;

   public LogicalWarGame(LogicalWarMap logicalWarMap, List<Faction> factionsInTurnOrder) {
      _logicalWarMap = logicalWarMap;

      _movementLogic = new MovementLogic();
      _fuelLogic = new FuelLogic();
      _attackLogic = new AttackLogic();
      _transportLogic = new TransportLogic();
      _deployLogic = new DeployLogic(_fuelLogic);

      _unitModule = new ModuleUnits(factionsInTurnOrder);
      _turnOrderModule = new ModuleTurnOrder(factionsInTurnOrder);
      _buildingsModule = new ModuleBuildings();
      _moneyModule = new ModuleMoney();
      _moneyModule.init(factionsInTurnOrder);

      _gameStarted = false;
      _listeners = new HashSet<>(0);
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

   @Override
   public int getDayNumber() {
      return _turnOrderModule.getDayNumber();
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

   private void fireBuildingCaptured(Building building) {
      for (WarGameListener listener : _listeners) {
         listener.buildingWasCaptured(building);
      }
   }

   private void fireUnitDeployed(LogicalUnit logicalUnit, Position position) {
      for (WarGameListener listener : _listeners) {
         listener.unitDeployed(logicalUnit, position);
      }
   }

   // Setup

   @Override
   public void addBuilding(Position position, TerrainType terrainType, Faction faction) {
      _buildingsModule.addBuilding(position, terrainType, faction);
   }

   @Override
   public void addUnit(LogicalUnit unit, Position position, Faction faction) {
      if (_unitModule.hasUnitAtPosition(position)) {
         throw new UnitCollisionException();
      }
      if (!_turnOrderModule.factionIsInGame(faction)) {
         throw new FactionNotInGameException();
      }
      if (_gameStarted) {
         throw new IllegalStateException("addUnit not allowed after game start!");
      }
      // TODO: Throw on infinite movement cost for unit at position

      _unitModule.addUnit(unit, position, faction);
   }

   @Override
   public void setFundsForFaction(Faction faction, int money) {
      _moneyModule.addMoneyForFaction(faction, money);
   }

   @Override
   public void callGameStart() {
      if (_buildingsModule.getFactions().size() != _turnOrderModule.numberOfFactions()) {
         throw new IllegalSetupException("BuildingsModule had wrong number of factions! (" + _buildingsModule.getFactions().size() + " instead of " + _turnOrderModule.numberOfFactions() + ")");
      }
      _gameStarted = true;
      doBeginningOfTurn();
   }

   @Override
   public InfiniteInteger getFuelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile) {
      return _fuelLogic.getFuelCostForMovementTypeOnTerrainType(travellingUnit.getMovementType(), _logicalWarMap.getTerrainForTile(tile));
   }

   @Override
   public InfiniteInteger getTravelCostForUnitOnTile(LogicalUnit travellingUnit, Position tile) {
      if (hasUnitAtPosition(tile) && areEnemies(travellingUnit, getUnitAtPosition(tile))) {
         return InfiniteInteger.infinite();
      }
      return _movementLogic.getTravelCostForMovementTypeOnTerrainType(travellingUnit.getMovementType(), _logicalWarMap.getTerrainForTile(tile));
   }

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

   @Override
   public Set<LogicalUnit> getAdjacentUnits(Position position) {
      Collection<Position> adjacentPositions = getAdjacentPositions(position);
      Set<LogicalUnit> adjacentUnits = new HashSet<>(4);
      for (Position adjacentPosition : adjacentPositions) {
         addUnitFromPositionToList(adjacentUnits, adjacentPosition);
      }
      return adjacentUnits;
   }

   @Override
   public Set<LogicalUnit> getUnitsSuppliableFromPosition(LogicalUnit supplier, Position supplyingPosition) {
      Set<LogicalUnit> adjacentUnits = getAdjacentUnits(supplyingPosition);
      return getUnitsSuppliableByUnit(adjacentUnits, supplier);
   }

   @Override
   public boolean hasBuildingAtPosition(Position position) {
      return _buildingsModule.hasBuildingAtPosition(position);
   }

   @Override
   public Building getBuildingAtPosition(Position position) {
      return _buildingsModule.getBuildingAtPosition(position);
   }

   @Override
   public List<UnitType> getTypesDeployableFromBuilding(TerrainType buildingType) {
      return _deployLogic.getTypesDeployableFromBuilding(buildingType);
   }

   @Override
   public int getCostForNewUnit(UnitType unitType) {
      return _deployLogic.getCostForUnitType(unitType);
   }

   // Fuel query
   private Set<LogicalUnit> getUnitsSuppliableByUnit(Set<LogicalUnit> targetUnits, LogicalUnit supplier) {
      Set<LogicalUnit> suppliableUnits = new HashSet<>(0);
      for (LogicalUnit targetUnit : targetUnits) {
         if (!supplier.equals(targetUnit) && !areEnemies(supplier, targetUnit)) {
            if (_fuelLogic.canResupplyUnit(supplier.getType(), targetUnit.getType())) {
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
         if (areEnemies(attacker, targetUnit)) {
            if (_attackLogic.canAttack(attacker.getType(), targetUnit.getType())) {
               // TODO: If attacker is out of ammo AND cannot attack with secondary weapon!?
               attackableUnits.add(targetUnit);
            }
         }
      }
      return attackableUnits;
   }

   private void addUnitFromPositionToList(Collection<LogicalUnit> unitList, Position position) {
      if (hasUnitAtPosition(position)) {
         unitList.add(getUnitAtPosition(position));
      }
   }

   private void throwOnGameNotStarted() {
      if (!_gameStarted) {
         throw new LogicException("Cannot execute move before game has started!");
      }
   }

   private void throwOnUnitNotMovable(LogicalUnit logicalUnit) {
      if (!unitBelongsToCurrentlyActiveFaction(logicalUnit)) {
         throw new LogicException("Tried to move unit of faction " + _unitModule.getFactionForUnit(logicalUnit) +
               " on other faction's turn (" + getCurrentlyActiveFaction() + ")!");
      } else if (!unitCanStillMoveThisTurn(logicalUnit)) {
         throw new LogicException("Unit has already moved this turn!");
      }
   }

   private void checkExecuteMove(LogicalUnit logicalUnit, Path path) {
      throwOnGameNotStarted();
      throwOnUnitNotMovable(logicalUnit);
      throwOnIllegalPath(logicalUnit, path);
   }

   @Override
   public void executeMove(LogicalUnit logicalUnit, Path path) {
      checkExecuteMove(logicalUnit, path);
      internalExecuteMove(logicalUnit, path);
      _unitModule.expendUnitsTurn(logicalUnit);
   }

   private void internalExecuteMove(LogicalUnit logicalUnit, Path path) {
      _unitModule.moveUnit(logicalUnit, path.getFinalPosition());
      subtractFuelForUnitAndPath(path, logicalUnit);
      invalidateOptimalPathsCache();
   }

   private void throwOnIllegalPath(LogicalUnit logicalUnit, Path path) {
      Cost cost = getCostForUnitAndPath(path, logicalUnit);
      if (cost.getMovementCost().isInfinite() || cost.getMovementCost().getInteger() > getMovementRangeForUnit(logicalUnit)) {
         throw new LogicException("Move is illegal for unit!");
      }
      InfiniteInteger fuelCost = cost.getFuelCost();
      if (fuelCost.isInfinite()) {
         throw new LogicException("Path is illegal for unit!");
      }
      if (logicalUnit.getFuel() < fuelCost.getInteger()) {
         throw new LogicException("Unit has too little fuel for this path!");
      }
   }

   private int getMovementRangeForUnit(LogicalUnit logicalUnit) {
      // TODO: CO Modifiers
      return logicalUnit.getBaseMovementRange();
   }

   private void subtractFuelForUnitAndPath(Path path, LogicalUnit logicalUnit) {
      Cost cost = getCostForUnitAndPath(path, logicalUnit);
      logicalUnit.subtractFuel(cost.getFuelCost().getInteger());
   }

   private void checkAttackMove(LogicalUnit movingUnit, Path path, LogicalUnit attackedUnit) {
      throwOnGameNotStarted();
      throwOnUnitNotMovable(movingUnit);
      throwOnIllegalPath(movingUnit, path);
      // TODO: Check if move + attack is allowed
      throwOnIllegalAttack(movingUnit, path.getFinalPosition(), attackedUnit);
   }

   private void throwOnIllegalAttack(LogicalUnit attackingUnit, Position attackPosition, LogicalUnit targetUnit) {
      if (!getUnitsAttackableFromPosition(attackingUnit, attackPosition).contains(targetUnit)) {
         throw new LogicException("Unit is not allowed to attack that target!");
      }
   }

   @Override
   public void executeAttackMove(LogicalUnit movingUnit, Path path, LogicalUnit attackedUnit) {
      checkAttackMove(movingUnit, path, attackedUnit);
      internalExecuteMove(movingUnit, path);
      internalExecuteAttack(movingUnit, attackedUnit);
      _unitModule.expendUnitsTurn(movingUnit);
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
      return _unitModule.unitIsOnMap(defendingUnit) &&
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
      throwOnGameNotStarted();
      throwOnUnitNotMovable(movingUnit);
      throwOnIllegalPath(movingUnit, path);
      internalExecuteMove(movingUnit, path);
      internalExecuteSupply(movingUnit);
      _unitModule.expendUnitsTurn(movingUnit);
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

   @Override
   public void executeUnloadMove(LogicalUnit transport, LogicalUnit unloadingUnit, Path movementPath, Position unloadPosition) {
      throwOnGameNotStarted();
      throwOnUnitNotMovable(transport);
      throwOnIllegalPath(transport, movementPath);
      internalExecuteMove(transport, movementPath);
      _unitModule.unloadUnitFromTransport(transport, unloadingUnit, unloadPosition);
      _unitModule.expendUnitsTurn(transport);
   }

   @Override
   public void executeLoadMove(LogicalUnit movingUnit, Path path) {
      throwOnGameNotStarted();
      throwOnUnitNotMovable(movingUnit);
      throwOnIllegalPath(movingUnit, path);
      LogicalUnit transport = getUnitAtPosition(path.getFinalPosition());
      if (!canLoadOnto(movingUnit, transport)) {
         throw new LogicException("Cannot load onto that unit!");
      }
      internalExecuteLoadMove(movingUnit, path, transport);
      _unitModule.expendUnitsTurn(movingUnit);
   }

   private void internalExecuteLoadMove(LogicalUnit movingUnit, Path movementPath, LogicalUnit transport) {
      subtractFuelForUnitAndPath(movementPath, movingUnit);
      _unitModule.loadUnitOntoTransport(movingUnit, transport);
   }

   @Override
   public boolean canLoadOnto(LogicalUnit loadingUnit, LogicalUnit transporter) {
      return !areEnemies(loadingUnit, transporter) &&
            _transportLogic.canTransport(transporter.getType(), loadingUnit.getType()) &&
            !transportIsFull(transporter);
   }

   @Override
   public boolean canJoinWith(LogicalUnit joiningUnit, LogicalUnit joinedUnit) {
      return !joiningUnit.equals(joinedUnit) &&
            !areEnemies(joiningUnit, joinedUnit) &&
            joiningUnit.getType() == joinedUnit.getType() &&
            joiningUnit.isDamaged() && joinedUnit.isDamaged();
   }

   private boolean transportIsFull(LogicalUnit transporter) {
      return getTransportedUnits(transporter).size() >= _transportLogic.getTransportLimit(transporter.getType());
   }

   @Override
   public List<LogicalUnit> getTransportedUnits(LogicalUnit transporter) {
      return _unitModule.getTransportedUnits(transporter);
   }

   private void subtractDamageForUnit(LogicalUnit damagedUnit, int damageInPercent) {
      int newHp = damagedUnit.getHp1To100() - damageInPercent;
      damagedUnit.setHp1To100(newHp);
      if (damagedUnit.getHp1To10() <= 0) {
         destroyUnit(damagedUnit);
      }
   }

   private void destroyUnit(LogicalUnit destroyedUnit) {
      destroyedUnit.setUnitDestroyed();
      _unitModule.destroyUnit(destroyedUnit);
      fireUnitDestroyed(destroyedUnit);
      for (LogicalUnit transportedUnit : getTransportedUnits(destroyedUnit)) {
         transportedUnit.setUnitDestroyed();
         _unitModule.destroyUnitOnTransport(destroyedUnit, transportedUnit);
         fireTransportedUnitDestroyed(transportedUnit);
      }
      invalidateOptimalPathsCache();
   }

   @Override
   public void executeJoinMove(LogicalUnit movingUnit, Path movementPath) {
      throwOnGameNotStarted();
      throwOnUnitNotMovable(movingUnit);
      // TODO: Check: other unit needs also to be fresh?
      throwOnIllegalPath(movingUnit, movementPath);
      throwOnIllegalJoinMove(movingUnit, movementPath);

      // TODO: Expend fuel for moving unit.
      LogicalUnit joinedUnit = getUnitAtPosition(movementPath.getFinalPosition());
      internalExecuteJoin(movingUnit, joinedUnit);
      fireUnitJoined(movingUnit);
      _unitModule.expendUnitsTurn(joinedUnit);
   }

   private void throwOnIllegalJoinMove(LogicalUnit movingUnit, Path movementPath) {
      LogicalUnit joinedUnit = getUnitAtPosition(movementPath.getFinalPosition());
      if (!canJoin(movingUnit, joinedUnit)) {
         throw new LogicException("Units cannot join!");
      }
   }

   @Override
   public void executeCaptureMove(LogicalUnit movingUnit, Path movementPath) {
      throwOnGameNotStarted();
      throwOnUnitNotMovable(movingUnit);
      throwOnIllegalPath(movingUnit, movementPath);
      throwOnIllegalCapture(movingUnit, movementPath);

      internalExecuteMove(movingUnit, movementPath);
      _unitModule.expendUnitsTurn(movingUnit);
      internalExecuteCapture(movingUnit, movementPath);
   }

   @Override
   public void executeDeployMove(Position position, UnitType unitType) {
      if (!hasBuildingAtPosition(position)) {
         throw new LogicException("Can't deploy unit on tile without appropriate building!");
      }
      if (hasUnitAtPosition(position)) {
         throw new LogicException("Can't deploy unit on occupied tile!");
      }
      Building building = getBuildingAtPosition(position);
      if (!_deployLogic.isTypeDeployableFromBuilding(building.getBuildingType(), unitType)) {
         throw new LogicException("Can't deploy unit on tile without appropriate building!");
      }
      Faction faction = getCurrentlyActiveFaction();
      if (building.getFaction() != faction) {
         throw new LogicException("Can't deploy unit on tile without appropriate building!");
      }
      int cost = _deployLogic.getCostForUnitType(unitType);
      int money = _moneyModule.getMoneyForFaction(faction);
      if (cost > money) {
         throw new LogicException("Tried to subtract " + money + " G from " + faction + ", but they only have " + _moneyModule.getMoneyForFaction(faction) + " G");
      }
      internalDeployUnit(position, unitType, faction);
   }

   private void internalDeployUnit(Position position, UnitType unitType, Faction faction) {
      _moneyModule.subtractMoneyForFaction(faction, _deployLogic.getCostForUnitType(unitType));
      LogicalUnit newUnit = _deployLogic.createLogicalUnit(unitType);
      _unitModule.addUnit(newUnit, position, faction);
      fireUnitDeployed(newUnit, position);
   }

   private void internalExecuteCapture(LogicalUnit movingUnit, Path movementPath) {
      BuildingImpl building = _buildingsModule.getBuildingAtPosition(movementPath.getFinalPosition());
      building.doCapture(movingUnit.getHp1To10(), _unitModule.getFactionForUnit(movingUnit));
      boolean completedCapture = !building.isCapturing();
      if (completedCapture) {
         fireBuildingCaptured(building);
      }
   }

   private void throwOnIllegalCapture(LogicalUnit movingUnit, Path movementPath) {
      if (!movingUnit.canCapture()) {
         throw new LogicException("Unit type " + movingUnit.getType() + " cannot capture property!");
      }
      if (!_buildingsModule.hasBuildingAtPosition(movementPath.getFinalPosition())) {
         throw new LogicException("No building at position " + movementPath.getFinalPosition() + "! Cannot do capture move.");
      }
   }

   private boolean canJoin(LogicalUnit joiningUnit, LogicalUnit joinedUnit) {
      return unitsBelongToSameFaction(joiningUnit, joinedUnit) &&
            unitsAreSameType(joiningUnit, joinedUnit) &&
            joiningUnit.getHp1To10() < 10 &&
            joinedUnit.getHp1To10() < 10;
   }

   private boolean unitsBelongToSameFaction(LogicalUnit unitOne, LogicalUnit unitTwo) {
      return getFactionForUnit(unitOne) == getFactionForUnit(unitTwo);
   }

   private boolean unitsAreSameType(LogicalUnit unitOne, LogicalUnit unitTwo) {
      return unitOne.getType() == unitTwo.getType();
   }

   private void internalExecuteJoin(LogicalUnit movingUnit, LogicalUnit joinedUnit) {
      joinedUnit.setHp1To100(Math.min(movingUnit.getHp1To100() + joinedUnit.getHp1To100(), 100));
      joinedUnit.addFuel(movingUnit.getFuel());
      _unitModule.removeUnit(movingUnit);
   }

   @Override
   public void endTurn() {
      throwOnGameNotStarted();
      _unitModule.expendAllUnitsTurns();
      _turnOrderModule.advanceToNextFactionInTurn();
      doBeginningOfTurn();
   }

   private void doBeginningOfTurn() {
      _logger.info("Current Faction is " + _turnOrderModule.currentlyActiveFaction());
      _unitModule.refreshUnitsInFaction(_turnOrderModule.currentlyActiveFaction());
      invalidateOptimalPathsCache();
      addIncomeFromProperties(_turnOrderModule.currentlyActiveFaction());

      handleHealAndFuelEventsAtTurnStart();
   }

   private void addIncomeFromProperties(Faction faction) {
      _moneyModule.addMoneyForFaction(faction, INCOME_PER_PROPERTY * _buildingsModule.getBuildingsForFaction(faction).size());
   }

   private void handleHealAndFuelEventsAtTurnStart() {
      Faction faction = getCurrentlyActiveFaction();
      Set<LogicalUnit> unitsThatWillBeResupplied = new HashSet<>();

      // Check resupply by APC
      Set<LogicalUnit> apcsInActiveFaction = _unitModule.getAllUnitsFromFactionOfType(faction, UnitType.APC);
      for (LogicalUnit apc : apcsInActiveFaction) {
         Position supplierPosition = getPositionOfUnit(apc);
         unitsThatWillBeResupplied.addAll(getUnitsSuppliableFromPosition(apc, supplierPosition));
      }

      // Repair and resupply units on appropriate friendly buildings
      for (LogicalUnit unit : _unitModule.getAllUnitsFromFaction(faction)) {
         if (unitIsOnResupplyingBuilding(unit)) {
            unitsThatWillBeResupplied.add(unit);
            doHeal(unit);
         }
      }

      // Subtract per-day fuel consumptions, except for units that will be resupplied
      // Check for crashing aircraft or ships
      subtractPerDayFuelConsumption(unitsThatWillBeResupplied);

      // Perform resupply
      for (LogicalUnit resuppliedUnit : unitsThatWillBeResupplied) {
         resuppliedUnit.resupply();
      }
   }

   private void doHeal(LogicalUnit unit) {
      Faction faction = _unitModule.getFactionForUnit(unit);
      int wantHealingBy = Math.min(2, 10 - unit.getHp1To10());
      if (wantHealingBy == 0) {
         return;
      }
      int moneyFactionCanAfford = Math.min(_moneyModule.getMoneyForFaction(faction), wantHealingBy*100);
      int amountToHealInPercent = moneyFactionCanAfford / 10;
      unit.healHpPercent(amountToHealInPercent);
      _moneyModule.subtractMoneyForFaction(faction, moneyFactionCanAfford);
   }

   private void subtractPerDayFuelConsumption(Set<LogicalUnit> excusedUnits) {
      Set<LogicalUnit> units = getAllUnitsInActiveFaction();
      units.removeAll(excusedUnits);
      for (LogicalUnit unit : units) {
         int costPerTurn = _fuelLogic.getFuelCostPerTurn(unit);
         unit.subtractFuel(costPerTurn);
         if (unit.getFuel() <= 0 && _fuelLogic.unitTypeIsDestroyedWhenOutOfFuel(unit.getType())) {
            destroyUnit(unit);
         }
         _logger.fine("Unit " + unit + " consumed " + costPerTurn + " fuel at start of turn.");
      }
   }

   private boolean unitIsOnResupplyingBuilding(LogicalUnit unit) {
      if (_unitModule.unitIsBeingTransported(unit)) {
         return false;
      }
      Position unitPosition = getPositionOfUnit(unit);
      if (!_buildingsModule.hasBuildingAtPosition(unitPosition)) {
         return false;
      }
      Building building = _buildingsModule.getBuildingAtPosition(unitPosition);
      return building.getFaction() == _unitModule.getFactionForUnit(unit) &&
            _fuelLogic.buildingCanResupplyUnit(building.getBuildingType(), unit.getType());
   }

   public LogicalWarMap getLogicalWarMap() {
      return _logicalWarMap;
   }

   @Override
   public boolean isPositionInsideMap(Position position) {
      return _logicalWarMap.isPositionInsideMap(position);
   }

   @Override
   public Position getHqPosition(Faction faction) {
      return _buildingsModule.getHqPosition(faction);
   }

   @Override
   public int getMoneyForFaction(Faction faction) {
      return _moneyModule.getMoneyForFaction(faction);
   }

   @Override
   public List<Faction> getFactionsInGame() {
      return _turnOrderModule.getFactionsInTurnOrder();
   }

   @Override
   public Faction getCurrentlyActiveFaction() {
      return _turnOrderModule.currentlyActiveFaction();
   }

   public Collection<LogicalUnit> getAllUnits() {
      return _unitModule.getAllUnits();
   }

   @Override
   public LogicalUnit getUnitAtPosition(Position position) {
      if (!_unitModule.hasUnitAtPosition(position)) {
         throw new UnitNotFoundException();
      }
      return _unitModule.getUnitAtPosition(position);
   }

   @Override
   public Position getPositionOfUnit(LogicalUnit logicalUnit) {
      if (!_unitModule.unitIsOnMap(logicalUnit)) {
         throw new UnitNotFoundException();
      }
      return _unitModule.getPositionOfUnit(logicalUnit);
   }

   @Override
   public boolean isUnitDestroyed(LogicalUnit logicalUnit) {
      return _unitModule.isUnitDestroyed(logicalUnit);
   }

   @Override
   public Faction getFactionForUnit(LogicalUnit logicalUnit) {
      return _unitModule.getFactionForUnit(logicalUnit);
   }

   @Override
   public boolean unitCanStillMoveThisTurn(LogicalUnit logicalUnit) {
      return _unitModule.unitMayMoveThisTurn(logicalUnit);
   }

   @Override
   public boolean unitBelongsToCurrentlyActiveFaction(LogicalUnit unit) {
      return _unitModule.getFactionForUnit(unit) == _turnOrderModule.currentlyActiveFaction();
   }

   @Override
   public boolean hasUnitAtPosition(Position position) {
      return _unitModule.hasUnitAtPosition(position);
   }

   private static class NoSuppliableUnitsInRangeException extends LogicException {
      public NoSuppliableUnitsInRangeException(String s) {
         super(s);
      }
   }

   @Override
   public Set<LogicalUnit> getAllUnitsInActiveFaction() {
      return _unitModule.getAllUnitsFromFaction(_turnOrderModule.currentlyActiveFaction());
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

   @Override
   public boolean hasActiveUnitAtPosition(Position position) {
      return hasUnitAtPosition(position) && !unitHasMovedThisTurn(getUnitAtPosition(position));
   }

   @Override
   public Set<Position> getAdjacentVacantPositionsAfterMove(LogicalUnit movingUnit, Path path) {
      Position destination = path.getFinalPosition();
      Collection<Position> adjacentPositions = getAdjacentPositions(destination);
      Set<Position> adjacentVacantPositions = new HashSet<>();
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

   @Override
   public Set<LogicalUnit> getAttackableUnitsAfterMove(LogicalUnit attackingUnit, Path path) {
      Position destination = path.getFinalPosition();
      Set<LogicalUnit> attackableUnits;
      if (attackingUnit.isRanged() && !path.isEmpty()) {
         attackableUnits = new HashSet<>(0);
      } else {
         attackableUnits = getUnitsAttackableFromPosition(attackingUnit, destination);
      }
      return attackableUnits;
   }

   @Override
   public boolean areEnemies(LogicalUnit oneUnit, LogicalUnit anotherUnit) {
      return _unitModule.areEnemies(oneUnit, anotherUnit);
   }

   @Override
   public boolean areEnemies(Faction oneFaction, Faction anotherFaction) {
      return _unitModule.areEnemies(oneFaction, anotherFaction);
   }

   @Override
   public boolean areEnemies(LogicalUnit unit, Faction faction) {
      return _unitModule.areEnemies(unit, faction);
   }

   @Override
   public Set<LogicalUnit> getSuppliableUnitsAfterMove(LogicalUnit supplier, Path path) {
      Position destination = path.getFinalPosition();
      Set<LogicalUnit> suppliableUnits;
      suppliableUnits = getUnitsSuppliableFromPosition(supplier, destination);
      return suppliableUnits;
   }

   @Override
   public boolean hasEnemyBuildingAtPosition(LogicalUnit movingUnit, Position position) {
      if (_buildingsModule.hasBuildingAtPosition(position)) {
         Building building = _buildingsModule.getBuildingAtPosition(position);
         return _unitModule.areEnemies(movingUnit, building.getFaction());
      } else {
         return false;
      }
   }

   @Override
   public boolean hasFriendlyUnoccupiedBaseAtPosition(Faction faction, Position position) {
      if (hasUnitAtPosition(position)) {
         return false;
      }
      if (_buildingsModule.hasBuildingAtPosition(position)) {
         Building building = _buildingsModule.getBuildingAtPosition(position);
         return building.getBuildingType().isBase() && building.getFaction() == faction;
      }
      return false;
   }

   private Map<Position, PathWithCost> getAndCacheOptimalPathsToAllReachablePoints(LogicalUnit travellingUnit) {
      if (!travellingUnit.equals(_unitForWhichOptimalPathsAreCached)) {
         _cachedOptimalPathsForTravellingUnit = getOptimalPathsToAllReachablePoints(travellingUnit);
         _unitForWhichOptimalPathsAreCached = travellingUnit;
      }
      return _cachedOptimalPathsForTravellingUnit;
   }

   private Map<Position, PathWithCost> getOptimalPathsToAllReachablePoints(LogicalUnit travellingUnit) {
      PathFinder pathFinder = new PathFinder(travellingUnit, this, CostCalculator.createForUnitAndGame(travellingUnit, this));
      return pathFinder.getOptimalPathsToAllReachablePoints(getCostLimitForUnit(travellingUnit));
   }

   private CostCalculator createCostCalculatorForUnit(final LogicalUnit logicalUnit) {
      return new CostCalculator() {
         @Override
         public Cost getCostForPosition(Position position) {
            InfiniteInteger movementCost = getTravelCostForUnitOnTile(logicalUnit, position);
            InfiniteInteger fuelCost = getFuelCostForUnitOnTile(logicalUnit, position);
            return new Cost(movementCost, fuelCost);
         }
      };
   }

   private Cost getCostLimitForUnit(LogicalUnit logicalUnit) {
      InfiniteInteger movementLimit = InfiniteInteger.create(logicalUnit.getBaseMovementRange());
      InfiniteInteger fuelLimit = InfiniteInteger.create(logicalUnit.getFuel());
      return new Cost(movementLimit, fuelLimit);
   }

   @Override
   public boolean unitHasMovedThisTurn(LogicalUnit unit) {
      return unitBelongsToCurrentlyActiveFaction(unit) && !unitCanStillMoveThisTurn(unit);
   }

   private Set<LogicalUnit> getUnitsAttackableFromPosition(LogicalUnit attacker, Position attackingPosition) {
      if (attacker.isRanged()) {
         return getUnitsRemotelyAttackableFromPosition(attacker, attackingPosition);
      } else if (attacker.isMelee()) {
         return getUnitsDirectlyAttackableFromPosition(attacker, attackingPosition);
      } else {
         return new HashSet<>(0);
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

}
