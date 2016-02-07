package se.lolektivet.linus.linuswars;

import se.lolektivet.linus.linuswars.logic.Position;
import se.lolektivet.linus.linuswars.logic.game.LogicalUnit;
import se.lolektivet.linus.linuswars.logic.game.WarGameQueries;
import se.lolektivet.linus.linuswars.logic.pathfinding.Path;

import java.util.Set;

/**
 * Created by Linus on 2015-11-30.
 */
class MoveAnalyzer {
   private final WarGameQueries _warGameQueries;
   private final LogicalUnit _logicalUnit;
   private final Path _thePath;

   private boolean _hasAnalyzed;

   // NOTE: This class assumes the supplied path is allowed for the unit! I.e., no transport/fuel logic is considered.

   MoveAnalyzer(WarGameQueries warGameQueries, LogicalUnit logicalUnit, Path thePath) {
      _warGameQueries = warGameQueries;
      _logicalUnit = logicalUnit;
      _thePath = thePath;
      _hasAnalyzed = false;
   }

   // Note: This is "reserved" for future optimizations.
   void analyze() {
      if (_hasAnalyzed) {
         return;
      }
      _hasAnalyzed = true;
   }

   private void throwIfHasntAnalyzed() {
      if (!_hasAnalyzed) {
         throw new IllegalStateException("Must call analyze before querying!");
      }
   }

   boolean canDoSomeMove() {
      throwIfHasntAnalyzed();
      // Note: All other moves are implied by "wait".
      return canDoWait() ||
            canDoJoin() ||
            canDoLoad();
   }

   boolean canDoWait() {
      throwIfHasntAnalyzed();
      return unitIsMovable() &&
            (destinationIsVacant() ||
                  selfSelected());
   }

   boolean canDoLoad() {
      throwIfHasntAnalyzed();
      return unitIsMovable() &&
            (!destinationIsVacant() &&
                  canLoadOntoUnitAtDestination());
   }

   public boolean canDoUnload() {
      throwIfHasntAnalyzed();
      return canDoWait() && isTransportingUnits() && canUnload();
   }

   boolean canDoJoin() {
      throwIfHasntAnalyzed();
      return unitIsMovable() &&
            (!destinationIsVacant() &&
                  canJoinWithUnitAtDestination());
   }

   boolean canDoCapture() {
      throwIfHasntAnalyzed();
      return canDoWait() && canCaptureAtDestination();
   }

   boolean canDoAttack() {
      throwIfHasntAnalyzed();
      return canDoWait() && !getAttackableUnits().isEmpty();
   }

   boolean canDoSupply() {
      throwIfHasntAnalyzed();
      return canDoWait() && !getSuppliableUnits().isEmpty();
   }

   Set<Position> getAdjacentVacantPositions() {
      throwIfHasntAnalyzed();
      return _warGameQueries.getAdjacentVacantPositionsAfterMove(_logicalUnit, _thePath);
   }

   Set<LogicalUnit> getAttackableUnits() {
      throwIfHasntAnalyzed();
      return _warGameQueries.getAttackableUnitsAfterMove(_logicalUnit, _thePath);
   }

   private boolean unitIsMovable() {
      return _warGameQueries.unitBelongsToCurrentlyActiveFaction(_logicalUnit);
   }

   private boolean destinationIsVacant() {
      return !_warGameQueries.hasUnitAtPosition(_thePath.getFinalPosition());
   }

   private boolean selfSelected() {
      return _thePath.isEmpty();
   }

   private boolean canLoadOntoUnitAtDestination() {
      return _warGameQueries.canLoadOnto(_logicalUnit, _warGameQueries.getUnitAtPosition(_thePath.getFinalPosition()));
   }

   private boolean canJoinWithUnitAtDestination() {
      return _warGameQueries.canJoinWith(_logicalUnit, _warGameQueries.getUnitAtPosition(_thePath.getFinalPosition()));
   }

   private boolean isTransportingUnits() {
      return !_warGameQueries.getTransportedUnits(_logicalUnit).isEmpty();
   }

   private boolean canUnload() {
      return !getAdjacentVacantPositions().isEmpty();
   }

   private Set<LogicalUnit> getSuppliableUnits() {
      return _warGameQueries.getSuppliableUnitsAfterMove(_logicalUnit, _thePath);
   }

   private boolean canCaptureAtDestination() {
      return _logicalUnit.canCapture() &&
            _warGameQueries.hasEnemyBuildingAtPosition(_logicalUnit, _thePath.getFinalPosition());
   }
}
