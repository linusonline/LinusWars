package se.lolektivet.linus.linuswars.core.game;

import se.lolektivet.linus.linuswars.core.LogicException;
import se.lolektivet.linus.linuswars.core.Position;
import se.lolektivet.linus.linuswars.core.enums.Faction;
import se.lolektivet.linus.linuswars.core.enums.UnitType;

import java.util.*;

/**
 * Created by Linus on 2015-11-26.
 */
public class ModuleUnits {
   private final Map<LogicalUnit, Position> _positionsOfUnits;
   private final Map<Position, LogicalUnit> _unitsAtPositions;
   private final Map<LogicalUnit, Faction> _factionsOfUnits;
   private final Map<Faction, Collection<LogicalUnit>> _unitsInFaction;
   private final Map<LogicalUnit, List<LogicalUnit>> _transportedUnits;
   private final Map<Faction, Collection<LogicalUnit>> _transportedUnitsInFaction;
   private final Collection<LogicalUnit> _unitsLeftToMoveThisTurn;
   private final Map<Faction, Collection<LogicalUnit>> _destroyedUnitsByFaction;
   private final Collection<LogicalUnit> _destroyedUnits;

   public ModuleUnits(List<Faction> factionsInTurnOrder) {
      _positionsOfUnits = new HashMap<>();
      _unitsAtPositions = new HashMap<>();
      _transportedUnits = new HashMap<>(0);
      _factionsOfUnits = new HashMap<>();
      _unitsInFaction = new HashMap<>();
      _transportedUnitsInFaction = new HashMap<>(0);
      _unitsLeftToMoveThisTurn = new HashSet<>();
      _destroyedUnitsByFaction = new HashMap<>();
      _destroyedUnits = new HashSet<>();

      for (Faction faction : factionsInTurnOrder) {
         _unitsInFaction.put(faction, new HashSet<>());
         _transportedUnitsInFaction.put(faction, new HashSet<>(0));
         _destroyedUnitsByFaction.put(faction, new HashSet<>());
      }
   }

   Collection<LogicalUnit> getAllUnits() {
      return new HashSet<>(_factionsOfUnits.keySet());
   }

   void addUnit(LogicalUnit unit, Position position, Faction faction) {
      _unitsAtPositions.put(position, unit);
      _positionsOfUnits.put(unit, position);

      _unitsInFaction.get(faction).add(unit);
      _factionsOfUnits.put(unit, faction);
   }

   void destroyUnit(LogicalUnit unit) {
      Faction faction = getFactionForUnit(unit);
      removeUnit(unit);
      addDestroyedUnit(unit, faction);
   }

   void removeUnit(LogicalUnit unit) {
      Position position = _positionsOfUnits.get(unit);
      _unitsAtPositions.remove(position);
      _positionsOfUnits.remove(unit);

      Faction faction = _factionsOfUnits.get(unit);
      _factionsOfUnits.remove(unit);
      _unitsInFaction.get(faction).remove(unit);
   }

   boolean unitIsOnMap(LogicalUnit logicalUnit) {
      return _positionsOfUnits.containsKey(logicalUnit);
   }

   Position getPositionOfUnit(LogicalUnit logicalUnit) {
      return _positionsOfUnits.get(logicalUnit);
   }

   boolean hasUnitAtPosition(Position position) {
      return _unitsAtPositions.containsKey(position);
   }

   LogicalUnit getUnitAtPosition(Position position) {
      return _unitsAtPositions.get(position);
   }

   Faction getFactionForUnit(LogicalUnit logicalUnit) {
      return _factionsOfUnits.get(logicalUnit);
   }

   void moveUnit(LogicalUnit unit, Position newPosition) {
      Position oldPosition = _positionsOfUnits.get(unit);
      _unitsAtPositions.remove(oldPosition);
      _unitsAtPositions.put(newPosition, unit);
      _positionsOfUnits.put(unit, newPosition);
   }

   List<LogicalUnit> getTransportedUnits(LogicalUnit transporter) {
      List<LogicalUnit> unitsOnTransport = _transportedUnits.get(transporter);
      if (unitsOnTransport == null) {
         return new ArrayList<>(0);
      } else {
         return new ArrayList<>(unitsOnTransport);
      }
   }

   void loadUnitOntoTransport(LogicalUnit movingUnit, LogicalUnit transport) {
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

   void unloadUnitFromTransport(LogicalUnit transport, LogicalUnit unloadingUnit, Position unloadPosition) {
      removeUnitFromTransport(transport, unloadingUnit);

      _unitsAtPositions.put(unloadPosition, unloadingUnit);
      _positionsOfUnits.put(unloadingUnit, unloadPosition);
   }

   void destroyUnitOnTransport(LogicalUnit transport, LogicalUnit transportedUnit) {
      Faction faction = getFactionForUnit(transport);
      removeUnitFromTransport(transport, transportedUnit);
      addDestroyedUnit(transportedUnit, faction);
   }

   void removeUnitFromTransport(LogicalUnit transport, LogicalUnit transportedUnit) {
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

   Set<LogicalUnit> getAllUnitsFromFaction(Faction faction) {
      return new HashSet<>(_unitsInFaction.get(faction));
   }

   Set<LogicalUnit> getAllUnitsFromFactionOfType(Faction faction, UnitType type) {
      Set<LogicalUnit> unitsFromFactionOfType = new HashSet<>();
      for (LogicalUnit logicalUnit : _unitsInFaction.get(faction)) {
         if (logicalUnit.getType().equals(type)) {
            unitsFromFactionOfType.add(logicalUnit);
         }
      }
      return unitsFromFactionOfType;
   }

   boolean unitMayMoveThisTurn(LogicalUnit logicalUnit) {
      return _unitsLeftToMoveThisTurn.contains(logicalUnit);
   }

   void expendUnitsTurn(LogicalUnit logicalUnit) {
      _unitsLeftToMoveThisTurn.remove(logicalUnit);
   }

   void expendAllUnitsTurns() {
      _unitsLeftToMoveThisTurn.clear();
   }

   void refreshUnitsInFaction(Faction faction) {
      Collection<LogicalUnit> nonTransportedUnitsInFaction = new HashSet<>(_unitsInFaction.get(faction));
      nonTransportedUnitsInFaction.removeAll(_transportedUnitsInFaction.get(faction));
      _unitsLeftToMoveThisTurn.addAll(nonTransportedUnitsInFaction);
   }

   void addDestroyedUnit(LogicalUnit unit, Faction faction) {
      _destroyedUnitsByFaction.get(faction).add(unit);
      _destroyedUnits.add(unit);
   }

   Collection<LogicalUnit> getDestroyedUnitsInFaction(Faction faction) {
      return new HashSet<>(_destroyedUnitsByFaction.get(faction));
   }

   boolean isUnitDestroyed(LogicalUnit unit) {
      return _destroyedUnits.contains(unit);
   }

   boolean areEnemies(LogicalUnit oneUnit, LogicalUnit anotherUnit) {
      return areEnemies(getFactionForUnit(oneUnit), getFactionForUnit(anotherUnit));
   }

   boolean areEnemies(LogicalUnit unit, Faction faction) {
      return areEnemies(getFactionForUnit(unit), faction);
   }

   boolean areEnemies(Faction oneFaction, Faction anotherFaction) {
      return oneFaction != anotherFaction;
   }
}
